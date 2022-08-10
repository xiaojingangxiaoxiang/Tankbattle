package tankgame05;

import java.util.Vector;

/*
    自己的坦克
 */
public class Hero extends Tank {
    //在hero坦克类中使用发射子弹 , 表示一个射击行为(线程)。用Vector存放多个子弹
    Bullet bullet = null;
    Vector<Bullet> bullets = new Vector<>();
    int Bulletcap = 3; //子弹的上线
    public Hero(int x, int y, int direction) {
        super(x, y, direction);
    }

    public Hero() {

    }

    public void shot () {
        if (bullets.size() == Bulletcap) {
            return;
        }
        //创建Bullet；根据当前tank的方向
        switch (getDirection()) {
            case 0: //当前tank方向为上
                //用创建对象的方式,所以用选用构造器传参更好，无需多个set
                bullet = new Bullet(getX()+20,getY(),0);
                break;
            case 1:
                bullet = new Bullet(getX()+60,getY()+20,1);
                break;
            case 2:
                bullet = new Bullet(getX()+20,getY()+60,2);
                break;
            case 3:
                bullet = new Bullet(getX(),getY()+20,3);
                break;
        }
        //把新创建的shot放入到shots
        bullets.add(bullet);
        //所有东西初始化好就可以启动了
        new Thread(bullet).start();
    }
}
