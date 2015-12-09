package jp.tokushima.tatsuhama.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通貨単位を選択する画面
 * Created by tatsuhama on 15/12/09.
 */
public class CodeChooserActivity extends AppCompatActivity {

    @Bind(R.id.code_chooser_list)
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_chooser);
        ButterKnife.bind(this);

        final Code[] codes = Code.values();
        ArrayAdapter<Code> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, codes);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("value", codes[position].name());
                data.putExtras(bundle);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
