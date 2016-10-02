package generics;

public class __TopLevel
{
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Foo1$ltint$0$gt = new dart._runtime.types.simple.InterfaceTypeExpr(generics.Foo1.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_int$0 = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Bar2$ltint$0$double$0$gt = new dart._runtime.types.simple.InterfaceTypeExpr(generics.Bar2.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo), new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_double$0 = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Bar2$ltint$0$Object$gt = new dart._runtime.types.simple.InterfaceTypeExpr(generics.Bar2.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo), new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
  
  
  
    public static void main(String[] args)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      dart.core.__TopLevel.print("Expecting 12, 11, 13, 14.01, 2.3, 50, 51, 50, 51");
      generics.Foo1_interface<java.lang.Integer> fooFromConstructor = ((generics.Foo1_interface) generics.Foo1._new_Foo1$newMe(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Foo1$ltint$0$gt)));
      generics.Foo1_interface<java.lang.Integer> foo = ((generics.Foo1_interface) ((generics.Foo1_interface<java.lang.Integer>) generics.Foo1.<java.lang.Integer>factory$(dart2java$localTypeEnv.extend(generics.Foo1.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0)}))));
      generics.Foo1_interface<java.lang.Object> fooObject = ((generics.Foo1_interface) foo);
      fooObject.setVariable_Foo1(12);
      dart.core.__TopLevel.print(foo.getVariable_Foo1());
      foo.setVariable_Foo1(11);
      dart.core.__TopLevel.print(foo.getVariable_Foo1());
      generics.Bar2_interface<java.lang.Integer, java.lang.Double> bar = ((generics.Bar2_interface) generics.Bar2._new_Bar2$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Bar2$ltint$0$double$0$gt)));
      generics.Bar2_interface<java.lang.Object, java.lang.Double> barObjectDouble = ((generics.Bar2_interface) bar);
      generics.Bar2_interface<java.lang.Integer, java.lang.Object> barIntObject = ((generics.Bar2_interface) bar);
      barObjectDouble.setVarB_Bar2(14.01);
      barIntObject.setVarA_Bar2(13);
      dart.core.__TopLevel.print(barIntObject.bar_Bar2(1, 0.0));
      dart.core.__TopLevel.print(barObjectDouble.getVarB_Bar2());
      generics.Bar2_interface<java.lang.Integer, java.lang.Double> factoryBar = ((generics.Bar2_interface) ((generics.Bar2_interface<java.lang.Integer, java.lang.Double>) generics.Bar2.<java.lang.Integer, java.lang.Double>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0)}), 1, 2.3)));
      dart.core.__TopLevel.print(factoryBar.getVarB_Bar2());
      generics.Foo1_interface<generics.Bar2_interface<java.lang.Integer, java.lang.Object>> nestedGeneric12 = ((generics.Foo1_interface) ((generics.Foo1_interface<generics.Bar2_interface<java.lang.Integer, java.lang.Object>>) generics.Foo1.<generics.Bar2_interface<java.lang.Integer, java.lang.Object>>factory$(dart2java$localTypeEnv.extend(generics.Foo1.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Bar2$ltint$0$Object$gt)}))));
      nestedGeneric12.setVariable_Foo1(((generics.Bar2_interface) ((generics.Bar2_interface<java.lang.Integer, java.lang.Object>) generics.Bar2.<java.lang.Integer, java.lang.Object>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Object)}), 0, 0))));
      nestedGeneric12.setVariable_Foo1(((generics.Bar2_interface) ((generics.Bar2_interface<java.lang.Integer, java.lang.Integer>) generics.Bar2.<java.lang.Integer, java.lang.Integer>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0)}), 50, 51))));
      dart.core.__TopLevel.print(nestedGeneric12.foo_Foo1(((generics.Bar2_interface) nestedGeneric12.getVariable_Foo1())).getVarA_Bar2());
      dart.core.__TopLevel.print(nestedGeneric12.getVariable_Foo1().getVarB_Bar2());
      nestedGeneric12.writeVariable_Foo1(((generics.Bar2_interface) ((generics.Bar2_interface<java.lang.Integer, java.lang.Object>) generics.Bar2.<java.lang.Integer, java.lang.Object>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Object)}), 0, 0))));
      nestedGeneric12.writeVariable_Foo1(((generics.Bar2_interface) ((generics.Bar2_interface<java.lang.Integer, java.lang.Integer>) generics.Bar2.<java.lang.Integer, java.lang.Integer>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0)}), 50, 51))));
      dart.core.__TopLevel.print(nestedGeneric12.foo_Foo1(((generics.Bar2_interface) nestedGeneric12.getVariable_Foo1())).getVarA_Bar2());
      dart.core.__TopLevel.print(nestedGeneric12.getVariable_Foo1().getVarB_Bar2());
    }
}
