// Copyright 2016, the Dart project authors.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package dart._runtime.types.simple;

/**
 * Representation of a Dart interface type.
 */
public class InterfaceType extends Type {
  private static final InterfaceType[] EMPTY_INTERFACE_TYPE_ARRAY = null;

  /**
   * The "raw class" for this interface type; represents a declaration like
   * {@code class Foo<T> extends Bar<T,int>} from Dart source code.
   */
  final InterfaceTypeInfo info;

  /**
   * The reified superclass of this class.
   * <p>
   * Must be set after calling the constructor (unless this class is {@code Object}, in which case
   * {@code supertype} may be left as {@code null}).
   */
  InterfaceType supertype;

  /**
   * The reified mixin to this class.
   * <p>
   * May be set after calling the constructor or may be left as null if this class does not have a
   * (direct) mixin.
   */
  InterfaceType mixin;

  /**
   * The reified interfaces of this class.
   * <p>
   * May be set after calling the constructor or may be left as the empty array if this class does
   * not have any (direct) superinterfaces. May not be set to null.
   */
  InterfaceType[] interfaces;

  /**
   * The actual type parameters of this reified type.
   */
  final Type[] actualTypeParams;

  /**
   * The type environment for instances of this type.
   * <p>
   * This {@link TypeEnvironment} can resolve the type variables for this class, as well as those of
   * its superclass and mixin (if any).
   */
  public TypeEnvironment env;

  /**
   * The Java class to use for references to this Type.
   * <p>
   * This is generally a Java interface type, but for {@code @JavaClass}es, it may be a class type.
   * <p>
   * For primitive types, this returns the unboxed primitive type ({@code int}, {@code double}, or
   * {@code boolean}).
   */
  Class<?> getJavaType() {
    return info.javaInterface == null ? info.javaClass : info.javaInterface;
  }

  /**
   * Allocates a new {@code InterfaceType} instance.
   * <p>
   * <b>You must set {@link #supertype}, {@link #mixin}, and {@link #interfaces} as appropriate,
   * then call {@link #finishInitialization} after calling this constructor!</b>
   */
  InterfaceType(InterfaceTypeInfo info, Type[] actualTypeParams) {
    this.info = info;
    this.interfaces = EMPTY_INTERFACE_TYPE_ARRAY;
    this.actualTypeParams = actualTypeParams;

  }

  /**
   * This method is logically part of the constructor; every call to the constructor should be
   * followed closely by a call to {@code finishInitialization}, right after setting the supertype
   * fields (i.e. {@code superclass}, {@code mixin}, and {@code interfaces}.
   *
   * This is necessary because the supertype fields can indirectly refer cyclically to this class,
   * so we cannot always initialize them in the constructor.
   */
  void finishInitialization() {
    TypeEnvironment superEnv = supertype != null ? supertype.env : TypeEnvironment.ROOT;
    if (this.mixin != null) {
      superEnv = superEnv.extend(this.mixin.info.typeVariables, this.mixin.actualTypeParams);
    }
    this.env = superEnv.extend(this.info.typeVariables, this.actualTypeParams);
  }

  @Override
  protected boolean isSubtypeOfInterfaceType(InterfaceType other) {
    if (this.info == other.info) {
      // Same base class; check type parameters
      for (int i = 0; i < info.typeVariables.length; i++) {
        if (!this.actualTypeParams[i].isSubtypeOf(other.actualTypeParams[i])) {
          return false;
        }
      }
      return true;
    } else {
      // Check this type's supertypes.
      if (supertype != null && supertype.isSubtypeOfInterfaceType(other)) {
        return true;
      }
      if (mixin != null && mixin.isSubtypeOfInterfaceType(other)) {
        return true;
      }
      for (InterfaceType interface_ : interfaces) {
        if (interface_.isSubtypeOfInterfaceType(other)) {
          return true;
        }
      }
      return false;
    }
  }

  @Override
  protected boolean isSubtypeOfFunctionType(FunctionType other) {
    // TODO(andrewkrieger): Implement special handling of call method.
    return false;
  }

}
