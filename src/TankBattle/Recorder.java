package TankBattle;

import java.io.*;
import java.util.Vector;

public class Recorder {
    private static int killEnemyNum = 0;
    private static String recordFilePath = "src/TankBattle/myRecord.txt";
    private static Vector<EnemyTank> enemyTanks;
    private static Vector<Wall> walls;
    private static final Vector<Node> nodes = new Vector<>();
    private static Hero hero;

    public static String getRecordFilePath() {
        return recordFilePath;
    }

    public static void setKillEnemyNum(int killNum) {
        killEnemyNum = killNum;
    }

    public static void setRecordFilePath(String recordFilePath) {
        Recorder.recordFilePath = recordFilePath;
    }

    /**
     * 读取记录文件，记录文件格式如下
     * 写入格式：
     *  击杀敌方坦克数量: num
     *  Hero坐标方向: x y d
     *  剩余敌方坦克坐标方向:
     *      坦克1: x y direct enemyTank
     *      ...
     *      坦克n: x y direct enemyTank
     *  当前关卡剩余墙体坐标信息:
     *      墙体1: x y type wall
     *      ...
     *      墙体n: x y type wall
     * @return 返回一个节点
     */
    public static Vector<Node> parseRecorder() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(recordFilePath));
            // 读取杀敌数
            killEnemyNum = Integer.parseInt(reader.readLine());
            // 获得Hero的坐标及方向
            String[] heroXYD = reader.readLine().split(" ");
            Node heroNode = new Node(Integer.parseInt(heroXYD[0]), Integer.parseInt(heroXYD[1]), Integer.parseInt(heroXYD[2]), 0);
            nodes.add(heroNode);
            // 获得所有敌方坦克坐标及方向
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] pLine = line.split(" ");
                if (pLine[3].equals("enemyTank")) {
                    Node enemyNode = new Node(Integer.parseInt(pLine[0]), Integer.parseInt(pLine[1]), Integer.parseInt(pLine[2]), 0);
                    nodes.add(enemyNode);
                } else if (pLine[3].equals("wall")) {
                    Node wallNode = new Node(Integer.parseInt(pLine[0]), Integer.parseInt(pLine[1]), 999, Integer.parseInt(pLine[2]));
                    nodes.add(wallNode);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return nodes;
    }

    public static void setHero(Hero hero) {
        Recorder.hero = hero;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static void setWalls(Vector<Wall> walls) {
        Recorder.walls = walls;
    }

    public static void addKillEnemyNum() {
        killEnemyNum++;
    }

    public static int getKillEnemyNum() {
        return killEnemyNum;
    }

    /**
     * 写入格式：
     *  击杀敌方坦克数量: num
     *  Hero坐标方向: x y d
     *  剩余敌方坦克坐标方向:
     *      坦克1: x y direct enemyTank
     *      ...
     *      坦克n: x y direct enemyTank
     *  当前关卡剩余墙体坐标信息:
     *      墙体1: x y type wall
     *      ...
     *      墙体n: x y type wall
     * @throws IOException: 抛出IO警告
     */
    public static void saveRecorder() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(recordFilePath));
        // 写入击杀敌方坦克数量
        writer.write(killEnemyNum + "\r\n");
        // 写入Hero坐标及方向
        writer.write(hero.getX() + " " + hero.getY() + " " + hero.getDirect() + "\r\n");
        // 循环写入敌方所有坦克位置信息及方向
        for (EnemyTank enemyTank : enemyTanks) {
            if (enemyTank.isLive) {
                String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect() + " enemyTank" + "\r\n";
                writer.write(record);
            }
        }
        // TODO 目前只有一关，所以固定死了关卡数
//        writer.write("walls" + "\r\n");
        for (Wall wall : walls) {
            if (wall.isLive()) {
                String record = wall.getX() + " " + wall.getY() + " " + wall.getType() + " wall" + "\r\n";
                writer.write(record);
            }
        }
        writer.close();
    }
}
