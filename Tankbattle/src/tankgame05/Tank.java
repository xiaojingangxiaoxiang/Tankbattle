package tankgame05;
/*
    这个是坦克超类，会有一些特殊坦克继承这个类
 */
public class Tank {
    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标
    private int direction;//坦克的方向 0上 1右 2 下 3左
    boolean isLive = true;
    private double speed = 5;//控制坦克移动速度

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void moveUp(){
        //向上移动
        if (y > 0 && isLive) {
            y -= speed;
            if (y < 0) {
                y = 0;
            }
        }
    }
    public void moveDown(){
        //向下移动
        if (y <= 690 && isLive) {
            y += speed;
            if (y > 690) {
                y = 690;
            }
        }
    }
    public void moveRight(){
        //向右移动
        if (x <= 940 && isLive) {
            x += speed;
            if (x > 940) {
                x = 940;
            }
        }
    }
    public void moveLeft(){
        //向左移动
        if (x >= 0 && isLive) {
            x -= speed;
            if (x < 0) {
                x = 0;
            }
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
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

    public Tank(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Tank() {
    }
}
