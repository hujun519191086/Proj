package com.qc188.com.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.util.Pair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.util.InstanceFactory;

public class TypeBean
{
    public ArrayList<Pair<String, ArrayList<Pair<String, String>>>> list;
    public void matchData(String json)
    {
        UniversalEngine ue = InstanceFactory.getInstances(UniversalEngine.class);
        JSONArray jsonArray = (JSONArray) ue.getParseObj(json, TypeBean.class); // [ ] 总共
        list = parseJsonArray(jsonArray);
    }

    public String getMiddleText(int position)
    {
        String text = "";


        if (list != null && list.size() > position)
        {
            text = list.get(position).first;
        }
        else if (list != null && list.size() > position)
        {
            text = list.get(position).first;
        }
        return text;
    }
    public HashMap<String, Integer> groupPositionMap;
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
