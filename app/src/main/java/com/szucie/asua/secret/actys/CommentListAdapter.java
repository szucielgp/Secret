package com.szucie.asua.secret.actys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szucie.asua.secret.R;
import com.szucie.asua.secret.net.Comment;
import com.szucie.asua.secret.net.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUA on 2015/4/8.
 */
public class CommentListAdapter extends BaseAdapter {
    Context context = null;
    List<Comment> comments = new ArrayList<Comment>();

    public CommentListAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override

    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=  LayoutInflater.from(context).inflate(R.layout.layout_comment_cell,null);
            convertView.setTag(new ListCell((TextView)convertView.findViewById(R.id.comment_cell)));
        }
        //通过标签来加载
        ListCell lc = (ListCell)convertView.getTag();
        Comment comment = comments.get(position);
        lc.getTvCellLabel().setText(comment.getContent());
        return convertView;

    }
    public void clear(){
        comments.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Comment> data){
        comments.addAll(data);
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
