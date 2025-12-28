package com.han.listview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.han.listview.Model.ContactModel;
import com.han.listview.Model.HeaderItemModel;
import com.han.listview.Model.ListItemModel;
import com.han.listview.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private final List<ListItemModel> items;
    private final LayoutInflater inflater;

    public MyAdapter(Context context, List<ListItemModel> items) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ListItemModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getItemType();
    }

    @Override
    public int getViewTypeCount() {
        return 2; // header + contact
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemModel item = items.get(position);
        if (item.getItemType() == ListItemModel.TYPE_HEADER) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_header, parent, false);
            }
            TextView tv = convertView.findViewById(R.id.headerText);
            tv.setText(((HeaderItemModel) item).getLetter());
        } else {
            if (convertView == null) {
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView tv = convertView.findViewById(android.R.id.text1);
            tv.setText(((ContactModel) item).getName());
        }
        return convertView;
    }
}
