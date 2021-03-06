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

    ListView listview,tekiyoulistview;

    String listtitlestring,editstr2;

    Intent intent;

    int sum,inport,edittextnum;

    SharedPreferences spf;

    SharedPreferences.Editor editor;

    ArrayAdapter<String> adapter;

    ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listtitletextView = (TextView)findViewById(R.id.listtitletextView);

        tekiyoulistview = (ListView)findViewById(R.id.tekiyoulistview);

        outorin = (TextView)findViewById(R.id.outorin);

        moneyedit = (EditText)findViewById(R.id.money);

        listview = (ListView)findViewById(R.id.listview);

        shouhinnedittext = (EditText)findViewById(R.id.shouhinnedittext);

        inport = edittextnum = sum = 0;

        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1);

        adapter2 = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1);

        listtitlemethod();

        final String itemkakuninntitle = "メモした摘要内容　確認";
        final String itemkakuninnmessage = "メモした摘要の内容を表示しています。摘要の内容を以下のワードに設定するときは「設定」を押してください。\n\n";

        listview.setAdapter(adapter);
        tekiyoulistview.setAdapter(adapter2);

        tekiyoulistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final ArrayAdapter adapter = (ArrayAdapter)tekiyoulistview.getAdapter();

                final String item = (String)adapter.getItem(i);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(itemkakuninntitle)
                        .setMessage(itemkakuninnmessage + item)
                        .setPositiveButton(
                                R.string.kakunin,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int t = 0;
                                        t++;
                                    }
                                }
                        )
                        .setNeutralButton(
                                R.string.worddelete,

                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        listworddeleteItem(item);
                                    }
                                }
                        )
                        .setNegativeButton(
                                R.string.settei,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        shouhinnedittext.setText(item);
                                    }

                                }

                        ).show();

                return false;
            }
        });

        tekiyoulistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayAdapter adapter = (ArrayAdapter)tekiyoulistview.getAdapter();

                final String item = (String)adapter.getItem(i);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(itemkakuninntitle)
                        .setMessage(itemkakuninnmessage + item)
                        .setPositiveButton(
                                R.string.kakunin,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int t = 0;
                                        t++;
                                    }
                                }
                        )
                        .setNeutralButton(
                                R.string.worddelete,

                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        listworddeleteItem(item);
                                    }
                                }
                        )
                        .setNegativeButton(
                                R.string.settei,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        shouhinnedittext.setText(item);
                                    }

                                }

                        ).show();

            }
        });

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

                                        Toast.makeText(MainActivity.this,R.string.deleted, Toast.LENGTH_SHORT).show();

                                    }
                                }
                        ).show();
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
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

                                        Toast.makeText(MainActivity.this,R.string.deleted, Toast.LENGTH_SHORT).show();

                                    }
                                }
                        ).show();

                return false;
            }
        });


        List<Item> items;
        items = new Select().from(Item.class).execute();
        for(Item item:items){
            adapter.insert(item.name,0);
        }

        List<listwordItem> listworditems;
        listworditems = new Select().from(listwordItem.class).execute();
        for (listwordItem item:listworditems){
            adapter2.insert(item.listwordname,0);
        }
    }

    public void onResume(){
        super.onResume();

        listtitlemethod();
    }

    public void passwurdmethod() {

        spf = getSharedPreferences("listword", Context.MODE_PRIVATE);
        String editstr = spf.getString("listword", "");

        editstr2 = editstr;

        //int num = 0はパスワードを入力する。
        int num = 0;

        spf = getSharedPreferences("passwordlock",Context.MODE_PRIVATE);
        num = spf.getInt("passwordlock",0);

        if (editstr == null || editstr2 == null || num == 0) {

            int kaijo = 0;


            intent = getIntent();
            kaijo = intent.getIntExtra("kaijo", 0);


            if (kaijo == 0 && num == 0) {
                intent = new Intent(this, passwordActivity.class);
                intent.putExtra("backintent", 0);
                startActivity(intent);
                finish();
            }
        }

        spf = getSharedPreferences("passwordlock",Context.MODE_PRIVATE);
        editor = spf.edit();
        editor.putInt("passwordlock",0);
        editor.commit();
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
                                        if (moneyedit.getText().toString().equals("")) {
                                            inport = 0;
                                        }
                                        inport = Integer.valueOf(moneyedit.getText().toString());

                                    }
                                    String str, str2;
                                    str = str2 = "";

                                    String shuunyuustring = "";

                                    int sumstring = 0;

                                    if (inport == 0) {
                                        //EditText moneyeditで入力した値が0の場合
                                        str = "支出・収入なし";
                                        str2 = str;
                                    } else {

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
                                    }

                                    String sumstr = "";

                                    if (sum < 0) {
                                        sumstring = sum * -1;
                                        sumstr = "円収入";
                                    } else {
                                        sumstring = sum;
                                        sumstr = "円支出";
                                    }

                                    if (inport == 0) {
                                        str2 = str + "を入力";
                                    } else {
                                        str2 = inport + str + "を入力";
                                    }

                                    shuunyuustring = sumstring + sumstr;

                                    if (sum == 0) {
                                        shuunyuustring = "支出・収入なし";
                                    }

                                    if (inport == 0) {
                                        outorin.setText(str);
                                    } else {
                                        outorin.setText(str2);
                                    }

                                    Calendar calendar = Calendar.getInstance();

                                    int year, month, day, timehour, minits, s;

                                    year = calendar.get(Calendar.YEAR);
                                    month = calendar.get(Calendar.MONTH) + 1;
                                    day = calendar.get(Calendar.DATE);
                                    timehour = calendar.get(Calendar.HOUR_OF_DAY);
                                    minits = calendar.get(Calendar.MINUTE);
                                    s = calendar.get(Calendar.SECOND);

                                    String inputday = "\n(保存日：" + year + "年" + month + "月" + day + "日" + timehour + "時" + minits + "分" + s + "秒)";

                                    String resurch = shouhinnedittext.getText().toString();



                                    if (resurch == null || resurch == "") {
                                        resurch = "なし";
                                    } else {

                                        listwordItem item = new Select().from(listwordItem.class).where("listwordname =?", resurch).executeSingle();

                                        if (item == null) {
                                            adapter2.add(resurch);
                                            listwordinsertItem(resurch);
                                        }

                                    }



                                    listtitlestring = "摘要";

                                    String str3 = listtitlestring + "：" +  resurch + "\n" + "合計：" + shuunyuustring +"(" + str2 + ")" + inputday;

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

                                                            spf = getSharedPreferences("mainsum",Context.MODE_PRIVATE);
                                                            editor = spf.edit();
                                                            editor.putInt("mainsum",sum);
                                                            editor.commit();
                                                            inport = 0;

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



    public void listwordinsertItem(String listwordinsert){
        listwordItem item = new listwordItem();
        item.listwordname = listwordinsert;
        item.save();
    }

    public void listworddeleteItem(String listworddelete){
        listwordItem item = new listwordItem();
        item = new Select().from(listwordItem.class).where("listwordname =?",listworddelete).executeSingle();
        item.delete();
    }

    public void newintlistword(String newint){
        listwordItem item = new listwordItem();
        item = new Select().from(listwordItem.class).where("listwordname =?",newint).executeSingle();
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

        spf = getSharedPreferences("passwordlock",Context.MODE_PRIVATE);
        editor = spf.edit();
        editor.putInt("passwordlock",1);
        editor.commit();

        intent = new Intent(this,shakkinnActivity.class);
        startActivity(intent);
        finish();
    }


    public void listtitlemethod(){

        passwurdmethod();


        spf = getSharedPreferences("mainsum", Context.MODE_PRIVATE);
        sum = spf.getInt("mainsum",0);

        outorin.setText("入力データなし");

        if(editstr2 == null){
            editstr2 = "";
        }

        shouhinnedittext.setText(editstr2);
        moneyedit.setText("");

        spf = getSharedPreferences("listword", Context.MODE_PRIVATE);
        editor = spf.edit();
        editor.putString("listword",null);
        editor.commit();


    }

//    public void listtitletextView(View v){
//        listtitlesetteimethod();
//    }

}