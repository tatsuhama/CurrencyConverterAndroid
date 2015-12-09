package jp.tokushima.tatsuhama.currencyconverter;

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
        Toast.makeText(this, "onClickFromUnitButton", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.to_unit)
    void onClickToUnitButton() {
        Toast.makeText(this, "onClickToUnitButton", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_calc)
    void onClickCalc() {
        Toast.makeText(this, "onClickCalc", Toast.LENGTH_SHORT).show();
    }
}
