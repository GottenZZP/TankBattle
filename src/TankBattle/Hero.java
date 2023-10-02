package TankBattle;

public class Hero extends Tank {
    private int bulletNum = 50;

    public Hero(int x, int y) {
        super(x, y);
    }

    public int getBulletNum() {
        return bulletNum;
    }

    public void setBulletNum(int bulletNum) {
        this.bulletNum = bulletNum;
    }

    @Override
    public void shoot() {
        if (bullets.size() >= 10 || bulletNum <= 0)
            return;
        super.shoot();
        bulletNum--;
    }
}
