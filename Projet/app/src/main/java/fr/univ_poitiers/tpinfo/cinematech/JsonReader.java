package fr.univ_poitiers.tpinfo.cinematech;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {
    JSONObject json;

    JsonReader(String url) throws IOException, JSONException {
        json = readJsonFromUrl(url);
    }

    public void test() throws IOException, JSONException {
        Log.d("TAG", "ALL :" + json.toString());
        Log.d("TAG", "test: " + json.get("title"));
        Log.d("TAG", "test2: " + json.get("id"));
        Log.d("TAG", "test3: " + json.get("original_language"));
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        URL newURL = new URL(url);
        URLConnection uc = newURL.openConnection();
        InputStream is = uc.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}