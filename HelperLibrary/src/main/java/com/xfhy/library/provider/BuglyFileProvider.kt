package com.xfhy.library.provider

import android.support.v4.content.FileProvider

/**
 * Created by xfhy on 2018/4/1 17:20
 * Description : 因bugly和matisse都需要配置fileProvider,
 * 所以需要通过继承FileProvider类来解决合并冲突的问题
 */
class BuglyFileProvider:FileProvider()