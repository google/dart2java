package specialized_list;

public class __TopLevel
{
  
  
  
  
    public static void main(String[] args)
    {
      dart.core.__TopLevel.print("Expect output 5, 7, 9");
      dart._runtime.base.DartList._int intList = dart._runtime.base.DartList._int._fromArguments(int.class, 5, 7, 9);
      for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, intList.getLength()); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        dart.core.__TopLevel.print(intList.operatorAt_primitive(i));
      }
    }
}
