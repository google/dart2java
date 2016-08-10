// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// A library to help transform compounds and null-aware accessors into
/// let expressions.
library kernel.frontend.accessors;

import '../ast.dart';
import '../core_types.dart';

abstract class Accessor {
  Expression buildSimpleRead() {
    return _finish(_makeSimpleRead());
  }

  /// Returns an assignment to the accessor.
  ///
  /// The returned expression evaluates to the assigned value, unless
  /// [voidContext] is true, in which case it may evaluate to anything.
  Expression buildAssignment(Expression value, {bool voidContext: false}) {
    return _finish(_makeSimpleWrite(value, voidContext));
  }

  Expression buildNullAwareAssignment(DartType staticType, Expression value,
      {bool voidContext: false, CoreTypes coreTypes}) {
    if (voidContext) {
      return _finish(new ConditionalExpression(
          staticType,
          buildIsNull(_makeRead(), coreTypes: coreTypes),
          _makeWrite(value, voidContext),
          new NullLiteral()));
    }
    var tmp = new VariableDeclaration.forValue(_makeRead());
    return _finish(makeLet(
        tmp,
        new ConditionalExpression(
            staticType,
            buildIsNull(new VariableGet(tmp), coreTypes: coreTypes),
            _makeWrite(value, voidContext),
            new VariableGet(tmp))));
  }

  Expression buildCompoundAssignment(
      DartType staticType, Name binaryOperator, Expression value,
      {bool voidContext: false}) {
    return _finish(_makeWrite(
        makeBinary(staticType, _makeRead(), binaryOperator, value),
        voidContext));
  }

  Expression buildPrefixIncrement(DartType staticType, Name binaryOperator,
      {bool voidContext: false}) {
    return buildCompoundAssignment(
        staticType, binaryOperator, new IntLiteral(1),
        voidContext: voidContext);
  }

  Expression buildPostfixIncrement(DartType staticType, Name binaryOperator,
      {bool voidContext: false}) {
    if (voidContext) {
      return buildPrefixIncrement(staticType, binaryOperator,
          voidContext: true);
    }
    var value = new VariableDeclaration.forValue(_makeRead());
    valueAccess() => new VariableGet(value);
    var dummy = new VariableDeclaration.forValue(_makeWrite(
        makeBinary(
            staticType, valueAccess(), binaryOperator, new IntLiteral(1)),
        true));
    return _finish(makeLet(value, makeLet(dummy, valueAccess())));
  }

  Expression _makeSimpleRead() => _makeRead();

  Expression _makeSimpleWrite(Expression value, bool voidContext) {
    return _makeWrite(value, voidContext);
  }

  Expression _makeRead();

  Expression _makeWrite(Expression value, bool voidContext);

  Expression _finish(Expression body) => body;

  makeInvalidRead() => new InvalidExpression();

  makeInvalidWrite(Expression value) => wrapInvalid(value);
}

class VariableAccessor extends Accessor {
  VariableDeclaration variable;

  VariableAccessor(this.variable);

  _makeRead() => new VariableGet(variable);

  _makeWrite(Expression value, bool voidContext) {
    return variable.isFinal || variable.isConst
        ? makeInvalidWrite(value)
        : new VariableSet(variable, value);
  }
}

class PropertyAccessor extends Accessor {
  VariableDeclaration _receiverVariable;
  Expression receiver;
  Name name;
  DartType readStaticType;

  static Accessor make(DartType readStaticType,
      Expression receiver, Name name) {
    return new PropertyAccessor._internal(readStaticType, receiver, name);
  }

  PropertyAccessor._internal(this.readStaticType, this.receiver, this.name);

  _makeSimpleRead() => new PropertyGet(readStaticType, receiver, name);

  _makeSimpleWrite(Expression value, bool voidContext) {
    return new PropertySet(receiver, name, value);
  }

  receiverAccess() {
    _receiverVariable ??= new VariableDeclaration.forValue(receiver);
    return new VariableGet(_receiverVariable);
  }

  _makeRead() => new PropertyGet(readStaticType, receiverAccess(), name);

  _makeWrite(Expression value, bool voidContext) {
    return new PropertySet(receiverAccess(), name, value);
  }

  _finish(Expression body) => makeLet(_receiverVariable, body);
}

class NullAwarePropertyAccessor extends Accessor {
  VariableDeclaration receiver;
  Name name;
  DartType readStaticType;
  CoreTypes coreTypes;

  NullAwarePropertyAccessor(this.readStaticType, Expression receiver, this.name,
      {this.coreTypes})
      : this.receiver = makeOrReuseVariable(receiver);

  receiverAccess() => new VariableGet(receiver);

  _makeRead() => new PropertyGet(readStaticType, receiverAccess(), name);

  _makeWrite(Expression value, bool voidContext) {
    return new PropertySet(receiverAccess(), name, value);
  }

  _finish(Expression body) => makeLet(
      receiver,
      new ConditionalExpression(
          body.staticType,
          buildIsNull(receiverAccess(), coreTypes: coreTypes),
          new NullLiteral(),
          body));
}

class SuperPropertyAccessor extends Accessor {
  Member readTarget;
  Member writeTarget;
  DartType readStaticType;

  SuperPropertyAccessor(this.readStaticType, this.readTarget, this.writeTarget);

  _makeRead() => readTarget == null
      ? makeInvalidRead()
      : new SuperPropertyGet(readStaticType, readTarget);

  _makeWrite(Expression value, bool voidContext) {
    return writeTarget == null
        ? makeInvalidWrite(value)
        : new SuperPropertySet(writeTarget, value);
  }
}

final Name _indexGet = new Name('[]');
final Name _indexSet = new Name('[]=');

