package com.hebeitv.news.view.search;

import java.util.ArrayList;

import org.apache.http.Header;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.MrYang.zhuoyu.other.SirPair;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.HotWrodBean;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.utils.WindowUtil;

public class SearchActivity extends FrameActivity
{
    @ResetControl(id = R.id.iv_search_click, clickThis = true, isSrc = true, drawable = R.drawable.sousuo2)
    private ImageView iv_search_click;
    @ResetControl(id = R.id.et_search_content, drawable = R.drawable.search_bg)
    private EditText serach_content;
    @ResetControl(id = R.id.tl_search_hotWord)
    private TableLayout tl_search_hotWord;

    protected void onCreateView(android.os.Bundle savedInstanceState)
    {
        includeTop("搜索新闻", true, false, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        setCenterContent(R.layout.search);
    }

    @Override
    protected void init()
    {
        setCommonTopBack();
        getModule(IMessage.class).hotWord(12, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewClick(View v)
    {
        switch (v.getId())
        {

            case R.id.tag_second:
                HotWrodBean hwb = (HotWrodBean) v.getTag();
                startActivity(SearchOverActivity.class, new SirPair<String, String>(searchKey, hwb.hKey));
                break;
            case R.id.iv_search_click:
                String content = serach_content.getText().toString();
                if (TextUtils.isEmpty(content))
                {
                    WindowUtil.showToast(this, "请输入搜索关键词");
                }
                else
                {
                    startActivity(SearchOverActivity.class, new SirPair<String, String>(searchKey, content));
                }
                break;

            default:
                break;
        }
    }

    public static final String searchKey = "searchKey";

    @Override
    public void onFault(int messageId, int errorCode, Header[] headers, String reslutContent, Exception e)
    {
        // onSuccess(12, AutoSetValues.setListValues(new ArrayList<HotWrodBean>(), HotWrodBean.class, 50));// TIPS 要刪除
        super.onFault(messageId, errorCode, headers, reslutContent, e);
    }

    @Override
    public <T> void onSuccess(int messageId, T bean)
    {
        @SuppressWarnings("unchecked")
        ArrayList<HotWrodBean> hwblist = ((ArrayList<HotWrodBean>) bean);

        int line = (hwblist.size() + 2) / 3;

        for (int i = 0; i < line; i++)
        {

            TableRow tr = new TableRow(getApplicationContext());

            for (int j = 0; j < 3; j++)
            {
                int wordPosition = 3 * i + j;
                if (wordPosition < hwblist.size())
                {
                    TextView tv_hot = (TextView) View.inflate(getApplicationContext(), R.layout.hot_word, null);
                    tv_hot.setText(hwblist.get(wordPosition).hKey);
                    tv_hot.setTag(hwblist.get(wordPosition));
                    TableRow.LayoutParams param = new TableRow.LayoutParams(-2, -2);
                    param.setMargins(0, 0, DensityUtil.dip2px(3), 0);
                    tv_hot.setLayoutParams(param);
                    tv_hot.setId(R.id.tag_second);
                    tv_hot.setOnClickListener(this);

                    tr.addView(tv_hot);
                }
            }
            TableLayout.LayoutParams param = new TableLayout.LayoutParams(-2, -2);
            param.setMargins(0, DensityUtil.dip2px(3), 0, DensityUtil.dip2px(3));
            tr.setLayoutParams(param);

            tl_search_hotWord.addView(tr);
        }
        // tl_search_hotWord.setShrinkAllColumns(true);

    }

}
