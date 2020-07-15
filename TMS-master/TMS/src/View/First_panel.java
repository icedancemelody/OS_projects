package View;

import javax.swing.*;
import java.awt.*;

/*
初始化可视化界面，包含信号灯（开始），道路，信号灯板
*/
public class First_panel extends JPanel {
    protected void paintComponent(Graphics g) {

        g.setColor(Color.darkGray);//画路
        g.fillRect(0, 100, 400, 6);
        g.fillRect(0, 300, 400, 6);
        g.fillRect(100, 0, 6, 400);
        g.fillRect(300, 0, 6, 400);
        g.setColor(Color.gray);
        g.fillRect(0, 200, 400, 2);
        g.fillRect(200, 0, 2, 400);
        g.setColor(Color.blue);

        g.setColor(Color.black);//画信号灯板
        g.fillRect(202,306, 100, 40);
        g.fillRect(306,106, 40, 100);

        g.fillRect(106,60, 100, 40);
        g.fillRect(60,202,40, 100);

        g.setColor(Color.green);//画绿灯
        g.fillOval(222, 316, 20, 20);//N2S
        g.fillOval(126, 70, 20, 20);//S2N

        g.setColor(Color.red);//画红灯
        g.fillOval(70, 262, 20, 20);//E2W
        g.fillOval(316, 166, 20, 20);//W2E
    }
}
