package richards;

public class Packet extends dart._runtime.base.DartObject implements richards.Packet_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("richards.Packet");
    static {
      richards.Packet.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public richards.Packet_interface link;
    public int id;
    public int kind;
    public int a1;
    public dart._runtime.base.DartList._int a2;
  
    public Packet(dart._runtime.types.simple.Type type, richards.Packet_interface link, int id, int kind)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(link, id, kind);
    }
    public Packet(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(richards.Packet_interface link, int id, int kind)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.id = 0;
      this.kind = 0;
      this.a1 = 0;
      this.a2 = dart._runtime.base.DartList._int.newInstance(int.class, richards.Richards.DATA_SIZE);
      this.link = link;
      this.id = id;
      this.kind = kind;
      super._constructor();
    }
    public richards.Packet_interface addTo(richards.Packet_interface queue)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setLink(null);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(queue, null))
      {
        return this;
      }
      richards.Packet_interface peek = null;
      richards.Packet_interface next = queue;
      while ((!dart._runtime.helpers.ObjectHelper.operatorEqual(peek = next.getLink(), null)))
      {
        next = peek;
      }
      next.setLink(this);
      return queue;
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return "Packet";
    }
    public richards.Packet_interface getLink()
    {
      return this.link;
    }
    public int getId()
    {
      return this.id;
    }
    public int getKind()
    {
      return this.kind;
    }
    public int getA1()
    {
      return this.a1;
    }
    public dart._runtime.base.DartList._int getA2()
    {
      return this.a2;
    }
    public richards.Packet_interface setLink(richards.Packet_interface value)
    {
      this.link = value;
      return value;
    }
    public int setId(int value)
    {
      this.id = value;
      return value;
    }
    public int setKind(int value)
    {
      this.kind = value;
      return value;
    }
    public int setA1(int value)
    {
      this.a1 = value;
      return value;
    }
    public dart._runtime.base.DartList._int setA2(dart._runtime.base.DartList._int value)
    {
      this.a2 = value;
      return value;
    }
}
