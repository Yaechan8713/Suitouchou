package reduce.project.yaerei.suitouchou;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by yaerei on 2018/02/22.
 */

public class shakkinnActivity extends AppCompatActivity {

    Intent intent;

    EditText edittext;

    ListView listview;

    int sum,input;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shakkinn_main);

        edittext = (EditText)findViewById(R.id.edittext);
        listview = (ListView)findViewById(R.id.listview);
        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1);
        sum = input = 0;

        edittext.setText("0");

        listview.setAdapter(adapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayAdapter adapter = (ArrayAdapter)listview.getAdapter();

                final String item = (String)adapter.getItem(i);


                new AlertDialog.Builder(shakkinnActivity.this)
                        .setTitle(R.string.list)
                        .setMessage(R.string.input + item)
                        .setPositiveButton(
                                R.string.kakunin,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int t = 0;
                                        t++;
                                        t = 0;
                                    }
                                }
                        )
                        .setNeutralButton(
                                R.string.delete,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        delete(item);

                                        adapter.remove(item);

                                        Toast.makeText(shakkinnActivity.this, "項目を削除しました。", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ).show();
                return false;
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter adapter = (ArrayAdapter)listview.getAdapter();

                final String item = (String)adapter.getItem(i);
                adapter.insert(item,i);

                return false;
            }
        });


        List<shakkinnItem> items;
        items = new Select().from(shakkinnItem.class).execute();
        for(shakkinnItem item:items){
            adapter.insert(item.shakkinnname,0);
        }
    }

    public void paymoney(View v){
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }



    public void lendrun(int t){
        if(TextUtils.isEmpty(edittext.getText())){
            new AlertDialog.Builder(shakkinnActivity.this)
                    .setTitle(R.string.error)
                    .setMessage("支出・収入の金額が入力されておりません。\n支出・収入の金額は0円として計算します。")
                    .setNegativeButton(
                            R.string.ryoukai,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    input = 0;
                                }
                            }

                    ).show();
        }else{
            input = Integer.valueOf(edittext.getText().toString());
        }

        if(t == 0){
            sum = sum - input;
        }else{
            sum = sum + input;
        }

    }

    public void lend(View v){
        lendrun(1);
    }

    public void hennkinn(View v){
        lendrun(0);
    }



    public void insertItem(String insert){
        shakkinnItem item = new shakkinnItem();
        item.shakkinnname = insert;
        item.save();
    }

    public void delete(String delete){
        shakkinnItem item = new shakkinnItem();
        item = new Select().from(shakkinnItem.class).where("shakkinnname =?",delete).executeSingle();
        item.delete();
    }

    public void newint(String shakkinnnew){
        shakkinnItem item = new shakkinnItem();
        item = new Select().from(shakkinnItem.class).where("shakkinnname =?",shakkinnnew).executeSingle();
    }

}
