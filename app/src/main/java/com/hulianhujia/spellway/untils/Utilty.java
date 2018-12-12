package com.hulianhujia.spellway.untils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017\6\9 0009.
 */

public class Utilty {

    /**
     * Utilty解析数据
     */

    public static boolean handleResponse(String response) {

        if (response != null) {
            try {
                JSONArray jsonArray = new JSONArray(response);
//                JSONObject jsonObject = new JSONObject(response);
                for (int i = 0; i < jsonArray.length() ; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //JavaBean
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /*public static Weather handleWeatherResponse(String response) {

        if (response != null) {
            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
                String weatherContent=jsonArray.getJSONObject(0).toString();
                Weather weather = new Gson().fromJson(weatherContent, Weather.class);
//                Log.e("=========", "handleWeatherResponse: "+weather.toString() );
                return weather;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }*/

}
