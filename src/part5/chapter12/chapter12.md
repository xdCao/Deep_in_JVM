#第五部分 

##第12章 Java内存模型与线程

###一、概述

###二、硬件的效率与一致性

处理器与内存的速度矛盾：引入高速缓冲（Cache）：将运算需要使用的数据复制到缓存中，让运算能够快速进行，
当运算结束后再从缓存同步回内存之中。

>这样的设计解决了速度矛盾，但也引入了新的问题：缓存一致性。每个处理器有自己的高速缓存，又共享
同一主内存。当多个处理器的运算任务都涉及同一块主内存区域时，将可能导致各自的缓存数据不一致。因此
需要缓存一致性协议。

###三、Java内存模型（JMM）

####1、主内存与工作内存

java内存模型的目标是定义程序中各个变量的访问规则，即在虚拟机中将变量存储到内存和从内存中取出变量
这样的底层细节（此处的变量包含实例字段、静态字段和构成数组对象的元素，不包括局部变量和方法参数，因为这俩是线程私有的）

JMM规定了，所有的变量都存储在主内存中。每条线程有自己的工作内存，线程的工作内存中保存了被该线程使用到的
变量的主内存副本拷贝，线程对变量的所有操作都必须在工作内存中进行，而不能直接读写主内存中的变量，不同的线程
之间也无法直接访问对方工作内存中的变量，线程间变量值的传递均需要通过主内存来完成。

####2、内存间交互操作

lock、unlock

read：把一个变量的值从主内存传输到线程的工作内存中

load：把read操作从主内存中得到的变量值放入工作内存的变量副本中

use：把工作内存中一个变量的值传递给执行引擎

assign：把一个从执行引擎接收到的值赋给工作内存的变量

store：把工作内存中一个变量的值传送到主内存中

write：把store操作从工作内存中得到的变量的值放入主内存的变量中

满足规则：

1. 不允许read和load、store和write操作之一单独出现
2. 不允许一个线程丢弃它的最近的assign操作，即变量在工作内存中改变了之后必须把该变化同步回主内存中
3. 不允许线程无原因地（没有发生过assign操作）把数据从线程工作内存同步回主内存
4. 一个变量在同一时刻只允许一条线程对其进行lock操作，但lock操作可以被同一条线程重复执行多次
5. 如果对一个变量执行lock操作，那将会清空工作内存中此变量的值，在执行引擎使用这个变量前，需要重新执行load
或assign操作初始化变量的值
7. 如果一个变量实现没有被lock操作锁定，那就不允许对它执行unlock操作
8. 对一个变量执行unlock操作之前，必须先把该变量的值同步回主内存之中

上述规则确定了哪些内存访问操作在并发下是安全的

####3、对于volatile型变量的特殊规则

两个特性：

1. 保证此变量对所有线程的可见性，但是由于java中的运算并非源自操作，因此其在并发下一样是不安全的

>什么情况下是安全的？

>运算结果不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值
>变量不需要与其他的状态变量共同参与不变约束

2. 禁止指令重排序优化

>普通的变量仅仅会保证在该方法执行的过程中所有依赖赋值结果的地方都能获取到正确的结果，
而不能保证变量赋值操作的顺序与程序代码中的执行顺序一致

    注意DCL单例在jdk1.5之前仍然是不可靠的，因为volatile的禁止重排序的功能在那时存在bug
    
    private volatile static Singleton instance;
    
####4、对于long和double类型变量的特殊规则

java内存模型允许将没有被volatile修饰的64位数据的读写操作划分为两次32位的操作（不保证原子性）
，当然几乎没有人选择这样做，大多数情况下没有必要给long或double加上volatile

####5、原子性、可见性和有序性

原子性：基本类型数据的读写访问（read,load,assign,use,store,write）是具备原子性的，如果需要更大范围的原子性保证，可以使用lock、unlock（更高层面就是
同步语句块）

可见性：当一个线程修改了共享变量的值，其他线程能够立即得知这个修改。

    实现可见性：在变量修改后将新值同步回主内存，在变量读取前从主内存刷新变量值
    
>普通变量与volatile变量的区别：volatile保证了新值能立即同步到主内存，以及每次使用前立即从主内存刷新。

有序性：如果在本线程内观察，所有的操作都是有序的，在另外当一个线程观察，所有的操作都是无序的

    前半句：线程内表现为串行的语义
    后半句：指令重排序、工作内存与主内存同步延迟
    
####6、先行发生原则

###三、java与线程

####1、线程的实现

三种方式：

1. 使用内核线程实现

2. 使用用户线程实现

3. 使用用户线程和轻量级进程混合实现

线程调度

状态转换
