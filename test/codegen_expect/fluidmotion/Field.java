package fluidmotion;

public class Field extends dart._runtime.base.DartObject implements fluidmotion.Field_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(fluidmotion.Field.class, fluidmotion.Field_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      fluidmotion.Field.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public dart.core.List_interface__double dens;
    public dart.core.List_interface__double u;
    public dart.core.List_interface__double v;
    public int rowSize;
  
    public Field(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void setDensity(int x, int y, double d)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getDens().operatorAtPut_List__double(((x + 1) + ((y + 1) * this.getRowSize())), d);
    }
    public double getDensity(int x, int y)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getDens().operatorAt_List__double(((x + 1) + ((y + 1) * this.getRowSize())));
    }
    public void setVelocity(int x, int y, double xv, double yv)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getU().operatorAtPut_List__double(((x + 1) + ((y + 1) * this.getRowSize())), xv);
      this.getV().operatorAtPut_List__double(((x + 1) + ((y + 1) * this.getRowSize())), yv);
    }
    public double getXVelocity(int x, int y)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getU().operatorAt_List__double(((x + 1) + ((y + 1) * this.getRowSize())));
    }
    public double getYVelocity(int x, int y)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getV().operatorAt_List__double(((x + 1) + ((y + 1) * this.getRowSize())));
    }
    public void _constructor(dart.core.List_interface__double dens, dart.core.List_interface__double u, dart.core.List_interface__double v, int rowSize)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.dens = ((dart.core.List_interface__double) dens);
      this.u = ((dart.core.List_interface__double) u);
      this.v = ((dart.core.List_interface__double) v);
      this.rowSize = rowSize;
      super._constructor();
    }
    public dart.core.List_interface__double getDens()
    {
      return this.dens;
    }
    public dart.core.List_interface__double getU()
    {
      return this.u;
    }
    public dart.core.List_interface__double getV()
    {
      return this.v;
    }
    public int getRowSize()
    {
      return this.rowSize;
    }
    public static fluidmotion.Field_interface _new_Field$(dart._runtime.types.simple.Type type, dart.core.List_interface__double dens, dart.core.List_interface__double u, dart.core.List_interface__double v, int rowSize)
    {
      fluidmotion.Field result;
      result = new fluidmotion.Field(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(dens, u, v, rowSize);
      return result;
    }
}
