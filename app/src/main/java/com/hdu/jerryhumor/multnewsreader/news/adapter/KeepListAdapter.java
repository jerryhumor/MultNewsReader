package com.hdu.jerryhumor.multnewsreader.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.constant.NewsSource;
import com.hdu.jerryhumor.multnewsreader.keep.bean.KeepItem;
import com.hdu.jerryhumor.multnewsreader.util.TimeUtil;

import java.util.List;

/**
 * Created by hanjianhao on 2017/12/18.
 */

public class KeepListAdapter extends RecyclerView.Adapter<KeepListAdapter.ViewHolder>{

    private List<KeepItem> mKeepItemList;

    public KeepListAdapter(List<KeepItem> keepItemList){
        mKeepItemList = keepItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keep_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        KeepItem item = mKeepItemList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvCreateTime.setText(TimeUtil.getTimeFormatted(item.getCreateTime(), TimeUtil.FORMAT_MONTH_DAY));
        switch (item.getSource()){
            case NewsSource.CODE_NETEASE:
                holder.ivSource.setImageResource(R.mipmap.logo_netease);
                break;
            case NewsSource.CODE_SINA:
                holder.ivSource.setImageResource(R.mipmap.logo_sina);
                break;
            case NewsSource.CODE_ZHIHU:
                holder.ivSource.setImageResource(R.mipmap.logo_zhihu);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mKeepItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivSource;
        private TextView tvTitle, tvCreateTime;

        public ViewHolder(View view){
            super(view);
            ivSource = view.findViewById(R.id.iv_source);
            tvTitle = view.findViewById(R.id.tv_title);
            tvCreateTime = view.findViewById(R.id.tv_create_time);
        }

    }
}
