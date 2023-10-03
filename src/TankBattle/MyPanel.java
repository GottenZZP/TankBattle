package TankBattle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;
import java.util.Vector;

@SuppressWarnings({"all"})
public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero;
    // 存放敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>() ;
    Vector<Tank> tanks = new Vector<>();
    // 存放爆炸图片
    // 当子弹击中坦克时加入一个Bomb对象
    Vector<Bomb> bombs = new Vector<>();
    Vector<Node> nodes;
    Vector<Wall> walls = new Vector<>();
    int enemySize = 9;
    int wallSize = 20;
    // isWin: 0:游戏进行中 1:游戏获胜 2:游戏失败
    int isWin = 0;

    Random random = new Random();

    Image image1;
    Image image2;
    Image image3;

    public MyPanel(String isNext) {
        File file = new File(Recorder.getRecordFilePath());
        if (!file.exists()) {
            isNext = "1";
            System.out.println("记录文件不存在，只能开启新游戏！");
        } else {
            nodes = Recorder.parseRecorder();
            if (isNext.equals("1")) {
                Recorder.setKillEnemyNum(0);
            }
        }
        switch (isNext) {
            case "1": // 开始新游戏
                // 初始化墙体
                initWalls();

                // 初始化自己的坦克
                hero = new Hero(480, 690);
                hero.setSpeed(10);
                tanks.add(hero);
                hero.setAllTanks(tanks);
                hero.setAllWalls(walls);

                // 初始化敌方坦克
                for (int i = 0; i < enemySize; i++) {
//                    int randomX = random.nextInt(800) + 60;
//                    int randomY = random.nextInt(200) + 60;
                    // 创建敌方坦克对象
                    EnemyTank enemyTank = new EnemyTank((i + 1) * 100, 0);
                    enemyTank.setDirect(2);
                    enemyTank.setSpeed(1);
                    enemyTank.setAllTanks(tanks);
                    enemyTank.setAllWalls(walls);
                    // 开启敌方坦克线程，使得每个敌方坦克可以自主随机移动
                    new Thread(enemyTank).start();

                    // 给敌人创建子弹对象
                    Bullet bullet = new Bullet(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    // 将其加入该敌人的子弹vector中
                    enemyTank.getBullets().add(bullet);
                    // 启动该子弹对象
                    new Thread(bullet).start();
                    // 将坦克对象放入敌方坦克集合中
                    enemyTanks.add(enemyTank);
                    tanks.add(enemyTank);
                }
                break;
            case "2": // 继续上一局
                // 初始化墙体
                // TODO: 未完成Recorder类对墙体位置的记录和加载
                // 初始化自己的坦克
                hero = new Hero(nodes.get(0).getX(), nodes.get(0).getY());
                hero.setDirect(nodes.get(0).getDirect());
                hero.setSpeed(10);
                tanks.add(hero);
                hero.setAllTanks(tanks);
                hero.setAllWalls(walls);

                // 初始化敌方坦克
                for (int i = 1; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    if (node.getDirect() != 999) {
                        // 创建敌方坦克对象
                        EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                        enemyTank.setDirect(node.getDirect());
                        enemyTank.setSpeed(1);
                        enemyTank.setAllTanks(tanks);
                        enemyTank.setAllWalls(walls);
                        // 开启敌方坦克线程，使得每个敌方坦克可以自主随机移动
                        new Thread(enemyTank).start();

                        // 给敌人创建子弹对象
                        Bullet bullet = new Bullet(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                        // 将其加入该敌人的子弹vector中
                        enemyTank.getBullets().add(bullet);
                        // 启动该子弹对象
                        new Thread(bullet).start();
                        // 将坦克对象放入敌方坦克集合中
                        enemyTanks.add(enemyTank);
                        tanks.add(enemyTank);
                    }
                    if (node.getDirect() == 999){
                        initWall(node.getX(), node.getY(), node.getType());
                    }
                }
                break;
            default:
                System.out.println("输入错误！");
        }
        Recorder.setEnemyTanks(enemyTanks);
        Recorder.setHero(hero);
        Recorder.setWalls(walls);

        // 初始化坦克爆炸图片
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.png"));

        // 播放游戏音乐
        new AePlayWave("src/TankBattle/gameStartMusic.wav").start();
    }

    public void initWall(int x, int y, int type) {
        Wall wall = new Wall(x, y, type);
        walls.add(wall);
    }

    public void initWalls() {
        for (int i = 0; i < 1000; i += 20) {
            for (int j = 0; j < 750; j += 20) {
                // 初始化可摧毁的墙1
                if (i >= 160 && i <= 320 && j >= 160 && j <= 220) {
                    initWall(i, j, 0);
                }
                if (i >= 420 && i <= 580 && j >= 160 && j <= 220) {
                    initWall(i, j, 0);
                }
                if (i >= 680 && i <= 840 && j >= 160 && j <= 220) {
                    initWall(i, j, 0);
                }
                // 初始化不可摧毁的墙1
                if (i >= 160 && i <= 320 && j >= 340 && j <= 400) {
                    initWall(i, j, 1);
                }
                if (i >= 420 && i <= 580 && j >= 340 && j <= 400) {
                    initWall(i, j, 1);
                }
                if (i >= 680 && i <= 840 && j >= 340 && j <= 400) {
                    initWall(i, j, 1);
                }
                // 初始化可摧毁的墙2
                if (i >= 160 && i <= 320 && j >= 540 && j <= 600) {
                    initWall(i, j, 0);
                }
                if (i >= 420 && i <= 580 && j >= 540 && j <= 600) {
                    initWall(i, j, 0);
                }
                if (i >= 680 && i <= 840 && j >= 540 && j <= 600) {
                    initWall(i, j, 0);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isWin == 0) {
            // 绘制背景色
            g.fillRect(0, 0, 1000, 750);
            // 绘制墙体
            drawWalls(g);
            // 绘制Hero
            drawHero(g);
            // 绘制炸弹
            drawBomb(g);
            // 绘制敌方坦克
            drawEnemyTank(g);
        } else if (isWin == 1) {
            // 绘制获胜界面
            drawWinInterface(g);
        } else if (isWin == 2) {
            // 绘制失败界面
            drawLoseingInterface(g);
        }
        // 绘制右侧提示信息
        showInfo(g);
    }

    public void drawWinInterface(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 750);
        Font font = new Font("Yuan-ti SC", Font.BOLD, 100);
        g.setColor(Color.cyan);
        g.setFont(font);
        g.drawString("游戏获胜！", 300, 375);
    }
    public void drawLoseingInterface(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 750);
        Font font = new Font("Yuan-ti SC", Font.BOLD, 100);
        g.setColor(Color.yellow);
        g.setFont(font);
        g.drawString("游戏失败！", 300, 375);
    }

    public void drawWalls(Graphics g) {
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            if (wall.isLive()) {
                drawWall(g, wall.getX(), wall.getY(), wall.getType());
            }
        }
    }

    public void drawWall(Graphics g, int x, int y, int type) {
        g.setColor(Color.LIGHT_GRAY);
        if (type == 0) {
            g.fill3DRect(x, y, 20, 20, false);
        }
        else {
            g.fill3DRect(x, y, 20, 20, true);
        }
    }

    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("Yuan-ti SC", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累计击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 1);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getKillEnemyNum() + "/" + enemySize, 1080, 100);

        g.drawString("剩余弹量: ", 1020, 160);
        g.drawString(hero.getBulletNum() + "", 1150, 160);

        g.drawString("操作方式：", 1020, 600);
        Font font2 = new Font("Yuan-ti SC", Font.BOLD, 20);
        g.setFont(font2);
        g.drawString("W(上)、S(下)、A(左)、D(右)", 1020, 650);
        g.drawString("J(射击)", 1020, 700);
    }

    public void drawHero(Graphics g) {
        // 绘制自己坦克
        if (hero != null && hero.isLive()) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
            // 遍历hero的所有子弹
            for (int i = 0; i < hero.getBullets().size(); i++) {
                Bullet bullet = hero.getBullets().get(i);
                // 当前子弹不为空且存活时，绘制该子弹
                if (bullet != null && bullet.isLive()) {
                    drawBullet(bullet.getX(), bullet.getY(), g, 0);
                } else {
                    hero.getBullets().remove(bullet);
                }
            }
        }
    }

    public void drawBomb(Graphics g) {
        // 绘制爆炸效果
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 炸弹生命在某些范围时绘制指定图片
            if (bomb.getLife() > 6)
                g.drawImage(image3, bomb.getX(), bomb.getY(), 60, 60, this);
            else if (bomb.getLife() > 3)
                g.drawImage(image2, bomb.getX(), bomb.getY(), 60, 60, this);
            else
                g.drawImage(image1, bomb.getX(), bomb.getY(), 60, 60, this);
            bomb.lifeDown();

            if (bomb.getLife() == 0)
                bombs.remove(bomb);
        }
    }

    public void drawEnemyTank(Graphics g) {
        //绘制敌方坦克和其子弹
        for (EnemyTank enemyTank : enemyTanks) {
            // 若敌方坦克存活才绘制该坦克
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                // 绘制当前坦克的所有子弹
                for (int j = 0; j < enemyTank.getBullets().size(); j++) {
                    Bullet bullet = enemyTank.getBullets().get(j);
                    // 若子弹存活则绘制，否则将其移除该坦克的子弹集合中
                    if (bullet.isLive()) {
                        drawBullet(bullet.getX(), bullet.getY(), g, 1);
                    } else {
                        enemyTank.getBullets().remove(bullet);
                    }
                }
            }
        }
    }

    /**
     * 绘制坦克
     *
     * @param x      坦克的左上角x坐标
     * @param y      坦克的左上角y坐标
     * @param g      画笔
     * @param direct 坦克的方向（0:向上，1:向右，2:向下，3:向左）
     * @param type   坦克的类型（我方或敌方坦克）
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        // 根据不同类型的坦克，设置不同颜色
        switch (type) {
            case 0: // 我方坦克
                g.setColor(Color.cyan);
                break;
            case 1: // 敌方坦克
                g.setColor(Color.yellow);
                break;
        }
        // 画坦克
        switch (direct) {
            case 0: // 朝向上
                // 按照坦克定位画出坦克的左轮
                g.fill3DRect(x, y, 10, 60, false);
                // 按照坦克定位画出坦克的右轮
                g.fill3DRect(x + 30, y, 10, 60, false);
                // 按照坦克定位画出坦克的身子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                // 按照坦克定位画出坦克的盖子
                g.fillOval(x + 10, y + 20, 20, 20);
                // 按照坦克定位画出坦克的炮筒
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1: // 朝向右
                // 按照坦克定位画出坦克的左轮
                g.fill3DRect(x, y, 60, 10, false);
                // 按照坦克定位画出坦克的右轮
                g.fill3DRect(x, y + 30, 60, 10, false);
                // 按照坦克定位画出坦克的身子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                // 按照坦克定位画出坦克的盖子
                g.fillOval(x + 20, y + 10, 20, 20);
                // 按照坦克定位画出坦克的炮筒
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2: // 朝向下
                // 按照坦克定位画出坦克的左轮
                g.fill3DRect(x, y, 10, 60, false);
                // 按照坦克定位画出坦克的右轮
                g.fill3DRect(x + 30, y, 10, 60, false);
                // 按照坦克定位画出坦克的身子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                // 按照坦克定位画出坦克的盖子
                g.fillOval(x + 10, y + 20, 20, 20);
                // 按照坦克定位画出坦克的炮筒
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3: // 朝向左
                // 按照坦克定位画出坦克的左轮
                g.fill3DRect(x, y, 60, 10, false);
                // 按照坦克定位画出坦克的右轮
                g.fill3DRect(x, y + 30, 60, 10, false);
                // 按照坦克定位画出坦克的身子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                // 按照坦克定位画出坦克的盖子
                g.fillOval(x + 20, y + 10, 20, 20);
                // 按照坦克定位画出坦克的炮筒
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
        }
    }

    public void drawBullet(int x, int y, Graphics g, int type) {
        // 根据不同类型的坦克，设置不同颜色的子弹
        switch (type) {
            case 0: // 我方坦克
                g.setColor(Color.cyan);
                break;
            case 1: // 敌方坦克
                g.setColor(Color.yellow);
                break;
        }
        // 绘制子弹
        g.draw3DRect(x, y, 1, 1, false);
    }

    public void hitEnemyTank() {
        for (int i = 0; i < hero.getBullets().size(); i++) {
            Bullet bullet = hero.getBullets().get(i);
            // 若hero子弹还存活时才执行
            if (bullet != null && bullet.isLive()) {
                // 遍历每个坦克，判断是否被击中
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(bullet, enemyTank);
                }
                // 遍历每个墙体，判断是否被击中
                for (int j = 0; j < walls.size(); j++) {
                    Wall wall = walls.get(j);
                    hitWall(bullet, wall);
                }
            }
        }
    }

    public void hitHero() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.getBullets().size(); j++) {
                Bullet bullet = enemyTank.getBullets().get(j);
                if (bullet != null && bullet.isLive()) {
                    hitTank(bullet, hero);
                    // 遍历每个墙体，判断是否被击中
                    for (int k = 0; k < walls.size(); k++) {
                        Wall wall = walls.get(k);
                        hitWall(bullet, wall);
                    }
                }
            }
        }
    }

    public void setDeath(Bullet bullet, Obj obj) {
        bullet.setLive(false);

        // 若被击中的是Hero
        if (obj instanceof Hero) {
            hero.getBullets().remove(bullet);
            obj.setLive(false);
            // 当子弹击中坦克时，创建爆炸对象，并将其放入bomb集合中
            Bomb bomb = new Bomb(obj.getX(), obj.getY());
            bombs.add(bomb);
        }

        // 若被击中的是EnemyTank
        if (obj instanceof EnemyTank) {
            Recorder.addKillEnemyNum();
            obj.setLive(false);
            ((EnemyTank) obj).getBullets().remove(bullet);
            enemyTanks.remove((EnemyTank) obj);
            tanks.remove(obj);
            // 当子弹击中坦克时，创建爆炸对象，并将其放入bomb集合中
            Bomb bomb = new Bomb(obj.getX(), obj.getY());
            bombs.add(bomb);
        }
        // 若被击中的是墙体
        if (obj instanceof Wall) {
            // 只去除可摧毁墙体
            if (((Wall) obj).getType() == 0) {
                obj.setLive(false);
                walls.remove((Wall) obj);
            }
        }
    }

    public void hitTank(Bullet bullet, Tank tank) {
        switch (tank.getDirect()) {
            case 0:
            case 2:
                if (bullet.getX() > tank.getX() && bullet.getX() < tank.getX() + tank.getWidth()
                 && bullet.getY() > tank.getY() && bullet.getY() < tank.getY() + tank.getLength()) {
                    setDeath(bullet, tank);
                }
                break;
            case 1:
            case 3:
                if (bullet.getX() > tank.getX() && bullet.getX() < tank.getX() + tank.getLength()
                 && bullet.getY() > tank.getY() && bullet.getY() < tank.getY() + tank.getWidth()) {
                    setDeath(bullet, tank);
                }
                break;
        }
    }

    public void hitWall(Bullet bullet, Wall wall) {
        if (bullet.getX() >= wall.getX()
         && bullet.getX() <= wall.getX() + wall.getWidth()
         && bullet.getY() >= wall.getY()
         && bullet.getY() <= wall.getY() + wall.getLength()) {
            setDeath(bullet, wall);
        }
    }

    public void gameIsWin() {
        if (Recorder.getKillEnemyNum() == enemySize) {
            isWin = 1;
        }
        if (!hero.isLive()) {
            isWin = 2;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * 监听键盘事件
     *
     * @param e 监听键盘事件对象
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // 根据监听键盘从而改变坦克的方向和移动
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // 向上
                if (hero.getDirect() != 0)
                    hero.setDirect(0);
                hero.moveUp();
                break;
            case KeyEvent.VK_S: // 向下
                if (hero.getDirect() != 2)
                    hero.setDirect(2);
                hero.moveDown();
                break;
            case KeyEvent.VK_A: // 向左
                if (hero.getDirect() != 3)
                    hero.setDirect(3);
                hero.moveLeft();
                break;
            case KeyEvent.VK_D: // 向右
                if (hero.getDirect() != 1)
                    hero.setDirect(1);
                hero.moveRight();
                break;
            case KeyEvent.VK_J: // 坦克射击
                hero.shoot();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hitEnemyTank();
            hitHero();
            gameIsWin();
            this.repaint();
        }
    }
}
