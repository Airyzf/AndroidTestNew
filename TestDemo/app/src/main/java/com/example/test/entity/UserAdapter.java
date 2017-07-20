package com.example.test.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test.testdemo.R;

import java.util.List;

/**
 * Created by yuanzhaofeng
 * on 2017/7/20 15:20.
 * desc:
 * version:
 */
public class UserAdapter extends BaseAdapter {
    private List<User> userList;
    private Context context;

    public UserAdapter(Context context, List<User> users) {
        this.userList = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList == null ? 0 : userList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_user,null);
            viewHolder=new ViewHolder();
            viewHolder.tvId= ((TextView) convertView.findViewById(R.id.tvId));
            viewHolder.tvName= ((TextView) convertView.findViewById(R.id.tvName));
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        User user=userList.get(position);
        viewHolder.tvId.setText(String.valueOf(user.getId()));
        viewHolder.tvName.setText(String.valueOf(user.getName()));
        return convertView;
    }

    class ViewHolder{
        TextView tvId;
        TextView tvName;
    }
}
