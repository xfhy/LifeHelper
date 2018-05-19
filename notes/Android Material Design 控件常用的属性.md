# Android Material Design 控件常用的属性

## android:fitsSystemWindows="true"
是一个boolean值的内部属性，让view可以根据系统窗口(如status bar)来调整自己的布局，
如果值为true,就会调整view的padding属性来给system windows留出空间...
用于实现状态栏，即 沉浸式状态栏！
 
## Toolbar
android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
app:popupTheme="@style/ThemeOverlay.AppCompat.Light"
app:layout_scrollFlags="scroll|enterAlways" （CoordinatorLayout属性，子布局通过设置该属性确定是否可滑动）
 
说明：
app:popupTheme，这个属性就是用来自定义我们弹出的菜单的样式，在之前的Actionbar的溢出菜单，
我们是不能自定义他的样式的，只能根据你的theme来选择黑白两种，不能自己定义，现在我们可以定义弹出菜单的样式。
 
## CoordinatorLayout

- app:layout_scrollFlags （子布局设置是否可滑动）
- android:layout_gravity 属性控制组件在布局中的位置
- app:layout_behavior="@string/appbar_scrolling_view_behavior" 通知布局中包含滑动组件！
 
- 子布局通过app:layout_scrollFlags确定是否可滑动.给需要滑动的组件设置 app:layout_scrollFlags="scroll|enterAlways" 属性。 
设置的layout_scrollFlags有如下几种选项： 

    - scroll: 所有想滚动出屏幕的view都需要设置这个flag- 没有设置这个flag的view将被固定在屏幕顶部。
    - enterAlways: 这个flag让任意向下的滚动都会导致该view变为可见，启用快速“返回模式”。 
    - enterAlwaysCollapsed: 当你的视图已经设置minHeight属性又使用此标志时，你的视图只能以最小高度进入，只有当滚动视图到达顶部时才扩大到完整高度。 
    - exitUntilCollapsed: 滚动退出屏幕，最后折叠在顶端。
    
## CollapsingToolbarLayout

> 主要是提供一个可折叠的Toolbar容器，对容器中的不同View设置layout_collapseMode折叠模式，来达到不同的折叠效果。

- app:collapsedTitleGravity 指定折叠状态的标题如何放置，可选值:top、bottom等
- app:collapsedTitleTextAppearance="@style/CollapsedTitleStyle" 指定折叠状态标题文字的样貌
- app:expandedTitleTextAppearance="@style/ExpandedTitleStyle"  指定展开状态标题文字的样貌
- app:contentScrim="?attr/colorPrimaryDark" 指定CollapsingToolbarLayout完全被滚出到屏幕外时的ColorDrawable
- app:expandedTitleGravity  展开状态的标题如何放置
- app:titleEnabled指定是否显示标题文本
- app:toolbarId指定与之关联的ToolBar，如果未指定则默认使用第一个被发现的ToolBar子View
- app:expandedTitleMarginStart="10dp"
- app:expandedTitleMargin
- app:expandedTitleMarginBottom
- app:expandedTitleMarginEnd展开状态改变标题文字的位置，通过margin设置
- app:layout_collapseParallaxMultiplier="0.7"设置视差的系数，介于0.0-1.0之间。
- app:layout_collapseMode="pin"（子布局设置折叠模式）有两种
    - `pin`：固定模式，在折叠的时候最后固定在顶端
    - `parallax`：视差模式，在折叠的时候会有个视差折叠的效果
 
## Floating Action Button (FAB) 

- app:fabSize="normal" 是用来定义 FAB 的大小的，normal 的意思是在大多数情况下标准尺寸为 56dp 的按钮，但是万一你想使用较小的一个， mini 是另一个选择，它的大小将变成 40dp。
- app:elevation 　　为空闲状态下的阴影深度，
- app:backgroundTint	　　是指定默认的背景颜色 
- app:rippleColor	　　是指定点击时的背景颜色 
- app:border	　　	Width 　border的宽度 
- app:fabSize	　　是指FloatingActionButton的大小，可选normal|mini 
- app:pressedTranslationZ 	　　按下去时的z轴移动的距离

## TabLayout

- app:tabIndicatorColor 	　　tab的指示符颜色 
- app:tabSelectedTextColor 	　　选择tab的文本颜色 
- app:tabTextColor 	　　普通tab字体颜色 
- app:tabMode 	　　模式，可选fixed和scrollable fixed是指固定个数，scrollable是可以横行滚动app:tabGravity 	对齐方式，可选fill和center
- app:tabBackground="@color/colorPrimary"   整个TabLayout的背景颜色
- app:tabIndicatorColor="@color/color_ffffff"   指示器,选中时底部的横线
- app:tabTextAppearance="@style/TabLayoutTextStyle"  文字样式,可控制大小

## CardView

- android:foreground="?android:attr/selectableItemBackground"   加上后设置点击事件之后点击时有水波纹效果
- app:cardBackgroundColor      设置背景颜色
- app:cardCornerRadius         设置圆角大小
- app:cardElevation            设置z轴阴影高度
- app:cardMaxElevation         设置z轴最大高度值
- app:contentPadding           内容与边距的间隔
- app:contentPaddingLeft       内容与左边的间隔
- app:contentPaddingTop        内容与顶部的间隔
- app:contentPaddingRight      内容与右边的间隔
- app:contentPaddingBottom     内容与底部的间隔    
- app:paddingStart             内容与边距的间隔起始
- app:paddingEnd               内容与边距的间隔终止
- app:cardUseCompatPadding     设置内边距，在API21及以上版本和之前的版本仍旧具有一样的计算方式
- app:cardPreventConrerOverlap 在API20及以下版本中添加内边距，这个属性为了防止内容和边角的重叠
 注意：CardView中使用android:background设置背景颜色无效。