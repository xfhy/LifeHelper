有时候需要横向滚动的列表,带给用户更佳的UI效果.比如下面这种:
![](http://olg7c0d2n.bkt.clouddn.com/18-3-12/81149690.jpg)

这个时候我们在展示第一个item的同时也需要展示第二个item,那么可以看到
图中第二个item只展示了一点点头.其实就是动态设置每个item的宽度和padding

具体代码看下面

```kotlin
class HotSortCommonAdapter(sortDataList: MutableList<HotSortBean.Item>?) :
        BaseQuickAdapter<HotSortBean.Item, BaseViewHolder>(R.layout.video_item_home_video_card,
                sortDataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        val baseViewHolder = super.onCreateViewHolder(parent, viewType)
        baseViewHolder?.itemView?.let {
            val layoutParams = it.layoutParams as RecyclerView.LayoutParams
            //item宽度设置窄一点(稍微比屏幕宽度窄一点,这样用户才知道那里是可以进行左右滑动的)
            layoutParams.width = DevicesUtils.devicesSize.widthPixels -
                    DensityUtil.dip2px(it.context, 20f)

            //设置paddingEnd 为5dp
            it.setPadding(0, 0, DensityUtil.dip2px(it.context, 5f), 0)

            it.layoutParams = layoutParams
        }
        return baseViewHolder
    }

    override fun convert(holder: BaseViewHolder, item: HotSortBean.Item?) {
        //...
    }
}
```