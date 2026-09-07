public class LiveLockExample {

    private static final Object LOCK = new Object();

    private static volatile boolean threadOneTurn = true;

    public static void main(final String[] args) {
        final Thread threadOne = new Thread(() -> {
            while (true) {
                if (threadOneTurn) {
                    System.out.println("Поток 1: уступает Потоку 2");

                    threadOneTurn = false;
                }
            }
        });

        final Thread threadTwo = new Thread(() -> {
            while (true) {
                if (!threadOneTurn) {
                    System.out.println("Поток 2: уступает Потоку 1");

                    threadOneTurn = true;
                }
            }
        });

        threadOne.start();
        threadTwo.start();
    }
}