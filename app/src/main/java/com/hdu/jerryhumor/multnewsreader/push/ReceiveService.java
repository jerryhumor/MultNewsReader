package com.hdu.jerryhumor.multnewsreader.push;

/**
 * Created by jerryhumor on 2017/12/20.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.hdu.jerryhumor.multnewsreader.R;
import com.hdu.jerryhumor.multnewsreader.constant.IntentExtra;
import com.hdu.jerryhumor.multnewsreader.news.activity.NewsDetailActivity;
import com.hdu.jerryhumor.multnewsreader.util.JLog;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

/**
 * 继承 GTIntentService 接收来 个推的消息, 所有消息在线程中回调, 如果注册 该服务, 则务必要在 AndroidManifest中声明, 否则 法接
 受消息<br>
 * onReceiveMessageData 处 透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br> * onReceiveCommandResult 各种事件处 回执 <br> */

public class ReceiveService extends GTIntentService {

    public ReceiveService() {
        Log.i(TAG, "receive service init");
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Log.i(TAG, "on receive service pid: " + pid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        Log.i(TAG, "获取到透传信息");
        String str = new String(msg.getPayload());
        Log.i(TAG, "透传信息为:" + str);
        Gson gson = new Gson();
        JLog.i("创建GSon");
        try{
            PushNewsBean bean = gson.fromJson(str, PushNewsBean.class);
            if (bean == null){
                JLog.e("透传信息格式不符");
            }else{
                sendNotification(context, "推荐新闻", bean.getNewsTitle(), bean.getNewsId(), bean.getNewsTitle(), bean.getNewsSource());
            }
        }catch (Exception e){
            JLog.e("解析json数据出错");
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        Log.i(TAG, "on receive online state");
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.i(TAG, "on receive command result");
    }


    private PendingIntent generatePendingIntent(Context context, final int newsId, final String title, final int source){
        Intent intent = new Intent(getApplicationContext(), NewsDetailActivity.class);
        intent.putExtra(IntentExtra.NEWS_ID, newsId);
        intent.putExtra(IntentExtra.NEWS_TITLE, title);
        intent.putExtra(IntentExtra.NEWS_SOURCE, source);
        return PendingIntent.getActivity(context, 777, intent, FLAG_CANCEL_CURRENT);
    }

    private void sendNotification(Context context, final String title, final String content,
                                  final int newsId, final String newsTitle, final int newsSource) {

        JLog.i("发送通知，新闻标题：" + newsTitle);
        //获取系统的通知服务
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //创建Notification Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        PendingIntent pendingIntent = generatePendingIntent(context, newsId, newsTitle, newsSource);

        Notification notification = builder
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.push_small)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_news))
                .setOngoing(false)
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent, true)
                .build();

    /*
     * 第一个参数代表唯一的通知标识 这个数一样 后面的通知将会覆盖前面的通知
     */
        notificationManager.notify(1, notification);
    }

}
