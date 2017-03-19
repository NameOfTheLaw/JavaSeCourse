package javase.unit7.task3;

import java.util.Random;

public class UserResourceThread {

    public static void main(String[] args) throws InterruptedException {
        SharedResource res = new SharedResource();

        IntegerSetterGetter t1 = new IntegerSetterGetter("1", res);
        IntegerSetterGetter t2 = new IntegerSetterGetter("2", res);
        IntegerSetterGetter t3 = new IntegerSetterGetter("3", res);
        IntegerSetterGetter t4 = new IntegerSetterGetter("4", res);
        IntegerSetterGetter t5 = new IntegerSetterGetter("5", res);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        Thread.sleep(100);

        t1.stopThread();
        t2.stopThread();
        t3.stopThread();
        t4.stopThread();
        t5.stopThread();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

        System.out.println("main");
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
