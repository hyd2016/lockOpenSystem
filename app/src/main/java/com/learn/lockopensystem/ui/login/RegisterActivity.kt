package com.learn.lockopensystem.ui.login

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.learn.lockopensystem.R
import com.learn.lockopensystem.http.NetworkHelper
import com.learn.lockopensystem.model.ErrorCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    //用户名，密码，再次输入的密码的控件的获取值
    private var userName: String? = null
    private var psw: String? = null
    private var pswAgain: String? = null
    private var nickname: String? = null
    private var smsCode: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register)
        //设置此界面为竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        init()
    }

    private fun init() { //从activity_register.xml 页面中获取对应的UI控件
        btn_sms_code.setOnClickListener {
            et_user_name.text?.let {
                if (it.toString().length == 11 && it.toString().isDigitsOnly()) {
                    NetworkHelper.getRetrofit().SMSAuth(it.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { result ->
                                if (result.result == 0) {
                                    Toast.makeText(this, "验证码已经发送，请注意查收", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        ErrorCode.getErrorInfo(result.result),
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }

                            },
                            { throwable ->
                                Toast.makeText(this, "网络请求失败了呀！", Toast.LENGTH_LONG).show()
                                throwable.printStackTrace()
                            }
                        )
                } else {
                    Toast.makeText(this, "手机号码有误", Toast.LENGTH_SHORT).show()
                }

            }

        }

        //注册按钮
        btn_register.setOnClickListener {
            //获取输入在相应控件中的字符串
            editString
            //判断输入框内容

            when {
                TextUtils.isEmpty(userName) -> {
                    Toast.makeText(this@RegisterActivity, "请输入手机号", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(smsCode) -> {
                    Toast.makeText(this@RegisterActivity, "请输入验证码", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(psw) -> {
                    Toast.makeText(this@RegisterActivity, "请输入密码", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(pswAgain) -> {
                    Toast.makeText(this@RegisterActivity, "请再次输入密码", Toast.LENGTH_SHORT).show()
                }
                psw != pswAgain -> {
                    Toast.makeText(this@RegisterActivity, "输入两次的密码不一样", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(nickname) -> {
                    Toast.makeText(this@RegisterActivity, "请输入昵称", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    NetworkHelper.getRetrofit().register(userName!!, smsCode!!, psw!!, nickname!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { result ->
                                if (result.result == 0) {
                                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show()
                                    saveRegisterInfo(userName, psw)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        ErrorCode.getErrorInfo(result.result),
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }

                            },
                            { throwable ->
                                Toast.makeText(this, "网络请求失败了呀！", Toast.LENGTH_LONG).show()
                                throwable.printStackTrace()
                            }
                        )
                }
            }
        }
    }

    /**
     * 获取控件中的字符串
     */
    private val editString: Unit
        get() {
            userName = et_user_name?.text.toString()
            psw = et_psw?.text.toString()
            pswAgain = et_psw_again?.text.toString()
            nickname = et_nickname?.text.toString()
            smsCode = et_sms_code?.text.toString()
        }

    /**
     * 保存账号和密码到SharedPreferences中SharedPreferences
     */
    private fun saveRegisterInfo(userName: String?, psw: String?) {
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        val sp =
            getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        val editor = sp.edit()
        editor.putString("loginUserName", userName)
        editor.putString("loginPsw", psw)
        //提交修改 editor.commit();
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish() // back button
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}