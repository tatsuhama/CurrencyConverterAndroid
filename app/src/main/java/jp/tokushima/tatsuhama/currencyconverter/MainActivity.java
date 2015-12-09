package jp.tokushima.tatsuhama.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

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
        kawaseApi.getCurrency("json", mFromCode, mToCode, new Callback<HashMap<String, String>>() {
            @Override
            public void success(HashMap<String, String> map, Response response) {
                String value = map.get(mToCode.name());
                if (value != null) {
                    double factor = Double.parseDouble(value);
                    storeRealm(factor);
                    calc(factor);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("", "");
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
