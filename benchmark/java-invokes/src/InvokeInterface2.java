/**
 * Created by stanm on 6/20/16.
 */
public class InvokeInterface2 {

    private interface I0 {
        int getInteger();
    }

    private interface I1 {
        void f1();
    }

    private interface I2 {
        void f2();
    }

    private interface I3 {
        void f3();
    }

    private interface I4 {
        void f4();
    }

    private interface I5 {
        void f5();
    }

    private interface I6 {
        void f6();
    }

    private interface I7 {
        void f7();
    }

    private interface I8 {
        void f8();
    }

    private interface I9 {
        void f9();
    }

    private static class A1 implements I0, I2, I3, I4, I5, I6, I7, I8, I9 {
        public int getInteger() {
            return 1;
        }

        public void f2() {}
        public void f3() {}
        public void f4() {}
        public void f5() {}
        public void f6() {}
        public void f7() {}
        public void f8() {}
        public void f9() {}
    }

    private static class A2 implements I0, I1, I3, I4, I5, I6, I7, I8, I9 {
        public void f1() {}

        public int getInteger() {
            return 1;
        }

        public void f3() {}
        public void f4() {}
        public void f5() {}
        public void f6() {}
        public void f7() {}
        public void f8() {}
        public void f9() {}
    }

    private static class A3 implements I0, I1, I2, I4, I5, I6, I7, I8, I9 {
        public void f1() {}
        public void f2() {}

        public int getInteger() {
            return 1;
        }

        public void f4() {}
        public void f5() {}
        public void f6() {}
        public void f7() {}
        public void f8() {}
        public void f9() {}
    }

    private static class A4 implements I0, I1, I2, I3, I5, I6, I7, I8, I9 {
        public void f1() {}
        public void f2() {}
        public void f3() {}

        public int getInteger() {
            return 1;
        }

        public void f5() {}
        public void f6() {}
        public void f7() {}
        public void f8() {}
        public void f9() {}
    }

    private static class A5 implements I0, I1, I2, I3, I4, I6, I7, I8, I9 {
        public void f1() {}
        public void f2() {}
        public void f3() {}
        public void f4() {}

        public int getInteger() {
            return 1;
        }

        public void f6() {}
        public void f7() {}
        public void f8() {}
        public void f9() {}
    }

    private static class A6 implements I0, I1, I2, I3, I4, I5, I7, I8, I9 {
        public void f1() {}
        public void f2() {}
        public void f3() {}
        public void f4() {}
        public void f5() {}

        public int getInteger() {
            return 1;
        }

        public void f7() {}
        public void f8() {}
        public void f9() {}
    }

    private static class A7 implements I0, I1, I2, I3, I4, I5, I6, I8, I9 {
        public void f1() {}
        public void f2() {}
        public void f3() {}
        public void f4() {}
        public void f5() {}
        public void f6() {}

        public int getInteger() {
            return 1;
        }

        public void f8() {}
        public void f9() {}
    }

    private static class A8 implements I0, I1, I2, I3, I4, I5, I6, I7, I9 {

        public void f1() {}
        public void f2() {}
        public void f3() {}
        public void f4() {}
        public void f5() {}
        public void f6() {}
        public void f7() {}

        public int getInteger() {
            return 1;
        }

        public void f9() {}
    }

    private static class A9 implements I0, I1, I2, I3, I4, I5, I6, I7, I8 {

        public void f1() {}
        public void f2() {}
        public void f3() {}
        public void f4() {}
        public void f5() {}
        public void f6() {}
        public void f7() {}
        public void f8() {}

        public int getInteger() {
            return 1;
        }
    }

    static volatile I0[] is = {new A1(), new A2(), new A3(), new A4(), new A5(), new A6(), new A7(), new A8(), new A9()};

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: ./a.out <outer-loop-iterations> <inner-loop-iterations>");
            return;
        }

        int outerRep, innerRep;
        try {
            outerRep = Integer.parseInt(args[0]);
            innerRep = Integer.parseInt(args[1]);
        } catch (Throwable _) {
            System.err.println("Error: could not parse command line argument.");
            return;
        }

        System.out.println("iteration,rep " + innerRep);
        for (int id = 0; id < outerRep; ++id) {
            System.out.print(id + ",");
            {
                long tm1 = System.nanoTime();
                for (int k = 0; k < innerRep; ++k) {
                    is[k % 9].getInteger();
                }
                long tm2 = System.nanoTime();
                System.out.println(1.0 * (tm2 - tm1) / innerRep);
            }
        }
    }
}
