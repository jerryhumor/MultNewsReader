package com.hdu.jerryhumor.multnewsreader.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.constant.NewsSource;
import com.hdu.jerryhumor.multnewsreader.net.bean.NewsInfo;
import com.hdu.jerryhumor.multnewsreader.util.TimeUtil;

import java.util.List;

/**
 * Created by jerryhumor on 2017/11/10.
 *
 * 新闻列表 RecyclerView 的适配器
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private List<NewsInfo> mNewsInfoList;
    private OnItemClickListener mOnItemViewClickListener;

    public NewsListAdapter(List<NewsInfo> newsInfoList){
        mNewsInfoList = newsInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        NewsInfo newsInfo = mNewsInfoList.get(position);
        switch (newsInfo.getSource()){
            case NewsSource.CODE_NETEASE:
                holder.ivLogo.setImageResource(R.mipmap.logo_netease);
                break;
            case NewsSource.CODE_SINA:
                holder.ivLogo.setImageResource(R.mipmap.logo_sina);
                break;
            case NewsSource.CODE_ZHIHU:
                holder.ivLogo.setImageResource(R.mipmap.logo_zhihu);
                break;
            default:
                holder.ivLogo.setImageResource(R.mipmap.logo_unknown);
                break;
        }
        holder.tvTime.setText(TimeUtil.getTimeFormatted(newsInfo.getNewsTime(), TimeUtil.FORMAT_DEFAULT));
        holder.tvTitle.setText(newsInfo.getTitle());
        if (mOnItemViewClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemViewClickListener.onClickItem(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mNewsInfoList.size();
    }

    public void setOnItemViewClickListener(OnItemClickListener onClickListener){
        this.mOnItemViewClickListener = onClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvTime;
        ImageView ivLogo;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tv_title);
            this.tvTime = itemView.findViewById(R.id.tv_time);
            this.ivLogo = itemView.findViewById(R.id.iv_logo);
            this.itemView = itemView;
        }
    }

    public interface OnItemClickListener{
        void onClickItem(int position);
    }
}
