package jp.tokushima.tatsuhama.currencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mFromEdit;
    private Button mFromButton;
    private TextView mToText;
    private Button mToButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFromEdit = (EditText) findViewById(R.id.from_edit);
        mFromButton = (Button) findViewById(R.id.from_unit);
        mFromButton.setOnClickListener(this);
        mToText = (TextView) findViewById(R.id.to_text);
        mToButton = (Button) findViewById(R.id.to_unit);
        mToButton.setOnClickListener(this);
        findViewById(R.id.button_calc).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.from_unit:
                Toast.makeText(this, "onClickFromUnitButton", Toast.LENGTH_SHORT).show();
                break;
            case R.id.to_unit:
                Toast.makeText(this, "onClickToUnitButton", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_calc:
                Toast.makeText(this, "onClickCalc", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
