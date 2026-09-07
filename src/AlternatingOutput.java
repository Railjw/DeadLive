public class AlternatingOutput {

    private static final Object LOCK = new Object();

    private static boolean oneTurn = true;

    public static void main(final String[] args) {
        final Thread threadOne = new Thread(() -> {
            while (true) {
                synchronized (LOCK) {
                    while (!oneTurn) {
                        try {
                            LOCK.wait();
                        } catch (final InterruptedException exception) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    System.out.println("1");

                    oneTurn = false;
                    LOCK.notifyAll();
                }
            }
        });

        final Thread threadTwo = new Thread(() -> {
            while (true) {
                synchronized (LOCK) {
                    while (oneTurn) {
                        try {
                            LOCK.wait();
                        } catch (final InterruptedException exception) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    System.out.println("2");

                    oneTurn = true;
                    LOCK.notifyAll();
                }
            }
        });

        threadOne.start();
        threadTwo.start();
    }
}