在代码中动态加载布局,然后通过该View去findViewById找到子控件,进而监听点击事件
```
val mHeaderView = mMainNavigaView.inflateHeaderView(R.layout.layout_drawer_header)
val mUserIconIv = mHeaderView.findViewById<ImageView>(R.id.mUserIconIv)
mUserIconIv.setOnClickListener {
	Logger.e("测试")
}
```