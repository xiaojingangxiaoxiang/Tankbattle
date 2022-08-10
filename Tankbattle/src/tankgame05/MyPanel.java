package tankgame05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Objects;
import java.util.Vector;

/*
    坦克大战的绘图区域
 */
// 为了让Panel不停重绘子弹，需要将JPanel实现接口Runnable
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人坦克数量
    //初始化敌人坦克用Vector集合封装为以后多线程着想
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int EnmeyTankSizes = 5;
    //创建一个Vecter集合用来存储或者说计数爆炸情况
    Vector<Bomb> bombs = new Vector<>();
    //用一张gif图片表示爆炸效果
    Image image1 = null; //爆炸效果图片
    //存放坦克当前位置
    Vector<Tank> tankPositions = new Vector<>();
    //
    Vector<Node> nodes = null;
    public MyPanel (String key){//构造器
        //将敌人坦克集合传入到Recorder类
        Recorder.setEnemyTanks(enemyTanks);
        //读取上局游戏
        if (new File("D:/myRecord.txt").exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();//读取分数，和返回敌人坦克位置集合
        } else {
            System.out.println("文件丢失，重新开始游戏");
            key = "N";
        }
        switch (key) {
            case "J":
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY(), node.getDirection());
                    //enemyTanks.add(enemyTank);出现bug的原因add了两次
                    //将敌人坦克集合传入到EnemyTank类
                    enemyTank.setEnemyTanks(enemyTanks);
                    //创建敌人坦克子弹
                    Bullet bullet = new Bullet(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    //加入到enemyTank的Vector成员
                    enemyTank.bullets.add(bullet);
                    //启动子弹线程
                    new Thread(bullet).start();
                    enemyTanks.add(enemyTank);
                    //启动敌人坦克运动线程
                    new Thread(enemyTank).start();
                }
                break;
            case "N" : //新游戏
            //for 循环遍历装填
            for (int i =0; i < EnmeyTankSizes; i++) {
                //创建敌人坦克
                EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0, 1);
                //将敌人坦克集合传入到EnemyTank类
                enemyTank.setEnemyTanks(enemyTanks);
                //创建敌人坦克子弹
                Bullet bullet = new Bullet(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                //加入到enemyTank的Vector成员
                enemyTank.bullets.add(bullet);
                //启动子弹线程
                new Thread(bullet).start();
                enemyTanks.add(enemyTank);
                //启动敌人坦克运动线程
                new Thread(enemyTank).start();
            }
            break;
            default:
                System.out.println("输入指令有误");
                break;
        }
        //初始化hero坦克
        hero = new Hero(0,500, 0);//初始化自己的坦克位置
        hero.setSpeed(5);  //初始化坦克的速度
        //初始化爆炸效果图片
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/Boom.gif"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);//游戏(画布大小)区域。填充矩形，默认黑色
        //编写方法显示我方击毁敌方坦克的数量。
        this.showInfo(g);
        //画出坦克--封装到方法drawTank();
        if (hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 0);
        }
        //画出hero坦克的子弹
        for (int i = 0; i < hero.bullets.size(); i++) {
            Bullet bullet = hero.bullets.get(i);
            if (bullet != null && bullet.isLive) {
                g.draw3DRect(bullet.getX(), bullet.getY(),2,2,false);
            } else {
                hero.bullets.remove(bullet);
            }
        }
