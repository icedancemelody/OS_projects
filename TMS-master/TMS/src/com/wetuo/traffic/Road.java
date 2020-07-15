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
	����·�ĳ����ȴ�����
	 */
	private Queue<Run_cars> Run_car_S2N=new LinkedList<Run_cars>();
	private Queue<Run_cars> Run_car_N2S=new LinkedList<Run_cars>();
	private Queue<Run_cars> Run_car_W2E=new LinkedList<Run_cars>();
	private Queue<Run_cars> Run_car_E2W=new LinkedList<Run_cars>();

	/*
	��¼ÿ��·�����ȴ����еĳ���
	 */
	private int count_1=0;
	private int count_2=0;
	private int count_3=0;
	private int count_4=0;
	//�����ȴ�С��

	protected void paintcar(Graphics g, int position_x, int position_y,String direction,int type) {

		/*
		���swtichʱΪ���ڲ�ͬ��Road�߳̿��ӻ��ȴ����г�����
		 */
		switch (direction)
		{
			case "S2N":{
				if(type==1) {
					g.setColor(Color.black);//����ͨ��
					g.fillOval(position_x, position_y + 10 * count_1, 10, 10);
					Run_cars x1=new Run_cars(position_x,position_y + 10 * count_1,1,"S2N");
                    Run_car_S2N.offer(x1);
				}
				else{
					g.setColor(Color.red);//�����⳵
					g.fillOval(position_x, position_y + 10 * count_1, 10, 10);
					Run_cars x1=new Run_cars(position_x,position_y + 10 * count_1,2,"S2N");
					Run_car_S2N.add(x1);
				}
				count_1++;
				break;
			}
			case "N2S":{
				if(type==1) {
					g.setColor(Color.black);//����ͨ��
					g.fillOval(position_x, position_y - 10 * count_2, 10, 10);
					Run_cars x1=new Run_cars(position_x,position_y - 10 * count_2,1,"N2S");
					Run_car_N2S.offer(x1);
				}
				else{
					g.setColor(Color.red);//�����⳵
					g.fillOval(position_x, position_y - 10 * count_2, 10, 10);
					Run_cars x1=new Run_cars(position_x,position_y - 10 * count_2,2,"N2S");
					Run_car_N2S.offer(x1);
				}
				count_2++;
				break;
			}
			case "W2E":{
				if(type==1) {
					g.setColor(Color.black);//����ͨ��
					g.fillOval(position_x- 10 * count_3, position_y , 10, 10);
					Run_cars x1=new Run_cars(position_x-10 * count_3,position_y ,1,"W2E");
					Run_car_W2E.offer(x1);
				}
				else{
					g.setColor(Color.red);//�����⳵
					g.fillOval(position_x- 10 * count_3, position_y , 10, 10);
					Run_cars x1=new Run_cars(position_x-10 * count_3,position_y,2,"W2E");
					Run_car_W2E.offer(x1);
				}
				count_3++;
				break;
			}
			case "E2W":{
				if(type==1) {
					g.setColor(Color.black);//����ͨ��
					g.fillOval(position_x+ 10 * count_4, position_y , 10, 10);
					Run_cars x1=new Run_cars(position_x+ 10 * count_4,position_y,1,"E2W");
					Run_car_E2W.offer(x1);
				}
				else{
					g.setColor(Color.red);//�����⳵
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
	���ӻ������ȴ�����
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


	List<String> vechicles = new ArrayList<String>();//�ȴ���������
	private String name = null;
	int special_car_num;//���⳵����Ŀ
	public Road(String name,JPanel panel) {
		this.name = name;
		ExecutorService pool = Executors.newSingleThreadExecutor();
		//����һ�����̻߳����̳߳أ���ֻ����Ψһ�Ĺ����߳���ִ�����񣬱�֤����������ָ��˳��(FIFO, LIFO, ���ȼ�)ִ�С�
		//�����������һ��Runnableʵ���������첽��ִ��
		pool.execute(new Runnable(){
			@Override
			public void run() {

				for(int i=1;i<10000;i++) {
					try {
						final double d = Math.random();
						final int sleeptime = (int)(d*100)+10;
						Thread.sleep(sleeptime*100);//���ֹͣ0-10���������̳߳�����
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//��ȡ��ǰ���е����⳵����Ŀ
					if(Lamp.valueOf(Road.this.name).get_flag()==1){
					special_car_num=Lamp.valueOf(Road.this.name).get_num();
						Lamp.valueOf(Road.this.name).set_flag(0);}

					//���������·�����⳵��û�����꣬����뵽�˴��ж��Ķ�����
					if(Lamp.valueOf(Road.this.name).get_last_num()>0)
					{
						String last1=Lamp.valueOf(Road.this.name).get_last();
						vechicles.add(last1+"_"+i+"_"+"speical");//��һ��û��������⳵
						if(panel!=null) Add_car(Road.this.name,panel,2);
						Lamp.valueOf(last1).decrease_num();
					}

					//���ζ������⳵��
                    if(special_car_num>0)
					{
						vechicles.add(Road.this.name+"_"+i+"_"+"speical");//�����⳵�ȼ��뵽�ȴ����У������ȷ��񣬾��������ٶȿ죩
						if(panel!=null) Add_car(Road.this.name,panel,2);
						special_car_num--;
						Lamp.valueOf(Road.this.name).set_flag(special_car_num);
					}
					//��ͨ�����뵽�ȴ�����
                    else
					{
						if(panel!=null) Add_car(Road.this.name,panel,1);
						vechicles.add(Road.this.name+"_"+i+"_"+"common");
						}
				}
			}
			
		});
		//��ʱ��
		ScheduledExecutorService timer = Executors.newScheduledThreadPool(100);
		timer.scheduleAtFixedRate(
				new Runnable(){
					public void run() {
						//�����û�г�
						if(vechicles.size()>0) {
								boolean lighted = 
										Lamp.valueOf(Road.this.name).isLighted();//���˷�����ʲô��
							    /*
							    Ϊ���ֲ�Lamp��oppositeΪ�յ������������
							    ��N2S��W2E�����̵߳��źŵ��л�����
							     */
							    switch (Road.this.name)
								{
									case"N2S":{
										boolean lighted1 =
												Lamp.valueOf(Road.this.name).isLighted();//���˷�����ʲô��
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
												Lamp.valueOf(Road.this.name).isLighted();//���˷�����ʲô��
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

                                //�����̵�
								if(lighted) {
								String temp=vechicles.get(0);
                                String temp1=temp.substring(0,3);//ȡ���������ķ���
								String temp2=temp.substring(6,7);//ȡ��������������
								/*
								������������̵Ʒ���һ�£�׼��ͨ��
								������������̵Ʒ���һ�£��������⳵����׼��ͨ��
								���ҽ������еĳ����ӵȴ�λ�÷���·���룬��ʾ�ƶ�
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
								//���⳵��
								else if(temp2.equals("s"))
								{
									/*
									�����⳵�����ڷ���Ϊ��ƣ��������ǿ����ƶ�
									���⳵���ƶ��Ŀ��ӻ�ʵ�֣����ں�ʱ����
									�����޷����źŵ�ÿ8����л�����֮�����
									���ÿ��ӻ���ò�����
									����������������⳵�����ƶ���ֻ���������ϵ����
									����̫����tat)
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
