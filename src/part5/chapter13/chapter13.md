#第五部分

##第13章 线程安全与锁优化

###二、线程安全

定义：当多个线程访问一个对象时，如果不用考虑这些线程在运行环境下的调度和交替执行
，也不需要进行额外的同步，或者在调用方进行任何其他的协调操作，调用这个对象的行为
都可以得到正确的结果，那这个对象时线程安全的。

####1、java中的线程安全

由强到弱：5类

1. 不可变

对于一个基本类型，将他设置为final就行，对于一个对象，需要保证对象的行为不会对其状态产生任何影响
（String类,调用它的任何方法，都不会影响它原来的值，只会返回一个新构造的字符串对象）

2. 绝对线程安全

3. 相对线程安全

这是我们通常意义上讲的线程安全，需要保证对这个对象单独的操作是线程安全的，我们在调用的时候不需要做
额外的保障措施，但是对于一些特定顺序的连续调用，就可能需要在调用端使用额外的同步手段来保证调用的正确性。

4. 线程兼容

5. 线程对立

####2、实现线程安全的方法

