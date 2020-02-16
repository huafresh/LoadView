## 背景
实际应用场景中，我们经常会遇到这样的场景：一个页面在几种状态视图之间切换，
比如正在加载、加载错误、暂无数据等，如果每一个页面都在xml中引入上述的几种状态视图，
并且每个页面都重复的去处理这些视图的显示和隐藏逻辑，那将会比较痛苦。一个解决办法
是xml中通过include引入各种视图，然后显示隐藏逻辑放到baseActivity中处理，
这样确实能减少一些工作量，但是个人一直不太提倡在BaseActivity封装ui操作，
因为ui是变的最频繁的，封装会让修改变的麻烦。

## 解决方案
LoadView可以低侵入解决上述提到的问题，LoadView会在内容视图上增加一层FrameLayout容器，
这个容器包含了上述提到的各种状态视图，然后通过show(type)方法显示特定的视图，
后面有代码展示用法，看完就很好理解了。

## 基本使用
step1: 先注册全局使用的各种类型的视图。
```
MultiLayout.registerLoadingLayout(new LoadingLayout());
MultiLayout.registerErrorLayout(new LoadErrorLayout());
// 通过这个方法可以注册任意自定义的视图类型
MultiLayout.registerLayoutProvider(1, new CustomLayout());
```
step2: 调用wrap方法在给定的视图中包装FrameLayout容器
```
RecyclerView rv = findViewById(R.id.rv);
MultiLayout multiLayout = MultiLayout.wrap(rv);
```
step3: 根据需要切换视图。
```
multiLayout.showError();
multiLayout.showContent(); // 展示被包装的原始内容视图
multiLayout.showWithType(1); // 展示自定义类型的视图
```
step4: 一般加载失败页面会有重试按钮，可以这样设置点击事件：
```
// 设置某个类型视图顶层的点击事件
multiLayout.setLayoutOnClickListener(int type, listener);
// 设置某个child子视图的点击事件，第一个参数是child view的id
multiLayout.setChildOnClickListener(R.id.jump, listener);
```
## 鸣谢
部分设计思想来源于此库： [https://github.com/KingJA/LoadSir](https://github.com/KingJA/LoadSir)

