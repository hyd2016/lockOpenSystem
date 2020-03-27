package com.learn.lockopensystem.ui.addlock

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.learn.lockopensystem.R
import com.learn.lockopensystem.http.NetworkHelper
import com.learn.lockopensystem.model.DataCenter
import com.learn.lockopensystem.model.ErrorCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_addlock.*

class AddBlockFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_addlock, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        btn_add_lock.setOnClickListener {
            val lockId = et_add_lock_id.text.toString()
            val lockPsw = et_add_lock_psw.text.toString()
            when {
                TextUtils.isEmpty(lockId) -> {
                    Toast.makeText(context, "锁编号为空", Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(lockPsw) -> {
                    Toast.makeText(context, "锁密码为空", Toast.LENGTH_LONG).show()
                }
                else -> {
                    if (DataCenter.oauthResult == null) {
                        Toast.makeText(context, "添加失败", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    NetworkHelper.getRetrofit().addLock(lockId, lockPsw, DataCenter.oauthResult?.token!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                Toast.makeText(context, ErrorCode.getErrorInfo(it.result), Toast.LENGTH_LONG)
                                    .show()
                            },
                            {
                                Toast.makeText(context, "网络请求失败了呀！", Toast.LENGTH_LONG).show()
                                it.printStackTrace()
                            }
                        )
                }
            }
        }
        btn_add_camera.setOnClickListener {
            IntentIntegrator.forSupportFragment(this).initiateScan()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null) {
            if(result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                addBloc(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    private fun addBloc(result:String){
        val id_pw = result.split('&')
        if (id_pw.size != 2){
            return
        }
        val lockId = id_pw[0].substring(id_pw[0].indexOf('=') + 1)
        val lockPsw = id_pw[1].substring(id_pw[1].indexOf('=') + 1)
        when {
            TextUtils.isEmpty(lockId) -> {
                Toast.makeText(context, "锁编号为空", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(lockPsw) -> {
                Toast.makeText(context, "锁密码为空", Toast.LENGTH_LONG).show()
            }
            else -> {
                if (DataCenter.oauthResult == null) {
                    Toast.makeText(context, "添加失败", Toast.LENGTH_LONG).show()
                    return
                }
                NetworkHelper.getRetrofit()
                    .addLock(lockId, lockPsw, DataCenter.oauthResult?.token!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Toast.makeText(
                                context,
                                ErrorCode.getErrorInfo(it.result),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        },
                        {
                            Toast.makeText(context, "网络请求失败了呀！", Toast.LENGTH_LONG).show()
                            Log.d("addlock", it.toString())
                        }
                    )
            }
        }
    }
}