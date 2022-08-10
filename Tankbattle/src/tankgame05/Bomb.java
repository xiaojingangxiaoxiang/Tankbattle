package tankgame05;
/*
    坦克爆炸效果 .就是一张gif图或者多张图片有序播放
 */
public class Bomb {
    int x;
    int y; //爆炸图片的坐标
    int life = 45; //生命周期
    boolean isLive = true; //是否还存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //画出时间，因为他也是有生命周期的
    public void LifeDown() {
        if (this.life > 0){
            this.life--;
        } else {
            this.isLive = false;
        }
    }
}
