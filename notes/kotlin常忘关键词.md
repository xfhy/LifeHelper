### 1. JvmOverloads
在Kotlin中@JvmOverloads注解的作用就是：在有默认参数值的方法中使用@JvmOverloads注解，则Kotlin就会暴露多个重载方法。
可能还是云里雾里，直接上代码，代码解释一切：
如果我们在kotlin中写如下代码：
```
fun f(a: String, b: Int = 0, c: String="abc"){
    ...
}
```
相当于在Java中声明
```
void f(String a, int b, String c){
}
```
默认参数没有起到任何作用。

但是如果使用的了@JvmOverloads注解：
```
@JvmOverloads fun f(a: String, b: Int=0, c:String="abc"){
}
```
相当于在Java中声明了3个方法：
```
void f(String a)
void f(String a, int b)
void f(String a, int b, String c)
```

### 2. as? 可空转化
当我们用as进行强转时如果转化失败,那么会抛异常,这种情况下,建议尽量用as?,当然,转化出来的是可空类型的

### 3. 提前结束let
这个直接看代码,就是在外面加一个标签
```
mOldNewsSortList?.forEachIndexed oldDataLoop@ { index, newsSortBean ->
    if (newsSortBean.isChecked) {
        dataChanged = true
        return@oldDataLoop
    }
}
```

### 4. 集合中筛选
用filter吧,忘掉remove....
