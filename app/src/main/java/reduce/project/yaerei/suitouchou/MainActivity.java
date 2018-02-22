package reduce.project.yaerei.suitouchou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView outorin;

    EditText moneyedit,shouhinnedittext;

    ListView listview;

    Intent intent;

    int sum,inport;

    SharedPreferences spf;

    SharedPreferences.Editor editor;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outorin = (TextView)findViewById(R.id.outorin);

        moneyedit = (EditText)findViewById(R.id.money);

        listview = (ListView)findViewById(R.id.listview);

        shouhinnedittext = (EditText)findViewById(R.id.shouhinnedittext);

        inport = sum = 0;

        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1);

        spf = getSharedPreferences("sum", Context.MODE_PRIVATE);
        sum = spf.getInt("sum",0);

        shouhinnedittext.setText("");
        moneyedit.setText("0");

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayAdapter adapter = (ArrayAdapter)listview.getAdapter();

                final String item = (String)adapter.getItem(i);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.list)
                        .setMessage(R.string.input + item)
                        .setNegativeButton(
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

                                        deleteItem(item);

                                        adapter.remove(item);

                                        Toast.makeText(MainActivity.this, "項目を削除しました。", Toast.LENGTH_SHORT).show();

                                    }
                                }
                        ).show();
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


        List<Item> items;
        items = new Select().from(Item.class).execute();
        for(Item item:items){
            adapter.insert(item.name,0);
        }
    }


    public void shishutu(View v){
        run(1);
    }

    public void shuunyuu(View v){
        run(0);
    }

//    int i = 1の時は支出
//    int i = 0は収入
    public void run(int i) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.kakunin)
                .setMessage("リストデータを保存します。")
                .setNegativeButton(
                        R.string.ryoukai,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (TextUtils.isEmpty(shouhinnedittext.getText())) {
                                    //商品名が入力されていない場合

                                    new AlertDialog.Builder(MainActivity.this)
                                            .setTitle(R.string.error/*"@string/error"*/)
                                            .setMessage("商品名(リストタイトル)が入力されていません。\n商品名(リストタイトル)を入力してください。")
                                            .setPositiveButton(
                                                    R.string.ryoukai,

                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            //「了解」ボタンを押したときの処理
//                                                               EditTextの初期化
                                                            shouhinnedittext.setText("");
                                                            moneyedit.setText("0");
                                                        }
                                                    }
                                            ).show();
                                } else {
                                    //商品名が入力されている場合

                                    if (TextUtils.isEmpty(moneyedit.getText())) {
                                        //支出・収入の金額が入力されてない場合

                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle(R.string.error)
                                                .setMessage("支出・収入の金額が入力されておりません。\n支出・収入の金額は0円として計算します。")
                                                .setPositiveButton(
                                                        R.string.ryoukai,

                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                //「了解」ボタンを押したときの処理
                                                                //自動的に支出・収入の金額を0円にする
                                                                inport = 0;
                                                            }
                                                        }
                                                ).show();
                                    } else {
                                        //支出・収入の金額が入力されている場合
                                        inport = Integer.valueOf(moneyedit.getText().toString());
                                    }
                                    String str, str2;
                                    str = str2 = "";

                                    if (inport == 0) {
                                        //EditText moneyeditで入力した値が0の場合
                                        str = "支出なし。収入なし。";
                                    } else {
                                        //EditText moneyeditで入力した値が0ではない場合
                                        if (i == 1) {
                                            //支出計算
                                            sum = sum + inport;
                                            str = "円支出";
                                        } else {
                                            //収入計算
                                            sum = sum - inport;
                                            str = "円収入";
                                        }
                                    }
                                    str2 = inport + str;
                                    outorin.setText(str2);

                                    String str3 = (shouhinnedittext.getText().toString()) + "\n" + "合計：" + sum + "円\n" + str2;

                                    adapter.add(str3);
                                    insertItem(str3);

                                    spf = getSharedPreferences("sum",Context.MODE_PRIVATE);
                                    editor = spf.edit();
                                    editor.putInt("sum",sum);
                                    editor.commit();
                                }
                            }
                        })
                .setNeutralButton(
                        R.string.cancel,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int t = 0;
                                t++;
                            }
                        }
                ).show();
    }




    public void insertItem(String insert){
//        Itemクラスにデータ保存
        Item item = new Item();
        item.name = insert;
        item.save();
    }

    public void deleteItem(String delete){
//        Itemクラスに保存したデータを削除
        Item item = new Item();
        item = new Select().from(Item.class).where("name =?",delete).executeSingle();
        item.delete();
    }

    public void newint(String newstr){
        Item item = new Item();
        item = new Select().from(Item.class).where("name =?",newstr).executeSingle();
    }

    public void lendmoney(View v){
        intent = new Intent(this,shakkinnActivity.class);
        startActivity(intent);
        finish();
    }
}