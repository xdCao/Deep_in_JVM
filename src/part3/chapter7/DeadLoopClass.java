package part3.chapter7;

/**
 * Created by xdcao on 2017/4/25.
 */
public class DeadLoopClass {

    static {
        if(true){
            System.out.println(Thread.currentThread()+"init DeadLoopClass");
            while (true){

            }
        }
    }

    public static void main(String[] args){

        Runnable script= () -> {
            System.out.println(Thread.currentThread()+"start");
            DeadLoopClass dlc=new DeadLoopClass();
            System.out.println(Thread.currentThread()+" run over");
        };

        Thread thread1=new Thread(script);
        Thread thread2=new Thread(script);
        thread1.start();
        thread2.start();

    }

}
