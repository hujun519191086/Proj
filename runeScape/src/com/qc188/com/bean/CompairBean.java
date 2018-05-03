package com.qc188.com.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.util.Pair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class CompairBean
{

    private static final String TAG = "CompairBean";
    public ArrayList<Pair<String, ArrayList<Pair<String, String>>>> leftList;
    public ArrayList<Pair<String, ArrayList<Pair<String, String>>>> rightList;

    public ArrayList<Pair<String, ArrayList<Pair<String, String>>>> differentLeft;
    public ArrayList<Pair<String, ArrayList<Pair<String, String>>>> differentRight;

    public HashMap<String, Integer> groupPositionMap;
    public HashMap<String, Integer> differentPositinMap;

    public String getMiddleText(int position, boolean isDifferent)
    {
        String text = "";

        if (isDifferent)
        {

            if (differentLeft != null && differentLeft.size() > position)
            {

                text = differentLeft.get(position).first;
            }
            else if (differentLeft != null && differentRight.size() > position)
            {
                text = differentRight.get(position).first;
            }
        }

        if (leftList != null && leftList.size() > position)
        {
            text = leftList.get(position).first;
        }
        else if (rightList != null && rightList.size() > position)
        {
            text = rightList.get(position).first;
        }
        return text;
    }

    public void matchData(String json)
    {
        UniversalEngine ue = InstanceFactory.getInstances(UniversalEngine.class);
        JSONArray jsonArray = (JSONArray) ue.getParseObj(json, CompairBean.class); // [ ] 总共

        leftList = parseJsonArray(jsonArray);
        HashMap<String, Object> ignore = ue.getIgnoreJson();
        jsonArray = (JSONArray) ignore.get("comparisonRight");
        rightList = parseJsonArray(jsonArray);
        compairDifferent();
    }

    private void compairDifferent()
    {
        differentLeft = new ArrayList<Pair<String, ArrayList<Pair<String, String>>>>();
        differentRight = new ArrayList<Pair<String, ArrayList<Pair<String, String>>>>();
        differentPositinMap = new HashMap<String, Integer>();
        int groupCount = 0;
        for (int i = 0; i < leftList.size(); i++)
        {
            ArrayList<Pair<String, String>> leftContent = leftList.get(i).second;
            ArrayList<Pair<String, String>> rightContent = rightList.get(i).second;

            ArrayList<Pair<String, String>> leftDifList = new ArrayList<Pair<String, String>>();
            ArrayList<Pair<String, String>> rightDifList = new ArrayList<Pair<String, String>>();
            if (leftContent != null && rightContent != null)
            {
                for (int j = 0; j < leftContent.size(); j++)
                {
                    String leftValue = leftContent.get(j).second;

                    String rightValue = rightContent.get(j).second;

                    if (!leftValue.equals(rightValue))// 不相等
                    {
                        leftDifList.add(new Pair<String, String>(leftContent.get(j).first, leftValue));
                        rightDifList.add(new Pair<String, String>(rightContent.get(j).first, rightValue));
                    }
                }
            }
            if (leftDifList.size() > 0)
            {
                differentPositinMap.put(leftList.get(i).first, groupCount++);
                LogUtil.d(TAG, "putDiffrernt:" + leftList.get(i).first);
                differentLeft.add(new Pair<String, ArrayList<Pair<String, String>>>(leftList.get(i).first, leftDifList));
                differentRight.add(new Pair<String, ArrayList<Pair<String, String>>>(rightList.get(i).first, rightDifList));
            }
        }

    }

    private ArrayList<Pair<String, ArrayList<Pair<String, String>>>> parseJsonArray(JSONArray jsonArray)
    {
        ArrayList<Pair<String, ArrayList<Pair<String, String>>>> leftList = new ArrayList<Pair<String, ArrayList<Pair<String, String>>>>();
        groupPositionMap = new HashMap<String, Integer>();
        int groupCount = 0;
        for (int i = 0; i < jsonArray.size(); i++)
        {
            Pair<String, ArrayList<Pair<String, String>>> firstMap = null;
            JSONObject jsonObj = (JSONObject) jsonArray.get(i); // "":{"":""} 这个模式

            Set<String> keySet = jsonObj.keySet();

            for (String set : keySet)
            {
                groupPositionMap.put(set, groupCount++);
                String keyStr = keySet.toArray()[0].toString(); // "":
                jsonObj = (JSONObject) jsonObj.get(keyStr); // {"":""}
                ArrayList<Pair<String, String>> childMap = new ArrayList<Pair<String, String>>();
                Set<String> childKeySet = jsonObj.keySet();
                for (String childKey : childKeySet)
                {
                    String childValue = (String) jsonObj.get(childKey);
                    childMap.add(new Pair<String, String>(childKey, childValue));
                }
                firstMap = new Pair<String, ArrayList<Pair<String, String>>>(set, childMap);
            }
            leftList.add(firstMap);
        }

        return leftList;

    }
}
