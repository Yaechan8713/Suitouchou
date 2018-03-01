package reduce.project.yaerei.suitouchou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by yaerei on 2018/02/27.
 */

public class listtitleActivity extends AppCompatActivity {

    Intent intent;

    ListView listtitlelistView;

    EditText listtitleeditText;

    ArrayAdapter<String> adapter;

    int spinnernum;

    String spinnerstring,intentstr;

    Spinner spinner;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlisttitle_main);

        listtitleeditText = (EditText) findViewById(R.id.listtitleeditText);

        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);

        listtitlelistView = (ListView) findViewById(R.id.listtitlelistView);

        spinner = (Spinner) findViewById(R.id.spinner);

        intentstr = "";

        spinnernum = spinner.getSelectedItemPosition();
        spinnerstring = (String) spinner.getSelectedItem();

        spinnernum = 0;

        listtitlelistView.setAdapter(adapter);

        listtitlelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayAdapter listadapter = (ArrayAdapter) listtitlelistView.getAdapter();

                final String item = (String) listadapter.getItem(i);

                intentmethod(item);
            }
        });

        listtitlelistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                return false;
            }
        });

        List<listtitleItem> listtitleitems;
        listtitleitems = new Select().from(listtitleItem.class).execute();
        for (listtitleItem itemclass : listtitleitems) {
            adapter.insert(itemclass.listtitlename, 0);
        }

    }

    public void intentmethod(final String intentstring) {
        new AlertDialog.Builder(listtitleActivity.this)
                .setTitle("リストタイトル設定")
                .setMessage("「" + intentstring + "」をリストタイトルに設定しますか？")
                .setPositiveButton(
                        R.string.settei,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sharedPreferences = getSharedPreferences("listtitle", Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("listtitle", intentstring);
                                editor.commit();

                                Toast.makeText(listtitleActivity.this, "「" + intentstring + "」を保存しました。", Toast.LENGTH_SHORT).show();

                                mainActivityintent();
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

    public void mainActivityintent() {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void inputbutton(View v) {

        if (TextUtils.isEmpty(listtitleeditText.getText())) {
            intentstr = spinner.getSelectedItem().toString();
        } else {
            intentstr = listtitleeditText.getText().toString();
        }

        new AlertDialog.Builder(listtitleActivity.this)
                .setTitle(R.string.kakunin)
                .setMessage("「" + intentstr + "」を保存しますか？")
                .setPositiveButton(
                        R.string.input,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                intentmethod();
                                intentstr = "";
                                listtitleeditText.setText(intentstr);
                            }
                        }
                ).show();
    }



    public void intentmethod(){
        adapter.add(intentstr);
        insertItem(intentstr);

    }

    public void insertItem(String insert){
        listtitleItem item = new listtitleItem();
        item.listtitlename = insert;
        item.save();
    }

    public void deleteItem(String delete){
        listtitleItem item = new listtitleItem();
        item = new Select().from(listtitleItem.class).where("listtitlename =?",delete).executeSingle();
        item.delete();
    }

    public void newint(String newint){
        listtitleItem item = new listtitleItem();
        item = new Select().from(listtitleItem.class).where("listtitlename =?",newint).executeSingle();
    }
}