# LiveDataBus
利用LivaData来实现的数据传递的简单例子,可用来替换EventBus,该例子实现较为简单，容易理解其原理，有以下优点

## LiveDataBus优点
|LiveDataBus优点|解释
|---|---|
LiveDataBus的实现极其简单|相比EventBus复杂的实现，LiveDataBus只需要一个类就可以实现
LivaDataBus可以减小apk包的大小|LiveDataBus只依赖Android官方组件LiveData，本身只一个类。EventBus 57Kb,RxJava 2.2M
LiveDataBus依赖方支持更好|LiveDataBus只依赖Android官方组件LiveData，相比RxBus依赖的RxJava和RxAndroid，依赖方支持更好
LiveDataBus具有生命周期感知|LiveDataBus具有生命周期感知，在Android系统中使用调用者不需要调用凡注册，相比EventBus和RxBus使用更为方便，并且没有内存泄漏风险

## 入门文档
* [android事件通信方案对比与LiveDataBus详解](https://blog.csdn.net/qq_23081779/article/details/99693939)

## 更完善的成熟用法
[LiveEventBus](https://github.com/JeremyLiao/LiveEventBus)