package TankBattle;

public class Wall extends Obj implements Runnable {
    private int type;
    private int length = 20;
    private int width = 20;

    /**
     *
     * @param x 墙体横坐标
     * @param y 墙体纵坐标
     * @param type 0: 可摧毁 1: 不可摧毁 2: 可穿过
     */
    public Wall(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void run() {

    }
}
