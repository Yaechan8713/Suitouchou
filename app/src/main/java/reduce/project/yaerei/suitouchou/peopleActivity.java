package reduce.project.yaerei.suitouchou;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by yaerei on 2018/02/24.
 */

public class peopleActivity extends AppCompatActivity {

    ListView peoplelistView,keishoulistView;

    Intent intent;

    ArrayAdapter<String> adapter,keishouadapter;

    EditText peopleeditText,keishouedittext;

    String keishoustr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_main);

        keishoustr = "";

        keishoulistView = (ListView)findViewById(R.id.keishoulistView);

        keishouedittext = (EditText)findViewById(R.id.keishouedittext);

        peopleeditText = (EditText)findViewById(R.id.peopleedittext);

        peoplelistView = (ListView)findViewById(R.id.peoplelistView);

        keishouadapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_2);

        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1);


        keishoulistView.setAdapter(keishouadapter);

        peoplelistView.setAdapter(adapter);

        keishoulistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayAdapter adapter2 = (ArrayAdapter)keishoulistView.getAdapter();

                final String keishouitem = (String)adapter2.getItem(i);

                new AlertDialog.Builder(peopleActivity.this)
                        .setTitle("次の敬称を設定しますか？")
                        .setMessage("敬称：" + keishouitem)
                        .setPositiveButton(
                                R.string.delete,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        keishoudeleteItem(keishouitem);

                                        adapter2.remove(keishouitem);
                                    }
                                }
                        )
                        .setNegativeButton(
                                R.string.settei,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        keishoustr = keishouitem;

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

                String str;

                if(keishoustr == ""){
                    str = "なし";
                }else{
                    str = keishoustr;
                }

                new AlertDialog.Builder(peopleActivity.this)
                        .setTitle("次の人を借金差出人に登録しますか？")
                        .setMessage(item + "\n" + str)
                        .setPositiveButton(
                                "登録",

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        intentmethod(item + keishoustr);
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
                        )
                        .setNeutralButton(
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
        intent = new Intent(this,shakkinnActivity.class);
        intent.putExtra("people",str);
        startActivity(intent);
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



    public void input(View v){
        String item = peopleeditText.getText().toString();
        adapter.add(item);
        insertItem(item);
    }
}
