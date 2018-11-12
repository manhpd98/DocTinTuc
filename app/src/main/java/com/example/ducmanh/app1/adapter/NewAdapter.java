package com.example.ducmanh.app1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ducmanh.app1.R;
import com.example.ducmanh.app1.model.News;
import java.util.ArrayList;

/**
 * Created by Administrator on 19/5/2018.
 */

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHoder> {

    private LayoutInflater inflater;
    private ArrayList<News> listNew;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public NewAdapter(ArrayList<News> listNew, Context context) {
        this.listNew = listNew;
        this.inflater = LayoutInflater.from(context);
    }

    public NewAdapter(ArrayList<News> list) {
        this.listNew = list;
    }


    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view,parent,false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, final int position) {
        final News news = listNew.get(position);
        holder.bindData(news);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClick.onItemLongClick(position,listNew.get(position));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNew.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        private ImageView imgNews;
        private TextView tvTitle,tvDesc,tvPubData;
        public ViewHoder(View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvPubData = itemView.findViewById(R.id.tv_pubdate);
        }

        public void bindData(News news){
            Glide.with(itemView.getContext())
                    .load(news.getImg())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imgNews);
            tvTitle.setText(news.getTitle());
            tvDesc.setText(news.getDesc());
            tvPubData.setText(news.getPubDate());
        }
    }

    public interface OnItemClick{
        void onItemClick(int position);
        void onItemLongClick(int position, News news);
    }

    public void filterList(ArrayList<News> list){
        listNew = list;
        notifyDataSetChanged();
    }

    public void setFilter(ArrayList<News> news) {
        listNew = new ArrayList<>();
        listNew.clear();
        listNew.addAll(news);
        notifyDataSetChanged();
    }

}
