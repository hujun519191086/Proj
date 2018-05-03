package com.qc188.com.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qc188.com.R;
import com.qc188.com.bean.BrandDetailPicSearchMap;
import com.qc188.com.bean.BrandPicBean;
import com.qc188.com.bean.PageInfoBean;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.framwork.BaseAsync;
import com.qc188.com.ui.adapter.BrandDetail_PicAdapter;
import com.qc188.com.ui.adapter.BrandDetail_searchPopAdapter;
import com.qc188.com.ui.control.PullToRefreshView;
import com.qc188.com.ui.control.PullToRefreshView.OnFooterRefreshListener;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;
import com.qc188.com.util.SystemNotification;

public class BrandDetail_Pic extends BaseActivity implements OnItemClickListener, OnClickListener, OnFooterRefreshListener
{

    private GridView gv_brandDetail_pic;
    private String brandId;
    private List<BrandPicBean> allList;
    private BrandDetailPicSearchMap searchUrl; // 搜索的bean
    private PageInfoBean pageInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ImageLoader.getInstance().stop();
        setContentView(R.layout.brand_detail_pic);

        setTitleContent("图片");
        visibleBackButton();
        // 10-02 20:46:43.998: D/BrandDetail_Pic(31060):
        // url:http://www.qc188.com/app/photo.asp?id=1327

        gv_brandDetail_pic = (GridView) findViewById(R.id.gv_brandDetail_pic);

        ptv_brandDetail_pic_refresh = (PullToRefreshView) findViewById(R.id.ptv_brandDetail_pic_refresh);
        brandId = "" + getIntent().getIntExtra("brandId", -1);
        new BrandPicAsync(this).execute(brandId);

        gv_brandDetail_pic.setOnItemClickListener(this);

        setClick();

