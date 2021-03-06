package part2.chapter2;

/**
 * Created by xdcao on 2017/4/23.
 * VM Args:-Xss128k
 */
public class JavaVMStackSOF {

    private int stackLength=1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args){
        JavaVMStackSOF oom=new JavaVMStackSOF();

        try {
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length: "+oom.stackLength);
            throw e;
        }
    }

}
