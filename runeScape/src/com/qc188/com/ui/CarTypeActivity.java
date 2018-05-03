package com.qc188.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.BrandDetailPicSearchMap;
import com.qc188.com.bean.BrandPicBean;
import com.qc188.com.bean.CarTypeItemBean;
import com.qc188.com.bean.CarTypeTitleBean;
import com.qc188.com.bean.PageInfoBean;
import com.qc188.com.bean.TypeBean;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.framwork.BaseAsync;
import com.qc188.com.ui.adapter.BrandDetail_PicAdapter;
import com.qc188.com.ui.adapter.BrandDetail_searchPopAdapter;
import com.qc188.com.ui.adapter.CarTypeAdapter;
import com.qc188.com.ui.adapter.CarTypeOptionAdapter;
import com.qc188.com.ui.control.CompairContentView;
import com.qc188.com.ui.control.PullToRefreshView;
import com.qc188.com.ui.control.PullToRefreshView.OnFooterRefreshListener;
import com.qc188.com.ui.manager.CompairManager;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.HttpClientUtils;
import com.qc188.com.util.ImageLoadUtil;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;
import com.qc188.com.util.SystemNotification;

public class CarTypeActivity extends BaseActivity implements OnClickListener, OnScrollListener, OnFooterRefreshListener
{

