package nuttapon.dots.co.th.dotssolutions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter{

    private Context context;
    private String[] titlesStrings;

    public MenuAdapter(Context context, String[] titlesStrings, int[] iconInts) {
        this.context = context;
        this.titlesStrings = titlesStrings;
        this.iconInts = iconInts;
    }

    private  int[] iconInts;



    @Override
    public int getCount() {
        return titlesStrings.length;  // สร้าง ListView ตามจำนวน

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.listview_menu,parent,false);

        ImageView imageView=view.findViewById(R.id.ImvIcon);
        imageView.setImageResource(iconInts[position]);

        TextView textView=view.findViewById(R.id.txtTitle);
        textView.setText(titlesStrings[position]);


        return view;
    }
}
