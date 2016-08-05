/**
 * Created by stanm on 6/20/16.
 */
public class InvokeVirtual1A {

    private static class R {
        public int getInteger() { return 1; }
    }

    private static class A extends R {
        public int getInteger() {
            return 1;
        }
    }

    static volatile R[] os = {new A()};

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
                    os[k % 1].getInteger();
                }
                long tm2 = System.nanoTime();
                System.out.println(1.0 * (tm2 - tm1) / innerRep);
            }
        }
    }
}
