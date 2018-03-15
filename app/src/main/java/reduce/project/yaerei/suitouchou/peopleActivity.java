package reduce.project.yaerei.suitouchou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by yaerei on 2018/02/24.
 */

public class peopleActivity extends AppCompatActivity {

    ListView peoplelistView,keishoulistView;

    Intent intent;

    SharedPreferences spr;

    SharedPreferences.Editor editor;

    ArrayAdapter<String> adapter,keishouadapter;

    EditText peopleeditText,keishouedittext;

    String keishoustr,peoplestr;

    TextView peopletextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_main);

        keishoustr = "";

        spr = getSharedPreferences("peoplename",Context.MODE_PRIVATE);
        peoplestr = spr.getString("peoplename","");

        spr = getSharedPreferences("keishouname",Context.MODE_PRIVATE);
        keishoustr = spr.getString("keishouname","");

        if(peoplestr == "" || peoplestr == null) {

            peoplestr = "とある人";
        }

        keishoulistView = (ListView)findViewById(R.id.keishoulistView);

        peopletextView = (TextView)findViewById(R.id.peopletextView);

        keishouedittext = (EditText)findViewById(R.id.keishouedittext);

        peopleeditText = (EditText)findViewById(R.id.peopleedittext);

        peoplelistView = (ListView)findViewById(R.id.peoplelistView);

        Context ct = this;

        int rsc = android.R.layout.simple_expandable_list_item_1;

        keishouadapter = new ArrayAdapter(ct,rsc);

        adapter = new ArrayAdapter(ct,rsc);

        peopletextViewmethod(peoplestr,keishoustr);


        keishoulistView.setAdapter(keishouadapter);

        peoplelistView.setAdapter(adapter);

        keishoulistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayAdapter adapter2 = (ArrayAdapter)keishoulistView.getAdapter();

                final String keishouitem = (String)adapter2.getItem(i);

                keishoustr = keishouitem;

                new AlertDialog.Builder(peopleActivity.this)
                        .setTitle("次の敬称を設定しますか？")
                        .setMessage("敬称：" + keishouitem)
                        .setNegativeButton(
                                R.string.delete,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        keishoudeleteItem(keishouitem);

                                        adapter2.remove(keishouitem);


                                    }
                                }
                        )
                        .setPositiveButton(
                                R.string.settei,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        keishoustr = keishouitem;
                                        peopletextViewmethod(peoplestr,keishouitem);
                                        Toast.makeText(peopleActivity.this, "敬称を「" + keishoustr + "」に設定しました。", Toast.LENGTH_SHORT).show();
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
        });

        peoplelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayAdapter adapter1 = (ArrayAdapter)peoplelistView.getAdapter();

                final String item = (String)adapter1.getItem(i);

                peoplestr = item;

                new AlertDialog.Builder(peopleActivity.this)
                        .setTitle("次の人を借金差出人に登録しますか？")
                        .setMessage(item)
                        .setPositiveButton(
                                "登録",

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(keishoustr == null || keishoustr == ""){
                                            keishoustr = "さん";
                                        }

                                        peopletextViewmethod(item,keishoustr);

                                        intentmethod(item + keishoustr);
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
                        )
                        .setNegativeButton(
                                R.string.delete,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        deleteItem(item);

                                        adapter1.remove(item);

                                        Toast.makeText(peopleActivity.this, "項目を削除しました。", Toast.LENGTH_SHORT).show();

                                    }
                                }
                        ).show();
            }
        });

        peoplelistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter adapter = (ArrayAdapter) peoplelistView.getAdapter();

                final String item = (String) adapter.getItem(i);
                adapter.insert(item, i);