class IndexAccessor extends Accessor {
  Expression receiver;
  Expression index;
  VariableDeclaration receiverVariable;
  VariableDeclaration indexVariable;
  DartType readStaticType;

  static Accessor make(DartType readStaticType, Expression receiver, Expression index) {
    return new IndexAccessor._internal(readStaticType, receiver, index);
  }

  IndexAccessor._internal(this.readStaticType, this.receiver, this.index);

  _makeSimpleRead() => new MethodInvocation(
      readStaticType, receiver, _indexGet, new Arguments(<Expression>[index]));

  _makeSimpleWrite(Expression value, bool voidContext) {
    if (!voidContext) return _makeWriteAndReturn(value);
    return new MethodInvocation(const VoidType(), receiver, _indexSet,
        new Arguments(<Expression>[index, value]));
  }

  receiverAccess() {
    // We cannot reuse the receiver if it is a variable since it might be
    // reassigned in the index expression.
    receiverVariable ??= new VariableDeclaration.forValue(receiver);
    return new VariableGet(receiverVariable);
  }

  indexAccess() {
    indexVariable ??= new VariableDeclaration.forValue(index);
    return new VariableGet(indexVariable);
  }

  _makeRead() {
    return new MethodInvocation(readStaticType, receiverAccess(), _indexGet,
        new Arguments(<Expression>[indexAccess()]));
  }

  _makeWrite(Expression value, bool voidContext) {
    if (!voidContext) return _makeWriteAndReturn(value);
    return new MethodInvocation(const VoidType(), receiverAccess(), _indexSet,
        new Arguments(<Expression>[indexAccess(), value]));
  }

  _makeWriteAndReturn(Expression value) {
    // The call to []= does not return the value like direct-style assignments
    // do.  We need to bind the value in a let.
    var valueVariable = new VariableDeclaration.forValue(value);
    var dummy = new VariableDeclaration.forValue(new MethodInvocation(
        const DynamicType(),
        receiverAccess(),
        _indexSet,
        new Arguments(
            <Expression>[indexAccess(), new VariableGet(valueVariable)])));
    return makeLet(
        valueVariable, makeLet(dummy, new VariableGet(valueVariable)));
  }

  Expression _finish(Expression body) {
    return makeLet(receiverVariable, makeLet(indexVariable, body));
  }
}

class SuperIndexAccessor extends Accessor {
  Expression index;
  Member readTarget;
  Member writeTarget;
  VariableDeclaration indexVariable;
  DartType readStaticType;

  SuperIndexAccessor(this.readStaticType, this.index, this.readTarget, this.writeTarget);

  indexAccess() {
    indexVariable ??= new VariableDeclaration.forValue(index);
    return new VariableGet(indexVariable);
  }

  _makeSimpleRead() => readTarget == null
      ? makeInvalidRead()
      : new SuperMethodInvocation(
          readStaticType, readTarget, new Arguments(<Expression>[index]));

  _makeSimpleWrite(Expression value, bool voidContext) {
    return writeTarget == null
        ? makeInvalidWrite(value)
        : new SuperMethodInvocation(const VoidType(), writeTarget,
            new Arguments(<Expression>[index, value]));
  }

  _makeRead() {
    return readTarget == null
        ? makeInvalidRead()
        : new SuperMethodInvocation(readStaticType, readTarget,
            new Arguments(<Expression>[indexAccess()]));
  }

  _makeWrite(Expression value, bool voidContext) {
    return writeTarget == null
        ? makeInvalidWrite(value)
        : new SuperMethodInvocation(
            voidContext ? const VoidType() : const DynamicType(),
            writeTarget,
            new Arguments(<Expression>[indexAccess(), value]));
  }

  Expression _finish(Expression body) {
    return makeLet(indexVariable, body);
  }
}

class StaticAccessor extends Accessor {
  Member readTarget;
  Member writeTarget;
  DartType readStaticType;

  StaticAccessor(this.readStaticType, this.readTarget, this.writeTarget);

  _makeRead() => readTarget == null
      ? makeInvalidRead()
      : new StaticGet(readStaticType, readTarget);

  _makeWrite(Expression value, bool voidContext) {
    return writeTarget == null
        ? makeInvalidWrite(value)
        : new StaticSet(writeTarget, value);
  }
}

class ReadOnlyAccessor extends Accessor {
  Expression expression;
  VariableDeclaration value;

  ReadOnlyAccessor(this.expression);

  _makeSimpleRead() => expression;

  _makeRead() {
    value ??= new VariableDeclaration.forValue(expression);
    return new VariableGet(value);
  }

  _makeWrite(Expression value, bool voidContext) => makeInvalidWrite(value);

  Expression _finish(Expression body) => makeLet(value, body);
}

Expression makeLet(VariableDeclaration variable, Expression body) {
  if (variable == null) return body;
  return new Let(variable, body);
}

Expression makeBinary(
    DartType staticType, Expression left, Name operator, Expression right) {
  return new MethodInvocation(
      staticType, left, operator, new Arguments(<Expression>[right]));
}

final Name _equalOperator = new Name('==');

Expression buildIsNull(Expression value, {CoreTypes coreTypes}) {
  return makeBinary(
      new InterfaceType((coreTypes ?? CoreTypes.instance).boolClass),
      value,
      _equalOperator,
      new NullLiteral());
}

VariableDeclaration makeOrReuseVariable(Expression value) {
  // TODO: Devise a way to remember if a variable declaration was reused
  // or is fresh (hence needs a let binding).
  return new VariableDeclaration.forValue(value);
}

Expression wrapInvalid(Expression e) {
  return new Let(new VariableDeclaration.forValue(e), new InvalidExpression());
}
