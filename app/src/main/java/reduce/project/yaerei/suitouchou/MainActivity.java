package reduce.project.yaerei.suitouchou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView outorin,listtitletextView;

    EditText moneyedit,shouhinnedittext;

    ListView listview;

    String listtitlestring;

    Intent intent;

    int sum,inport,edittextnum;

    SharedPreferences spf;

    SharedPreferences.Editor editor;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listtitletextView = (TextView)findViewById(R.id.listtitletextView);

        outorin = (TextView)findViewById(R.id.outorin);

        moneyedit = (EditText)findViewById(R.id.money);

        listview = (ListView)findViewById(R.id.listview);

        shouhinnedittext = (EditText)findViewById(R.id.shouhinnedittext);

        inport = edittextnum = sum = 0;

        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1);

        listtitlemethod();

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayAdapter adapter = (ArrayAdapter)listview.getAdapter();

                final String item = (String)adapter.getItem(i);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.list)
                        .setMessage("保存したリストを表示しております。\n\n" + item)
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

                                        Toast.makeText(MainActivity.this, "項目を削除しました。(合計金額は変更していません) ", Toast.LENGTH_SHORT).show();

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

    public void onResume(){
        super.onResume();

        listtitlemethod();
    }

    public void passwurdmethod(){

        int kaijo = 0;

        intent = getIntent();
        kaijo = intent.getIntExtra("kaijo",0);


        if(kaijo == 0) {
            intent = new Intent(this, passwordActivity.class);
            intent.putExtra("backintent", 0);
            startActivity(intent);
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
    public void run(final int ii) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.kakunin)
                .setMessage("リストデータを保存しますか？")
                .setNegativeButton(
                        R.string.input2,

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
                                        if(moneyedit.getText().toString().equals("")){
                                            inport = 0;
                                        }
                                        inport = Integer.valueOf(moneyedit.getText().toString());

                                    }
                                    String str, str2;
                                    str = str2 = "";

                                    String shuunyuustring = "";

                                    if (inport == 0) {
                                        //EditText moneyeditで入力した値が0の場合
                                        str = "支出・収入なし";
                                        str2 = shuunyuustring = str;
                                    } else {

                                        int sumstring = 0;

                                        //EditText moneyeditで入力した値が0ではない場合
                                        if (ii == 1) {
                                            //支出計算
                                            sumstring = sum = sum + inport;
                                            str = "円支出";
                                        } else {
                                            //収入計算
                                            sum = sum - inport;
                                            str = "円収入";
                                        }

                                        String sumstr = "";

                                        if(sum < 0) {
                                            sumstring = sum * -1;
                                            sumstr = "円収入";
                                        }else{
                                            sumstring = sum;
                                            sumstr = "円支出";
                                        }

                                        str2 = inport + str + "を入力";

                                        shuunyuustring =  sumstring + sumstr;

                                        if(sum == 0){
                                            shuunyuustring = "支出・収入なし";
                                        }
                                    }

                                    if(inport == 0){
                                        outorin.setText(str);
                                    }else {
                                        outorin.setText(str2);
                                    }

                                    Calendar calendar = Calendar.getInstance();

                                    int year,month,day,timehour,minits,s;

                                    year = calendar.get(Calendar.YEAR);
                                    month = calendar.get(Calendar.MONTH) + 1;
                                    day = calendar.get(Calendar.DATE);
                                    timehour = calendar.get(Calendar.HOUR_OF_DAY);
                                    minits = calendar.get(Calendar.MINUTE);
                                    s = calendar.get(Calendar.SECOND);

                                    String inputday = "\n(保存日：" + year + "年" + month + "月" + day + "日" + timehour + "時" + minits + "分" + s +"秒)";

                                    String str3 = (listtitlestring + "：" + shouhinnedittext.getText().toString()) + "\n" + "合計：" + shuunyuustring +"(" + str2 + ")" + inputday;

                                    final String finalstr = str3;

                                    edittextnum = 0;

                                    shouhinnedittext.setText("");
                                    moneyedit.setText(edittextnum + "");

                                    if (ii == 1) {
                                        sum = sum - inport;
                                    }else{
                                        sum = sum + inport;
                                    }


                                    new AlertDialog.Builder(MainActivity.this)
                                            .setTitle(R.string.kakunin)
                                            .setMessage("保存するリストの内容は以下の通りでよろしいですか？\n\n" + finalstr)
                                            .setPositiveButton(
                                                    R.string.ok,

                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {

                                                            adapter.add(finalstr);
                                                            insertItem(finalstr);

                                                            if(ii == 1){
                                                                sum = sum + inport;
                                                            }else{
                                                                sum = sum - inport;
                                                            }

                                                            edittextnum = 1;

                                                        }
                                                    }
                                            )
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
        spf = getSharedPreferences("mainsum",Context.MODE_PRIVATE);
        editor = spf.edit();
        editor.putInt("mainsum",sum);
        editor.commit();
        inport = 0;
    }



    public void sumtoast(View v){
        int toastnum = 0;
        String toaststring = "";
        if(sum > 0){
            toastnum = sum;
            toaststring = "支出";
        }else if(sum == 0){
            toastnum = sum;
            toaststring = "(支出・収入なし)";
        }else{
            toaststring = "収入";
            toastnum = sum * -1;
        }


        Toast.makeText(MainActivity.this, "合計金額：" + toastnum + "円" + toaststring, Toast.LENGTH_SHORT).show();
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

    public void listtitlesettei(View v){
        listtitlesetteimethod();
    }

    public void listtitlemethod(){

        passwurdmethod();

        spf = getSharedPreferences("listtitle",Context.MODE_PRIVATE);
        listtitlestring = spf.getString("listtitle","");

        if(listtitlestring == "" || listtitlestring == null){

            listtitlestring = "その他";

        }

        listtitletextView.setText(listtitlestring + "：");


        spf = getSharedPreferences("mainsum", Context.MODE_PRIVATE);
        sum = spf.getInt("mainsum",0);

        outorin.setText("入力データなし");

        shouhinnedittext.setText("");
        moneyedit.setText("0");


    }

    public void listtitletextView(View v){
        listtitlesetteimethod();
    }

    public void listtitlesetteimethod(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.kakunin)
                .setMessage("リストタイトルを設定しますか？")
                .setPositiveButton(
                        R.string.ok,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listtitleActivitymethodintent();
                            }
                        }
                )
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

    public void listtitleActivitymethodintent(){
        intent = new Intent(this,listtitleActivity.class);
        startActivity(intent);
    }
}