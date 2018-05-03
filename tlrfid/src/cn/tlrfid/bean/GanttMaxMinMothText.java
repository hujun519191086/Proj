package cn.tlrfid.bean;

import java.io.Serializable;

import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.view.GanttChartActivity;
import android.text.TextUtils;

/**
 * 工期时间计算
 * 
 * @author Administrator
 * 
 */
public class GanttMaxMinMothText implements Serializable {
	
	private static final String TAG = "GanttMaxMinMothText";
	private int mothCount; // 从头至尾一共有多少个月份
	private int startMoth;// 从几月份开始
	private int startYear;// 从几几年开始
	private int startDay;// 从几号开始
	private String projectTitle;// 仅能在 ProjectManageActivity 跳转到ProgressQueryActivity有效的字符. 其他地点不能使用.
	
	private int endMoth;// 从几月结束
	private int endYear;// 几几年结束
	private int endDay; // 几号结束
	
	public int getStartDay() {
		return startDay;
	}
	
	public int getEndDay() {
		return endDay;
	}
	
	public int getMothCount() {
		return mothCount;
	}
	
	public int getStartMoth() {
		return startMoth;
	}
	
	public int getEndMoth() {
		return endMoth;
	}
	
	private int marginLeft;
	private int width;
	
	public int getWidth() {
		return width;
	}
	
	private String projectName;
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public int getMarginLeft(String allStartTime) {
		String[] startSpilt = allStartTime.split("-");
		int offsetYear = startYear - Integer.valueOf(startSpilt[0]);
		
		int width = GanttChartActivity.WIDTHOFFSET * 12 * offsetYear;
		width += (startMoth - Integer.valueOf(startSpilt[1])) * GanttChartActivity.WIDTHOFFSET;
		marginLeft += width;
		return marginLeft;
	}
	
	private int progress;
	
	public int getProgress() {
		return progress;
	}
	
	private int finishRate; // 百分比
	
	public void setProgress(int finishRate) {
		this.finishRate = finishRate;
		progress = width * finishRate / 100;
	}
	
	private int outofWidth;
	
	public int getOutofWidth() {
		return outofWidth;
	}
	
	private int outOfDay;
	
	public int getFinishRate() {
		return finishRate;
	}
	
	public int getOutOfDay() {
		return outOfDay;
	}
	
	public void setOutofWidth(int outOfDay) {
		this.outOfDay = outOfDay;
		outofWidth = GanttChartActivity.DAY_WIDTHOFFSET * outOfDay;
	}
	
	public void setMothCount(int mothCount) {
		this.mothCount = mothCount;
	}
	
	private String startTime;// 计划开始时间
	private String endTime; // 计划关闭时间
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public GanttMaxMinMothText(String startTime, String endTime) {
		if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
			throw new RuntimeException("不能传入空字符串!!!");
		}
		this.startTime = startTime;
		this.endTime = endTime;
		String start[] = startTime.split("-");
		String end[] = endTime.split("-");
		if (start.length < 2 || end.length < 2) {
			throw new RuntimeException("设置工期开启时间和关闭时间错误!!!");
		} else {
			
			startYear = Integer.valueOf(start[0]);
			startMoth = Integer.valueOf(start[1]);
			startDay = Integer.valueOf(start[2]);
			
			endYear = Integer.valueOf(end[0]);
			endMoth = Integer.valueOf(end[1]);
			endDay = Integer.valueOf(end[2]);
			
			if (endYear - startYear > 0) {
				int tempStartYearMoth = 12 - startMoth;
				tempStartYearMoth += (endYear - startYear - 1) * 12;
				tempStartYearMoth += endMoth;
				mothCount = tempStartYearMoth + 1;
			} else {
				mothCount = endMoth - startMoth + 1;
			}
			
			marginLeft = GanttChartActivity.DAY_WIDTHOFFSET * startDay;
			
			int marginRight = GanttChartActivity.DAY_WIDTHOFFSET * (30 - endDay);
			width = mothCount * GanttChartActivity.WIDTHOFFSET - marginLeft - marginRight;
			LogUtil.d(TAG, "width:" + width);
		}
		
	}
	
	public String getProjectTitle() {
		return projectTitle;
	}
	
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
}
