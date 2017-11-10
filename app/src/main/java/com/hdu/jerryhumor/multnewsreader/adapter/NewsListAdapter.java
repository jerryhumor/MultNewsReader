package com.hdu.jerryhumor.multnewsreader.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.constant.NewsConstant;
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

    public NewsListAdapter(List<NewsInfo> newsInfoList){
        mNewsInfoList = newsInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsInfo newsInfo = mNewsInfoList.get(position);
        switch (newsInfo.getSourceInt()){
            case NewsConstant.SOURCE_NETEASE:
                holder.ivLogo.setImageResource(R.mipmap.logo_netease);
                break;
            case NewsConstant.SOURCE_SINA:
                holder.ivLogo.setImageResource(R.mipmap.logo_sina);
                break;
            case NewsConstant.SOURCE_ZHIHU:
                holder.ivLogo.setImageResource(R.mipmap.logo_zhihu);
                break;
            default:
                break;
        }
        holder.tvTime.setText(TimeUtil.getTimeFormatted(newsInfo.getTime(), TimeUtil.FORMAT_DEFAULT));
        holder.tvTitle.setText(newsInfo.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNewsInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvTime;
        ImageView ivLogo;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_time);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivLogo = itemView.findViewById(R.id.iv_logo);
        }
    }
}
