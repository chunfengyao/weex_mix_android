# Weex混合框架整合说明

***当前存在的问题：***
- Demo页面的跳转暂时还存在一些问题。（预计是官方的Demo的js中路由url的问题）
- 页面图片过多/列表过多时会导致页面异常的卡顿。（内存优化，此处主要是vue界面的优化）
- 启动时如果渲染失败了或者是出现了内部错误（此处原因是测试运行于X86的设备，但Weex未提供X86机型的js转换so库），会导致页面异常卡顿（主要原因是CPU持续被满载，可能的原因是内部的异常处理机制导致的。）
- 同样是启动时渲染失败或者出现了内部错误（环境同问题3），此时Weex会立即调用监听回调的onException方法，但是，此时页面未必一定渲染成功了，所以，会导致一定的用户体验的下降，此处的解决方式：其一，在收到异常回调时，增加延时退出的机制。其二，将Weex的页面渲染时机设置到onAttachedToWindow中，保证大部分的页面工作已完成，再去执行Weex的页面初始化逻辑。

***

#### Vue页面跳转
*（此部分主要由vue文件编译后的单个（/bundle）js文件处理跳转逻辑，此处不赘述）：*
> 跳转相关，详见：[Weex navigatior跳转][1]
> Demo/在线开发，详见：[在线开发工具][2]
> 开发资料，详见：[官方文档][3]

[1]:https://www.jianshu.com/p/8258fa144e38
[2]:http://dotwe.org/vue
[3]:https://weex.apache.org/zh/guide/introduction.html

***


#### 开发说明：
***加载Weex引擎：***
> 调用weex_mix模块中的com.sinitek.mix.utils.WeexUtils#initWeex方法，传入当前Application对象，以及Weex配置文件（可选，主要是图片引擎和网络请求引擎的注册，管理类中有一个默认实现）

***打开Weex渲染的页面：***
> 当前使用的是单页面的封装（考虑到业务需求），打开Weex页面，仅需要通过管理类的指定方法（com.sinitek.mix.innerAC.WeexActivityManager#startWeexActivityByWebWeexUrl方法或者com.sinitek.mix.innerAC.WeexActivityManager#startWeexActivityByAssertFilePath方法），传入指定的参数（当前封装有两种加载模式：1、本地文件模式，加载Assert文件夹中的符合标准的js文件。2、服务器模式，通过指定url请求服务器上的js文件。注：两种模式仅文件存储方向和页面中的路由url不一致，其他均无差异。），相关参数的说明位于方法注释中。即可打开Weex页面，在页面的整个生命周期中，都有详尽的日志信息供调试使用（vue页面部分请自行处理日志信息的输出！！！）。

***拓展说明：***
> 当前使用的图片加载引擎为Picasso，且禁用了内存缓存（由于在页面加载大量图片时会产生OOM现象，此处临时处理方案为禁用内存缓存，后期可以考虑使用jni申请更多内存区域供Picasso作为图片内存缓存区使用，）。

>当前使用的网络请求引擎为Weex自带的请求引擎，另外模块中有使用okHttp写的请求引擎（未完全测试），可以自由替换。

>当前未创建自定义的模块进行拓展处理，开发者可自行创建基于BaseModule的模块，并通过管理类提供的指定方法，将自定义模块注册到Weex引擎中。

***集成说明：***
> 当前的Weex框架集成于模块weex_mix中，移植至项目C流程：导出weex_mix模块，在项目C中导入该模块。在项目C的Application中调用管理类的init方法，对Weex进行初始化操作，测试运行，检查整合结果。


