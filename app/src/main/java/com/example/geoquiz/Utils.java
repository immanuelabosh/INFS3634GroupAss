package com.example.geoquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.pixplicity.sharp.Sharp;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {
    private static OkHttpClient httpClient;
    private static String score = "score";

    public static void fetchSvg(Context context, String url, final ImageView target) {
        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
                    .build();
        }

        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               // target.setImageDrawable(R.drawable.fallback_image);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                Sharp.loadInputStream(stream).into(target);
                stream.close();
            }
        });
    }

//shared prefs hold the username and score
    public static void editPrefs(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPrefs(String key, String defaultValue, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);;
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void addToScore(int questsRight, int questsCompleted, Context context) {
        String currectScore = getScore(context);
        String[] scores = currectScore.split("/");
        questsRight += Integer.parseInt(scores[0]);
        questsCompleted += Integer.parseInt(scores[1]);
        editPrefs("score", questsRight + "/" + questsCompleted, context);
    }
    public static String getScore(Context context) {
        return getPrefs(score, "0/0", context);
    }

    public static void setUsername (String username, Context context) {
        editPrefs("username", username,context);
    }
    public static String getUsername(Context context) {
        return getPrefs("username", "User", context);
    }






}