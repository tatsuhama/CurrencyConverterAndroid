package jp.tokushima.tatsuhama.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        Toast.makeText(this, "onClickCalc", Toast.LENGTH_SHORT).show();
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
