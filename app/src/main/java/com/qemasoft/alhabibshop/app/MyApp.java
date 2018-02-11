package com.qemasoft.alhabibshop.app;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.qemasoft.alhabibshop.app.AppConstants.LANGUAGE_KEY;

/**
 * Created by Inzimam Tariq on 1/17/2018.
 */

public class MyApp extends Application {
    private Locale locale;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        Configuration config = getBaseContext().getResources().getConfiguration();
        String lang = Preferences
                .getSharedPreferenceString(this, LANGUAGE_KEY, "ar");
        Log.e("MyApp", "language in MyApp = " + lang);
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        
        if (lang.contains("ar")) {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/DroidKufi-Regular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        }
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }
}
