package com.wetuo.traffic;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
信号灯切换的线程，兼具可视化界面的修改功能
 */
public class LampController extends JPanel {

	private Lamp currentLamp;

	//可视化切换灯
	protected void paintlight(Graphics g, int position_x, int position_y, String color) {
		if(color.equals("red")) {
			g.setColor(Color.red);//画红灯
			g.fillOval(position_x, position_y, 20, 20);//E2W
		}
		else {
			g.setColor(Color.green);
			g.fillOval(position_x, position_y, 20, 20);//E2W
		}
	}

	public void Change_light(int position_x,int position_y,String color,JPanel panel)
	{
		Graphics l=panel.getGraphics();
		paintlight(l,position_x,position_y,color);
	}

	public void change_light(String position,JPanel panel)
	{
		if(position.equals("S2N")||position.equals("N2S"))
		{
			Change_light(222,316,"red",panel);
			Change_light(126,70,"red",panel);
			Change_light(70,262,"green",panel);
			Change_light(316,166,"green",panel);
		}
		else
		{
			Change_light(222,316,"green",panel);
			Change_light(126,70,"green",panel);
			Change_light(70,262,"red",panel);
			Change_light(316,166,"red",panel);
		}
	}

	public LampController(String name,JPanel panel) {
		currentLamp = Lamp.S2N;//初始为S2N为绿灯
		currentLamp.light();//此灯变绿

		ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
		timer.scheduleAtFixedRate(
				new Runnable(){
					public void run() {
						System.out.println("CHANGE LIGHT");
                        change_light(currentLamp.name(),panel);
                        /*
                        在每次切换信号灯的时候
                        将路中间已经从队列出去的行驶车辆清空
                        */
                        Graphics tempx=panel.getGraphics();
                        tempx.clearRect(105,105,195,195);
						tempx.fillRect(0, 200, 400, 2);
						tempx.fillRect(200, 0, 2, 400);

						currentLamp = currentLamp.blackOut();
						currentLamp.set_flag(1);
					}
				}, 
				8,//8秒换灯
				8,
		TimeUnit.SECONDS);
	}

}
