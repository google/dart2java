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
      generics.Foo1_interface__int fooFromConstructor = ((generics.Foo1_interface__int) generics.Foo1._newnewMe(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Foo1$ltint$0$gt)));
      generics.Foo1_interface__int foo = ((generics.Foo1_interface__int) ((generics.Foo1_interface__int) generics.Foo1.<java.lang.Integer>factory$(dart2java$localTypeEnv.extend(generics.Foo1.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0)}))));
      generics.Foo1_interface<java.lang.Object> fooObject = ((generics.Foo1_interface) foo);
      fooObject.setVariable_Foo1(12);
      dart.core.__TopLevel.print(foo.getVariable_Foo1__int());
      foo.setVariable_Foo1__int(11);
      dart.core.__TopLevel.print(foo.getVariable_Foo1__int());
      generics.Bar2_interface__int_double bar = ((generics.Bar2_interface__int_double) generics.Bar2._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Bar2$ltint$0$double$0$gt)));
      generics.Bar2_interface__generic_double<java.lang.Object> barObjectDouble = ((generics.Bar2_interface__generic_double) bar);
      generics.Bar2_interface__int_generic<java.lang.Object> barIntObject = ((generics.Bar2_interface__int_generic) bar);
      barObjectDouble.setVarB_Bar2__generic_double(14.01);
      barIntObject.setVarA_Bar2__int_generic(13);
      dart.core.__TopLevel.print(barIntObject.bar_Bar2__int_generic(1, 0.0));
      dart.core.__TopLevel.print(barObjectDouble.getVarB_Bar2__generic_double());
      generics.Bar2_interface__int_double factoryBar = ((generics.Bar2_interface__int_double) ((generics.Bar2_interface__int_double) generics.Bar2.<java.lang.Integer, java.lang.Double>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0)}), 1, 2.3)));
      dart.core.__TopLevel.print(factoryBar.getVarB_Bar2__int_double());
      generics.Foo1_interface<generics.Bar2_interface__int_generic<java.lang.Object>> nestedGeneric12 = ((generics.Foo1_interface) ((generics.Foo1_interface<generics.Bar2_interface__int_generic<java.lang.Object>>) generics.Foo1.<generics.Bar2_interface__int_generic<java.lang.Object>>factory$(dart2java$localTypeEnv.extend(generics.Foo1.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Bar2$ltint$0$Object$gt)}))));
      nestedGeneric12.setVariable_Foo1(((generics.Bar2_interface__int_generic) ((generics.Bar2_interface__int_generic<java.lang.Object>) generics.Bar2.<java.lang.Integer, java.lang.Object>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Object)}), 0, 0))));
      nestedGeneric12.setVariable_Foo1(((generics.Bar2_interface__int_generic) ((generics.Bar2_interface__int_int) generics.Bar2.<java.lang.Integer, java.lang.Integer>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0)}), 50, 51))));
      dart.core.__TopLevel.print(nestedGeneric12.foo_Foo1(((generics.Bar2_interface__int_generic) nestedGeneric12.getVariable_Foo1())).getVarA_Bar2__int_generic());
      dart.core.__TopLevel.print(nestedGeneric12.getVariable_Foo1().getVarB_Bar2__int_generic());
      nestedGeneric12.writeVariable_Foo1(((generics.Bar2_interface__int_generic) ((generics.Bar2_interface__int_generic<java.lang.Object>) generics.Bar2.<java.lang.Integer, java.lang.Object>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Object)}), 0, 0))));
      nestedGeneric12.writeVariable_Foo1(((generics.Bar2_interface__int_generic) ((generics.Bar2_interface__int_int) generics.Bar2.<java.lang.Integer, java.lang.Integer>factory$newBar(dart2java$localTypeEnv.extend(generics.Bar2.factory$newBar$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0)}), 50, 51))));
      dart.core.__TopLevel.print(nestedGeneric12.foo_Foo1(((generics.Bar2_interface__int_generic) nestedGeneric12.getVariable_Foo1())).getVarA_Bar2__int_generic());
      dart.core.__TopLevel.print(nestedGeneric12.getVariable_Foo1().getVarB_Bar2__int_generic());
    }
}
