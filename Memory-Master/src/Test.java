import java.util.LinkedList;
import java.util.Scanner;

public class Test {

    private int []JobId = {1, 2, 3, 2, 4, 3, 1, 5, 6, 7, 6};
    private int []act = {0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1};//0表示申请，1表示释放
    private int []size = {130, 60, 100, 60, 200, 100, 130, 140, 60, 50, 60};

    private Memory memory = new Memory();


    public int[] getCurrentInstruct(int i,int FitWay) {
        int []temp = {JobId[i], act[i], size[i]};
        if(act[i]==0) {
            if(FitWay==1) {
                boolean x = memory.FristFit(temp[2], temp[0]);
            }
            if(FitWay==2){
                boolean x = memory.BestFit(temp[2], temp[0]);
            }
        }
        else {
            memory.Re_connect(temp[0]);
        }

        System.out.println("------------------------");
        System.out.println("已分配资源：");
        System.out.println("始址 大小 作业数");

        for(int j=0;j<memory.DividedRecord.size();j++) {
            Record print_temp=memory.DividedRecord.get(j);
            System.out.println(print_temp.head+" "+print_temp.size+" "+print_temp.JobId);
        }

        System.out.println("------------------------");

        LinkedList<Block> ForDivideRecord=memory.Get_Blocks();

        System.out.println("------------------------");
        System.out.println("未分配资源：");
        System.out.println("始址 大小 作业数");
        for(Block block:ForDivideRecord) {
            if(block.IsFree){
                System.out.println(block.head+" "+block.size+" "+block.jobId);
            }
        }
        System.out.println("------------------------");
        System.out.println("\n");

        return temp;
    }


    public static void main(String[] args) throws InterruptedException {
       Test t=new Test();
       Scanner sc=new Scanner(System.in);
       int FitWay;
       System.out.println("请输入数字（1表示首次适应算法），2（表示最佳适应算法）：");
       FitWay=sc.nextInt();
       for(int i=0;i<11;i++) {
           if(t.act[i]==0){
               System.out.println("作业" + t.JobId[i] + "申请"+t.size[i]+"K");
           }
           else {
               System.out.println("作业" + t.JobId[i] + "释放"+t.size[i]+"K");
           }
       }
       System.out.println("\n");
       for(int i=0;i<11;i++) {
           if(t.act[i]==0){
               System.out.println("作业" + t.JobId[i] + "申请"+t.size[i]+"K");
           }
           else {
               System.out.println("作业" + t.JobId[i] + "释放"+t.size[i]+"K");
           }
           t.getCurrentInstruct(i,FitWay);
           Thread.sleep(3000);
       }
    }
}
