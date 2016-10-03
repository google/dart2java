package fluidmotion;

public class FluidField extends dart._runtime.base.DartObject implements fluidmotion.FluidField_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(fluidmotion.FluidField.class, fluidmotion.FluidField_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_double$0 = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltdouble$0$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Field = new dart._runtime.types.simple.InterfaceTypeExpr(fluidmotion.Field.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_FluidField = new dart._runtime.types.simple.InterfaceTypeExpr(fluidmotion.FluidField.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      fluidmotion.FluidField.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public java.lang.Object canvas;
    public int iterations;
    public double dt;
    public int size;
    public dart.core.List_interface__double dens;
    public dart.core.List_interface__double dens_prev;
    public dart.core.List_interface__double u;
    public dart.core.List_interface__double u_prev;
    public dart.core.List_interface__double v;
    public dart.core.List_interface__double v_prev;
    public int width;
    public int height;
    public int rowSize;
    public static fluidmotion.FluidField_interface _lastCreated;
  
    public FluidField(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public static boolean approxEquals(double a, double b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      return (dart._runtime.helpers.DoubleHelper.abs((a - b)) < 0.000001);
    }
    public void validate(java.lang.Object expectedDens, java.lang.Object expectedU, java.lang.Object expectedV)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      double sumDens = 0.0;
      double sumU = 0.0;
      double sumV = 0.0;
      for (int i = 0; (i < this.getDens().getLength_List__double()); i = (i + 1))
      {
        sumDens = (sumDens + this.getDens().operatorAt_List__double(i));
        sumU = (sumU + this.getU().operatorAt_List__double(i));
        sumV = (sumV + this.getV().operatorAt_List__double(i));
      }
      if ((((!fluidmotion.FluidField.approxEquals(sumDens, ((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(expectedDens)))) || (!fluidmotion.FluidField.approxEquals(sumU, ((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(expectedU))))) || (!fluidmotion.FluidField.approxEquals(sumV, ((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(expectedV))))))
      {
        throw new RuntimeException("Incorrect result");
      }
    }
    public void reset()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setDens(((dart.core.List_interface__double) ((dart.core.List_interface__double) dart.core.List.<java.lang.Double>factory$filled(dart2java$localTypeEnv.extend(dart.core.List.factory$filled$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0)}), this.getSize(), 0.0))));
      this.setDens_prev(((dart.core.List_interface__double) ((dart.core.List_interface__double) dart.core.List.<java.lang.Double>factory$filled(dart2java$localTypeEnv.extend(dart.core.List.factory$filled$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0)}), this.getSize(), 0.0))));
      this.setU(((dart.core.List_interface__double) ((dart.core.List_interface__double) dart.core.List.<java.lang.Double>factory$filled(dart2java$localTypeEnv.extend(dart.core.List.factory$filled$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0)}), this.getSize(), 0.0))));
      this.setU_prev(((dart.core.List_interface__double) ((dart.core.List_interface__double) dart.core.List.<java.lang.Double>factory$filled(dart2java$localTypeEnv.extend(dart.core.List.factory$filled$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0)}), this.getSize(), 0.0))));
      this.setV(((dart.core.List_interface__double) ((dart.core.List_interface__double) dart.core.List.<java.lang.Double>factory$filled(dart2java$localTypeEnv.extend(dart.core.List.factory$filled$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0)}), this.getSize(), 0.0))));
      this.setV_prev(((dart.core.List_interface__double) ((dart.core.List_interface__double) dart.core.List.<java.lang.Double>factory$filled(dart2java$localTypeEnv.extend(dart.core.List.factory$filled$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0)}), this.getSize(), 0.0))));
    }
    public void addFields(dart.core.List_interface__double x, dart.core.List_interface__double s, double dt)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      for (int i = 0; (i < this.getSize()); i = (i + 1))
      {
        x.operatorAtPut_List__double(i, (x.operatorAt_List__double(i) + (dt * s.operatorAt_List__double(i))));
      }
    }
    public void set_bnd(int b, dart.core.List_interface__double x)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if ((b == 1))
      {
        int i = 1;
        for (; (i <= this.getWidth()); i = (i + 1))
        {
          x.operatorAtPut_List__double(i, x.operatorAt_List__double((i + this.getRowSize())));
          x.operatorAtPut_List__double((i + ((this.getHeight() + 1) * this.getRowSize())), x.operatorAt_List__double((i + (this.getHeight() * this.getRowSize()))));
        }
        for (int j = 1; (j <= this.getHeight()); j = (j + 1))
        {
          x.operatorAtPut_List__double((j * this.getRowSize()), (-x.operatorAt_List__double((1 + (j * this.getRowSize())))));
          x.operatorAtPut_List__double(((this.getWidth() + 1) + (j * this.getRowSize())), (-x.operatorAt_List__double((this.getWidth() + (j * this.getRowSize())))));
        }
      }
      else
      {
        if ((b == 2))
        {
          for (int i = 1; (i <= this.getWidth()); i = (i + 1))
          {
            x.operatorAtPut_List__double(i, (-x.operatorAt_List__double((i + this.getRowSize()))));
            x.operatorAtPut_List__double((i + ((this.getHeight() + 1) * this.getRowSize())), (-x.operatorAt_List__double((i + (this.getHeight() * this.getRowSize())))));
          }
          for (int j = 1; (j <= this.getHeight()); j = (j + 1))
          {
            x.operatorAtPut_List__double((j * this.getRowSize()), x.operatorAt_List__double((1 + (j * this.getRowSize()))));
            x.operatorAtPut_List__double(((this.getWidth() + 1) + (j * this.getRowSize())), x.operatorAt_List__double((this.getWidth() + (j * this.getRowSize()))));
          }
        }
        else
        {
          for (int i = 1; (i <= this.getWidth()); i = (i + 1))
          {
            x.operatorAtPut_List__double(i, x.operatorAt_List__double((i + this.getRowSize())));
            x.operatorAtPut_List__double((i + ((this.getHeight() + 1) * this.getRowSize())), x.operatorAt_List__double((i + (this.getHeight() * this.getRowSize()))));
          }
          for (int j = 1; (j <= this.getHeight()); j = (j + 1))
          {
            x.operatorAtPut_List__double((j * this.getRowSize()), x.operatorAt_List__double((1 + (j * this.getRowSize()))));
            x.operatorAtPut_List__double(((this.getWidth() + 1) + (j * this.getRowSize())), x.operatorAt_List__double((this.getWidth() + (j * this.getRowSize()))));
          }
        }
      }
      int maxEdge = ((this.getHeight() + 1) * this.getRowSize());
      x.operatorAtPut_List__double(0, (0.5 * (x.operatorAt_List__double(1) + x.operatorAt_List__double(this.getRowSize()))));
      x.operatorAtPut_List__double(maxEdge, (0.5 * (x.operatorAt_List__double((1 + maxEdge)) + x.operatorAt_List__double((this.getHeight() * this.getRowSize())))));
      x.operatorAtPut_List__double((this.getWidth() + 1), (0.5 * (x.operatorAt_List__double(this.getWidth()) + x.operatorAt_List__double(((this.getWidth() + 1) + this.getRowSize())))));
      x.operatorAtPut_List__double(((this.getWidth() + 1) + maxEdge), (0.5 * (x.operatorAt_List__double((this.getWidth() + maxEdge)) + x.operatorAt_List__double(((this.getWidth() + 1) + (this.getHeight() * this.getRowSize()))))));
    }
    public void lin_solve(int b, dart.core.List_interface__double x, dart.core.List_interface__double x0, int a, int c)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if (((a == 0) && (c == 1)))
      {
        for (int j = 1; (j <= this.getHeight()); j = (j + 1))
        {
          int currentRow = (j * this.getRowSize());
          currentRow = (currentRow + 1);
          for (int i = 0; (i < this.getWidth()); i = (i + 1))
          {
            x.operatorAtPut_List__double(currentRow, x0.operatorAt_List__double(currentRow));
            currentRow = (currentRow + 1);
          }
        }
        this.set_bnd(b, ((dart.core.List_interface__double) x));
      }
      else
      {
        double invC = dart._runtime.helpers.IntegerHelper.operatorDivide(1, c);
        for (int k = 0; (k < this.getIterations()); k = (k + 1))
        {
          for (int j = 1; (j <= this.getHeight()); j = (j + 1))
          {
            int lastRow = ((j - 1) * this.getRowSize());
            int currentRow = (j * this.getRowSize());
            int nextRow = ((j + 1) * this.getRowSize());
            double lastX = x.operatorAt_List__double(currentRow);
            currentRow = (currentRow + 1);
            for (int i = 1; (i <= this.getWidth()); i = (i + 1))
            {
              double tmp1 = ((x0.operatorAt_List__double(currentRow) + dart._runtime.helpers.IntegerHelper.operatorStar(a, (((lastX + x.operatorAt_List__double(currentRow = (currentRow + 1))) + x.operatorAt_List__double(lastRow = (lastRow + 1))) + x.operatorAt_List__double(nextRow = (nextRow + 1))))) * invC);
              lastX = tmp1;
              x.operatorAtPut_List__double((currentRow - 1), tmp1);
            }
          }
          this.set_bnd(b, ((dart.core.List_interface__double) x));
        }
      }
    }
    public void diffuse(int b, dart.core.List_interface__double x, dart.core.List_interface__double x0, double dt)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int a = 0;
      this.lin_solve(b, ((dart.core.List_interface__double) x), ((dart.core.List_interface__double) x0), a, (1 + (4 * a)));
    }
    public void lin_solve2(dart.core.List_interface__double x, dart.core.List_interface__double x0, dart.core.List_interface__double y, dart.core.List_interface__double y0, int a, int c)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if (((a == 0) && (c == 1)))
      {
        for (int j = 1; (j <= this.getHeight()); j = (j + 1))
        {
          int currentRow = (j * this.getRowSize());
          currentRow = (currentRow + 1);
          for (int i = 0; (i < this.getWidth()); i = (i + 1))
          {
            x.operatorAtPut_List__double(currentRow, x0.operatorAt_List__double(currentRow));
            y.operatorAtPut_List__double(currentRow, y0.operatorAt_List__double(currentRow));
            currentRow = (currentRow + 1);
          }
        }
        this.set_bnd(1, ((dart.core.List_interface__double) x));
        this.set_bnd(2, ((dart.core.List_interface__double) y));
      }
      else
      {
        double invC = dart._runtime.helpers.IntegerHelper.operatorDivide(1, c);
        for (int k = 0; (k < this.getIterations()); k = (k + 1))
        {
          for (int j = 1; (j <= this.getHeight()); j = (j + 1))
          {
            int lastRow = ((j - 1) * this.getRowSize());
            int currentRow = (j * this.getRowSize());
            int nextRow = ((j + 1) * this.getRowSize());
            double lastX = x.operatorAt_List__double(currentRow);
            double lastY = y.operatorAt_List__double(currentRow);
            currentRow = (currentRow + 1);
            for (int i = 1; (i <= this.getWidth()); i = (i + 1))
            {
              double tmp2 = ((x0.operatorAt_List__double(currentRow) + dart._runtime.helpers.IntegerHelper.operatorStar(a, (((lastX + x.operatorAt_List__double(currentRow)) + x.operatorAt_List__double(lastRow)) + x.operatorAt_List__double(nextRow)))) * invC);
              lastX = tmp2;
              x.operatorAtPut_List__double(currentRow, tmp2);
              double tmp3 = ((y0.operatorAt_List__double(currentRow) + dart._runtime.helpers.IntegerHelper.operatorStar(a, (((lastY + y.operatorAt_List__double(currentRow = (currentRow + 1))) + y.operatorAt_List__double(lastRow = (lastRow + 1))) + y.operatorAt_List__double(nextRow = (nextRow + 1))))) * invC);
              lastY = tmp3;
              y.operatorAtPut_List__double((currentRow - 1), tmp3);
            }
          }
          this.set_bnd(1, ((dart.core.List_interface__double) x));
          this.set_bnd(2, ((dart.core.List_interface__double) y));
        }
      }
    }
    public void diffuse2(dart.core.List_interface__double x, dart.core.List_interface__double x0, java.lang.Object y, dart.core.List_interface__double y0, double dt)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int a = 0;
      this.lin_solve2(((dart.core.List_interface__double) x), ((dart.core.List_interface__double) x0), ((dart.core.List_interface__double) ((dart.core.List_interface__double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltdouble$0$gt).check(y))), ((dart.core.List_interface__double) y0), a, (1 + (4 * a)));
    }
    public void advect(int b, dart.core.List_interface__double d, dart.core.List_interface__double d0, dart.core.List_interface__double u, dart.core.List_interface__double v, double dt)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      double Wdt0 = dart._runtime.helpers.DoubleHelper.operatorStar(dt, this.getWidth());
      double Hdt0 = dart._runtime.helpers.DoubleHelper.operatorStar(dt, this.getHeight());
      double Wp5 = dart._runtime.helpers.IntegerHelper.operatorPlus(this.getWidth(), 0.5);
      double Hp5 = dart._runtime.helpers.IntegerHelper.operatorPlus(this.getHeight(), 0.5);
      for (int j = 1; (j <= this.getHeight()); j = (j + 1))
      {
        int pos = (j * this.getRowSize());
        for (int i = 1; (i <= this.getWidth()); i = (i + 1))
        {
          double x = dart._runtime.helpers.IntegerHelper.operatorMinus(i, (Wdt0 * u.operatorAt_List__double(pos = (pos + 1))));
          double y = dart._runtime.helpers.IntegerHelper.operatorMinus(j, (Hdt0 * v.operatorAt_List__double(pos)));
          if ((x < 0.5))
          {
            x = 0.5;
          }
          else
          {
            if ((x > Wp5))
            {
              x = Wp5;
            }
          }
          int i0 = dart._runtime.helpers.DoubleHelper.toInt(x);
          int i1 = (i0 + 1);
          if ((y < 0.5))
          {
            y = 0.5;
          }
          else
          {
            if ((y > Hp5))
            {
              y = Hp5;
            }
          }
          int j0 = dart._runtime.helpers.DoubleHelper.toInt(y);
          int j1 = (j0 + 1);
          double s1 = dart._runtime.helpers.DoubleHelper.operatorMinus(x, i0);
          double s0 = dart._runtime.helpers.IntegerHelper.operatorMinus(1, s1);
          double t1 = dart._runtime.helpers.DoubleHelper.operatorMinus(y, j0);
          double t0 = dart._runtime.helpers.IntegerHelper.operatorMinus(1, t1);
          int row1 = (j0 * this.getRowSize());
          int row2 = (j1 * this.getRowSize());
          d.operatorAtPut_List__double(pos, ((s0 * ((t0 * d0.operatorAt_List__double((i0 + row1))) + (t1 * d0.operatorAt_List__double((i0 + row2))))) + (s1 * ((t0 * d0.operatorAt_List__double((i1 + row1))) + (t1 * d0.operatorAt_List__double((i1 + row2)))))));
        }
      }
      this.set_bnd(b, ((dart.core.List_interface__double) d));
    }
    public void project(dart.core.List_interface__double u, dart.core.List_interface__double v, dart.core.List_interface__double p, dart.core.List_interface__double div)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      double h = ((-0.5) / dart.math.__TopLevel.sqrt((this.getWidth() * this.getHeight())));
      for (int j = 1; (j <= this.getHeight()); j = (j + 1))
      {
        int row = (j * this.getRowSize());
        int previousRow = ((j - 1) * this.getRowSize());
        int prevValue = (row - 1);
        int currentRow = row;
        int nextValue = (row + 1);
        int nextRow = ((j + 1) * this.getRowSize());
        for (int i = 1; (i <= this.getWidth()); i = (i + 1))
        {
          div.operatorAtPut_List__double(currentRow = (currentRow + 1), (h * (((u.operatorAt_List__double(nextValue = (nextValue + 1)) - u.operatorAt_List__double(prevValue = (prevValue + 1))) + v.operatorAt_List__double(nextRow = (nextRow + 1))) - v.operatorAt_List__double(previousRow = (previousRow + 1)))));
          p.operatorAtPut_List__double(currentRow, 0.0);
        }
      }
      this.set_bnd(0, ((dart.core.List_interface__double) div));
      this.set_bnd(0, ((dart.core.List_interface__double) p));
      this.lin_solve(0, ((dart.core.List_interface__double) p), ((dart.core.List_interface__double) div), 1, 4);
      double wScale = dart._runtime.helpers.DoubleHelper.operatorStar(0.5, this.getWidth());
      double hScale = dart._runtime.helpers.DoubleHelper.operatorStar(0.5, this.getHeight());
      for (int j = 1; (j <= this.getHeight()); j = (j + 1))
      {
        int prevPos = ((j * this.getRowSize()) - 1);
        int currentPos = (j * this.getRowSize());
        int nextPos = ((j * this.getRowSize()) + 1);
        int prevRow = ((j - 1) * this.getRowSize());
        int currentRow = (j * this.getRowSize());
        int nextRow = ((j + 1) * this.getRowSize());
        for (int i = 1; (i <= this.getWidth()); i = (i + 1))
        {
          u.operatorAtPut_List__double(currentPos = (currentPos + 1), (u.operatorAt_List__double(currentPos) - (wScale * (p.operatorAt_List__double(nextPos = (nextPos + 1)) - p.operatorAt_List__double(prevPos = (prevPos + 1))))));
          v.operatorAtPut_List__double(currentPos, (v.operatorAt_List__double(currentPos) - (hScale * (p.operatorAt_List__double(nextRow = (nextRow + 1)) - p.operatorAt_List__double(prevRow = (prevRow + 1))))));
        }
      }
      this.set_bnd(1, ((dart.core.List_interface__double) u));
      this.set_bnd(2, ((dart.core.List_interface__double) v));
    }
    public void dens_step(dart.core.List_interface__double x, dart.core.List_interface__double x0, dart.core.List_interface__double u, dart.core.List_interface__double v, double dt)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.addFields(((dart.core.List_interface__double) x), ((dart.core.List_interface__double) x0), dt);
      this.diffuse(0, ((dart.core.List_interface__double) x0), ((dart.core.List_interface__double) x), dt);
      this.advect(0, ((dart.core.List_interface__double) x), ((dart.core.List_interface__double) x0), ((dart.core.List_interface__double) u), ((dart.core.List_interface__double) v), dt);
    }
    public void vel_step(dart.core.List_interface__double u, dart.core.List_interface__double v, dart.core.List_interface__double u0, dart.core.List_interface__double v0, double dt)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.addFields(((dart.core.List_interface__double) u), ((dart.core.List_interface__double) u0), dt);
      this.addFields(((dart.core.List_interface__double) v), ((dart.core.List_interface__double) v0), dt);
      dart.core.List_interface__double temp = ((dart.core.List_interface__double) u0);
      u0 = ((dart.core.List_interface__double) u);
      u = ((dart.core.List_interface__double) temp);
      temp = ((dart.core.List_interface__double) v0);
      v0 = ((dart.core.List_interface__double) v);
      v = ((dart.core.List_interface__double) temp);
      this.diffuse2(((dart.core.List_interface__double) u), ((dart.core.List_interface__double) u0), v, ((dart.core.List_interface__double) v0), dt);
      this.project(((dart.core.List_interface__double) u), ((dart.core.List_interface__double) v), ((dart.core.List_interface__double) u0), ((dart.core.List_interface__double) v0));
      temp = ((dart.core.List_interface__double) u0);
      u0 = ((dart.core.List_interface__double) u);
      u = ((dart.core.List_interface__double) temp);
      temp = ((dart.core.List_interface__double) v0);
      v0 = ((dart.core.List_interface__double) v);
      v = ((dart.core.List_interface__double) temp);
      this.advect(1, ((dart.core.List_interface__double) u), ((dart.core.List_interface__double) u0), ((dart.core.List_interface__double) u0), ((dart.core.List_interface__double) v0), dt);
      this.advect(2, ((dart.core.List_interface__double) v), ((dart.core.List_interface__double) v0), ((dart.core.List_interface__double) u0), ((dart.core.List_interface__double) v0), dt);
      this.project(((dart.core.List_interface__double) u), ((dart.core.List_interface__double) v), ((dart.core.List_interface__double) u0), ((dart.core.List_interface__double) v0));
    }
    public void queryUI(dart.core.List_interface__double d, dart.core.List_interface__double u, dart.core.List_interface__double v)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      for (int i = 0; (i < this.getSize()); i = (i + 1))
      {
        u.operatorAtPut_List__double(i, 0.0);
        v.operatorAtPut_List__double(i, 0.0);
        d.operatorAtPut_List__double(i, 0.0);
      }
      fluidmotion.FluidMotion.prepareFrame(fluidmotion.Field._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Field), ((dart.core.List_interface__double) d), ((dart.core.List_interface__double) u), ((dart.core.List_interface__double) v), this.getRowSize()));
    }
    public void update()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.queryUI(((dart.core.List_interface__double) this.getDens_prev()), ((dart.core.List_interface__double) this.getU_prev()), ((dart.core.List_interface__double) this.getV_prev()));
      this.vel_step(((dart.core.List_interface__double) this.getU()), ((dart.core.List_interface__double) this.getV()), ((dart.core.List_interface__double) this.getU_prev()), ((dart.core.List_interface__double) this.getV_prev()), this.getDt());
      this.dens_step(((dart.core.List_interface__double) this.getDens()), ((dart.core.List_interface__double) this.getDens_prev()), ((dart.core.List_interface__double) this.getU()), ((dart.core.List_interface__double) this.getV()), this.getDt());
    }
    public void _constructor(java.lang.Object canvas, int hRes, int wRes, int iterations)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.dt = 0.1;
      this.canvas = canvas;
      this.iterations = iterations;
      this.width = wRes;
      this.height = hRes;
      this.rowSize = (wRes + 2);
      this.size = ((wRes + 2) * (hRes + 2));
      super._constructor();
      this.reset();
    }
    public java.lang.Object getCanvas()
    {
      return this.canvas;
    }
    public int getIterations()
    {
      return this.iterations;
    }
    public double getDt()
    {
      return this.dt;
    }
    public int getSize()
    {
      return this.size;
    }
    public dart.core.List_interface__double getDens()
    {
      return this.dens;
    }
    public dart.core.List_interface__double getDens_prev()
    {
      return this.dens_prev;
    }
    public dart.core.List_interface__double getU()
    {
      return this.u;
    }
    public dart.core.List_interface__double getU_prev()
    {
      return this.u_prev;
    }
    public dart.core.List_interface__double getV()
    {
      return this.v;
    }
    public dart.core.List_interface__double getV_prev()
    {
      return this.v_prev;
    }
    public int getWidth()
    {
      return this.width;
    }
    public int getHeight()
    {
      return this.height;
    }
    public int getRowSize()
    {
      return this.rowSize;
    }
    public static fluidmotion.FluidField_interface get_lastCreated()
    {
      return fluidmotion.FluidField._lastCreated;
    }
    public dart.core.List_interface__double setDens(dart.core.List_interface__double value)
    {
      this.dens = value;
      return value;
    }
    public dart.core.List_interface__double setDens_prev(dart.core.List_interface__double value)
    {
      this.dens_prev = value;
      return value;
    }
    public dart.core.List_interface__double setU(dart.core.List_interface__double value)
    {
      this.u = value;
      return value;
    }
    public dart.core.List_interface__double setU_prev(dart.core.List_interface__double value)
    {
      this.u_prev = value;
      return value;
    }
    public dart.core.List_interface__double setV(dart.core.List_interface__double value)
    {
      this.v = value;
      return value;
    }
    public dart.core.List_interface__double setV_prev(dart.core.List_interface__double value)
    {
      this.v_prev = value;
      return value;
    }
    public static fluidmotion.FluidField_interface set_lastCreated(fluidmotion.FluidField_interface value)
    {
      fluidmotion.FluidField._lastCreated = value;
      return value;
    }
    public static fluidmotion.FluidField_interface factory$create(java.lang.Object canvas, int hRes, int wRes, int iterations)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      final int res = (wRes * hRes);
      if (((res > 0) && (res < 1000000)))
      {
        fluidmotion.FluidField._lastCreated = fluidmotion.FluidField._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_FluidField), canvas, hRes, wRes, iterations);
      }
      else
      {
        if (dart._runtime.helpers.ObjectHelper.operatorEqual(fluidmotion.FluidField._lastCreated, null))
        {
          fluidmotion.FluidField._lastCreated = fluidmotion.FluidField._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_FluidField), canvas, 64, 64, iterations);
        }
      }
      return fluidmotion.FluidField._lastCreated;
    }
    public static fluidmotion.FluidField_interface _new(dart._runtime.types.simple.Type type, java.lang.Object canvas, int hRes, int wRes, int iterations)
    {
      fluidmotion.FluidField result;
      result = new fluidmotion.FluidField(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(canvas, hRes, wRes, iterations);
      return result;
    }
}
