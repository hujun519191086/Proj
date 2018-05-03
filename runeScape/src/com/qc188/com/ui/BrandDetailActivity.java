package com.qc188.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.BrandDetailEngineBean;
import com.qc188.com.bean.BrandDetailMsgBean;
import com.qc188.com.bean.KouBeiBean;
import com.qc188.com.bean.SearchContentBean;
import com.qc188.com.engine.BrandDetailEngine;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.framwork.BaseAsync;
import com.qc188.com.ui.MainActivity.AnimCommon;
import com.qc188.com.ui.adapter.BrandDetailKouBeiAdapter;
import com.qc188.com.ui.adapter.SliderAdapter;
import com.qc188.com.ui.control.BrandDetailContent;
import com.qc188.com.ui.manager.CompairManager;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.ConstantValues.STATUS;
import com.qc188.com.util.DataPackageUtil;
import com.qc188.com.util.ImageLoadUtil;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;
import com.qc188.com.util.TextFormatUtil;

/**
 * 车系首页
 * 
 * @author mryang
 * 
 */
public class BrandDetailActivity extends BaseActivity implements OnClickListener, OnItemClickListener
{
    private static final String TAG = "BrandDetailActivity";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brand_list);

        findViews();
        init();
        SearchContentBean scBean = (SearchContentBean) getIntent().getSerializableExtra("carBrand");
		DataPackageUtil.getPackage().put(scBean.getCar_name());
        setTitleContent(scBean.getCar_name());
    }

    private ImageView cursor;
    private int offset = 0;
    private int bmpW;
    private int baseWidth = 0;
    private int margin = 0;

    // 列表顶部
    private RelativeLayout rl_brandList_titleSelect;
    private RelativeLayout rl_maintitle_textbackground;

    private ArrayList<TextView> textLists;

    private class ContentHolder
    {
        TextView tv_brandDetail_level = (TextView) findViewById(R.id.tv_brandDetail_level);
        TextView tv_brandDetail_engine = (TextView) findViewById(R.id.tv_brandDetail_engine);
        TextView tv_brandDetail_transmission = (TextView) findViewById(R.id.tv_brandDetail_transmission);
        TextView tv_brandDetail_guideSale = (TextView) findViewById(R.id.tv_brandDetail_guideSale);
        ImageView iv_brandList_topImage = (ImageView) findViewById(R.id.iv_brandList_topImage);

        BrandDetailContent bdc_brandDetail_content = (BrandDetailContent) findViewById(R.id.bdc_brandDetail_content);

    }

    private void findViews()
    {
        textLists = new ArrayList<TextView>();
        rl_brandList_titleSelect = (RelativeLayout) findViewById(R.id.rl_brandList_titleSelect);
        rl_maintitle_textbackground = (RelativeLayout) findViewById(R.id.rl_maintitle_textbackground);

        // 四个字.
        tv_home_title1 = (TextView) findViewById(R.id.tv_home_title1);
        TextView tv_home_title2 = (TextView) findViewById(R.id.tv_home_title2);
        TextView tv_home_title3 = (TextView) findViewById(R.id.tv_home_title3);
        TextView tv_home_title4 = (TextView) findViewById(R.id.tv_home_title4);
        TextView tv_home_title5 = (TextView) findViewById(R.id.tv_home_title5);
        TextView tv_home_title6 = (TextView) findViewById(R.id.tv_home_title6);
        textLists.add(tv_home_title1);
        textLists.add(tv_home_title2);
        textLists.add(tv_home_title3);
        textLists.add(tv_home_title4);
        textLists.add(tv_home_title5);
        textLists.add(tv_home_title6);
        // 11-30 09:52:19.981: D/BrandDetail_Pic(2537):
        // url:http://www.qc188.com/app/photo.asp?id=1335&page=0

        findViewById(R.id.tv_home_title2).setOnClickListener(this);
        findViewById(R.id.tv_home_title3).setOnClickListener(this);
        findViewById(R.id.tv_home_title4).setOnClickListener(this);
        findViewById(R.id.tv_home_title5).setOnClickListener(this);
        findViewById(R.id.tv_home_title6).setOnClickListener(this);

        ll_brandDetail_content = (LinearLayout) findViewById(R.id.ll_brandDetail_content);

        contentHolder = new ContentHolder();
        tv_brandDetail_showStopSale = (TextView) findViewById(R.id.tv_brandDetail_showStopSale);

        contentHolder.bdc_brandDetail_content.setOnItemClickListener(this);
        tv_brandDetail_showStopSale.setVisibility(View.INVISIBLE);
        tv_brandDetail_showStopSale.setOnClickListener(this);

        rl_brandDetail_content = (RelativeLayout) findViewById(R.id.rl_brandDetail_content);
        // sv_brandDetail_contentScroll = (ScrollView)
        // findViewById(R.id.sv_brandDetail_contentScroll);

        bt_brandDetail_compair = (Button) findViewById(R.id.bt_brandDetail_compair);

    }

    private int brandId;

    @SuppressWarnings("rawtypes")
    @SuppressLint("UseSparseArrays")
    private void init()
    {

        visibleBackButton();
        asyncList = new ArrayList<BaseAsync>();
        allViews = new HashMap<Integer, View>();
        tv_home_title1.setOnClickListener(this);
        tv_home_title1.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout()
            {
                rl_brandList_titleSelect.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                LayoutParams lp = new RelativeLayout.LayoutParams(tv_home_title1.getWidth(), tv_home_title1.getHeight());
                lp.leftMargin = tv_home_title1.getLeft();
                lp.rightMargin = tv_home_title1.getRight();
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                rl_maintitle_textbackground.setLayoutParams(lp);
                baseWidth = findViewById(R.id.tv_home_title1).getWidth();
                rl_brandList_titleSelect.setVisibility(View.VISIBLE);
                rl_maintitle_textbackground.setBackgroundResource(R.drawable.home_title_item_color);
                margin = tv_home_title1.getLeft() + 1;
            }
        });
        cursor = new ImageView(getApplicationContext());
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.page_item1).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 3 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);

        SearchContentBean scBean = (SearchContentBean) getIntent().getSerializableExtra("carBrand");
        brandId = scBean.getCar_id();
        new BrandDetialMsgGetAsync(this).execute(brandId + "");

        lastSelect = R.id.tv_home_title1;

    }

    private int currentItem = 1;
    private int ll_state = 0;
    private TextView tv_home_title1;

    private ListView lv;
    private TextView tv;

    private HashMap<Integer, View> allViews;

    private String fromWhere;
    private String whereName;

    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.iv_home_clickRefresh:
                // hideRefresh();
                return;

            case R.id.tv_brandDetail_showStopSale: // 点击显示停售
                contentHolder.bdc_brandDetail_content.openNotSaleList(this);
                tv_brandDetail_showStopSale.setVisibility(View.GONE);
                return;

            case R.id.bt_brandDetail_compair:// 点击对比
                startActivity(CompairSelelctActivity.class);
                overridePendingTransition(R.anim.activity_enter, R.anim.activirty_out);
                return;
        }

        View view = null;
        if (v.getId() != R.id.tv_home_title2)
        {
            if (lv == null)
            {
                lv = new ListView(getApplicationContext());
                tv = new TextView(getApplicationContext());
                tv.setBackgroundColor(Color.BLACK);
            }

            view = ll_brandDetail_content.getChildAt(0);

            if (allViews.get(lastSelect) == null)
            {
                allViews.put(lastSelect, view);
            }

            ll_brandDetail_content.removeAllViews();
            view = allViews.get(v.getId());

            lastSelect = v.getId();
        }
        switch (v.getId())
        {
            case R.id.tv_home_title1:
                if (0 != ll_state)
                {
                    startTranslateAnimation(proIndex, 0, 0);
                    ll_state = 0;
                    brandDetail(view);
                }
                break;
            case R.id.tv_home_title2:
                if (baseWidth != ll_state)
                {
                    // startTranslateAnimation(proIndex, (baseWidth + margin), 1);
                    // ll_state = baseWidth;
                    brandImage(R.id.tv_home_title2, view, allViews);
                }
                break;
            case R.id.tv_home_title3:// 评测
                if (baseWidth * 2 != ll_state)
                {
                    startTranslateAnimation(proIndex, (baseWidth + margin) * 2, 2);
                    ll_state = baseWidth * 2;
                    fromWhere = ConstantValues.FROMEVALUATION;
                    whereName = "评测";
                    brandTesting(v.getId(), view, allViews);
                }
                break;
            case R.id.tv_home_title4:
                if (baseWidth * 3 != ll_state)
                {
                    startTranslateAnimation(proIndex, (baseWidth + margin) * 3, 3);
                    ll_state = baseWidth * 3;
                    fromWhere = ConstantValues.FROMPRAISE;
                    whereName = "对比";
                    brandKouBei(v.getId(), view, allViews);
                }
                break;
            case R.id.tv_home_title5:// 导购
                LogUtil.d(TAG, " click 5:" + ll_state + ",,,,,:" + baseWidth * 4);
                if (baseWidth * 4 != ll_state)
                {
                    startTranslateAnimation(proIndex, (baseWidth + margin) * 4, 4);
                    ll_state = baseWidth * 4;
                    fromWhere = ConstantValues.FROMEduibi;
                    whereName = "导购";
                    brandCompare(v.getId(), view, allViews);
                }
                break;

            case R.id.tv_home_title6:
                LogUtil.d(TAG, " click 5:" + ll_state + ",,,,,:" + baseWidth * 5);
                if (baseWidth * 5 != ll_state)
                {
                    startTranslateAnimation(proIndex, (baseWidth + margin) * 5, 5);
                    ll_state = baseWidth * 5;
                }
                break;

        }

    }

    public void brandTesting(int id, View historyView, HashMap<Integer, View> allViews)
    {
        if (historyView == null)
        {
            historyView = View.inflate(getApplicationContext(), R.layout.brand_detail_koubei, null);
            allViews.put(id, historyView);

        }
        ll_brandDetail_content.addView(historyView);
        ListView lv = (ListView) historyView.findViewById(R.id.lv_brandDetail_koubeiLIst);
        new LoadKouBeiAsync(this, ConstantValues.BRAND_DETAIL_TESTING + brandId, lv).execute();
    }

    public void brandCompare(int id, View historyView, HashMap<Integer, View> allViews)
    {
        if (historyView == null)
        {
            historyView = View.inflate(getApplicationContext(), R.layout.brand_detail_koubei, null);
            allViews.put(id, historyView);
        }
        ll_brandDetail_content.addView(historyView);
        ListView lv = (ListView) historyView.findViewById(R.id.lv_brandDetail_koubeiLIst);
        new LoadKouBeiAsync(this, ConstantValues.BRAND_DETAIL_COMPARE + brandId, lv).execute();
    }

    /**
     * 详情图文。
     * 
     * @param id
     * @param historyView
     * @param allViews
     */
    @SuppressWarnings("unchecked")
    private void brandImage(int id, View historyView, HashMap<Integer, View> allViews)
    {
        startActivity(BrandDetail_Pic.class, new Pair<String, Integer>("brandId", brandId));
    }

    /**
     * 去详情. 第1个界面
     */
    private void brandDetail(View historyView)
    {
        ll_brandDetail_content.addView(historyView);
    }

    /**
     * 去口碑 第3个界面
     * 
     * @param historyView
     */
    private void brandKouBei(int id, View historyView, HashMap<Integer, View> allViews)
    {
        if (historyView == null)
        {
            historyView = View.inflate(getApplicationContext(), R.layout.brand_detail_koubei, null);
            allViews.put(id, historyView);
        }
        ll_brandDetail_content.addView(historyView);
        ListView lv = (ListView) historyView.findViewById(R.id.lv_brandDetail_koubeiLIst);
        new LoadKouBeiAsync(this, ConstantValues.BRAND_DETAIL_KOUBEI + brandId, lv).execute();
    }

    private int proIndex = 0;

    private void startTranslateAnimation(int fromX, int toX, final int position)
    {
        TranslateAnimation ta = new TranslateAnimation(fromX, toX, 0, 0);
        proIndex = toX;
        // ta.initialize(fromX, toX, 0, 0);
        ta.setDuration(200);
        ta.setAnimationListener(new AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                for (int i = 0; i < textLists.size(); i++)
                {
                    if (position == i)
                    {
                        textLists.get(i).setTextColor(Color.WHITE);
                    }
                    else
                    {
                        textLists.get(i).setTextColor(Color.BLACK);
                    }
                }
            }
        });
        ta.setFillAfter(true);
        rl_maintitle_textbackground.startAnimation(ta);
    }

    private int lastSelect = 0;
    private ArrayList<BaseAsync> asyncList;
    private SliderAdapter sliderAdapter;
    private ContentHolder contentHolder;
    private LinearLayout ll_brandDetail_content;

    @Override
    protected void onStop()
    {
        super.onStop();
        bt_brandDetail_compair.setVisibility(View.GONE);
        CompairManager.getManger().hideCompairView();
        for (int i = 0; i < asyncList.size(); i++)
        {
            asyncList.get(i).clear();
        }
    }

    private TextView tv_brandDetail_showStopSale;
    private RelativeLayout rl_brandDetail_content;
    private ScrollView sv_brandDetail_contentScroll;

    private BrandDetailMsgBean postBdBean;
    private List<BrandDetailEngineBean> postBdeBeanList;
    private Button bt_brandDetail_compair;

    @Override
    protected void onStart()
    {
        // matchData(postBdBean, postBdeBeanList, true);
        if (contentHolder != null)
        {
            contentHolder.bdc_brandDetail_content.notifyData();
        }

        TextView bt_brandDetail_compairCount = (TextView) findViewById(R.id.bt_brandDetail_compairCount);
        CompairManager.getManger().setCountView(bt_brandDetail_compairCount);
        bt_brandDetail_compair.setVisibility(View.VISIBLE);
        bt_brandDetail_compair.setText("对比");
        bt_brandDetail_compair.setOnClickListener(this);
        CompairManager.getManger().showCompairView();
        super.onStart();
    }

    private void matchData(BrandDetailMsgBean bdmBean, List<BrandDetailEngineBean> listBdBean, boolean refresh)
    {
        postBdBean = bdmBean;
        postBdeBeanList = listBdBean;

        if (bdmBean == null || listBdBean == null || listBdBean.size() <= 0)
        {
            if (!refresh)
            {
                out(STATUS.UNKONW_ERROR_INDEX);
            }
            return;
        }
        Map<String, ?> addInCompairList = CompairManager.getManger().getSharedMap();
        contentHolder.tv_brandDetail_engine.setText("发动机：" + bdmBean.getEngine());
        contentHolder.tv_brandDetail_guideSale.setText("指导价：" + bdmBean.getGuideSale());

        TextFormatUtil.changeTextColor(contentHolder.tv_brandDetail_guideSale, 4, -1, Color.RED);
        contentHolder.tv_brandDetail_level.setText("级别：" + bdmBean.getLevel());
        contentHolder.tv_brandDetail_transmission.setText("变速箱：" + bdmBean.getTransmission());
        ImageLoadUtil.loadImageFromDefault(bdmBean.getImage_url(), contentHolder.iv_brandList_topImage, ConstantValues.DEFAULT_DRAWABLE);

        contentHolder.bdc_brandDetail_content.addList(listBdBean, addInCompairList, this);

        if (!contentHolder.bdc_brandDetail_content.hasStopSale())
        {
            tv_brandDetail_showStopSale.setVisibility(View.GONE);
        }
        else
        {
            tv_brandDetail_showStopSale.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 当async返回数据的时候，进行数据填写
     */
    private void matchData(BrandDetailMsgBean bdmBean, List<BrandDetailEngineBean> listBdBean)
    {
        matchData(bdmBean, listBdBean, false);
    }

    /**
     * 获取汽车详情async
     * 
     * @author mryang
     * 
     */
    private class BrandDetialMsgGetAsync extends BaseAsync<String, Integer>
    {

        private BrandDetailEngine bdEngine;

        public BrandDetialMsgGetAsync(Activity activity)
        {
            super(activity);
            setCheckedLink(false);
        }

        @Override
        public void clear()
        {

        }

        @Override
        public void onPost(Integer result)
        {

            if (result != STATUS.SUCCESS)
            {
                out(result);
                return;
            }
            LogUtil.d(TAG, "bdEngine.getBrandDetailBean():" + bdEngine.getBrandDetailBean() + "  bdEngine.getBrandEngineBean():" + bdEngine.getBrandEngineBean());
            matchData(bdEngine.getBrandDetailBean(), bdEngine.getBrandEngineBean());
        }

        @Override
        protected Integer doInBackground(String... params)
        {

            bdEngine = InstanceFactory.getInstances(BrandDetailEngine.class);
            return bdEngine.getJsonToMatchBean(Integer.valueOf(params[0]));
        }

    }

    private class LoadKouBeiAsync extends BaseAsync<Void, List<KouBeiBean>> implements OnItemClickListener
    {

        private String json;

        private String url;

        private ListView lv;

        public LoadKouBeiAsync(Activity activity, String str, ListView lv)
        {
            super(activity);
            this.lv = lv;
            url = str;
            this.json = ConstantValues.homeUrlPreference.getString(url, "-1");
        }

        @Override
        protected void onPre()
        {
            if (json == "-1")
            {
                return;
            }
            UniversalEngine ue = InstanceFactory.getInstances(UniversalEngine.class);
            List<KouBeiBean> list = ue.parseString(json, KouBeiBean.class);
            onPost(list);
        }

        @Override
        public void clear()
        {

        }

        @Override
        public void onPost(List<KouBeiBean> result)
        {
            if (result != null)
            {
                lv.setAdapter(new BrandDetailKouBeiAdapter(getApplicationContext(), result, fromWhere, whereName));
                lv.setOnItemClickListener(this);
            }
        }

        @Override
        protected List<KouBeiBean> doInBackground(Void... params)
        {

            UniversalEngine ue = InstanceFactory.getInstances(UniversalEngine.class);
            LogUtil.d(TAG, "车系条目url:" + url);
            List<KouBeiBean> list = ue.getUrlBean(url, KouBeiBean.class);
            return list;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            // 10-18 13:55:38.192: D/BrandDetailActivity(14500):
            // 车系条目url:http://www.qc188.com/app/testing.asp?id=1919
            Intent intent = new Intent();

            KouBeiBean kbb = (KouBeiBean) arg1.getTag(R.id.tag_first);
            String fromWhere = kbb.getFromWhere();
            String whereName = kbb.getWhereName();
            intent.setClass(getApplicationContext(), ContentActivity.class);
            intent.putExtra(ConstantValues.TO_CONTENT_TAG, "" + kbb.getReputID());
            intent.putExtra(ConstantValues.FROMEWHERE, fromWhere);
            intent.putExtra(ConstantValues.TITLE_NAME, whereName);
            AnimCommon.set(R.anim.froomright_in_translate, R.anim.state_translate);
            startActivity(intent);
        }
    }

    // 10-18 14:26:57.748: D/UniversalEngineImpl(18356):
    // url:http://www.qc188.com/app/dbdgv.asp?msg_id=36758&page_index=0
    // json:{"status":0,"msg":{}}


	@SuppressWarnings("unchecked")
	@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        String brandId = ((Integer) view.getTag(R.id.tag_fourth)).toString();
        String introduce = (String) view.getTag(R.id.tag_third);

        SearchContentBean scBean = (SearchContentBean) getIntent().getSerializableExtra("carBrand");
        startActivity(CarTypeActivity.class, new Pair<String, String>("car_id", scBean.getCar_id() + ""), new Pair<String, String>("brandId", brandId), new Pair<String, String>("introduce", introduce));
    }

    // String json = ConstantValues.homeUrlPreference.getString(url, "-1");
}
