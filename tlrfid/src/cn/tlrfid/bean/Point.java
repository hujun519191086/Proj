package cn.tlrfid.bean;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;

public class Point {
	public Point pre;
	private HashMap<Integer, Point> childs;

	private ArrayList<Integer> keys;

	public HashMap<Integer, Point> getChilds() {
		return childs;
	}

	public int deep = 0;

	@Override
	public String toString() {
		return "PointBean [pre=" + (pre == null ? null : pre.psBean.getId())
				+ ", childs=" + childs + ", psBean=" + psBean + "]";
	}

	@SuppressLint("UseSparseArrays")
	public void setChilds(Point child) {
		if (childs == null) {
			childs = new HashMap<Integer, Point>();
			keys = new ArrayList<Integer>();
		}
		child.pre = this;
		child.deep = deep + 1;
		keys.add(child.psBean.getId());
		childs.put(child.psBean.getId(), child);
	}

	public ArrayList<Integer> getKeys() {
		return keys;
	}

	public boolean hasChild() {
		return childs == null ? false : childs.size() < 1 ? false : true;
	}

	// �ӽڵ��Ƿ�����??/
	public boolean childHasChild() {
		if (hasChild()) {
			for (int i = 0; i < childs.size(); i++) {
				if (childs.get(i).hasChild()) {
					return true;
				}
			}
		}
		return false;
	}

	public int childCount() {
		return hasChild() ? childs.size() : 0;
	}

	public ProjectScheduleBean psBean;

}
