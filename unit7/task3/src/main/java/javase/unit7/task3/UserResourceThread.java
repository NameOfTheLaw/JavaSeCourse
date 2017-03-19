package javase.unit7.task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserResourceThread {

    public static void main(String[] args) throws InterruptedException {
        SharedResource res = new SharedResource();

        ThreadPool threadPool = new ThreadPool(
                new IntegerSetterGetter("1", res),
                new IntegerSetterGetter("2", res),
                new IntegerSetterGetter("3", res),
                new IntegerSetterGetter("4", res),
                new IntegerSetterGetter("5", res));

        threadPool.start();

        Thread.sleep(100);

        threadPool.stop();

        threadPool.join();

        System.out.println("main");
    }
}

class ThreadPool {

    private List<IntegerSetterGetter> threads = new ArrayList<>();

    public ThreadPool(IntegerSetterGetter... threads) {
        Arrays.stream(threads)
                .forEach(this::add);
    }

    public void add(IntegerSetterGetter thread) {
        threads.add(thread);
    }

    public void start() {
        threads.forEach(Thread::start);
    }

    public void stop() {
        threads.forEach(IntegerSetterGetter::stopThread);
    }

    public void join() throws InterruptedException {
        for (IntegerSetterGetter thread : threads) {
            thread.join();
        }
    }
}

class IntegerSetterGetter extends Thread {

    private SharedResource resource;
    private boolean run;
    private Random rand = new Random();
    private static volatile Integer setterCount = 0;
    private static volatile Integer setterGetterCount = 0;

    public IntegerSetterGetter(String name, SharedResource resource) {
        super(name);

        this.resource = resource;
        this.run = true;
    }

    public void stopThread() {
        run = false;
    }

    @Override
    public synchronized void start() {
        synchronized (setterGetterCount) {
            setterGetterCount++;
        }
        super.start();
    }

    public void run() {
        int action;

        try {
            while (run && setterGetterCount > 1) {
                action = rand.nextInt(1000);
                if (action % 2 == 0 && setterCount > 0) {
                    getIntegersFromResource();
                } else {
                    synchronized (setterCount) {
                        setterCount++;
                    }

                    setIntegersIntoResource();

                    synchronized (setterCount) {
                        setterCount--;
                    }
                }
            }

            System.out.println("Поток " + getName() + " завершил работу.");

            synchronized (setterGetterCount) {
                setterGetterCount--;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void getIntegersFromResource() throws InterruptedException {
        Integer number;

        synchronized (resource) {
            System.out.println("Поток " + getName() + " хочет извлечь число.");

            number = resource.getELement();
            while (number == null) {
                System.out.println("Поток " + getName() + " ждет пока очередь заполнится.");
                resource.wait();
                System.out.println("Поток " + getName() + " возобновил работу.");

                number = resource.getELement();
            }

            System.out.println("Поток " + getName() + " извлек число " + number);
        }

    }

    private void setIntegersIntoResource() throws InterruptedException {
        Integer number = rand.nextInt(500);

        synchronized (resource) {
            resource.setElement(number);
            System.out.println("Поток " + getName() + " записал число " + number);

            resource.notify();
        }
    }
}