    private ArrayList<TextView> textLists;
    private String introduce;
    private String car_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.car_type);
        ll_carType_content = findById(R.id.ll_carType_content);
        rl_carType_textbackground = findById(R.id.rl_carType_textbackground);

        textLists = new ArrayList<TextView>();
        allViews = new HashMap<Integer, View>();

        iv_carType_topImage = findById(R.id.iv_carType_topImage);
        tv_carType_structure = findById(R.id.tv_carType_structure);
        tv_carType_drive = findById(R.id.tv_carType_drive);
        tv_carType_drive_gearbox = findById(R.id.tv_carType_drive_gearbox);
        tv_carType_guild_sale = findById(R.id.tv_carType_guild_sale);
        tv_carType_the_lowest = findById(R.id.tv_carType_the_lowest);
        lv_carType_indexAreaList = findById(R.id.lv_carType_indexAreaList);

        final RelativeLayout rl_carType_titleSelect = findById(R.id.rl_carType_titleSelect);
        final TextView tv_carType_title1 = (TextView) findViewById(R.id.tv_carType_title1);
        TextView tv_carType_title2 = (TextView) findViewById(R.id.tv_carType_title2);
        TextView tv_carType_title3 = (TextView) findViewById(R.id.tv_carType_title3);
        textLists.add(tv_carType_title1);
        textLists.add(tv_carType_title2);
        textLists.add(tv_carType_title3);
        findViewById(R.id.tv_carType_title1).setOnClickListener(this);
        findViewById(R.id.tv_carType_title2).setOnClickListener(this);
        findViewById(R.id.tv_carType_title3).setOnClickListener(this);

        lastSelect = R.id.tv_carType_title1;
        brandId = (String) getIntent().getSerializableExtra("brandId");
        car_id = (String) getIntent().getSerializableExtra("car_id");
        introduce = (String) getIntent().getSerializableExtra("introduce");

        LogUtil.d(TAG, "introduce:" + introduce);
        setTitleContent(introduce);

        TextView tv_carType_addToCompair = findById(R.id.tv_carType_addToCompair);
        checkInCompairMap(tv_carType_addToCompair);

        tv_carType_addToCompair.setOnClickListener(this);

        new CardTypeContentAsync(this).execute(brandId);

        tv_carType_title1.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout()
            {
                tv_carType_title1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                LayoutParams lp = new RelativeLayout.LayoutParams(tv_carType_title1.getWidth(), tv_carType_title1.getHeight());
                lp.leftMargin = tv_carType_title1.getLeft();
                lp.rightMargin = tv_carType_title1.getRight();
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                rl_carType_textbackground.setLayoutParams(lp);
                baseWidth = tv_carType_title1.getWidth();
                rl_carType_titleSelect.setVisibility(View.VISIBLE);
                rl_carType_textbackground.setBackgroundResource(R.drawable.home_title_item_color);
                margin = tv_carType_title1.getLeft() + 1;
            }
        });
        visibleBackButton();
        bt_brandDetail_compair = (Button) findViewById(R.id.bt_brandDetail_compair);
        bt_brandDetail_compair.setVisibility(View.VISIBLE);
        bt_brandDetail_compair.setText("对比");
        bt_brandDetail_compair.setOnClickListener(this);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        postDelay(new Runnable()
        {

            @Override
            public void run()
            {

                TextView bt_brandDetail_compairCount = (TextView) findViewById(R.id.bt_brandDetail_compairCount);
                CompairManager.getManger().setCountView(bt_brandDetail_compairCount);
                CompairManager.getManger().showCompairView();
            }
        }, 800);

    }

    private Button bt_brandDetail_compair;

    private int margin = 0;
    private int ll_state = 0;
    private int proIndex = 0;
    private RelativeLayout rl_carType_textbackground;
    private LinearLayout ll_carType_content;

    private HashMap<Integer, View> allViews;
    private int lastSelect = 0;
    private int baseWidth = 0;

    private CarTypeOptionAdapter carTypeAdapter;

    public void onClick(View v)
    {

        switch (v.getId())
        {

            case R.id.tv_carType_addToCompair:
                CompairManager.getManger().putCompair(brandId.toString(), introduce);
                checkInCompairMap(v);
                return;
            case R.id.tw_carType_detail_showTitleText:
                if (tw_carType_detail_titleTab.getVisibility() == View.VISIBLE)
                {
                    TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -tw_carType_detail_titleTab.getHeight());
                    ta.setDuration(500);
                    ta.setFillAfter(false);
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
                            tw_carType_detail_titleTab.setVisibility(View.GONE);

                        }
                    });

                    if (workView != null)
                    {
                        workView.startAnimation(ta);
                    }
                    else
                    {
                        tw_carType_detail_titleTab.startAnimation(ta);
                    }
                }
                else
                {
                    TranslateAnimation ta = new TranslateAnimation(0, 0, -tw_carType_detail_titleTab.getHeight(), 0);
                    ta.setDuration(500);
                    ta.setFillAfter(false);
                    tw_carType_detail_titleTab.setVisibility(View.VISIBLE);

                    if (workView != null)
                    {
                        workView.startAnimation(ta);

                    }
                    else
                    {
                        tw_carType_detail_titleTab.startAnimation(ta);

                    }
                }
                return;

            case R.id.bt_brandDetail_compair:
                startActivity(CompairSelelctActivity.class);
                overridePendingTransition(R.anim.activity_enter, R.anim.activirty_out);
                return;

            case R.id.tv_carType_pic_selectType: // 点击颜色或者类型
            case R.id.tv_carType_pic_selectColor:

                alertPicDialog(v);
                return;
            default:
        }

        // switch (v.getId())
        // {
        // case R.id.iv_home_clickRefresh:
        // // hideRefresh();
        // return;
        //
        // case R.id.tv_brandDetail_showStopSale: // 点击显示停售
        // return;
        //
        // case R.id.bt_brandDetail_compair:// 点击对比
        // startActivity(CompairSelelctActivity.class);
        // overridePendingTransition(R.anim.activity_enter,
        // R.anim.activirty_out);
        // return;
        // }
        //
        View view = null;

        view = ll_carType_content.getChildAt(0);

        if (allViews.get(lastSelect) == null)
        {
            allViews.put(lastSelect, view);
        }

        ll_carType_content.removeAllViews();
        view = allViews.get(v.getId());
        lastSelect = v.getId();
        switch (v.getId())
        {
            case R.id.tv_carType_title1:
                if (0 != ll_state)
                {
                    startTranslateAnimation(proIndex, 0, 0);
                    ll_state = 0;
                    ll_carType_content.addView(view);
                    workView = view;
                    // brandDetail(view);
                    new CardTypeContentAsync(this, false).execute(brandId);
                }
                break;
            case R.id.tv_carType_title2:
                if (baseWidth != ll_state)
                {
                    startTranslateAnimation(proIndex, (baseWidth + margin) * 1, 1);
                    ll_state = baseWidth * 1;
                    if (view == null)
                    {
                        view = View.inflate(getApplicationContext(), R.layout.car_type_image, null);
                        allViews.put(v.getId(), view);
                    }
                    workView = view;
                    ll_carType_content.addView(view);
                    gv_carType_pic = findById(R.id.gv_carType_pic);
                    ptv_carType_pic_refresh = findById(R.id.ptv_carType_pic_refresh);
                    tv_carType_pic_selectType = findById(R.id.tv_carType_pic_selectType);
                    tv_carType_pic_selectColor = findById(R.id.tv_carType_pic_selectColor);
                    ptv_carType_pic_refresh2 = findById(R.id.ptv_carType_pic_refresh);
                    ptv_carType_pic_refresh2.setOnFooterRefreshListener(this);
                    tv_carType_pic_selectType.setOnClickListener(this);
                    tv_carType_pic_selectColor.setOnClickListener(this);

                    new CarTypePicAsync(this).execute(((String) getIntent().getSerializableExtra("brandId")));
                    // startTranslateAnimation(proIndex, (baseWidth + margin), 1);
                    // ll_state = baseWidth;
                    // brandImage(R.id.tv_home_title2, view, allViews);
                }
                break;
            case R.id.tv_carType_title3:// 性能
                if (baseWidth * 2 != ll_state)
                {
                    startTranslateAnimation(proIndex, (baseWidth + margin) * 2, 2);
                    ll_state = baseWidth * 2;
                    if (view == null)
                    {
                        view = View.inflate(getApplicationContext(), R.layout.car_type_detail, null);
                        allViews.put(v.getId(), view);
                    }
                    workView = view;
                    ll_carType_content.addView(view);
                    elv_carType_Content = findById(R.id.elv_carType_Content);
                    tv_carType_flower = findById(R.id.tv_carType_flower);
                    tw_carType_detail_showTitleText = findById(R.id.tw_carType_detail_showTitleText);
                    tw_carType_detail_showTitleText.setOnClickListener(this);
                    tw_carType_detail_titleTab = (TableLayout) view.findViewById(R.id.tw_carType_detail_titleTab);
                    ccView = new CompairContentView(tw_carType_detail_titleTab, new TabItemClick(), false);
                    new CarOptionMsgAysnc(this).execute(brandId);
                    // fromWhere = ConstantValues.FROMEVALUATION;
                    // whereName = "评测";
                    // brandTesting(v.getId(), view, allViews);
                }
                break;

        }
    }

    private View workView;

    private void checkInCompairMap(View tv)
    {

        if (CompairManager.getManger().inCompairMap(brandId.toString()))
        {
            ((TextView) tv).setText("已添加");
            tv.setEnabled(false);
        }
        else
        {
            ((TextView) tv).setText("+对比");
            tv.setEnabled(true);

        }
    }

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
        rl_carType_textbackground.startAnimation(ta);
    }

    private class CardTypeContentAsync extends BaseAsync<String, Pair<CarTypeTitleBean, List<CarTypeItemBean>>>
    {
        // /CarTypeItemBean
        public CardTypeContentAsync(Activity activity)
        {
            super(activity);
        }

        public CardTypeContentAsync(Activity activity, boolean lock)
        {
            super(activity, lock);
        }

        @Override
        public void clear()
        {

        }

        @Override
        public void onPost(Pair<CarTypeTitleBean, List<CarTypeItemBean>> result)
        {
            LogUtil.d(TAG, "result:" + result.first + "    second:" + result.second);
            CarTypeTitleBean ctBean = result.first;
            if (ctBean == null)
            {
                SystemNotification.showToast(getApplicationContext(), "数据加载失败...请重试!");
                return;
            }
            tv_carType_structure.setText("车身结构: " + ctBean.getStructure());
            tv_carType_drive.setText("驱动方式: " + ctBean.getDrive());
            tv_carType_drive_gearbox.setText("变速箱: " + ctBean.getDrive_gearbox());
            tv_carType_guild_sale.setText("厂商指导价: " + ctBean.getGuild_sale());
            tv_carType_the_lowest.setText(ctBean.getThe_lowest());

			CarTypeAdapter ctadapter = new CarTypeAdapter(getApplicationContext(), CarTypeActivity.this);
            ctadapter.setItemList(result.second);
            lv_carType_indexAreaList.setAdapter(ctadapter);
            ImageLoadUtil.loadImageFromDefault(ctBean.getImg(), iv_carType_topImage, ConstantValues.DEFAULT_DRAWABLE);
        }

        @Override
        protected Pair<CarTypeTitleBean, List<CarTypeItemBean>> doInBackground(String... params)
        {
            UniversalEngine ue = InstanceFactory.getInstances(UniversalEngine.class);
            CarTypeTitleBean ctBean = ue.getUrlBean(ConstantValues.CAR_TYPE_URL + params[0], CarTypeTitleBean.class);
            List<CarTypeItemBean> list = ue.getBean(ue.getIgnoreJson().get("address"), CarTypeItemBean.class);
            return new Pair<CarTypeTitleBean, List<CarTypeItemBean>>(ctBean, list);
        }
    }

    BrandDetail_PicAdapter bdPicAdapter;
    private PullToRefreshView ptv_carType_pic_refresh;
    private GridView gv_carType_pic;

    private static final String TAG = "CarTypeActivity";

    private class CarTypePicAsync extends BaseAsync<String, List<BrandPicBean>>
    {

        private int page = 1;

        public CarTypePicAsync(Activity activity)
        {
            super(activity);
        }

        public CarTypePicAsync(Activity activity, int page)
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
            if (pageInfoBean.hasNext())
            {
                ptv_carType_pic_refresh2.setVisibility(View.VISIBLE);
            }
            else
            {
                ptv_carType_pic_refresh2.setVisibility(View.INVISIBLE);
            }

            if (gv_carType_pic.getAdapter() == null)
            {
                gv_carType_pic.setAdapter(bdPicAdapter);
            }
            if (ptv_carType_pic_refresh.inRefresh())
            {
                ptv_carType_pic_refresh.onFooterRefreshComplete();
            }
        }

        @Override
        protected List<BrandPicBean> doInBackground(String... params)
        {
            String url = "";
            if (searchUrl != null)
            {
                url = searchUrl.getUrl(car_id, page);
            }
            else
            {
                url = ConstantValues.BRAND_CARlIST_DETAIL_PIC + car_id + "&cid=" + params[0] + "&page=" + page;
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
            searchUrl.carListUrl = "&cid=" + params[0];
            LogUtil.d("resultBean", "result:" + allList);

            return allList;
        }
    }

    private PageInfoBean pageInfoBean;
    private BrandDetailPicSearchMap searchUrl; // 搜索的bean
    private List<BrandPicBean> allList;
    private String brandId;

    // item1 的参数
    private ImageView iv_carType_topImage;
    private TextView tv_carType_structure;
    private TextView tv_carType_drive;
    private TextView tv_carType_drive_gearbox;
    private TextView tv_carType_guild_sale;
    private TextView tv_carType_the_lowest;
    private ListView lv_carType_indexAreaList;
    private CompairContentView ccView;
    private ExpandableListView elv_carType_Content;
    private TextView tv_carType_flower;

    // item1 end
    private TypeBean cBean;
    private TextView tw_carType_detail_showTitleText;
    private TableLayout tw_carType_detail_titleTab;
    private TextView tv_carType_pic_selectColor;
    private TextView tv_carType_pic_selectType;
    private PullToRefreshView ptv_carType_pic_refresh2;

    private class CarOptionMsgAysnc extends BaseAsync<String, TypeBean>
    {

        public CarOptionMsgAysnc(Activity activity)
        {
            super(activity);
        }

        @Override
        public void clear()
        {

        }

        @Override
        public void onPost(TypeBean result)
        {
            // cBean = result;
            // LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,
            // totalHeight);
            // elv_compairContent.setLayoutParams(p);

            if (elv_carType_Content != null)
            {
                elv_carType_Content.setAdapter(carTypeAdapter);
                int groupCount = carTypeAdapter.getGroupCount();
                for (int i = 0; i < groupCount; i++)
                {
                    elv_carType_Content.expandGroup(i);
                }
                elv_carType_Content.setGroupIndicator(null);
                elv_carType_Content.setOnScrollListener(CarTypeActivity.this);
                ccView.matchData(result.groupPositionMap);
            }
        }

        @Override
        protected TypeBean doInBackground(String... params)
        {

            String json = HttpClientUtils.getJson(ConstantValues.CAR_TYPE_OPTION_URL + params[0]);
            cBean = new TypeBean();
            if (json == null)
            {
                return null;
            }
            cBean.matchData(json);
            carTypeAdapter = new CarTypeOptionAdapter(getApplicationContext());
            carTypeAdapter.addData(cBean);
            return cBean;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        LogUtil.d(TAG, "totalItemCount:" + totalItemCount);
        View childView = view.getChildAt(1);
        if (childView == null)
        {
            childView = view.getChildAt(0);

            if (childView != null)
            {
                ccView.changBackgroud(cBean.list.get(0).first);
                tv_carType_flower.setText(cBean.list.get(0).first);
            }
            return;
        }

        if (childView instanceof TextView)
        {
            changeConpairContentFlower(((TextView) childView).getText().toString(), -1);
            childView = view.getChildAt(0);
            RelativeLayout.LayoutParams params = (LayoutParams) tv_carType_flower.getLayoutParams();
            params.topMargin = childView.getTop();
            tv_carType_flower.setLayoutParams(params);
            LogUtil.d(TAG, "groupTop:" + childView.getTop());
        }
        else
        {
            childView = view.getChildAt(0);
            if (childView != null && childView instanceof TextView)
            {
                changeConpairContentFlower(((TextView) childView).getText().toString(), 0);
            }
            RelativeLayout.LayoutParams params = (LayoutParams) tv_carType_flower.getLayoutParams();
            params.topMargin = 0;
            tv_carType_flower.setLayoutParams(params);
            tv_carType_flower.setPadding(0, 0, 0, 0);

        }
    }

    /**
     * 弹出图片选择框.
     * 
     * @param v
     */
    private void alertPicDialog(final View v)
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

            case R.id.tv_carType_pic_selectType: // 点击颜色或者类型
                set = searchUrl.getTypeName();
                selectPosition = searchUrl.getSelectTypePosition(keyName);
                break;
            case R.id.tv_carType_pic_selectColor:
                set = searchUrl.getColorName();
                selectPosition = searchUrl.getSelectColorPosition(keyName);
                isColor = true;
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

    // 01-21 16:08:40.218: D/UniversalEngineImpl(28647): url:http://www.qc188.com/app/carphoto.asp?id=1052&cid=8342&page=1 json:{"status": 0,

    /**
     * 刷新删选。
     */
    private void refreshSelect()
    {
        new CarTypePicAsync(this).execute(brandId);
    }

    private void changeConpairContentFlower(String text, int fix)
    {
        if (tw_carType_detail_showTitleText != null)
        {
            Integer position = cBean.groupPositionMap.get(text);
            text = cBean.getMiddleText(position + fix);
            ccView.changBackgroud(text);
            tw_carType_detail_showTitleText.setText(text);
            tv_carType_flower.setText(text);
        }
    }

    private class TabItemClick implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            TextView tempView = (TextView) v;
            LogUtil.d(TAG, "TabItemClick:" + tempView.getText().toString());
            elv_carType_Content.setSelectedGroup(cBean.groupPositionMap.get(tempView.getText().toString()));
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view)
    {
        if (pageInfoBean.hasNext())
        {
            view.visibleFooter();
            new CarTypePicAsync(this, pageInfoBean.getIndex() + 1).execute(brandId);
        }
        else
        {

            view.onFooterRefreshComplete();
            view.hideFooter();
            SystemNotification.showToast(getApplicationContext(), "没有更多数据！");
        }
    }
}
