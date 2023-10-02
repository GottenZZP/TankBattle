package TankBattle;

public class Node {
    private int x, y;
    private int direct;
    private int type;

    /**
     * 存储所保存的坐标信息文件
     * @param x 坦克/墙体的横坐标
     * @param y 坦克/墙体的纵坐标
     * @param direct 坦克的方向（当type为1时不表示任何值）
     * @param type 0: 坦克 1: 墙体
     */
    public Node(int x, int y, int direct, int type) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }
}
