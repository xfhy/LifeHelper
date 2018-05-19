package com.xfhy.provider.bean

import cn.bmob.v3.BmobUser

/**
 * Created by xfhy on 2018/3/2 23:24
 * Description : 用户信息
 *
 * userAvatarUrl:头像地址
 */

class UserInfo : BmobUser() {

    /**
     * 用户头像
     */
    var userAvatarUrl: String = ""

}
