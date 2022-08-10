package tankgame05;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    //用于存放地方坦克子弹
    Vector<Bullet> bullets = new Vector<>();
    //用于存放场上enmeyTanks。
    Vector<EnemyTank> enemyTanks = new Vector<>();

    int BulletNumber = 5;//敌人坦克子弹数量
    public EnemyTank(int x, int y, int direction) {
        super(x, y, direction);
    }

    public EnemyTank() {
    }
    //用于传入Mypanel初始化好的向量集合enemyTanks传入到这里
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public boolean isTouchTank() {
        switch (this.getDirection()) {
            case 0: //向上
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //遍历并获取当前敌人坦克向量
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {  //如果不是当前坦克本身
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2) {
                            //另外一个坦克的朝向是上或者下的时候
                            //此时this坦克的上边坐标是：this.getX()和this.getX()+40
                            //另外一个坦克的判定框为：enemyTank.getX()~enemyTank.getX()+40 && enemyTank.getY()~enemyTank.getY()+60
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;//左上角点在另一个坦克内部
                            }
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        } else if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1: //向右
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //遍历并获取当前敌人坦克向量
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2) {
                            if (this.getX()+60 >= enemyTank.getX()
                                    && this.getX()+60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (this.getX()+60 >= enemyTank.getX()
                                    && this.getX()+60 <= enemyTank.getX() + 40
                                    && this.getY()+40 >= enemyTank.getY()
                                    && this.getY()+40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3) {
                            if (this.getX()+60 >= enemyTank.getX()
                                    && this.getX()+60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            if (this.getX()+60 >= enemyTank.getX()
                                    && this.getX()+60 <= enemyTank.getX() + 60
                                    && this.getY()+40 >= enemyTank.getY()
                                    && this.getY()+40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: //向下
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //遍历并获取当前敌人坦克向量
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY()+60 >= enemyTank.getY()
                                    && this.getY()+60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (this.getX()+40 >= enemyTank.getX()
                                    && this.getX()+40 <= enemyTank.getX() + 40
                                    && this.getY()+60 >= enemyTank.getY()
                                    && this.getY()+60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY()+60 >= enemyTank.getY()
                                    && this.getY()+60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            if (this.getX()+40 >= enemyTank.getX()
                                    && this.getX()+40 <= enemyTank.getX() + 60
                                    && this.getY()+60 >= enemyTank.getY()
                                    && this.getY()+60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: //向左
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //遍历并获取当前敌人坦克向量
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (this != enemyTank) {
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY()+40 >= enemyTank.getY()
                                    && this.getY()+40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY()+40 >= enemyTank.getY()
                                    && this.getY()+40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }
    @Override
    public void run() {
        while (true) {
            Bullet bullet = null;
            //实现敌人坦克子弹没了继续发射,条件还有是坦克必须活着
            if (bullets.size() < BulletNumber && this.isLive) {
                switch (this.getDirection()) {
                    case 0:
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
                //这步骤将对象加入到集合中，这样不会丢失，因为每次指创建一个，不保存的话就没办法画出他们的轨迹
                bullets.add(bullet);
                //启动线程，只需启动对象即可不用遍历集合。每次的对象都不同
                new Thread (bullet).start();
                //改变敌人坦克速度
            }

            this.setSpeed(5);
            double direction = Math.random()*4;//生成[0,1)的随机数 double类型
            double persistent = Math.random()*4+2;
            switch ((int) direction) {
                case 0:
                    this.setDirection(0);
                    while ((int) persistent >=0 && !isTouchTank()) {
                        this.moveUp();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        --persistent;
                    }
                    break;
                case 1:
                    this.setDirection(1);
                    while ((int) persistent >=0 && !isTouchTank()) {
                        this.moveRight();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        --persistent;
                    }
                    break;
                case 2:
                    this.setDirection(2);
                    while ((int) persistent >=0 && !isTouchTank()) {
                        this.moveDown();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        --persistent;
                    }
                    break;
                case 3:
                    this.setDirection(3);
                    while ((int) persistent >=0 && !isTouchTank()) {
                        this.moveLeft();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        --persistent;
                    }
                    break;
            }
            if (!isLive) {
                System.out.println("敌人坦克线程退出");
                break;
            }
        }
    }
}
