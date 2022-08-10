package tankgame05;

import java.io.*;
import java.util.Vector;

/*
    该类用于记录我方坦克击毁敌人坦克数
    游戏结束时，将文件写入到文件(IO).myRecord.txt
 */
public class Recorder {
    //定义变量，记录我方坦克数量
    private static int allEnemyTankNum = 0;
    //定义IO对象准备写数据到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "D:/myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    //定义一个Node的Vector,用于保存敌人的信息Node。
    private static Vector<Node> nodes = new Vector<>();
    //当游戏退出时，将allEnemyTankNum保存到文件src..
    public static void keepRecord() throws IOException {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");//类属性调用
            if (enemyTanks != null) {
                for (int i = 0; i < Recorder.enemyTanks.size(); i++) {
                    EnemyTank enemyTank = Recorder.enemyTanks.get(i);
                    if (enemyTank.isLive) { //建议判断
                        bw.write(enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirection() + "\r\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }
    public static void addallEnemyTankNum() {
        //击毁一个敌人坦克的时候++
        Recorder.allEnemyTankNum++;
    }
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //该方法在继续上局游戏的时候调用
    public static Vector<Node> getNodesAndEnemyTankRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件，生成nodes集合
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]),
                        Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return nodes;
    }

}