        ptv_brandDetail_pic_refresh.setOnFooterRefreshListener(this);
    }

    private void setClick()
    {

        findViewById(R.id.tv_brandDetail_pic_selectType).setOnClickListener(this);
        findViewById(R.id.tv_brandDetail_pic_selectColor).setOnClickListener(this);
        findViewById(R.id.tv_brandDetail_pic_selectLevel).setOnClickListener(this);
    }

    private static final String TAG = "BrandDetail_Pic";

    private BrandDetail_PicAdapter bdPicAdapter;

    private class BrandPicAsync extends BaseAsync<String, List<BrandPicBean>>
    {

        private int page = 1;

        public BrandPicAsync(Activity activity)
        {
            super(activity);
        }

        public BrandPicAsync(Activity activity, int page)
        {
            super(activity);
            this.page = page;
        }

        @Override
        public void clear()
        {

        }

        @Override
        public void onPost(List<BrandPicBean> result)
        {
            // LogUtil.d("BaseAsync","result:"+Arrays.toString(result.toArray()));
            if (bdPicAdapter == null)
            {
                bdPicAdapter = new BrandDetail_PicAdapter(getApplicationContext());
            }

            bdPicAdapter.setList(allList);

            if (gv_brandDetail_pic.getAdapter() == null)
            {
                gv_brandDetail_pic.setAdapter(bdPicAdapter);
            }
            if (ptv_brandDetail_pic_refresh.inRefresh())
            {
                ptv_brandDetail_pic_refresh.onFooterRefreshComplete();
            }
        }

        // 12-03 02:41:29.244: D/BrandDetail_Pic(3355):
        // url:http://www.qc188.com/app/photo.asp?id=1313&color=030708&carList=3986&page=0

        @Override
        protected List<BrandPicBean> doInBackground(String... params)
        {
            String url = "";
            if (searchUrl != null)
            {
                url = searchUrl.getUrl(params[0], page);
            }
            else
            {
                url = ConstantValues.BRAND_DETAIL_PIC + params[0] + "&page=" + page;
            }
            LogUtil.d(TAG, "url:" + url);
            UniversalEngine ue = InstanceFactory.getInstances(UniversalEngine.class);
            List<BrandPicBean> tempList = ue.getUrlBean(url, BrandPicBean.class);
            if (tempList == null)
            {
                return null;
            }
            pageInfoBean = ue.getBean(ue.getIgnoreJson().get("pageInfo"), PageInfoBean.class);
            if (allList == null)
            {
                allList = tempList;
            }
            else
            {

                if (pageInfoBean.getIndex() == 1)
                {
                    allList.clear();
                }
                allList.addAll(tempList);
            }
            if (allList == null)
            {
                return null;
            }

            searchUrl = ue.getBean(ue.getIgnoreJson().get("searchUrl"), BrandDetailPicSearchMap.class);
            LogUtil.d("resultBean", "result:" + allList);

            return allList;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        ArrayList<BrandPicBean> picList = (ArrayList<BrandPicBean>) view.getTag();
        BrandPicBean[] bpb = new BrandPicBean[picList.size()];
        picList.toArray(bpb);
        startActivity(BrandDetailPagerActivity.class, new Pair<String, ArrayList<BrandPicBean>>("brandDetail", picList), new Pair<String, Integer>("position", position));
    }

    @Override
    public void onClick(final View v)
    {
        if (searchUrl == null)
        {
            SystemNotification.showToast(getApplicationContext(), "此车型没有图片,无法进行筛选!");
            return;
        }

        ListView lv = new ListView(getApplicationContext());
        lv.setCacheColorHint(0);
        List<String> set = null;
        boolean isColor = false;
        int selectPosition = 0;

        TextView tv = (TextView) v;
        String keyName = tv.getText().toString();

        switch (v.getId())
        {
            case R.id.tv_brandDetail_pic_selectType:
                set = searchUrl.getTypeName();
                selectPosition = searchUrl.getSelectTypePosition(keyName);
                break;
            case R.id.tv_brandDetail_pic_selectColor:
                set = searchUrl.getColorName();
                selectPosition = searchUrl.getSelectColorPosition(keyName);
                isColor = true;
                break;
            case R.id.tv_brandDetail_pic_selectLevel:
                set = searchUrl.getCarNameList();
                selectPosition = searchUrl.getSelectCardListPosition(keyName);
                break;
        }

        LogUtil.d(TAG, "selectPosition:" + selectPosition);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).setCancelable(true).create();
        final List<String> tempList = new ArrayList<String>();
        tempList.addAll(set);
        lv.setBackgroundColor(Color.WHITE);
        lv.setDivider(getResources().getDrawable(R.color.gray));
        lv.setDividerHeight(1);

        BrandDetail_searchPopAdapter bdsPop = new BrandDetail_searchPopAdapter(getApplicationContext(), tempList);
        if (isColor)
        {
            bdsPop.setColorList(searchUrl.getColor());
        }
        bdsPop.setSlectPosition(selectPosition);
        lv.setAdapter(bdsPop);

        // lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
        // R.layout.just_text, tempList));
        lv.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String str = tempList.get(position);
                alertDialog.dismiss();
                select(v.getId(), str);

                TextView tempTv = ((TextView) v);
                tempTv.setText(str);

            }
        });

        alertDialog.show();
        Window dialogWindow = alertDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DensityUtil.getWidthPixels() - DensityUtil.dip2px(5);
        lp.height = DensityUtil.getHeightPixels() - DensityUtil.dip2px(200);
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setContentView(lv, lp);

    }

    private PullToRefreshView ptv_brandDetail_pic_refresh;

    private void select(final int selectKey, final String selectStr)
    {

        new BaseAsync<Void, String>(this)
        {

            @Override
            public void clear()
            {
            }

            @Override
            public void onPost(String result)
            {
                refreshSelect();
            }

            @Override
            protected String doInBackground(Void... params)
            {
                searchUrl.setSearchType(selectKey, selectStr);
                return "abc";
            }
        }.execute();
    }

    // 12-03 01:54:16.521: D/BrandDetail_Pic(3355):
    // url:http://www.qc188.com/app/photo.asp?id=1313&color=BFC1C7&page=0

    /**
     * 刷新删选。
     */
    private void refreshSelect()
    {

        new BrandPicAsync(this).execute(brandId);

    }

    public void onFooterRefresh(PullToRefreshView view)
    {

        if (pageInfoBean.hasNext())
        {

            new BrandPicAsync(this, pageInfoBean.getIndex() + 1).execute(brandId);
        }
        else
        {

            view.onFooterRefreshComplete();

            SystemNotification.showToast(getApplicationContext(), "没有更多数据！");
        }
    }

}
