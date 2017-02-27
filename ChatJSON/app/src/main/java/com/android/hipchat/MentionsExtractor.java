package com.android.hipchat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nanditha.gangappa on 1/10/2017.
 */
public class MentionsExtractor {
 private   static  JSONObject mentionsObject = null;


    static JSONObject extractMentions(String query) {
        mentionsObject = null;
        ArrayList<String> mentionsArray = new ArrayList<>();
        Pattern p = Pattern.compile("(@[A-Z|a-z]+)*");
       Matcher matcher =  p.matcher(query);
        while (matcher.find()) {
            String match = matcher.group();
            System.out.println(" mentions3 " + match);
            if ((match != null) && (match.length() > 1) && (match.startsWith("@"))) {
                mentionsArray.add(match.substring(1));
            }
        }
        for (String s : mentionsArray) {
            System.out.print(s+" * ");
        }
        if (mentionsArray.size() > 0) {
            mentionsObject = new JSONObject();
            try {
                mentionsObject.put(Constants.sJsonString, new JSONArray(mentionsArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("\n"+mentionsObject.toString());
            return mentionsObject;
        } else
        return null;
    }
}