//                adapter.remove(item);


                return false;
            }
        });


        List<keishouItem> keishouitems;
        keishouitems = new Select().from(keishouItem.class).execute();
        for(keishouItem item2 : keishouitems){
            keishouadapter.insert(item2.keishouname,0);
        }

        List<peopleItem> items;
        items = new Select().from(peopleItem.class).execute();
        for(peopleItem item : items){
            adapter.insert(item.peoplename,0);
        }
    }

    public void intentmethod(String str){

        spr = getSharedPreferences("people",Context.MODE_PRIVATE);
        editor = spr.edit();
        editor.putString("people",str);
        editor.commit();


        intent = new Intent(this,shakkinnActivity.class);
        startActivity(intent);
        finish();
    }

    public void peopletextViewmethod(String peoplename,String keishouname){
        String sprstr1 = "";
        String sprstr2 = "";
        int i = 0;
        for(i = 0;i < 2;i++){
            if(i == 0){
                sprstr1 = "peoplename";
                sprstr2 = peoplename;
            }else{
                sprstr1 = "keishouname";
                sprstr2 = keishouname;
            }

            spr = getSharedPreferences(sprstr1,Context.MODE_PRIVATE);
            editor = spr.edit();
            editor.putString(sprstr1,sprstr2);
            editor.commit();
        }

        peopletextView.setText("差出人：" + peoplename + keishouname);

    }

    public void keishouinput(View v){
        keishoustr = keishouedittext.getText().toString();

        new AlertDialog.Builder(peopleActivity.this)
                .setTitle(R.string.kakunin)
                .setMessage("次の敬称を登録しますか？\n敬称：" + keishoustr)
                .setPositiveButton(
                        R.string.touroku,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                keishouadapter.add(keishoustr);
                                keishouinsertItem(keishoustr);
                                keishouedittext.setText("");
                            }
                        }
                )
                .setNeutralButton(
                        R.string.cancel,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                keishoustr = "";
                            }
                        }
                ).show();
    }






    public void keishouinsertItem(String keishouinsert){
        keishouItem item = new keishouItem();
        item.keishouname = keishouinsert;
        item.save();
    }

    public void keishoudeleteItem(String keishoudelete){
        keishouItem item = new keishouItem();
        item = new Select().from(keishouItem.class).where("keishouname =?",keishoudelete).executeSingle();
        item.delete();
    }

    public void keishounewint(String keishounewint){
        keishouItem item = new keishouItem();
        item = new Select().from(keishouItem.class).where("keishouname =?",keishounewint).executeSingle();
    }

    public void insertItem(String insert){
//        Itemクラスにデータ保存
        peopleItem item = new peopleItem();
        item.peoplename = insert;
        item.save();
    }

    public void deleteItem(String delete){
//        Itemクラスに保存したデータを削除
        peopleItem item = new peopleItem();
        item = new Select().from(peopleItem.class).where("peoplename =?",delete).executeSingle();
        item.delete();
    }

    public void newint(String newstr){
        peopleItem item = new peopleItem();
        item = new Select().from(peopleItem.class).where("peoplename =?",newstr).executeSingle();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent e){
//        戻るボタンがクリックされた時の処理
        if(e.getKeyCode() == KeyEvent.KEYCODE_BACK){
            intent = new Intent(this,shakkinnActivity.class);
            startActivity(intent);
            finish();
        }
        return super.dispatchKeyEvent(e);
    }


    public void input(View v) {
        final String item = peopleeditText.getText().toString();

        if (item == null || item == "") {
            new AlertDialog.Builder(peopleActivity.this)
                    .setTitle("エラー")
                    .setMessage("差出人が入力されていません。")
                    .setPositiveButton(
                            R.string.ryoukai,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int t = 0;
                                    t++;
                                }
                            }
                    ).show();
        } else {

            if (keishoustr == "" || keishoustr == null) {
                keishoustr = "さん";
            }

            new AlertDialog.Builder(peopleActivity.this)
                    .setTitle(R.string.kakunin)
                    .setMessage("次の人を差出人リスト登録しますか？\n" + item + keishoustr)
                    .setPositiveButton(
                            R.string.touroku,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    adapter.add(item);
                                    insertItem(item);

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

            peopleeditText.setText("");
        }
    }
}
