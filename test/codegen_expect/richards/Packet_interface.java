package richards;

public interface Packet_interface extends dart._runtime.base.DartObject_interface
{
  richards.Packet_interface addTo(richards.Packet_interface queue);
  java.lang.String toString();
  richards.Packet_interface getLink();
  int getId();
  int getKind();
  int getA1();
  dart.core.List_interface__int getA2();
  richards.Packet_interface setLink(richards.Packet_interface value);
  int setId(int value);
  int setKind(int value);
  int setA1(int value);
  dart.core.List_interface__int setA2(dart.core.List_interface__int value);

}
