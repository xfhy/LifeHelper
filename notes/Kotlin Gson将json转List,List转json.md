## Gson将json转List,List转json

### json转List
```
val mNewsSortList = Gson().fromJson(newsSortConfig, object : TypeToken<List<NewsSortBean>>() {}
                    .type) as? MutableList<NewsSortBean>
```

### List转json
```
val newsSortJson = Gson().toJson(mSortAdapter.data, object : TypeToken<List<NewsSortBean>>() {}.type)
```