//        if (hero.bullets != null && hero.bullets.bull) {
//            g.draw3DRect(hero.bullet.getX(), hero.bullet.getY(),2,2,false);
//        }
        //画出爆炸效果，因为在击中时候存放bomb效果，所以不用判断tank是否islive
        for (Bomb bomb : bombs) {
            if (bomb.isLive) {
                g.drawImage(image1, bomb.x, bomb.y, 40, 60, this);
            }
            bomb.LifeDown();
        }
        //取出敌人坦克属性
        for (EnemyTank enemyTank : enemyTanks) {
            //判断当前敌人坦克是否活着 ，活着时画出敌人坦克
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
                //画出敌人坦克子弹(写在坦克里面是因为坦克没了肯定不发射子弹)
                for (int j = 0; j < enemyTank.bullets.size(); j++) {
                    //取出子弹
                    Bullet bullet = enemyTank.bullets.get(j);
                    if (bullet.isLive) {
                        g.draw3DRect(bullet.getX(), bullet.getY(), 1, 1, false);
                    } else {
                        //移除子弹
                        enemyTank.bullets.remove(bullet);
                    }
                }
            }
        }
    }

    /**
     *
     * @param x 坦克的左上角x坐标
     * @param y 坦克的左上角y坐标
     * @param g 画笔
     * @param direction 坦克的方向(上下左右)
     * @param type 坦克的类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        switch (type) {//根据不同类型的坦克设置不同的颜色
            case 0: //我们的坦克(青色的)
                g.setColor(Color.cyan);
                break;
            case 1: //敌人的坦克(红色)
                g.setColor(Color.RED);
                break;
        }
        switch (direction) {//根据不同的方向绘制坦克 0：上 1：右 2：下 3：左
            case 0: //代表向上的坦克
                g.fill3DRect(x, y, 10, 60, false);//左边的轮子 绘制一个填充有当前颜色的3D高亮矩形。矩形的边缘将被突出示，使其看起来好像边缘是从左上角倾斜并点亮。用于突出显示效果的颜色将根据当前颜色确定。
                g.fill3DRect(x+30,y,10,60,false);//右边的轮子
                g.fill3DRect(x+10,y+10,20,40,false);//坦克的盖子
                g.drawOval(x+10,y+20,20,20);//坦克小圆盖
                g.drawLine(x+20,y+30,x+20,y);//画出炮筒
                break;
            case 1: //表示向右的坦克
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.drawOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2: //向下的坦克
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.drawOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3: //表示向左的坦克
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.drawOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;
            default:
                System.out.println("先不做处理");
        }
    }

    //编写子弹碰到坦克，坦克消失的方法，由于需要用到坦克的参数和子弹的参数，所以写在两者都创建的地方
    //干脆在重绘的时候判断是否有坦克爆炸
    public void hitTank(Bullet bullet, Tank tank) {
        //判断子弹击中坦克
        switch (tank.getDirection()) {
            case 0: //坦克向上
            case 2: //坦克向下
                if (bullet.getX() > tank.getX() && bullet.getX() < tank.getX()+ 40 && bullet.getY() > tank.getY() && bullet.getY() < tank.getY()+60) {
                    bullet.isLive = false;
                    tank.isLive  = false;
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1: //坦克向右
            case 3: //坦克向左
                if (bullet.getX() > tank.getX() && bullet.getX() < tank.getX()+ 60 && bullet.getY() > tank.getY() && bullet.getY() < tank.getY()+40) {
                    bullet.isLive = false;
                    tank.isLive  = false;
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }
    //编写击毁敌方坦克数，显示分数的方法
    public void showInfo(Graphics g) {
        //画出玩家的总成绩
        //设置颜色
        g.setColor(Color.black);
        Font 宋体 = new Font("宋体", Font.BOLD, 25);
        g.setFont(宋体);
        g.drawString("您累计击毁敌方坦克",1020,30);
        //画出坦克图标
        this.drawTank(1020, 60, g, 0,1);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTankNum()+"", 1080, 100);
        this.repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //实现键盘按下wsad控制坦克移动
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_W) {
            //坦克方向为上
            hero.setDirection(0);//方向置为上
            hero.moveUp();
        } else if (e.getKeyCode()==KeyEvent.VK_D) {
            //坦克方向为右
            hero.setDirection(1);//坦克方向置为右
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            //坦克方向为下
            hero.setDirection(2);//坦克方向置为下
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            //坦克方向为左
            hero.setDirection(3);//坦克方向置为左
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
                hero.shot(); //启动shot方法
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
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //我方子弹击中敌方坦克时
            for (int i = 0; i < hero.bullets.size(); i++) {
                Bullet bullet = hero.bullets.get(i);
                if (bullet != null && bullet.isLive) {// 当我的子弹还是存活的
                    //遍历所有敌人的坦克
                    for (int j = 0; j < enemyTanks.size(); j++) {
                        EnemyTank enemyTank = enemyTanks.get(j);
                        hitTank(bullet, enemyTank);
                        if (!(enemyTank.isLive)) {
                            enemyTanks.remove(enemyTank);
                            Recorder.addallEnemyTankNum();
                        }
                    }
                }
            }
            //敌方子弹击中我方坦克时
            for (int i = 0; i < enemyTanks.size(); i++) { //取出敌方坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                for (int j = 0; j < enemyTank.bullets.size(); j++) { //分别取出敌方坦克的子弹
                    Bullet bullet = enemyTank.bullets.get(j);
                    if (bullet != null && bullet.isLive && hero.isLive) { //如果敌方有子弹并且时isLive状态时候，并且我方坦克是isLive
                        hitTank(bullet, hero);
                    }
                }
            }
            this.repaint(); //每间隔50毫秒重写绘制
        }
    }
}
