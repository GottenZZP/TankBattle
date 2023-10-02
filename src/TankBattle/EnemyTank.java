package TankBattle;

import java.util.Random;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    private int randomSpeed = 50;

    Random random = new Random();

    public int getRandomSpeed() {
        return randomSpeed;
    }

    public void setRandomSpeed(int randomSpeed) {
        this.randomSpeed = randomSpeed;
    }

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (isLive) {
            if (bullets.size() < 3) {
                super.shoot();
            }

            int numMove = random.nextInt(20) + 20;
            switch (getDirect()) {
                case 0: // 向上
                    for (int i = 0; i < numMove; i++) {
                        moveUp();
                        try {
                            Thread.sleep(randomSpeed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2: // 向下
                    for (int i = 0; i < numMove; i++) {
                        moveDown();
                        try {
                            Thread.sleep(randomSpeed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3: // 向左
                    for (int i = 0; i < numMove; i++) {
                        moveLeft();
                        try {
                            Thread.sleep(randomSpeed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1: // 向右
                    for (int i = 0; i < numMove; i++) {
                        moveRight();
                        try {
                            Thread.sleep(randomSpeed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }

            // 随机生成方向并移动
            setDirect(random.nextInt(4));
        }
    }
}
