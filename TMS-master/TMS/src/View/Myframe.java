package View;

import com.wetuo.traffic.LampController;
import com.wetuo.traffic.Road;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
创建一个可视化窗口
*/

public class Myframe extends JFrame {

    private static JPanel temp7;//虚拟Panel，并没有实际使用

    First_panel temp1=new First_panel();//可修改的界面
    LampController temp5=new LampController("6",temp1);//将可修改界面传递到红绿灯交替控制任务中
    /*
     将可修改界面传递到四条道路车辆任务中
    */
    Road temp40=new Road("S2N",temp1);
    Road temp41=new Road("W2E",temp1);
    Road temp42=new Road("E2W",temp1);
    Road temp43=new Road("N2S",temp1);

    /*
    给每条道路加入对应的标签
    */

    JLabel s2n=new JLabel();
    JLabel n2s=new JLabel();
    JLabel w2e=new JLabel();
    JLabel e2w=new JLabel();

    public Myframe(String Title) {
        super(Title);

       /*
        给每条道路加入对应的标签
        */

        s2n.setText("N2S");
        s2n.setBounds(232,60,50,50);
        this.add(s2n);
        n2s.setText("S2N");
        n2s.setBounds(156,306,50,50);
        this.add(n2s);
        w2e.setText("E2W");
        w2e.setBounds(322,206,50,50);
        this.add(w2e);
        e2w.setText("W2E");
        e2w.setBounds(60,150,50,50);
        this.add(e2w);

        //参数设置
        this.setSize(420, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(temp1);
        //显示窗口
        this.setVisible(true);
    }

    /*
    传入控制器，初始化五个任务(1个信号灯交替控制，4个道路就绪车辆和移动车辆的任务）
     */
    public static void main(String[] args) {
        String[] directions = new String[] { "S2N", "E2W", "N2S", "W2E" };//四个方向
        for (int i = 0; i < directions.length; i++) {
            new Road(directions[i],temp7);
        }
        new LampController("S2N",temp7);
        new Myframe("Traffic controller");//创建界面
    }
}

