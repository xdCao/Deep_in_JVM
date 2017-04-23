package chapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xdcao on 2017/4/23.
 * VM Args -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {

    static class OOMObject{

    }


    public static void main(String[] args){
        List<OOMObject> list=new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }


}
