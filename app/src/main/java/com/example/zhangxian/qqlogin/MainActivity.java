package com.example.zhangxian.qqlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private String qqNum;
    private String qqPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Button button_login = findViewById(R.id.button_login);
        reloadInfo();
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1获取qq号码 密码
                qqNum = ((EditText) findViewById(R.id.qq_num)).getText().toString().trim();
                qqPassword = ((EditText) findViewById(R.id.qq_password)).getText().toString().trim();
                CheckBox checkBox = findViewById(R.id.checkBox_saveInfo);
                if (checkBox.isChecked()) {
                    try {
                        File file = new File(getFilesDir(), "info.txt");
                        FileOutputStream fos = new FileOutputStream(file);
                        String info = qqNum + "##" + qqPassword;
                        fos.write(info.getBytes());
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                else
                {
                    delInfo();
                }
                //2、判断是否为空
                if (qqNum == null || qqNum.equals("")) {
                    Toast.makeText(MainActivity.this, "QQ号码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (qqPassword == null || qqPassword.equals("")) {
                    Toast.makeText(MainActivity.this, "QQ密码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean result = checkPass(qqNum, qqPassword);
                if (result) {
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 模拟判断登陆
     *
     * @param qNum
     * @param qqPass
     * @return
     */
    private boolean checkPass(String qNum, String qqPass) {
        if (qNum == null || qNum.length() == 0 || qqPass == null || qqPass.length() == 0) {
            return false;
        } else if (qNum.equals("100000") && qqPass.equals("888888")) {
            return true;
        } else {
            return false;
        }


    }


    private void reloadInfo()

    {
         EditText qqNumEdit = findViewById(R.id.qq_num);
         EditText qqPassEdit = findViewById(R.id.qq_password);
        File file = new File(getFilesDir(),"info.txt");
        if(file.exists()&&file.length()>0)
        {
            try
            {
                CheckBox checkBox = findViewById(R.id.checkBox_saveInfo);
                checkBox.setChecked(true);
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String info[] = br.readLine().split("##");
                qqNumEdit.setText(info[0]);
                qqPassEdit.setText(info[1]);

            }
            catch (Exception e)
            {

            }

        }
    }
    private void delInfo()
    {
        File file = new File(getFilesDir(),"info.txt");
        if (file.exists())
        {
            file.delete();
        }
    }
}
