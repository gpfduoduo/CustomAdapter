# CustomAdapter
custom adapter 

## Description
custom adapter, you need not write many adapter with unuseful code.   
In details, for example

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
