package reduce.project.yaerei.suitouchou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yaerei on 2018/02/22.
 */

public class shakkinnActivity extends AppCompatActivity {

    String people;

    TextView sumtextView,peopletextView;

    SharedPreferences spr,peoplespr;

    String peoplekara;

    SharedPreferences.Editor editor;

    Intent intent;

    //        int edittextnum = 0の時はedittextに金額を表示。それ以外の時は金額を表示しない。
    int edittextnum;

    EditText edittext;

    ListView listview;

    int sum,input;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shakkinn_main);

        spr = getSharedPreferences("shakkinnsum", Context.MODE_PRIVATE);

        peopletextView = (TextView)findViewById(R.id.peopletextView);

        edittextnum = 0;

        peoplekara = "";

        peoplespr = getSharedPreferences("people",Context.MODE_PRIVATE);
        people = peoplespr.getString("people","");

        if(people == null || people == ""){
            people = "とある人";
        }

        peopletextView.setText(people + "から");

        sumtextView = (TextView)findViewById(R.id.sumtextView);
        edittext = (EditText)findViewById(R.id.edittext);
        listview = (ListView)findViewById(R.id.listview);
        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1);
        sum = input = 0;
        sum = spr.getInt("shakkinnsum",0);

        edittext.setText("");

        passwurdmethod();

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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

                                        Toast.makeText(shakkinnActivity.this,R.string.deleted, Toast.LENGTH_SHORT).show();
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


        List<shakkinnItem> items;
        items = new Select().from(shakkinnItem.class).execute();
        for(shakkinnItem item:items){
            adapter.insert(item.shakkinnname,0);
        }
    }

    public void peoplekaramethod(){
        peoplekara = people + "から";
    }

    public void paymoney(View v){

        SharedPreferences spf = getSharedPreferences("listword", Context.MODE_PRIVATE);
        editor = spf.edit();
        editor.putString("listword","");
        editor.commit();

        spr = getSharedPreferences("passwordlock",Context.MODE_PRIVATE);
        editor = spr.edit();
        editor.putInt("passwordlock",1);
        editor.commit();

        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }



    public void lendrun(final int t){
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
            if(edittext.getText().toString().equals("")){
                input = 0;
            }
            input = Integer.valueOf(edittext.getText().toString());
        }

        String str1 = "";

        Calendar calendar = Calendar.getInstance();

        int year,month,day,timehour,minits,s;

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        timehour = calendar.get(Calendar.HOUR_OF_DAY);
        minits = calendar.get(Calendar.MINUTE);
        s = calendar.get(Calendar.SECOND);

        String inputday = "\n(保存日：" + year + "年" + month + "月" + day + "日" + timehour + "時" + minits + "分" + s +"秒)";
        String str = "";

        if(t == 0){
            sum = sum - input;
            str1 = input + "円返した";

            str = input + "円の返金";
        }else{
            sum = sum + input;
            str1 = input + "円借りた";

            str = input + "円の借金";
        }

        str = str + "を入力";


        if(t == 0){
            peoplekara = people + "に";
        }else{
            peoplekaramethod();
        }

        String str2 = "入力内容：" + peoplekara +  str1 + inputday;

        String str3 = str;

        if(input == 0){
            str3 = "借金・返金の入力なし";
            str = "入力内容：" + str3;
            str2 = str + inputday;
        }

        sumtextView.setText(str3);

        String hyojistr1;
        hyojistr1 = "";

        int sumhyoji = 0;

        String sumstring = "の合計";

        if(sum >= 0){
            hyojistr1 = "借金" + sumstring + "：";
            sumhyoji = sum;
        }else{
            hyojistr1 = "返金" + sumstring +"：";
            sumhyoji = sum * -1;
        }

        String hyojistr3 = hyojistr1 + sumhyoji + "円";

        if(sum == 0){
            hyojistr3 = "合計：借金・返金なし";
        }

        final String hyojistr2 = hyojistr3 + "\n" + str2;

        edittextnum = 0;

        edittext.setText(edittextnum + "");


        if (t == 0) {
            sum = sum + input;
        } else {
            sum = sum - input;
        }

        new AlertDialog.Builder(shakkinnActivity.this)
                .setTitle(R.string.kakunin)
                .setMessage("以下の内容でリストを保存しますか？\n\n" + hyojistr2)
                .setPositiveButton(
                        R.string.input2,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                adapter.add(hyojistr2);
                                insertItem(hyojistr2);


                                if(t == 0){
                                    sum = sum - input;
                                }else{
                                    sum = sum + input;
                                }

                                edittextnum = 1;

                                editor = spr.edit();
                                editor.putInt("shakkinnsum",sum);
                                editor.commit();

                                edittext.setText("");

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

        editor = spr.edit();
        editor.putInt("shakkinnsum",sum);
        editor.commit();

//        input = 0;

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


    public void onResume(){
        super.onResume();

        passwurdmethod();
    }

    public void passwurdmethod(){

        int num = 0;

        spr = getSharedPreferences("passwordlock",Context.MODE_PRIVATE);
        num = spr.getInt("passwordlock",0);

        if(people == null || people == "" || num == 0){

            intent = getIntent();
            num = intent.getIntExtra("peoplenum",0);

            if(people == null) {

                int kaijo = 0;

                intent = getIntent();
                kaijo = intent.getIntExtra("kaijo", 0);


                if (kaijo == 0) {
                    intent = new Intent(this, passwordActivity.class);
                    intent.putExtra("backintent", 1);
                    startActivity(intent);
                    finish();
                }

            }

            spr = getSharedPreferences("passwordlock",Context.MODE_PRIVATE);
            editor = spr.edit();
            editor.putInt("passwordlock",0);
            editor.commit();

            people = "とある人";

            spr = getSharedPreferences("people",Context.MODE_PRIVATE);
            editor = spr.edit();
            editor.putString("people",null);
            editor.commit();
        }

        peoplekaramethod();
    }

    public void forpepole(View v){
        shakkinnpeopleintent();
    }

    public void peopletextView(View v){
        shakkinnpeopleintent();
    }

    public void shakkinnpeopleintent(){

        new AlertDialog.Builder(shakkinnActivity.this)
                .setTitle(R.string.kakunin)
                .setMessage("借金の差出人を設定しますか？")
                .setPositiveButton(
                        R.string.settei,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                peopleintent();
                            }
                        }
                )
                .setNegativeButton(
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


    @Override
    public boolean dispatchKeyEvent(KeyEvent e){
//        戻るボタンがクリックされた時の処理
        if(e.getKeyCode() == KeyEvent.KEYCODE_BACK){

            spr = getSharedPreferences("passwordlock",Context.MODE_PRIVATE);
            editor = spr.edit();
            editor.putInt("passwordlock",1);
            editor.commit();

//            ↓借金差出人のレイアウトから、MainActivityに勝手に飛んでしまう原因のコード。
            intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.dispatchKeyEvent(e);
    }


    public void peopleintent() {

        intent = new Intent(this, peopleActivity.class);
        startActivity(intent);
        finish();
    }
}
