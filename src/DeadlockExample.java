public class DeadlockExample {

    private static final Object RESOURCE_ONE = new Object();
    private static final Object RESOURCE_TWO = new Object();

    public static void main(final String[] args) {
        final Thread threadOne = new Thread(() -> {
            synchronized (RESOURCE_ONE) {
                System.out.println("Поток 1: захватил RESOURCE_ONE");

                try {
                    Thread.sleep(100);
                } catch (final InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println("Поток 1: ожидает RESOURCE_TWO");

                synchronized (RESOURCE_TWO) {
                    System.out.println("Поток 1: захватил RESOURCE_TWO");
                }
            }
        });

        final Thread threadTwo = new Thread(() -> {
            synchronized (RESOURCE_TWO) {
                System.out.println("Поток 2: захватил RESOURCE_TWO");

                try {
                    Thread.sleep(100);
                } catch (final InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println("Поток 2: ожидает RESOURCE_ONE");

                synchronized (RESOURCE_ONE) {
                    System.out.println("Поток 2: захватил RESOURCE_ONE");
                }
            }
        });

        threadOne.start();
        threadTwo.start();

    }
}