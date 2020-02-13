package com.learn.lockopensystem.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.learn.lockopensystem.MainActivity
import com.learn.lockopensystem.R
import com.learn.lockopensystem.http.NetworkHelper
import com.learn.lockopensystem.model.DataCenter
import com.learn.lockopensystem.model.ErrorCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var userName:String? = null
    private var psw:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    //获取界面控件
    private fun init() {
        readPsw()
        //立即注册控件的点击事件
        tv_register.setOnClickListener {
            //为了跳转到注册界面，并实现注册功能
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener {
            startActivity(Intent(this@LoginActivity, LostFindActivity::class.java))
        }
        //登录按钮的点击事件
        btn_login.setOnClickListener {
            //开始登录，获取用户名和密码 getText().toString().trim();
            userName = et_user_name?.text.toString()
            psw = et_psw?.text.toString()

            // TextUtils.isEmpty
            when {
                TextUtils.isEmpty(userName) or TextUtils.isEmpty(psw)-> {
                    Toast.makeText(this, "手机号或密码为空", Toast.LENGTH_SHORT).show()
                }
                else -> { //一致登录成功
                    NetworkHelper.getRetrofit().login(userName!!, psw!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                if (it.result == 0) {
                                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                                    DataCenter.oauthResult = it
                                    startActivity(Intent(this, MainActivity::class.java))
                                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                                    saveLoginStatus(userName!!, psw!!)
                                    finish()
                                } else {
                                    Toast.makeText(this, ErrorCode.getErrorInfo(it.result), Toast.LENGTH_LONG)
                                        .show()
                                }

                            },
                            {
                                Toast.makeText(this, "网络请求失败了呀！", Toast.LENGTH_LONG).show()
                                it.printStackTrace()
                            }
                        )


                }
            }
        }
    }

    /**
     * 从SharedPreferences中根据用户名读取密码
     */
    private fun readPsw() {
        val sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        //sp.getString() userName, "";
        val name = sp.getString("loginUserName", "")
        val psw = sp.getString("loginPsw", "")
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(psw)) {
            et_user_name?.setText(name)
            et_psw?.setText(psw)
        }
    }

    /**
     * 保存登录状态和登录用户名到SharedPreferences中
     */
    private fun saveLoginStatus(
        userName: String,
        psw: String
    ) { //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        val sp =
            getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        //获取编辑器
        val editor = sp.edit()
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName)
        editor.putString("loginPsw", psw)
        //提交修改
        editor.apply()
    }

}

