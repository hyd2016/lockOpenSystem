package com.learn.lockopensystem.ui.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.learn.lockopensystem.R
import com.learn.lockopensystem.http.NetworkHelper
import com.learn.lockopensystem.model.DataCenter
import com.learn.lockopensystem.model.ErrorCode
import com.learn.lockopensystem.ui.login.LoginActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_logout.*

class LogoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_logout, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun init() {
        btn_logout.setOnClickListener {
            DataCenter.oauthResult?.token?.let {token ->
                NetworkHelper.getRetrofit().logout(token)
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
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}