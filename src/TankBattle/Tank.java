package TankBattle;

import java.util.Vector;

public class Tank extends Obj {
    // 坦克的坐标
    private int direct = 0;
    private int speed = 1;
    private int length = 60;
    private int width = 40;
    public Vector<Tank> allTanks = null;
    public Vector<Wall> allWalls = null;

    public Vector<Bullet> bullets = new Vector<>();


    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setBullets(Vector<Bullet> bullets) {
        this.bullets = bullets;
    }
    public Vector<Bullet> getBullets() {
        return bullets;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setAllTanks(Vector<Tank> allTanks) {
        this.allTanks = allTanks;
    }

    public void setAllWalls(Vector<Wall> allWalls) {
        this.allWalls = allWalls;
    }

    public void shoot() {
        Bullet bullet = null;
        // Hero发射子弹
        // 接受Hero的方向，让子弹按照Hero的方向来射击
        switch (getDirect()) {
            case 0: // 上
                bullet = new Bullet(getX() + 20, getY(), 0);
                break;
            case 1: // 右
                bullet = new Bullet(getX() + 60, getY() + 20, 1);
                break;
            case 2: // 下
                bullet = new Bullet(getX() + 20, getY() + 60, 2);
                break;
            case 3: // 左
                bullet = new Bullet(getX(), getY() + 20, 3);
                break;
        }
        bullets.add(bullet);
        // 开始子弹线程
        new Thread(bullet).start();
    }

    /**
     * 检测坦克是否碰撞
     * @return 返回true表示碰撞
     */
    public boolean isTouchTank() {
        // 根据当前坦克
        switch (getDirect()) {
            case 0:
                for (int i = 0; i < allTanks.size(); i++) {
                    Tank tank = allTanks.get(i);
                    if (tank != this) {
                        if (tank.getDirect() == 0 || tank.getDirect() == 2) {
                            if (getX() >= tank.getX()
                             && getX() <= tank.getX() + getWidth()
                             && getY() >= tank.getY()
                             && getY() <= tank.getY() + getLength()) {
                                return false;
                            }
                            if (getX() + getWidth() >= tank.getX()
                             && getX() + getWidth() <= tank.getX() + getWidth()
                             && getY() >= tank.getY()
                             && getY() <= tank.getY() + getLength()) {
                                return false;
                            }
                        }
                        if (tank.getDirect() == 1 || tank.getDirect() == 3) {
                            if (getX() >= tank.getX()
                             && getX() <= tank.getX() + getLength()
                             && getY() >= tank.getY()
                             && getY() <= tank.getY() + getWidth()) {
                                return false;
                            }
                            if (getX() + getWidth() >= tank.getX()
                             && getX() + getWidth() <= tank.getX() + getLength()
                             && getY() >= tank.getY()
                             && getY() <= tank.getY() + getWidth()) {
                                return false;
                            }
                        }
                    }
                }
                break;
            case 1:
                for (int i = 0; i < allTanks.size(); i++) {
                    Tank tank = allTanks.get(i);
                    if (tank != this) {
                        if (tank.getDirect() == 0 || tank.getDirect() == 2) {
                            if (getX() + getLength() >= tank.getX()
                             && getX() + getLength() <= tank.getX() + getWidth()
                             && getY() >= tank.getY()
                             && getY() <= tank.getY() + getLength()) {
                                return false;
                            }
                            if (getX() + getLength() >= tank.getX()
                             && getX() + getLength() <= tank.getX() + getWidth()
                             && getY() + getWidth() >= tank.getY()
                             && getY() + getWidth() <= tank.getY() + getLength()) {
                                return false;
                            }
                        }
                        if (tank.getDirect() == 1 || tank.getDirect() == 3) {
                            if (getX() + getLength() >= tank.getX()
                             && getX() + getLength() <= tank.getX() + getLength()
                             && getY() >= tank.getY()
                             && getY() <= tank.getY() + getWidth()) {
                                return false;
                            }
                            if (getX() + getLength() >= tank.getX()
                             && getX() + getLength() <= tank.getX() + getLength()
                             && getY() + getWidth() >= tank.getY()
                             && getY() + getWidth() <= tank.getY() + getWidth()) {
                                return false;
                            }
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < allTanks.size(); i++) {
                    Tank tank = allTanks.get(i);
                    if (tank != this) {
                        if (tank.getDirect() == 0 || tank.getDirect() == 2) {
                            if (getX() >= tank.getX()
                             && getX() <= tank.getX() + getLength()
                             && getY() + getLength() >= tank.getY()
                             && getY() + getLength() <= tank.getY() + getWidth()) {
                                return false;
                            }
                            if (getX() + getWidth() >= tank.getX()
                             && getX() + getWidth() <= tank.getX() + getLength()
                             && getY() + getLength() >= tank.getY()
                             && getY() + getLength() <= tank.getY() + getWidth()) {
                                return false;
                            }
                        }
                        if (tank.getDirect() == 1 || tank.getDirect() == 3) {
                            if (getX() >= tank.getX()
                             && getX() <= tank.getX() + getWidth()
                             && getY() + getLength() >= tank.getY()
                             && getY() + getLength() <= tank.getY() + getLength()) {
                                return false;
                            }
                            if (getX() + getWidth() >= tank.getX()
                             && getX() + getWidth() <= tank.getX() + getWidth()
                             && getY() + getLength() >= tank.getY()
                             && getY() + getLength() <= tank.getY() + getLength()) {
                                return false;
                            }
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < allTanks.size(); i++) {
                    Tank tank = allTanks.get(i);
                    if (tank != this) {
                        if (tank.getDirect() == 0 || tank.getDirect() == 2) {
                            if (getX() >= tank.getX()
                             && getX() <= tank.getX() + getWidth()
                             && getY() >= tank.getY()
                             && getY() <= tank.getY() + getLength()) {
                                return false;
                            }
                            if (getX() >= tank.getX()
                             && getX() <= tank.getX() + getWidth()
                             && getY() + getWidth() >= tank.getY()
                             && getY() + getWidth() <= tank.getY() + getLength()) {
                                return false;
                            }
                        }
                        if (tank.getDirect() == 1 || tank.getDirect() == 3) {
                            if (getX() >= tank.getX()
                             && getX() <= tank.getX() + getLength()
                             && getY() >= tank.getY()
                             && getY() <= tank.getY() + getWidth()) {
                                return false;
                            }
                            if (getX() >= tank.getX()
                             && getX() <= tank.getX() + getLength()
                             && getY() + getWidth() >= tank.getY()
                             && getY() + getWidth() <= tank.getY() + getWidth()) {
                                return false;
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }

    public boolean isTouchWall() {
        switch (getDirect()) {
            case 0:
                for (int i = 0; i < allWalls.size(); i++) {
                    Wall wall = allWalls.get(i);
                    if (getX() >= wall.getX()
                     && getX() <= wall.getX() + wall.getWidth()
                     && getY() >= wall.getY()
                     && getY() <= wall.getY() + wall.getLength()) {
                        return false;
                    }
                    if (getX() + getWidth() >= wall.getX()
                     && getX() + getWidth() <= wall.getX() + wall.getWidth()
                     && getY() >= wall.getY()
                     && getY() <= wall.getY() + wall.getLength()) {
                        return false;
                    }
                }
                break;
            case 1:
                for (int i = 0; i < allWalls.size(); i++) {
                    Wall wall = allWalls.get(i);
                    if (getX() + getLength() >= wall.getX()
                     && getX() + getLength() <= wall.getX() + wall.getWidth()
                     && getY() >= wall.getY()
                     && getY() <= wall.getY() + wall.getLength()) {
                        return false;
                    }
                    if (getX() + getLength() >= wall.getX()
                     && getX() + getLength() <= wall.getX() + wall.getWidth()
                     && getY() + getWidth() >= wall.getY()
                     && getY() + getWidth() <= wall.getY() + wall.getLength()) {
                        return false;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < allWalls.size(); i++) {
                    Wall wall = allWalls.get(i);
                    if (getX() >= wall.getX()
                     && getX() <= wall.getX() + wall.getWidth()
                     && getY() + getLength() >= wall.getY()
                     && getY() + getLength() <= wall.getY() + wall.getLength()) {
                        return false;
                    }
                    if (getX() + getWidth() >= wall.getX()
                     && getX() + getWidth() <= wall.getX() + wall.getWidth()
                     && getY() + getLength() >= wall.getY()
                     && getY() + getLength() <= wall.getY() + wall.getLength()) {
                        return false;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < allWalls.size(); i++) {
                    Wall wall = allWalls.get(i);
                    if (getX() >= wall.getX()
                     && getX() <= wall.getX() + wall.getWidth()
                     && getY() >= wall.getY()
                     && getY() <= wall.getY() + wall.getLength()) {
                        return false;
                    }
                    if (getX() >= wall.getX()
                     && getX() <= wall.getX() + wall.getWidth()
                     && getY() + getWidth() >= wall.getY()
                     && getY() + getWidth() <= wall.getY() + wall.getLength()) {
                        return false;
                    }
                }
                break;
        }
        return true;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void moveUp() {
        if (y - speed >= 0 && isTouchTank() && isTouchWall())
            y -= speed;
    }

    public void moveDown() {
        if (y + speed + 60 <= 750 && isTouchTank() && isTouchWall())
            y += speed;
    }

    public void moveLeft() {
        if (x - speed >= 0 && isTouchTank() && isTouchWall())
            x -= speed;
    }

    public void moveRight() {
        if (x + speed + 60 <= 1000 && isTouchTank() && isTouchWall())
            x += speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }
}
