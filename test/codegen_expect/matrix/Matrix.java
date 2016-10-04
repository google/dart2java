package matrix;

public class Matrix extends matrix.BenchmarkBase implements matrix.Matrix_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(matrix.Matrix.class, matrix.Matrix_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltint$0$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_int$0 = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BenchmarkBase = new dart._runtime.types.simple.InterfaceTypeExpr(matrix.BenchmarkBase.dart2java$typeInfo);
    static {
      matrix.Matrix.dart2java$typeInfo.superclass = dart2java$typeExpr_BenchmarkBase;
    }
  
    public Matrix(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void run()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int size = 50;
      dart.core.List_interface<dart.core.List_interface__int> left = ((dart.core.List_interface) ((dart.core.List_interface<dart.core.List_interface__int>) dart.core.List.<dart.core.List_interface__int>factory$(dart2java$localTypeEnv.extend(dart.core.List.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltint$0$gt)}), size)));
      for (int i = 0; (i < left.getLength_List()); i = (i + 1))
      {
        left.operatorAtPut_List(i, ((dart.core.List_interface__int) ((dart.core.List_interface__int) dart.core.List.<java.lang.Integer>factory$(dart2java$localTypeEnv.extend(dart.core.List.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0)}), size))));
      }
      dart.core.List_interface<dart.core.List_interface__int> right = ((dart.core.List_interface) ((dart.core.List_interface<dart.core.List_interface__int>) dart.core.List.<dart.core.List_interface__int>factory$(dart2java$localTypeEnv.extend(dart.core.List.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltint$0$gt)}), size)));
      for (int i = 0; (i < right.getLength_List()); i = (i + 1))
      {
        right.operatorAtPut_List(i, ((dart.core.List_interface__int) ((dart.core.List_interface__int) dart.core.List.<java.lang.Integer>factory$(dart2java$localTypeEnv.extend(dart.core.List.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0)}), size))));
      }
      dart.core.List_interface<dart.core.List_interface__int> result = ((dart.core.List_interface) ((dart.core.List_interface<dart.core.List_interface__int>) dart.core.List.<dart.core.List_interface__int>factory$(dart2java$localTypeEnv.extend(dart.core.List.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltint$0$gt)}), size)));
      for (int i = 0; (i < result.getLength_List()); i = (i + 1))
      {
        result.operatorAtPut_List(i, ((dart.core.List_interface__int) ((dart.core.List_interface__int) dart.core.List.<java.lang.Integer>factory$(dart2java$localTypeEnv.extend(dart.core.List.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_int$0)}), size))));
      }
      for (int i = 0; (i < left.getLength_List()); i = (i + 1))
      {
        for (int j = 0; (j < left.getLength_List()); j = (j + 1))
        {
          left.operatorAt_List(i).operatorAtPut_List__int(j, (i * j));
        }
      }
      for (int i = 0; (i < right.getLength_List()); i = (i + 1))
      {
        for (int j = 0; (j < right.getLength_List()); j = (j + 1))
        {
          if ((i == j))
          {
            right.operatorAt_List(i).operatorAtPut_List__int(j, 1);
          }
          else
          {
            right.operatorAt_List(i).operatorAtPut_List__int(j, 0);
          }
        }
      }
      for (int i = 0; (i < result.getLength_List()); i = (i + 1))
      {
        for (int j = 0; (j < result.getLength_List()); j = (j + 1))
        {
          result.operatorAt_List(i).operatorAtPut_List__int(j, 0);
        }
      }
      for (int i = 0; (i < left.getLength_List()); i = (i + 1))
      {
        for (int j = 0; (j < left.operatorAt_List(i).getLength_List__int()); j = (j + 1))
        {
          for (int k = 0; (k < right.getLength_List()); k = (k + 1))
          {
            result.operatorAt_List(i).operatorAtPut_List__int(j, (result.operatorAt_List(i).operatorAt_List__int(j) + (left.operatorAt_List(i).operatorAt_List__int(k) * right.operatorAt_List(k).operatorAt_List__int(j))));
          }
        }
      }
      for (int i = 0; (i < result.getLength_List()); i = (i + 1))
      {
        for (int j = 0; (j < result.getLength_List()); j = (j + 1))
        {
          if ((!(result.operatorAt_List(i).operatorAt_List__int(j) == (i * j))))
          {
            dart.core.__TopLevel.print((((("ERROR: Expected " + (i * j)) + ", but found ") + result.operatorAt_List(i).operatorAt_List__int(j)) + ""));
          }
        }
      }
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor("Matrix");
    }
    public static matrix.Matrix_interface _new_Matrix$(dart._runtime.types.simple.Type type)
    {
      matrix.Matrix result;
      result = new matrix.Matrix(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
