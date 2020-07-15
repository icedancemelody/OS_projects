import java.util.LinkedList;

class Block {
    public int size;
    public int head;
    public int jobId;
    public boolean IsFree;

    public Block(int head, int size, int jobId) {
        this.head = head;
        this.size = size;
        this.jobId = jobId;
        this.IsFree = true;
    }
}

class Record{
    public int head;
    public int size;
    public int JobId;

    public Record(int head, int size, int JobId) {
        this.head = head;
        this.size = size;
        this.JobId = JobId;

    }
}

public class Memory {

    // 内存大小为640K
    public static final int totalMemory = 640;

    private int size;
    private LinkedList<Block> blocks;//以节点Block形式建立一个链表（数组）
    public LinkedList<Record> DividedRecord;//记录已分配的内存块

    public LinkedList<Block> Get_Blocks()
    {
        return this.blocks;
    }

    public Memory() {
        this.size = totalMemory;
        this.blocks = new LinkedList<>();
        this.DividedRecord=new LinkedList<>();
        blocks.add(new Block(0, size, 0));
    }

    //首次适应算法
    public boolean FristFit(int size, int jobId) {
        int location = 0;
        // 遍历分区链表
        for (Block block : this.blocks) {
            // 分配成功
            if (block.IsFree && (block.size >= size)) {
                DoAllocation(size, block, jobId, location);
                return true;
            }
            ++location;
        }
        // 分配失败
        return false;
    }

    //最佳适应算法
    public boolean BestFit(int size, int jobId) {
        int flag  = -1;
        int min = this.size + 1;//作为一个临时值
        Block bestBlock = null;
        int location = 0;
        // 遍历分区链表
        for (Block block : this.blocks) {
            if (block.IsFree && (block.size >= size)) {
                if (min > block.size) {
                    min = block.size;//一旦找到，min就会阻断之后的循环
                    bestBlock = block;
                    flag = location;
                }
            }
            ++location;
        }
        // 是否成功分配
        if (flag != -1) {
            DoAllocation(size, bestBlock, jobId, flag);
            return true;
        } else {
            return false;
        }
    }

    private void DoAllocation(int size, Block block, int jobId, int location) {
        // 如果当前分区较大
        if (block.size > size) {
            Block split = new Block(block.head + size, block.size - size, 0);//更新内存块的信息
            this.blocks.add(location + 1, split);//将新的内存块加入到数组
        }
        block.size = size;//内存块缩小，且变为不可用
        block.IsFree = false;
        block.jobId = jobId;
        //更新记录数组
        Record temp2= new Record(block.head,block.size,block.jobId);
        DividedRecord.add(temp2);
    }

    //内存释放算法
    public void Re_connect(int jobId) {
        int location = 0;
        //将记录已分配资源的数组中的内存块释放
        for(Record record:DividedRecord) {
            if(record.JobId==jobId) {
                DividedRecord.remove(record);
            }
        }
        //遍历分区链表
        for (int i = 0; i < blocks.size(); ++i) {
            Block block = blocks.get(i);
            if (block.jobId == jobId) {
                //如果回收分区不是尾分区且后一个分区为空闲, 则与后一个分区合并
                if (location < blocks.size() - 1 && blocks.get(location + 1).IsFree) {
                    Block next = blocks.get(location + 1);
                    block.size += next.size;
                    blocks.remove(next);
                }
                //如果回收分区不是首分区且前一个分区为空闲, 则与前一个分区合并
                if (location > 0 && blocks.get(location - 1).IsFree) {
                    Block previous = blocks.get(location - 1);
                    previous.size += block.size;
                    blocks.remove(block);
                    block = previous;
                }
                block.IsFree = true;
                block.jobId = 0;
            }
            ++location;
        }
    }
}
