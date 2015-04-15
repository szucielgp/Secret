package com.szucie.asua.secret.actys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szucie.asua.secret.R;
import com.szucie.asua.secret.net.Message;

import java.util.List;

/**
 * Created by ASUA on 2015/4/8.
 */
public class MessageListAdapter extends BaseAdapter {
    Context context = null;
    List<Message> msgs;

    public  MessageListAdapter(Context context,List<Message> msgs){
        this.context = context;
        this.msgs = msgs;
    };
    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
             convertView=  LayoutInflater.from(context).inflate(R.layout.layout_message_item,null);
             convertView.setTag(new ListCell((TextView)convertView.findViewById(R.id.mesgcell)));
        }
        //通过标签来加载
        ListCell lc = (ListCell)convertView.getTag();
        Message ms = msgs.get(position);
        lc.getTvCellLabel().setText(ms.getMessage());
        return convertView;
    }

    public void clear(){
        msgs.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Message> data){
        msgs.addAll(data);
        notifyDataSetChanged();
    }
    public static class ListCell{//这样做是为了处理当数量很多的时候的情况。

         public ListCell(TextView tvCellLabel){
            this.tvCellLabel = tvCellLabel;
         }
         private TextView tvCellLabel;

        public TextView getTvCellLabel(){
            return  tvCellLabel;
        }
    }


}
