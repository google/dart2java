class IntWrapper {
  int value = 0;

  IntWrapper(this.value);
}

class IntWrapperSubclass extends IntWrapper {
  IntWrapperSubclass(int value) : super(value);
}

int testSubclassSubtype() {
  List<IntWrapperSubclass> listSub = <IntWrapperSubclass>[
    new IntWrapperSubclass(24), new IntWrapperSubclass(12)];
  List<IntWrapper> listSuper = listSub;

  int result = 0;
  for (int i = 0; i < listSuper.length; i++) {
    result = result + listSub[i].value;
    result = result + listSuper[i].value;
  }

  return result;
}

int testTypeSubtypeInList() {
  List<IntWrapper> list = <IntWrapper>[
    new IntWrapper(24), new IntWrapperSubclass(12)];

  int result = 0;
  for (int i = 0; i < list.length; i++) {
    result = result + list[i].value;
  }

  return result;
}

int testSingleTypeArgumentSpecialization() {
  List<int> list = <int>[10, 20, 30];
  int result = 0;

  for (int i = 0; i < list.length; i++) {
    result = result + list[i];
  }

  return result;
}

String testAssignmentOfSpecialization() {
  List<int> list = <int>[10, 20, 30];
  List<Object> objectList = list;
  String result = "";

  for (int i = 0; i < objectList.length; i++) {
    result = result + objectList[i].toString();
  }

  return result;
}