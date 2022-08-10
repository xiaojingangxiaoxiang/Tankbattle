package tankgame05;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

/*

 */
public class HspTankGame05 extends JFrame {
    //定义MyPanel  这里方面下面调用的，先写一个(全局变量);
    private MyPanel mp = null;
    //用于判断游戏是否继续还是新开一句游戏
    static String key = null;
    public static void main(String[] args) {
        System.out.println("==========请输入您的选择===========");
        System.out.println("===========J:继续游戏=============");
        System.out.println("============N:新游戏==============");
        Scanner scanner = new Scanner(System.in);//标准键盘输入
        key = scanner.next();
        HspTankGame05 hspTankGame01 = new HspTankGame05(key);
    }

    public HspTankGame05(String key){
        mp = new MyPanel(key);
        this.add(mp); // 把面板添加到窗口
        new Thread(mp).start(); //启动不停绘制线程0
        this.setSize(1000,750);  //设置窗口大小
        this.addKeyListener(mp);//多态写法，传入一个keylistener
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame 中增加响应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.keepRecord();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("监听到关闭窗口");
                System.exit(0);
            }
        });
    }
}
