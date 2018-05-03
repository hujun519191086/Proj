package com.hebeitv.news.test;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.view.SecondeActivity;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;

public class TestActivity extends FrameActivity
{

    @ResetControl(id = R.id.tv, drawable = { R.drawable.ic_launcher }, clickThis = true)
    private TextView helloWord;

    protected void onCreateView(android.os.Bundle savedInstanceState)
    {
        setCenterContent(R.layout.test);

        // EventSender sender = EventSender.getSender("readerRegist.action", RegistBean.class);
        // sender.put("rName", "abc");
        // sender.put("rPhone", "123");
        // sender.put("rPass", "abcde");
        // sender.put("createTime", DateFormat.format("yyyy-mm-dd", System.currentTimeMillis()));
        // JNISupport.sendEvent("registInfos", sender, 1, this);
        IMessage message = getModule(IMessage.class);
        message.loadMyReadMessage(MessageId.MESSAGE_ID_MY_READ, "" + 0, this);
    }

    @Override
    protected void init()
    {

    }

    @Override
    public void onViewClick(View v)
    {
        setIntentFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(SecondeActivity.class);
    }

    private ArrayList<Integer> drawableList = new ArrayList<Integer>();

    // private class MyBaseAdapter extends FrameAdapter<Integer>
    // {
    // public MyBaseAdapter()
    // {
    // // super(AutoSetValues.setListValues(new ArrayList<String>(), String.class, 50), 10);
    // super(drawableList, 100);
    //
    // }
    //
    // @Override
    // public View getItemView(int position, View convertView, ViewGroup parent)
    // {
    //
    // // ImageLoadUtil.aa(getApplicationContext(), niv, "drawable://" + getItem(position));
    //
    // return convertView;
    // }
    // }
    //

    // private class Asyn extends AsyncTask<EventSender, Void, String>{
    //
    // @Override
    // protected String doInBackground(EventSender... params)
    // {
    // EventSender sender = params[0];
    // // Set<String> keys = sender.getKeySet();
    // // String buffer = "";
    // // for(String key:keys){
    // // buffer += (key+"="+sender.value(key))+"&";
    // // }
    // // buffer = buffer.substring(0, buffer.length()-1);
    // // return Post.sendPost(sender.getServerPath(), buffer);
    // // return Ccalculate.sendMessage(sender);
    //
    //
    // return "";
    //
    // }
    // @Override
    // protected void onPostExecute(String result)
    // {
    // tv.setText(result);
    // }
    //
    // }
    @Override
    public <T> void onSuccess(int messageId, T bean)
    {
        switch (messageId)
        {
            case MessageId.MESSAGE_ID_MY_READ:
                LogInfomation.d(TAG, "bean:" + bean);
                break;

            default:
                break;
        }
    }
}
