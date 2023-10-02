package TankBattle;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

public class TankGame01 extends JFrame {
    private MyPanel mp;
    public static void main(String[] args) {
        TankGame01 tankGame01 = new TankGame01();
    }

    public TankGame01() {
        System.out.println("开启新游戏（1）\n继续上一局（2）");
        Scanner scanner = new Scanner(System.in);
        String isNext = scanner.next();
        // 创建面板
        mp = new MyPanel(isNext);
        Thread thread = new Thread(mp);
        thread.start();
        // 在JFrame画框中添加mp面板
        this.add(mp);
        // 设置画框大小
        this.setSize(1300, 778);
        // 监听mp面板的键盘事件
        this.addKeyListener(mp);
        // 设置点击关闭窗口则退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置显示
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.saveRecorder();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("监听到关闭窗口");
                System.exit(0);
            }
        });
    }
}
