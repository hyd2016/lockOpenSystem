package com.learn.lockopensystem.ui.lockalarm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.lockopensystem.R
import com.learn.lockopensystem.http.NetworkHelper
import com.learn.lockopensystem.model.ErrorCode
import com.learn.lockopensystem.ui.lockopenrecord.LockOpenRecordActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_lock_alarm.*

class LockAlarmActivity : AppCompatActivity() {

    private lateinit var token: String
    private lateinit var lockId: String
    private lateinit var disposable: Disposable

    companion object {
        fun startLockAlarmActivity(context: Context, token: String, lockId: String) {
            val intent = Intent(context, LockOpenRecordActivity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("lockId", lockId)
            context.startActivity(intent)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_alarm)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        token = intent.getStringExtra("token")
        lockId = intent.getStringExtra("lockId")

        lock_alarm.layoutManager = LinearLayoutManager(this)
        disposable = NetworkHelper.getRetrofit().getLockAlarm(token, lockId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.result == 0) {
                        lock_alarm.adapter = LockAlarmAdapter(it.alarm)
                    }
                    Toast.makeText(this, ErrorCode.getErrorInfo(it.result), Toast.LENGTH_LONG)
                        .show()
                },
                {
                    Toast.makeText(this, "网络请求失败了呀！", Toast.LENGTH_LONG).show()
                    it.printStackTrace()
                }
            )

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
