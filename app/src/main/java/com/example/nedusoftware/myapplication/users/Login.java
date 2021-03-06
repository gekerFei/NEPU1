package com.example.nedusoftware.myapplication.users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.nedusoftware.myapplication.BaseActivity;
import com.example.nedusoftware.myapplication.MainActivity;
import com.example.nedusoftware.myapplication.R;
import com.example.nedusoftware.myapplication.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by NEDUsoftware on 2017/4/22.
 */

public class Login extends BaseActivity implements View.OnClickListener {
    private Button btn_login;
    private Button btn_register;
    private EditText et_id;
    private EditText et_psd;
    private SharedPreferences.Editor editor ;

    @Override
    public void initViews() {
        btn_login = (Button) findViewById(R.id.btn_login_in);
        et_id = (EditText) findViewById(R.id.et_login_id);
        et_psd = (EditText) findViewById(R.id.et_login_password);
        btn_register = (Button) findViewById(R.id.btn_login_register);
    }

    @Override
    public void initListeners() {
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_in:
                queryuser();
                break;
            case R.id.btn_login_register:
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                break;
        }
    }

    private void queryuser() {
        final String psd;
        if ((et_id.getText().toString().equals("")) || (et_psd.getText().toString().equals(""))) {
            Toast.makeText(Login.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
        } else {
            Md5 md5 = new Md5();
            psd = md5.getmd5(et_psd.getText().toString());
            BmobQuery<User> bmobQuery = new BmobQuery<User>();
            bmobQuery.addWhereEqualTo("userId", et_id.getText().toString());
            bmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if (e == null) {
                        if (list.size() != 0) {
                            for (User user : list) {
                                if (user.getUserPassword().equals(psd)) {
                                    Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    editor= getSharedPreferences("userdata", MODE_PRIVATE).edit();
                                    editor.putString("userName", user.getUserName());
                                    editor.putString("userSex", user.getUserSex());
                                    editor.putString("objectId", user.getObjectId());
                                    Log.e(TAG, "onSuccess: " + user.getObjectId());
                                    if (user.getIcon() != null) {
                                        editor.putBoolean("userIcon", true);
                                    } else {
                                        editor.putBoolean("userIcon", false);
                                    }

                                    addusers();
                                    startActivity(intent);
                                    Login.this.finish();
                                } else {
                                    Toast.makeText(Login.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    et_psd.setText("");
                                }
                            }
                        } else {
                            Toast.makeText(Login.this, "账号错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "查询失败", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }

    /**
     * 保存用户信息到本地
     */
    private void addusers() {
        editor.putString("userId", et_id.getText().toString());
        editor.putString("userPassword", et_id.getText().toString());
        editor.putBoolean("isuser", true);
        editor.apply();
    }
}
