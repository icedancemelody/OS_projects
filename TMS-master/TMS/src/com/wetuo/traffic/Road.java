package com.wetuo.traffic;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Road extends JPanel {

	/*
	四条路的车辆等待队列
	 */
	private Queue<Run_cars> Run_car_S2N=new LinkedList<Run_cars>();
	private Queue<Run_cars> Run_car_N2S=new LinkedList<Run_cars>();
	private Queue<Run_cars> Run_car_W2E=new LinkedList<Run_cars>();
	private Queue<Run_cars> Run_car_E2W=new LinkedList<Run_cars>();

	/*
	记录每条路车辆等待队列的长度
	 */
	private int count_1=0;
	private int count_2=0;
	private int count_3=0;
	private int count_4=0;
	//就绪等待小车

	protected void paintcar(Graphics g, int position_x, int position_y,String direction,int type) {

		/*
		这个swtich时为了在不同的Road线程可视化等待队列车辆的
		 */
		switch (direction)
		{
			case "S2N":{
				if(type==1) {
					g.setColor(Color.black);//画普通车
					g.fillOval(position_x, position_y + 10 * count_1, 10, 10);
					Run_cars x1=new Run_cars(position_x,position_y + 10 * count_1,1,"S2N");
                    Run_car_S2N.offer(x1);
				}
				else{
					g.setColor(Color.red);//画特殊车
					g.fillOval(position_x, position_y + 10 * count_1, 10, 10);
					Run_cars x1=new Run_cars(position_x,position_y + 10 * count_1,2,"S2N");
					Run_car_S2N.add(x1);
				}
				count_1++;
				break;
			}
			case "N2S":{
				if(type==1) {
					g.setColor(Color.black);//画普通车
					g.fillOval(position_x, position_y - 10 * count_2, 10, 10);
					Run_cars x1=new Run_cars(position_x,position_y - 10 * count_2,1,"N2S");
					Run_car_N2S.offer(x1);
				}
				else{
					g.setColor(Color.red);//画特殊车
					g.fillOval(position_x, position_y - 10 * count_2, 10, 10);
					Run_cars x1=new Run_cars(position_x,position_y - 10 * count_2,2,"N2S");
					Run_car_N2S.offer(x1);
				}
				count_2++;
				break;
			}
			case "W2E":{
				if(type==1) {
					g.setColor(Color.black);//画普通车
					g.fillOval(position_x- 10 * count_3, position_y , 10, 10);
					Run_cars x1=new Run_cars(position_x-10 * count_3,position_y ,1,"W2E");
					Run_car_W2E.offer(x1);
				}
				else{
					g.setColor(Color.red);//画特殊车
					g.fillOval(position_x- 10 * count_3, position_y , 10, 10);
					Run_cars x1=new Run_cars(position_x-10 * count_3,position_y,2,"W2E");
					Run_car_W2E.offer(x1);
				}
				count_3++;
				break;
			}
			case "E2W":{
				if(type==1) {
					g.setColor(Color.black);//画普通车
					g.fillOval(position_x+ 10 * count_4, position_y , 10, 10);
					Run_cars x1=new Run_cars(position_x+ 10 * count_4,position_y,1,"E2W");
					Run_car_E2W.offer(x1);
				}
				else{
					g.setColor(Color.red);//画特殊车
					g.fillOval(position_x+ 10 * count_4, position_y , 10, 10);
					Run_cars x1=new Run_cars(position_x+ 10 * count_4,position_y,2,"E2W");
					Run_car_E2W.offer(x1);
				}
				count_4++;
				break;
			}
		}
	}


	/*
	可视化就绪等待车辆
	 */
	public void addcar(int position_x,int position_y,JPanel panel,String direction,int type)
	{
		Graphics h=panel.getGraphics();
		paintcar(h, position_x, position_y,direction,type);
	}
	public void Add_car(String direction,JPanel panel,int type)
	{

		switch (direction)
		{
			case "S2N":{
				addcar(146,316,panel,"S2N",type);
				break;
			}
			case "N2S":{
				addcar(242,70,panel,"N2S",type);
				break;
			}
			case "W2E":{
				addcar(70,146,panel,"W2E",type);
				break;
			}
			case "E2W":{
				addcar(316,252,panel,"E2W",type);
				break;
			}
		}
	}


	List<String> vechicles = new ArrayList<String>();//等待队列数组
	private String name = null;
	int special_car_num;//特殊车辆数目
	public Road(String name,JPanel panel) {
		this.name = name;
		ExecutorService pool = Executors.newSingleThreadExecutor();
		//创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
		//这个方法接收一个Runnable实例，并且异步的执行
		pool.execute(new Runnable(){
			@Override
			public void run() {

				for(int i=1;i<10000;i++) {
					try {
						final double d = Math.random();
						final int sleeptime = (int)(d*100)+10;
						Thread.sleep(sleeptime*100);//随机停止0-10秒来就绪线程池任务
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//获取当前队列的特殊车辆数目
					if(Lamp.valueOf(Road.this.name).get_flag()==1){
					special_car_num=Lamp.valueOf(Road.this.name).get_num();
						Lamp.valueOf(Road.this.name).set_flag(0);}

					//如果上条道路有特殊车辆没有走完，则加入到此次行动的队列中
					if(Lamp.valueOf(Road.this.name).get_last_num()>0)
					{
						String last1=Lamp.valueOf(Road.this.name).get_last();
						vechicles.add(last1+"_"+i+"_"+"speical");//上一次没走完的特殊车
						if(panel!=null) Add_car(Road.this.name,panel,2);
						Lamp.valueOf(last1).decrease_num();
					}

					//本次队列特殊车辆
                    if(special_car_num>0)
					{
						vechicles.add(Road.this.name+"_"+i+"_"+"speical");//把特殊车先加入到等待队列（先来先服务，就体现了速度快）
						if(panel!=null) Add_car(Road.this.name,panel,2);
						special_car_num--;
						Lamp.valueOf(Road.this.name).set_flag(special_car_num);
					}
					//普通车加入到等待队列
                    else
					{
						if(panel!=null) Add_car(Road.this.name,panel,1);
						vechicles.add(Road.this.name+"_"+i+"_"+"common");
						}
				}
			}
			
		});
		//定时器
		ScheduledExecutorService timer = Executors.newScheduledThreadPool(100);
		timer.scheduleAtFixedRate(
				new Runnable(){
					public void run() {
						//检查有没有车
						if(vechicles.size()>0) {
								boolean lighted = 
										Lamp.valueOf(Road.this.name).isLighted();//检查此方向车是什么灯
							    /*
							    为了弥补Lamp中opposite为空的情况，在这里
							    将N2S和W2E两个线程的信号灯切换完善
							     */
							    switch (Road.this.name)
								{
									case"N2S":{
										boolean lighted1 =
												Lamp.valueOf(Road.this.name).isLighted();//检查此方向车是什么灯
										if(lighted1)
										{
											Lamp.valueOf(Lamp.valueOf(Road.this.name).get_opposite()).light();
										}
										else
										{
											Lamp.valueOf(Lamp.valueOf(Road.this.name).get_opposite()).blackOut();
										}
										break;
									}
									case"W2E":{
										boolean lighted2 =
												Lamp.valueOf(Road.this.name).isLighted();//检查此方向车是什么灯
										if(lighted2)
										{
											Lamp.valueOf(Lamp.valueOf(Road.this.name).get_opposite()).light();
										}
										else
										{
											Lamp.valueOf(Lamp.valueOf(Road.this.name).get_opposite()).blackOut();
										}
										break;
									}
								}

                                //当是绿灯
								if(lighted) {
								String temp=vechicles.get(0);
                                String temp1=temp.substring(0,3);//取得这辆车的方向
								String temp2=temp.substring(6,7);//取得这辆车的种类
								/*
								当车辆方向和绿灯方向一致，准许通行
								当车辆方向和绿灯方向不一致，但是特殊车辆，准许通行
								并且将队列中的车辆从等待位置放入路中央，表示移动
								*/
								if(temp1.equals(Road.this.name))
								{
									System.out.println(vechicles.remove(0)+" is traversing !");
									switch (Road.this.name) {
										case "S2N": {
											while (Run_car_S2N.size() > 0) {
												Run_cars temp_car = Run_car_S2N.element();
												Graphics x1 = panel.getGraphics();
													if (temp_car.type == 1) {
														x1.setColor(Color.black);
														x1.fillOval(temp_car.position_x, temp_car.position_y - 150, 10, 10);
													}
													else {
														x1.setColor(Color.red);
														x1.fillOval(temp_car.position_x, temp_car.position_y - 150, 10, 10);
													}
													x1.clearRect(temp_car.position_x, temp_car.position_y, 10, 10);
													Run_car_S2N.remove();
													count_1--;
											}
											break;
										}
										case "N2S": {
											while (Run_car_N2S.size() > 0) {
												Run_cars temp_car = Run_car_N2S.element();
												Graphics x1 = panel.getGraphics();
												if(temp_car.type==1)
												{
													x1.setColor(Color.black);
													x1.fillOval(temp_car.position_x, temp_car.position_y + 150, 10, 10);
												}
												else
												{
													x1.setColor(Color.red);
													x1.fillOval(temp_car.position_x, temp_car.position_y + 150, 10, 10);
												}


												x1.clearRect(temp_car.position_x, temp_car.position_y, 10, 10);
												Run_car_N2S.remove();
												count_2--;
											}
										}
										case "W2E": {
											while (Run_car_W2E.size() > 0) {
												Run_cars temp_car = Run_car_W2E.element();
												Graphics x1 = panel.getGraphics();

												if(temp_car.type==1)
												{
													x1.setColor(Color.black);
													x1.fillOval(temp_car.position_x+150, temp_car.position_y , 10, 10);
												}
												else
												{
													x1.setColor(Color.red);
													x1.fillOval(temp_car.position_x+150, temp_car.position_y , 10, 10);
												}
												x1.clearRect(temp_car.position_x, temp_car.position_y, 10, 10);
												Run_car_W2E.remove();
												count_3--;
											}
											break;
										}
										case "E2W": {
											while (Run_car_E2W.size() > 0) {
											Run_cars temp_car = Run_car_E2W.element();
											Graphics x1 = panel.getGraphics();
											if(temp_car.type==1)
											{
												x1.setColor(Color.black);
												x1.fillOval(temp_car.position_x-150, temp_car.position_y , 10, 10);
											}
											else
											{
												x1.setColor(Color.red);
												x1.fillOval(temp_car.position_x-150, temp_car.position_y , 10, 10);
											}
											x1.clearRect(temp_car.position_x, temp_car.position_y, 10, 10);
											Run_car_E2W.remove();
											count_4--;
										}break;
										}
									}
								}
								//特殊车辆
								else if(temp2.equals("s"))
								{
									/*
									当特殊车辆所在方向为红灯，理论上是可以移动
									特殊车辆移动的可视化实现，由于耗时过长
									导致无法在信号灯每8秒的切换周期之内完成
									会让可视化变得不合理
									所以这种情况下特殊车辆的移动，只进行文字上的输出
									（我太菜了tat)
									 */

									/*
									Graphics x1 = panel.getGraphics();
									switch (Lamp.valueOf(Road.this.name).get_last())
									{
										case"S2N":{
											Run_cars c1=Run_car_S2N.element();
											if(c1.type==2)
											{
												x1.setColor(Color.red);
												x1.fillOval(c1.position_x, c1.position_y - 150, 10, 10);
												x1.clearRect(c1.position_x, c1.position_y, 10, 10);
												Run_car_S2N.remove();
												count_1--;
											}
											break;

										}
										case"N2S":{
											Run_cars c1=Run_car_N2S.element();
											if(c1.type==2)
											{
												x1.setColor(Color.red);
												x1.fillOval(c1.position_x, c1.position_y + 150, 10, 10);
												x1.clearRect(c1.position_x, c1.position_y, 10, 10);
												Run_car_N2S.remove();
												count_2--;
											}
											break;

										}
										case"W2E":{
											Run_cars c1=Run_car_W2E.element();
											if(c1.type==2)
											{
												x1.setColor(Color.red);
												x1.fillOval(c1.position_x+150, c1.position_y , 10, 10);
												x1.clearRect(c1.position_x, c1.position_y, 10, 10);
												Run_car_W2E.remove();
												count_3--;
											}
											break;

										}
										case"E2W":{
											Run_cars c1=Run_car_E2W.element();
											if(c1.type==2)
											{
												x1.setColor(Color.red);
												x1.fillOval(c1.position_x-150, c1.position_y , 10, 10);
												x1.clearRect(c1.position_x, c1.position_y, 10, 10);
												Run_car_E2W.remove();
												count_4--;
											}
											break;

										}
									}
                                             */
										System.out.println(vechicles.remove(0)+" is traversing !");
								}
							}
						}
					}
				}, 
 				5,
				2,
				TimeUnit.SECONDS);
	}
}
