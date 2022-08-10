package tankgame05;
/*
    子弹类：由多线程实现功能
 */
public class Bullet implements Runnable{
    private int x;
    private int y;
    private double speed = 10; //子弹速度
    private int direction;
    boolean isLive = true;
    //构造函数
    public Bullet(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void run() {
    //在多线程中实现发射功能
        while (true) {
            switch (direction) {
                case 0: //子弹向上
                    y -= speed;
                    break;
                case 1: //子弹向右
                    x += speed;
                    break;
                case 2: //子弹向下
                    y += speed;
                    break;
                case 3: //子弹向左
                    x -= speed;
                    break;
            }
            try {
                Thread.sleep(50); //每次都休眠50ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //子弹碰到边界就退出
            //子弹打到敌人坦克也要退出(就是isLive是false)
            if (!(x > 0 && x < 1000 && y > 0 && y < 750 && isLive)) {
                isLive = false;
                break;
            }
        }
    }
}
