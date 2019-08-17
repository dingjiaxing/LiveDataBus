# LiveDataBus
利用LivaData来实现的数据传递的简单例子,可用来替换EventBus,该例子实现较为简单，容易理解其原理，有以下优点

## LiveData优点
|LiveData优点|解释
|---|---|
UI和实时数据保持一直|因为LiveData采用的是观察者模式，这样一来就可以在数据发生改变时获取通知，更新UI
避免内存泄漏|观察者被绑定到组件的生命周期上，当被绑定的组件销毁时，观察者会立刻自动清理自身的数据
不会再产生由于Activity处于stop状态而引起的崩溃|当Activity处于后台状态时，是不会收到LiveData的任何事件的
不需要再解决生命周期带来的问题|LiveData可以感知被绑定的组件的生命周期，只有在活跃状态才会通知数据变化
实时数据刷勋|当组件处于活跃状态或者从不活跃状态到活跃状态时总是能收到最新的数据
解决Configuration Change问题|当屏幕发生旋转或者被回收再次启动，立刻就能收到最新的数据

## 更完善的成熟用法
[LiveEventBus](https://github.com/JeremyLiao/LiveEventBus)