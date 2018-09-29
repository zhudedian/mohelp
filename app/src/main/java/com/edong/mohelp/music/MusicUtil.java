package com.edong.mohelp.music;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MusicUtil {

    public static List<Music> format(String json) throws JSONException{
        List<Music> list = null;
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.has("result")){
            JSONObject resultObject = jsonObject.getJSONObject("result");
            if (resultObject.has("sds")){
                JSONObject sdsObject = resultObject.getJSONObject("sds");
                if (sdsObject.has("data")){
                    JSONObject dataObject = sdsObject.getJSONObject("data");
                    if (dataObject.has("dbdata")){
                        JSONArray dbdata = dataObject.getJSONArray("dbdata");
                        list = new Gson().fromJson(dbdata.toString(), new TypeToken<List<Music>>() {}.getType());
                    }
                }
            }
        }
        return list;
    }
}
