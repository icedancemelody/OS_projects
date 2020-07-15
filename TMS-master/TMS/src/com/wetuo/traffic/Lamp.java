package com.wetuo.traffic;

/*
�����趨�źŵ�֮��Ĺ�ϵ�������л���ϵ�ȵ�
�Լ�������߳������У� ���������ʼ����һ���ж������⳵��
*/

public enum Lamp {
   S2N("N2S","E2W","W2E",false,0),E2W("W2E","N2S","S2N",false,0),
   N2S(null,"W2E","E2W",false,0),W2E(null,"S2N","N2S",false,0);//������
   private boolean lighted; //�Ƿ����̵�
   private String opposite; //�෴����ĵ�
   private String next; //��һ����
	private String last;//��һ����
	private int special_car_num; //���ε����⳵��
	private int flag;//�����ж��Ƿ��Ѿ���ȡ����һ�ε�·���⳵���������жϷ�

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
	   //�������⳵��
	   double d1=Math.random();
	   int special_car=(int)(d1*3)+1;//1-3�����⳵��
	   return special_car;
   }

   //�ϻ�û����ĳ�
   public int get_last_num()
   {
   	  return this.valueOf(this.last).get_num();
   }

   //������ε����⳵����
   public void decrease_num()
   {
   	   this.special_car_num--;
   }

   //�õ�����·�Ķ���
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

    //������ÿһ���л�·��ʱ���ҽ���һ�λ�ȡ���⳵�����ݡ�
	public int get_flag()
	{
		return flag;
	}

	public void set_flag(int x)
	{
		flag=x;
	}

   public boolean isLighted() {//�Ƿ����̵�
	   return lighted;
   }
   /*
   �л����̵ƣ�����enum���ܽ�����oppositeֵ��ʼ������oppositeΪ��ʱ
   ��Road�߳����в��ȴ�ʩ
    */
   public void light() {//�Ʊ���
		   this.lighted = true;
	   if(opposite != null) Lamp.valueOf(get_opposite()).light();//�������ǿգ�������Ҳ��
	   this.special_car_num=create_special();//�л���ʱ������һ���ж������⳵��ʼ��
	    }
   
   public Lamp blackOut() {  //����
		this.lighted = false;
		if(opposite != null) {
			Lamp.valueOf(opposite).blackOut();//�������ǿգ�������Ҳ����
		}
		Lamp  nextLamp = null;
		if(next != null) {
			nextLamp = Lamp.valueOf(next);
			System.out.println("�̵ƴ�"+name()+"------>�л�Ϊ"+next);
			nextLamp.light();
		}
		return nextLamp;
	}
}
