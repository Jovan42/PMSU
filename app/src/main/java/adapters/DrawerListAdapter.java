package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jovan.sf62_2017.R;

public class DrawerListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> mNavItems;

    public DrawerListAdapter(Context context) {
        mContext = context;
        mNavItems = new ArrayList<>();
        mNavItems.add("Posts");
        mNavItems.add("Settings");
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_list_item, null);
        } else {
            view = convertView;
        }
        TextView text = (TextView) view.findViewById(R.id.text);

        text.setText(mNavItems.get(position));
        return view;
    }
}
