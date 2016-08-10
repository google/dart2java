class IntWrapper {
  int value;

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
