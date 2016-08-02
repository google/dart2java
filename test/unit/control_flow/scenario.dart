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
