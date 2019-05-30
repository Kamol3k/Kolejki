import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public class Watek1 extends Thread {

        private final ArrayBlockingQueue kolejka1;
        Random random;

        public Watek1(ArrayBlockingQueue kolejka1, Random random) {
            this.kolejka1 = kolejka1;
            this.random = random;
        }


        @Override
        public void run() {
            while (true){

                kolejka1.add(random.nextInt(100));

                try {
                    sleep(Math.abs(random.nextLong() % 100 ));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        }
    }

    public class Watek2 extends Thread {

        private final ArrayBlockingQueue kolejka1;
        private final ArrayBlockingQueue kolejka2;
        private final Random random;

        public Watek2(ArrayBlockingQueue kolejka1, ArrayBlockingQueue kolejka2, Random random) {
            this.kolejka1 = kolejka1;
            this.kolejka2 = kolejka2;
            this.random = random;
        }


        @Override
        public void run() {
            while (true) {
                try {
                    int liczba = (int) kolejka1.take();
                    liczba = liczba * liczba;
                    kolejka2.add(liczba);
                    sleep(Math.abs(random.nextLong() % 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        }
    }

    public class Watek3 extends Thread {
        private final ArrayBlockingQueue kolejka2;
        private final Random random;

        public Watek3(ArrayBlockingQueue kolejka2, Random random) {
            this.kolejka2 = kolejka2;
            this.random = random;
        }


        @Override
        public void run() {

            while (true) {
                try {
                    int liczba = (int) kolejka2.take();
                    System.out.println(liczba);
                    sleep(Math.abs(random.nextLong() % 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        }
    }




    public static void main(String[] args) {

        ArrayBlockingQueue<Integer> kolejka1 = new ArrayBlockingQueue<>((20)), kolejka2 = new ArrayBlockingQueue<>(20);
        Random random = new Random(System.currentTimeMillis());


        System.out.println("Maszyna kolejkowa borza");

        final int W1_SIZE = 1;
        final int W2_SIZE = 1;
        final int W3_SIZE = 2;

        Watek1[] w1 = new Watek1[W1_SIZE];
        Watek2[] w2 = new Watek2[W2_SIZE];
        Watek3[] w3 = new Watek3[W3_SIZE];

        /*W1*/
        for (int i = 0; i < W1_SIZE; i++) {
            w1[i] = new Watek1(kolejka1, random);
            w1[i].setName("W1_"+i);
        }

        /*W2*/
        for (int i = 0; i < W2_SIZE; i++) {
            w2[i] = new Watek2(kolejka1, kolejka2, random);
            w2[i].setName("W2_"+i);
        }

        /*W3*/
        for (int i = 0; i < W3_SIZE; i++) {
            w3[i] = new Watek3(kolejka2, random);
            w3[i].setName("W3_"+i);
        }

        for (int i = 0; i < W1_SIZE; i++) {
            w1[i].start();
        }
        for (int i = 0; i < W2_SIZE; i++) {
            w2[i].start();
        }
        for (int i = 0; i < W3_SIZE; i++) {
            w3[i].start();
        }

    }
}

