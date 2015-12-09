package jp.tokushima.tatsuhama.currencyconverter;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by tatsuhama on 15/12/09.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
