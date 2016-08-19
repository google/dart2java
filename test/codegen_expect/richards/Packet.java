package richards;

public class Packet extends dart._runtime.base.DartObject
{
    public richards.Packet link = null;
    public java.lang.Integer id = null;
    public java.lang.Integer kind = null;
    public java.lang.Integer a1 = null;
    public dart._runtime.base.DartList._int a2 = null;
  
    public Packet(richards.Packet link, java.lang.Integer id, java.lang.Integer kind)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(link, id, kind);
    }
    public Packet(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(richards.Packet link, java.lang.Integer id, java.lang.Integer kind)
    {
      this.a1 = 0;
      this.a2 = dart._runtime.base.DartList._int.newInstance(java.lang.Integer.class, richards.Richards.DATA_SIZE);
      this.link = link;
      this.id = id;
      this.kind = kind;
      super._constructor();
    }
    public richards.Packet addTo(richards.Packet queue)
    {
      this.setLink(null);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(queue, null))
      {
        return this;
      }
      richards.Packet peek = null;
      richards.Packet next = queue;
      while ((!dart._runtime.helpers.ObjectHelper.operatorEqual(peek = next.getLink(), null)))
      {
        next = peek;
      }
      next.setLink(this);
      return queue;
    }
    public java.lang.String toString()
    {
      return "Packet";
    }
    public richards.Packet getLink()
    {
      return this.link;
    }
    public java.lang.Integer getId()
    {
      return this.id;
    }
    public java.lang.Integer getKind()
    {
      return this.kind;
    }
    public java.lang.Integer getA1()
    {
      return this.a1;
    }
    public dart._runtime.base.DartList._int getA2()
    {
      return this.a2;
    }
    public richards.Packet setLink(richards.Packet value)
    {
      this.link = value;
      return value;
    }
    public java.lang.Integer setId(java.lang.Integer value)
    {
      this.id = value;
      return value;
    }
    public java.lang.Integer setKind(java.lang.Integer value)
    {
      this.kind = value;
      return value;
    }
    public java.lang.Integer setA1(java.lang.Integer value)
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
