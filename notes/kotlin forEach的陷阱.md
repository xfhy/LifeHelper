
出错版本:不能这样用 ,forEach时不能操作元素
```
t?.itemList?.forEach { it ->
                            if (it.type == "textCard" || it.type == "briefCard") {
                                t.itemList?.remove(it)
                            }
                        }
```
错误:

```
03-18 09:31:37.179 25812-25812/com.xfhy.life E/AndroidRuntime: FATAL EXCEPTION: main
                                                               Process: com.xfhy.life, PID: 25812
                                                               java.lang.IllegalStateException: Fatal Exception thrown on Scheduler.
                                                                   at io.reactivex.android.schedulers.HandlerScheduler$ScheduledRunnable.run(HandlerScheduler.java:111)
                                                                   at android.os.Handler.handleCallback(Handler.java:739)
                                                                   at android.os.Handler.dispatchMessage(Handler.java:95)
                                                                   at android.os.Looper.loop(Looper.java:135)
                                                                   at android.app.ActivityThread.main(ActivityThread.java:5418)
                                                                   at java.lang.reflect.Method.invoke(Native Method)
                                                                   at java.lang.reflect.Method.invoke(Method.java:372)
                                                                   at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1037)
                                                                   at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:832)
                                                                Caused by: java.util.ConcurrentModificationException
                                                                   at java.util.ArrayList$ArrayListIterator.next(ArrayList.java:573)
                                                                   at com.xfhy.video.presenter.impl.VideoSearchPresenter$searchVideoByKey$1.onNext(VideoSearchPresenter.kt:48)
                                                                   at com.xfhy.video.presenter.impl.VideoSearchPresenter$searchVideoByKey$1.onNext(VideoSearchPresenter.kt:34)
                                                                   at io.reactivex.internal.operators.flowable.FlowableObserveOn$ObserveOnSubscriber.runAsync(FlowableObserveOn.java:400)
                                                                   at io.reactivex.internal.operators.flowable.FlowableObserveOn$BaseObserveOnSubscriber.run(FlowableObserveOn.java:176)
                                                                   at io.reactivex.android.schedulers.HandlerScheduler$ScheduledRunnable.run(HandlerScheduler.java:109)
                                                                   at android.os.Handler.handleCallback(Handler.java:739) 
                                                                   at android.os.Handler.dispatchMessage(Handler.java:95) 
                                                                   at android.os.Looper.loop(Looper.java:135) 
                                                                   at android.app.ActivityThread.main(ActivityThread.java:5418) 
                                                                   at java.lang.reflect.Method.invoke(Native Method) 
                                                                   at java.lang.reflect.Method.invoke(Method.java:372) 
                                                                   at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1037) 
                                                                   at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:832) 
```

正确写法:这样用是可以的,因为内部其实是for循环(while循环 从后往前)
```
t?.itemList?.forEachReversedByIndex { it ->
                            if (it.type == "textCard" || it.type == "briefCard") {
                                t.itemList?.remove(it)
                            }
                        }
```