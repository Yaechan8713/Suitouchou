package reduce.project.yaerei.suitouchou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yaerei on 2018/02/24.
 */

public class passwordActivity extends AppCompatActivity {
    String maru;

    int password,nyuuryoku;

    TextView passwordtextView;

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_main);

        int passwordfirst = 0;

        sharedPreferences = getSharedPreferences("passwordfirst",Context.MODE_PRIVATE);
        passwordfirst = sharedPreferences.getInt("passwordfirst",0);

        if(passwordfirst == 0){
            intent = new Intent(this,passwordinputActivity.class);
            startActivity(intent);
            finish();
         }

        password = 0;

        sharedPreferences = getSharedPreferences("password", Context.MODE_PRIVATE);
        password = sharedPreferences.getInt("password",0);

        passwordtextView = (TextView)findViewById(R.id.passwordtextView);
        maru = "";

    }

    public void firstpassword(){
        maru = "";
        passwordtextView.setText("パスワードを入力してください。");
        nyuuryoku = 0;

    }

    public void onResume(){
        super.onResume();

        //Activityclassが再び開かれた時
        new AlertDialog.Builder(passwordActivity.this)
                .setTitle("パスワード")
                .setMessage("再度、パスワードを入力してください。")
                .setPositiveButton(
                        R.string.ryoukai,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                firstpassword();
                            }
                        }
                ).show();

    }

    public void passwordrun(int passwordnyuuryoku){
        maru = maru + "●";

        passwordtextView.setText(maru + "\n");

        nyuuryoku = nyuuryoku * 10 + passwordnyuuryoku;
    }

    public void ok(View v){
        if(password == nyuuryoku){
            new AlertDialog.Builder(passwordActivity.this)
                    .setTitle("ロック解除")
                    .setMessage("ロックが解除されました！")
                    .setPositiveButton(
                            R.string.ok,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    intent = getIntent();
                                    int t = intent.getIntExtra("backintent",0);
                                    shakkinnActivityintent(t);
                                }
                            }
                    ).show();
        }else{
            new AlertDialog.Builder(passwordActivity.this)
                    .setTitle(R.string.error)
                    .setMessage("パスワードが違います。")
                    .setPositiveButton(
                            R.string.ryoukai,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    firstpassword();
                                }
                            }
                    ).show();
        }
    }

    public void shakkinnActivityintent(int getintent){

        Class<?> cls;

        if(getintent == 0){
            //MainActivityに戻す
            cls = MainActivity.class;
        }else {
            cls = shakkinnActivity.class;
        }

        intent = new Intent(this,cls);
        intent.putExtra("kaijo",1);
        startActivity(intent);
        finish();
    }

    public void num1(View v){
        passwordrun(1);
    }

    public void num2(View v){
        passwordrun(2);
    }

    public void num3(View v){
        passwordrun(3);
    }

    public void num4(View v){
        passwordrun(4);
    }

    public void num5(View v){
        passwordrun(5);
    }

    public void num6(View v){
        passwordrun(6);
    }

    public void num7(View v){
        passwordrun(7);
    }

    public void num8(View v){
        passwordrun(8);
    }

    public void num9(View v){
        passwordrun(9);
    }

    public void num0(View v){
        passwordrun(0);
    }


}
