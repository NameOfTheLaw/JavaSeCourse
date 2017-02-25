package javase.unit2.t7;

/**
 * Класс представляет собой интерфейс управления подводной лодкой.
 *
 * Created by andrey on 25.02.2017.
 */
@NonEnglishDocumentation(language = "ru")
public class AtomicBoat {

    private EngineForAtomicBoat engine = new EngineForAtomicBoat();

    /**
     * Включает подводную лодку.
     */
    public void on() {
        engine.on();
    }

    /**
     * Вызывает движение подводной лодки.
     */
    public void move() {
        if (!engine.isOn) {
            engine.on();
        }
        engine.startMoving();
    }

    /**
     * Проверка isMoving ли подводная лодка.
     *
     * @return
     */
    public boolean isMoving() {
        return engine.getRotateSpeed() > 0;
    }

    /**
     * Останавливает подводную лодку.
     */
    public void stop() {
        engine.reduceSpeed();
    }

    /**
     * Выключение подводной лодки.
     */
    public void off() {
        engine.off();
    }

    private class EngineForAtomicBoat {

        private boolean isOn = false;
        private int rotateSpeed = 0;

        public void on() {
            isOn = true;
        }

        public boolean isOn() {
            return isOn;
        }

        public void startMoving() {
            rotateSpeed = 1000;
        }

        public int getRotateSpeed() {
            return rotateSpeed;
        }

        public void off() {
            isOn = false;
            rotateSpeed = 0;
        }

        public void reduceSpeed() {
            rotateSpeed = 0;
        }
    }
}
