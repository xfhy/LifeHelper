当项目配置好kotlin之后,一些关键字比如`let`,`lazy`等在报错.\
看看项目的build.gradle中
`classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"`
是否与当前Android Studio是同一版本,如果不是,那么会有警告,不是的话就需要更新koltin插件了.
插件下载一般很慢,以下是下载地址:

`https://plugins.jetbrains.com/plugin/6954-kotlin`

用IDM或者迅雷下载好后,用Android Studio离线安装该插件就好