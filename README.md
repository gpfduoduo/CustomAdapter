# Description
Use custom adapter, you need not write many adapter with unuseful code.   
In details, for example below...

##实现：
调用开源的StickyGridHeader实现根据类似于iphone显示图片的功能，并且实现了滑动不加载图片，只有停止的时候加载当前页面的图片，这样很好的避免了卡断现象的发生。   

## Example

``` 
public class MyAdapter extends CustomAdapter<Zte>
{

    public MyAdapter(Context context, int resLayoutId, List<Zte> list)
    {
        super(context, resLayoutId, list);
    }

    @Override
    public void convert(ViewHolder viewHolder, Zte zte)
    {
        viewHolder.getTextView(R.id.id).setText(R.string.app_name + "\t" + zte.mCount);
        viewHolder.getImageView(R.id.project).setBackgroundResource(R.color.colorAccent);
    }
}

```

The entity calss
```
public class Zte
{
    public int mCount;
}

```

## 开源库 
[StickyGridHeaders](https://github.com/TonicArtos/StickyGridHeaders)
