package part2.chapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdcao on 2017/4/23.
 * -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args){

        List<String> list=new ArrayList<>();
        int i=0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }

    }

}
