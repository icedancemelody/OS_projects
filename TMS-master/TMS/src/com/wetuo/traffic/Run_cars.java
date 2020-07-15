package com.wetuo.traffic;

import java.awt.*;

/*
这个类是用来创建小车队列的模板，里面有队列元素应有的参数（java不带struct）
*/

public class Run_cars {

   public int position_x;//小车当前坐标x
   public int position_y;//小车当前坐标y
   public int type;//小车类型，1为普通，2为特殊
   public String direction;//小车行驶方向

    Run_cars(int position_x,int position_y,int type,String direction){
       this.position_x=position_x;
       this.position_y=position_y;
        this.type=type;
        this.direction=direction;
    }
}
