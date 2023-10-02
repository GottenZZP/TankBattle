//package TankBattle;
//
//import javax.swing.*;
//import java.awt.*;
//
//@SuppressWarnings({"all"})
//public class DrawCircle extends JFrame { // Jframe可以理解为一个画框，表示窗口
//    private MyPanel mp = null;
//    public static void main(String[] args) {
//        new DrawCircle();
//    }
//
//    public DrawCircle() {
//        // 初始化面板
//        mp = new MyPanel();
//        // 把面板放入到窗口
//        this.add(mp);
//        // 设置窗口大小
//        this.setSize(400, 300);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        // 设置显示
//        this.setVisible(true);
//    }
//}
//
//class MyPanel extends JPanel {
//    /**
//     * MyPanel对象是一个画板
//     * Graphics g可以理解成一支画笔，可以提供很多绘图方法
//     * @param g  the <code>Graphics</code> context in which to paint
//     */
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g); // 调用父类方法完成初始化
//        g.drawOval(10, 10, 100, 100);
//    }
//}