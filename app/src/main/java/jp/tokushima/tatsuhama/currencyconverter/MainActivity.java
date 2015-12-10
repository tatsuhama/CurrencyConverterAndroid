package jp.tokushima.tatsuhama.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FROM = 123;
    private static final int REQUEST_CODE_TO = 1234;
    private Code mFromCode = Code.USD;
    private Code mToCode = Code.JPY;
    @Bind(R.id.from_edit)
    EditText mFromEdit;
    @Bind(R.id.from_unit)
    Button mFromButton;
    @Bind(R.id.to_text)
    TextView mToText;
    @Bind(R.id.to_unit)
    Button mToButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void getCurrency() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        final KawaseApi kawaseApi = new RestAdapter.Builder()
                .setEndpoint("http://api.aoikujira.com/kawase")
                .setClient(new OkClient(client))
                .build()
                .create(KawaseApi.class);
        Observable<HashMap<String, String>> observable = kawaseApi.getCurrency("json", mFromCode, mToCode);
        observable
                .subscribeOn(Schedulers.newThread())
                // 以下、バックグラウンドスレッドで実行
                .map(new Func1<HashMap<String, String>, Double>() {
                    // HashMap<String, String> → Double の変換
                    @Override
                    public Double call(HashMap<String, String> map) {
                        String value = map.get(mToCode.name());
                        if (value != null) {
                            double dValue = Double.parseDouble(value);
                            storeRealm(dValue);
                            return dValue;
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                // 以下、メインスレッドで実行
                .subscribe(new Observer<Double>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Double aDouble) {
                        calc(aDouble);
                    }
                });
    }

    private void storeRealm(double factor) {
        Realm realm = Realm.getInstance(MainActivity.this);
        realm.beginTransaction();
        ApiResult result = realm.createObject(ApiResult.class);
        result.setRequestTime(System.currentTimeMillis());
        result.setFromCode(mFromCode.name());
        result.setToCode(mToCode.name());
        result.setFactor(factor);
        realm.commitTransaction();
    }

    /**
     * @param factor 係数
     */
    private void calc(double factor) {
        String fromValue = mFromEdit.getText().toString();
        if (!fromValue.isEmpty()) {
            double fromDValue = Double.parseDouble(fromValue);
            mToText.setText(Double.toString(fromDValue * factor));
        }
    }

    @OnClick(R.id.from_unit)
    void onClickFromUnitButton() {
        Intent intent = new Intent(this, CodeChooserActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FROM);
    }

    @OnClick(R.id.to_unit)
    void onClickToUnitButton() {
        Intent intent = new Intent(this, CodeChooserActivity.class);
        startActivityForResult(intent, REQUEST_CODE_TO);
    }

    @OnClick(R.id.button_calc)
    void onClickCalc() {
        Realm realm = Realm.getInstance(this);
        RealmQuery<ApiResult> query = realm.where(ApiResult.class);
        long now = System.currentTimeMillis();
        long tenMinutesBefore = now - 10 * 1000 * 60; // 10分前
        RealmResults<ApiResult> results = query
                .equalTo("fromCode", mFromCode.name())
                .equalTo("toCode", mToCode.name())
                .greaterThan("requestTime", tenMinutesBefore)
                .findAll();
        if (results.isEmpty()) {
            getCurrency();
        } else {
            double factor = results.first().getFactor();
            calc(factor);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            Code selectedCode = Code.valueOf(data.getStringExtra("value"));
            if (requestCode == REQUEST_CODE_FROM) {
                setFromCode(selectedCode);
            } else if (requestCode == REQUEST_CODE_TO) {
                setToCode(selectedCode);
            }
        }
    }

    private void setFromCode(Code code) {
        mFromCode = code;
        mFromButton.setText(code.getUnit());
    }

    private void setToCode(Code code) {
        mToCode = code;
        mToButton.setText(code.getUnit());
    }
}
