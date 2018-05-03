package com.qc188.com.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.SearchContentBean;
import com.qc188.com.bean.SortBean;
import com.qc188.com.engine.SearchEngine;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.framwork.BaseAsync;
import com.qc188.com.framwork.BaseBean;
import com.qc188.com.ui.adapter.SearchContentExpandableAdapter;
import com.qc188.com.ui.adapter.SortAdapter;
import com.qc188.com.ui.control.SideBar;
import com.qc188.com.util.CommboUtil;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class SearchActivity extends BaseActivity implements OnClickListener, OnChildClickListener {
	private static final String TAG = "SortActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort);
		init();
	}

	private SearchEngine sEngine;
	private ListView lv_sort_list;

	private SideBar sb_ll_sort_select;

	private RelativeLayout rl_searchLeft_drawer; // 点击条目进入的內容
	private DrawerLayout dl_search_drawer; // 整个左划控件

	private void init() {
		// TextView tv_sort_select = (TextView)
		// findViewById(R.id.tv_sort_select);
		setTitleContent("找车");
		sEngine = InstanceFactory.getInstances(SearchEngine.class);
		lv_sort_list = (ListView) findViewById(R.id.lv_sort_list);

		sb_ll_sort_select = (SideBar) findViewById(R.id.sb_ll_sort_select);

		dl_search_drawer = (DrawerLayout) findViewById(R.id.dl_search_drawer);
		// dl_search_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		rl_searchLeft_drawer = (RelativeLayout) findViewById(R.id.fl_searchright_drawer);

		Drawable leftClickSelect = getResources().getDrawable(R.drawable.select_bar_drawer);
		dl_search_drawer.setDrawerShadow(leftClickSelect, GravityCompat.END);
		ViewGroup.LayoutParams params = rl_searchLeft_drawer.getLayoutParams();
		params.width = DensityUtil.getWidthPixels() - DensityUtil.dip2px(70) - leftClickSelect.getIntrinsicWidth() + DensityUtil.dip2px(6);
		rl_searchLeft_drawer.setLayoutParams(params);
		dl_search_drawer.closeDrawer(rl_searchLeft_drawer);
		dl_search_drawer.setVisibility(View.GONE);
		dl_search_drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

			@Override
			public void onDrawerClosed(View drawerView) {
				if (loadBrandDetailContentAsync != null) {

					if (!loadBrandDetailContentAsync.isCancelled()) {
						loadBrandDetailContentAsync.cancel(false);
					}
					loadBrandDetailContentAsync = null;
				}
				if (mSortAdapter != null) {
					mSortAdapter.setSelectPosition(-1);
					mSortAdapter.notifyDataSetChanged();
				}

				if (searchContentAllAdapter != null) {
					searchContentAllAdapter.clearList();
				}

				if (searchContentOnsaleAdapter != null) {
					searchContentOnsaleAdapter.clearList();
				}
				loadingId = "";
				loadOnsale_lastLoadingid = "";
				loadAll_lastLoadingid = "";
				LogUtil.d(TAG, "gone");
				dl_search_drawer.setVisibility(View.GONE);
			}
		});

		findViewId();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (lv_sort_list.getAdapter() == null) {

			GetSortListAsync gsa = new GetSortListAsync(SearchActivity.this);
			String json = ConstantValues.homeUrlPreference.getString(ConstantValues.SORT_ITEM_URL, "");
			if (!TextUtils.isEmpty(json)) {
				ArrayList<SortBean> sortList = sEngine.getSortList(json);
				gsa.onPost(sortList);
			}
			gsa.execute();
			elv_search_expandableContent.setOnItemClickListener(gsa);
		}
	}

	public void findViewId() {

		pb_search_expandableContent_load = (ProgressBar) findViewById(R.id.pb_search_expandableContent_load);
		tv_searchContent_onsale = (TextView) findViewById(R.id.tv_searchContent_onsale);
		tv_searchContent_onsale_line = (TextView) findViewById(R.id.tv_searchContent_onsale_line);
		tv_searchContent_All = (TextView) findViewById(R.id.tv_searchContent_All);
		tv_searchContent_All_line = (TextView) findViewById(R.id.tv_searchContent_All_line);
		tv_searchContent_onsale.setOnClickListener(this);
		tv_searchContent_All.setOnClickListener(this);

		// lv_search_content = (ListView) findViewById(R.id.lv_search_content);
		// lv_search_content.setOnItemClickListener(gsa);

		elv_search_expandableContent = (ExpandableListView) findViewById(R.id.elv_search_expandableContent);
		elv_search_expandableContent.setGroupIndicator(null);

		elv_search_expandableContent.setChildDivider(new ColorDrawable(Color.GRAY));
		// elv_search_expandableContent.setDivider(null);
		elv_search_expandableContent.setOnChildClickListener(this);
	}

	/**
	 * load全部
	 */
	private void selectToAll() {
		tv_searchContent_onsale.setEnabled(true);
		tv_searchContent_onsale.setTextColor(0xFFDEDDDD);
		tv_searchContent_onsale_line.setBackgroundColor(0xFFDEDDDD);
		// tv_searchContent_onsale_line.setVisibility(View.GONE);
		// tv_searchContent_All_line.setVisibility(View.VISIBLE);
		tv_searchContent_All.setEnabled(false);
		tv_searchContent_All.setTextColor(0xFF000000);
		tv_searchContent_All_line.setBackgroundColor(0xFF2C4D91);

		if (!loadAll_lastLoadingid.equals(loadingId) || searchContentAllAdapter == null || searchContentAllAdapter.getGroupCount() < 1) {
			loadAll_lastLoadingid = loadingId;

			if (loadBrandDetailContentAsync != null && !loadBrandDetailContentAsync.isCancelled()) {
				loadBrandDetailContentAsync.cancel(false);
				loadBrandDetailContentAsync = null;
			}
			loadBrandDetailContentAsync = new SearchAllContentAsync(SearchActivity.this);
			loadBrandDetailContentAsync.execute(loadingId);
		} else {
			if (loadAll_lastLoadingid.equals(loadingId)) {
				elv_search_expandableContent.setAdapter(searchContentAllAdapter);
			}
		}
	}

	/**
	 * load在售
	 */
	private void selectToOnsale() {
		tv_searchContent_All.setEnabled(true);
		tv_searchContent_All.setTextColor(0xFFDEDDDD);
		tv_searchContent_All_line.setBackgroundColor(0xFFDEDDDD);
		// tv_searchContent_All_line.setVisibility(View.GONE);
		// tv_searchContent_onsale_line.setVisibility(View.VISIBLE);
		tv_searchContent_onsale.setEnabled(false);
		tv_searchContent_onsale.setTextColor(0xFF000000);
		tv_searchContent_onsale_line.setBackgroundColor(0xFF2C4D91);

		if (!loadOnsale_lastLoadingid.equals(loadingId) || searchContentOnsaleAdapter == null || searchContentOnsaleAdapter.getGroupCount() < 1) {
			loadOnsale_lastLoadingid = loadingId;
			if (loadBrandDetailContentAsync != null && !loadBrandDetailContentAsync.isCancelled()) {
				loadBrandDetailContentAsync.cancel(false);
				loadBrandDetailContentAsync = null;
			}
			loadBrandDetailContentAsync = new SearchOnsaleContentAsync(SearchActivity.this);
			loadBrandDetailContentAsync.execute(loadingId);

		} else {
			if (loadOnsale_lastLoadingid.equals(loadingId)) {
				elv_search_expandableContent.setAdapter(searchContentOnsaleAdapter);
			}
		}

	}

	SortAdapter mSortAdapter;
	private TextView tv_searchContent_onsale;
	private TextView tv_searchContent_onsale_line;
	private TextView tv_searchContent_All;
	private TextView tv_searchContent_All_line;
	// private ListView lv_search_content;
	private ExpandableListView elv_search_expandableContent;
	private String loadingId = "";
	private String loadAll_lastLoadingid = "";
	private String loadOnsale_lastLoadingid = "";

	private BaseAsync<String, ArrayList<SearchContentBean>> loadBrandDetailContentAsync;

	private class GetSortListAsync extends BaseAsync<Void, ArrayList<SortBean>> implements OnItemClickListener, OnScrollListener {
		public GetSortListAsync(Activity activity) {
			super(activity);
		}

		public GetSortListAsync(Activity activity, boolean lock) {
			super(activity, lock);
		}

		@Override
		public void onPost(ArrayList<SortBean> result) {

			if (result != null && result.size() > 0) {
				lv_sort_list.setAdapter(mSortAdapter);
				lv_sort_list.setOnItemClickListener(this);
				lv_sort_list.setOnScrollListener(this);
			}

		}

		@Override
		protected ArrayList<SortBean> doInBackground(Void... params) {
			ArrayList<SortBean> list = sEngine.getSearchList_async();
			if (list != null && list.size() > 0) {
				LogUtil.d(TAG, list.get(0).toString());
				mSortAdapter = new SortAdapter(getApplicationContext(), sb_ll_sort_select, list, lv_sort_list);
				LogUtil.d(TAG, "wordCount;" + mSortAdapter.getWordCount());
				sb_ll_sort_select.setWordCount(mSortAdapter.getWordCount());
				LogUtil.d(TAG, "wordCount;" + mSortAdapter.getWordCount());
			}
			return list;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			if (CommboUtil.isFastDoubleClick() || view instanceof TextView) {
				return;
			}
			LogUtil.d(TAG, "onItemClick:" + position);

			switch (parent.getId()) {
			case R.id.lv_search_content: // 搜索内容的item

				break;
			case R.id.lv_sort_list: // 搜索主界面
				if (view instanceof TextView) {
					return;
				}
				loadingId = "" + view.getTag(R.id.tag_first);
				int selectPosition = mSortAdapter.getSelectPosition();
				if (selectPosition == position) {
					mSortAdapter.setSelectPosition(-1);
					dl_search_drawer.closeDrawer(rl_searchLeft_drawer);
				} else {
					dl_search_drawer.setVisibility(View.VISIBLE);
					dl_search_drawer.openDrawer(rl_searchLeft_drawer);
					mSortAdapter.setSelectPosition(position);
					if (loadBrandDetailContentAsync != null) {

						if (!loadBrandDetailContentAsync.isCancelled()) {
							loadBrandDetailContentAsync.cancel(false);
						}
						loadBrandDetailContentAsync = null;
					}
					selectToOnsale();
					loadOnsale_lastLoadingid = loadingId;
					loadAll_lastLoadingid = "";
					// loadBrandDetailContentAsync.execute(loadingId);
				}//
				mSortAdapter.notifyDataSetChanged();

				break;

			}

		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		}

		@Override
		public void clear() {
			if (mSortAdapter != null) {
				mSortAdapter.clear();
			}
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_searchContent_onsale: // 在售
			selectToOnsale();
			break;
		case R.id.tv_searchContent_All:
			selectToAll();
			break;
		default:
			break;
		}

		expandGroup();
	}

	private SearchContentExpandableAdapter searchContentAllAdapter;// 所有
	private SearchContentExpandableAdapter searchContentOnsaleAdapter; // 在售
	private ProgressBar pb_search_expandableContent_load;

	/**
	 * 搜索内容
	 * 
	 * @author jieranyishen
	 * 
	 */
	public class SearchAllContentAsync extends BaseAsync<String, ArrayList<SearchContentBean>> {
		// private SearchContentAdapter scAdapter;

		public SearchAllContentAsync(Activity activity) {
			super(activity, false);
		}

		public void clear() {
			searchContentAllAdapter.clearList();
		}

		@Override
		public void onPost(ArrayList<SearchContentBean> result) {
			pb_search_expandableContent_load.setVisibility(View.GONE);
			elv_search_expandableContent.setVisibility(View.VISIBLE);
			if (result == null) {
				if (searchContentAllAdapter != null) {
					searchContentAllAdapter.clearList();
				}
				dl_search_drawer.closeDrawers();
				return;
			}

			elv_search_expandableContent.setAdapter(searchContentAllAdapter);
			expandGroup();
		}

		@Override
		protected void onPre() {
			pb_search_expandableContent_load.setVisibility(View.VISIBLE);
			elv_search_expandableContent.setVisibility(View.GONE);
		}

		@Override
		protected ArrayList<SearchContentBean> doInBackground(String... params) {
			SearchEngine se = InstanceFactory.getInstances(SearchEngine.class);

			ArrayList<SearchContentBean> list = se.getSearchContent_Async(params[0]);

			if (list == null) {
				SystemClock.sleep(500);
			}
			searchContentAllAdapter = new SearchContentExpandableAdapter(getApplicationContext(), list);
			return list;
		}
	}

	/**
	 * 搜索内容
	 * 
	 * @author jieranyishen
	 * 
	 */
	public class SearchOnsaleContentAsync extends BaseAsync<String, ArrayList<SearchContentBean>> {

		public SearchOnsaleContentAsync(Activity activity) {
			super(activity, false);
		}

		public void clear() {
			searchContentOnsaleAdapter.clearList();
		}

		@Override
		protected void onPre() {
			pb_search_expandableContent_load.setVisibility(View.VISIBLE);
			elv_search_expandableContent.setVisibility(View.GONE);
			if (searchContentOnsaleAdapter != null) {
				searchContentOnsaleAdapter.clearList();
			}
		}

		@Override
		public void onPost(ArrayList<SearchContentBean> result) {
			pb_search_expandableContent_load.setVisibility(View.GONE);
			elv_search_expandableContent.setVisibility(View.VISIBLE);
			if (result == null) {

				dl_search_drawer.closeDrawers();
				return;
			}

			elv_search_expandableContent.setAdapter(searchContentOnsaleAdapter);
			expandGroup();
		}

		@Override
		protected ArrayList<SearchContentBean> doInBackground(String... params) {
			SearchEngine se = InstanceFactory.getInstances(SearchEngine.class);
			ArrayList<SearchContentBean> list = se.getSearchOnsale_Async(params[0]);
			if (list == null) {
				SystemClock.sleep(500);
			}
			searchContentOnsaleAdapter = new SearchContentExpandableAdapter(getApplicationContext(), list);
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

		LogUtil.d(TAG, "fromFlag:" + getIntent().getStringExtra(ConstantValues.FROM_FLAG));
		if (CompairSelelctActivity.class.getSimpleName().equals(getIntent().getStringExtra(ConstantValues.FROM_FLAG))) {
			startActivity(AddCarTypeActivity.class, new Pair<String, BaseBean>("carBrand", (SearchContentBean) v.getTag(R.id.activity_tag)));
			finish();
		} else {
			startActivity(BrandDetailActivity.class, new Pair<String, BaseBean>("carBrand", (SearchContentBean) v.getTag(R.id.activity_tag)));
		}
		return false;
	}

	private void expandGroup() {

		ExpandableListAdapter expandableListAdapter = elv_search_expandableContent.getExpandableListAdapter();
		if (expandableListAdapter == null) {
			return;
		}
		int groupCount = expandableListAdapter.getGroupCount();
		for (int i = 0; i < groupCount; i++) {
			elv_search_expandableContent.expandGroup(i);
		}
	}
}
