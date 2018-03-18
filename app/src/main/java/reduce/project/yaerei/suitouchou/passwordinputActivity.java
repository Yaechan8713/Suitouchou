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
import android.widget.Toast;

/**
 * Created by yaerei on 2018/02/24.
 */

public class passwordinputActivity extends AppCompatActivity {

    TextView passwordinputtextView;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int password;

    String strpassword;

    String maru;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordinput_main);

        strpassword = "パスワードは8桁以下にしてください。";

        passwordinputtextView = (TextView)findViewById(R.id.passwordinputtextView);

        maru = "";

        password = 0;
    }

    public void passwordinput(int passwordinput) {

        maru = maru + "●";

        if (maru == "") {
            passwordinputtextView.setText(R.string.pleasesettingspassword);
        } else {
            passwordinputtextView.setText(maru + "\n");
        }

        password = password * 10 + passwordinput;

        if (password > 99999999) {

            new AlertDialog.Builder(passwordinputActivity.this)
                    .setTitle(R.string.error)
                    .setMessage(strpassword)
                    .setPositiveButton(
                            R.string.ryoukai,

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    reset();
                                }
                            }
                    ).show();
        } else {
            inputpasswordmethod(2);
        }
    }

    public void inputpassword(View v){
        inputpasswordmethod(1);
    }

    public void onResume(){
        super.onResume();

        //Activityclassが再び開かれた時
        new AlertDialog.Builder(passwordinputActivity.this)
                .setTitle("パスワード設定")
                .setMessage("8桁以下のパスワードを設定してください。")
                .setPositiveButton(
                        R.string.ryoukai,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                reset();

                            }
                        }
                ).show();

    }

    public void resetonclick(View v){
        new AlertDialog.Builder(passwordinputActivity.this)
                .setTitle("初期化")
                .setMessage("パスワードを初期化しますか？")
                .setPositiveButton(
                        R.string.reset1,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reset();
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

    public void reset(){
        maru = "";
        password = 0;
        passwordinputtextView.setText(R.string.pleasesettingspassword);

        inputpasswordmethod(2);
    }

    public void inputpasswordmethod(int judge){
        sharedPreferences = getSharedPreferences("passwordfirst", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("passwordfirst",1);
        editor.commit();

        sharedPreferences = getSharedPreferences("password",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("password",password);
        editor.commit();

        //judge = 1の時はもとの画面に戻る
        if(judge == 1){
            if(password == 0){
                new AlertDialog.Builder(passwordinputActivity.this)
                        .setTitle(R.string.error)
                        .setMessage("パスワードを設定してください。")
                        .setPositiveButton(
                                R.string.ryoukai,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        password = 0;
                                    }
                                }
                        ).show();
            }else {

                new AlertDialog.Builder(passwordinputActivity.this)
                        .setTitle("保存しました！")
                        .setMessage("パスワードを保存しました！")
                        .setPositiveButton(
                                R.string.repeatpassword,

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        inputintent();
                                    }
                                }
                        ).show();
            }
        }
    }

    public void inputintent(){
        intent = new Intent(this, passwordActivity.class);
        startActivity(intent);
        finish();
    }

    public void kakuninnpassword(View v){
        Toast.makeText(passwordinputActivity.this, "只今入力されているパスワード：" + password, Toast.LENGTH_SHORT).show();
    }


    public void num1(View v){
        passwordinput(1);
    }

    public void num2(View v){
        passwordinput(2);
    }

    public void num3(View v){
        passwordinput(3);
    }

    public void num4(View v){
        passwordinput(4);
    }

    public void num5(View v){
        passwordinput(5);
    }

    public void num6(View v){
        passwordinput(6);
    }

    public void num7(View v){
        passwordinput(7);
    }

    public void num8(View v){
        passwordinput(8);
    }

    public void num9(View v){
        passwordinput(9);
    }

    public void num0(View v){
        passwordinput(0);
    }
}
