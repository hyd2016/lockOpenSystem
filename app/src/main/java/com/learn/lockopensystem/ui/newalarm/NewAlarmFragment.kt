package com.learn.lockopensystem.ui.newalarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.lockopensystem.R
import com.learn.lockopensystem.http.NetworkHelper
import com.learn.lockopensystem.model.DataCenter
import com.learn.lockopensystem.model.ErrorCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_new_alarm.*

class NewAlarmFragment : Fragment() {
    private lateinit var disposable: Disposable


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_new_alarm, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        lock_alarm.layoutManager = LinearLayoutManager(context)
        if (DataCenter?.oauthResult == null) {
            Toast.makeText(context, "认证失败了！", Toast.LENGTH_LONG).show()
            return
        }
        disposable = NetworkHelper.getRetrofit().getNewAlarm(DataCenter.oauthResult?.token!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.result == 0) {
                        lock_alarm.adapter = NewAlarmAdapter(it.alarm)
                    }
                    Toast.makeText(context, ErrorCode.getErrorInfo(it.result), Toast.LENGTH_LONG)
                        .show()
                },
                {
                    Toast.makeText(context, "网络请求失败了呀！", Toast.LENGTH_LONG).show()
                    it.printStackTrace()
                }
            )
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}