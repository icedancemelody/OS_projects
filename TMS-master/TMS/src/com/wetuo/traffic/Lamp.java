package com.wetuo.traffic;

/*
用来设定信号灯之间的关系，包括切换关系等等
以及在这个线程任务中， 还会随机初始化下一次有多少特殊车辆
*/

public enum Lamp {
   S2N("N2S","E2W","W2E",false,0),E2W("W2E","N2S","S2N",false,0),
   N2S(null,"W2E","E2W",false,0),W2E(null,"S2N","N2S",false,0);//永动机
   private boolean lighted; //是否是绿灯
   private String opposite; //相反方向的灯
   private String next; //下一个灯
	private String last;//上一个灯
	private int special_car_num; //本次的特殊车辆
	private int flag;//用来判断是否已经获取了这一次道路特殊车辆数量的判断符

   Lamp(String opposite,String next,String last,boolean lighted,int special_car_num) {
		this.opposite = opposite;
		this.next = next;
		this.last=last;
		this.lighted = lighted;
		this.special_car_num=0;
		this.flag=1;
   }

   private int create_special()
   {
	   //创建特殊车辆
	   double d1=Math.random();
	   int special_car=(int)(d1*3)+1;//1-3辆特殊车辆
	   return special_car;
   }

   //上回没走完的车
   public int get_last_num()
   {
   	  return this.valueOf(this.last).get_num();
   }

   //减少这次的特殊车辆数
   public void decrease_num()
   {
   	   this.special_car_num--;
   }

   //得到此条路的对向
   public String get_opposite()
	{
		if(this.name().equals("S2N")||this.name().equals("E2W")) {
			return this.opposite;}
		else if(this.name().equals("N2S"))
		{
			return "S2N";
		}
		else {
			return "E2W";
		}
	}

   public String get_last()
   {
       return this.last;
   }

   public int get_num()
   {
   	   return special_car_num;
   }

    //用来在每一次切换路径时有且仅有一次获取特殊车辆数据。
	public int get_flag()
	{
		return flag;
	}

	public void set_flag(int x)
	{
		flag=x;
	}

   public boolean isLighted() {//是否是绿灯
	   return lighted;
   }
   /*
   切换红绿灯，由于enum不能将所有opposite值初始化，当opposite为空时
   在Road线程中有补救措施
    */
   public void light() {//灯变绿
		   this.lighted = true;
	   if(opposite != null) Lamp.valueOf(get_opposite()).light();//如果对向非空，则对向灯也亮
	   this.special_car_num=create_special();//切换灯时，把下一波有多少特殊车初始化
	    }
   
   public Lamp blackOut() {  //变红灯
		this.lighted = false;
		if(opposite != null) {
			Lamp.valueOf(opposite).blackOut();//如果对向非空，则对向灯也变红灯
		}
		Lamp  nextLamp = null;
		if(next != null) {
			nextLamp = Lamp.valueOf(next);
			System.out.println("绿灯从"+name()+"------>切换为"+next);
			nextLamp.light();
		}
		return nextLamp;
	}
}
