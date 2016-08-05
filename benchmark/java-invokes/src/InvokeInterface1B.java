/**
 * Created by stanm on 6/20/16.
 */
public class InvokeInterface1B {

    private interface I {
        int getInteger();
    }

    private static class A0 implements I {
        public int getInteger() {
            return 0;
        }
    }

    private static class A1 implements I {
        public int getInteger() {
            return 1;
        }
    }

    private static class A2 implements I {
        public int getInteger() {
            return 2;
        }
    }

    private static class A3 implements I {
        public int getInteger() {
            return 3;
        }
    }

    static volatile I[] is = {new A0(), new A1(), new A2(), new A3()};

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
                    is[k % 4].getInteger();
                }
                long tm2 = System.nanoTime();
                System.out.println(1.0 * (tm2 - tm1) / innerRep);
            }
        }
    }
}
