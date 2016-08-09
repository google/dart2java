bool isLessIfThen(int a, int b) {
  if (a < b) {
    return true;
  }

  return false;
}

bool isLessIfThenElse(int a, int b) {
  if (a < b) {
    return true;
  } else {
    return false;
  }
}

bool isLessTernary(int a, int b) {
  return a < b ? true : false;
}

int countWhile() {
  int i = 0;
  while (i < 10) {
    i = i + 1;
  }
  return i;
}

int countDo() {
  int i = 0;
  do {
    i = i + 1;
  } while (i < 10);
  return i;
}


int countFor() {
  int counter = 0;
  for (var i = 0; i < 10; i = i + 1) {
    counter = counter + 1;
  }
  return counter;
}

int countForMultiUpdate() {
  int counter = 0;
  int i = 0;
  int j = 0;
  for (; i < 10; i = i + 1, j = j - 1) {
    counter = counter + i - j;
  }
  return counter;
}
