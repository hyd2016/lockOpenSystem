package com.learn.lockopensystem.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.learn.lockopensystem.R
import com.learn.lockopensystem.http.NetworkHelper
import com.learn.lockopensystem.model.DataCenter
import com.learn.lockopensystem.model.ErrorCode
import com.learn.lockopensystem.ui.lockalarm.LockAlarmActivity
import com.learn.lockopensystem.ui.lockopenrecord.LockOpenRecordActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    private lateinit var token: String
    private lateinit var lockId: String
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        token = intent.getStringExtra("token")
        lockId = intent.getStringExtra("lockId")

        open_lock.setOnClickListener {
            NetworkHelper.getRetrofit().lockOpen(token, lockId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Toast.makeText(this, ErrorCode.getErrorInfo(it.result), Toast.LENGTH_LONG)
                            .show()
                    },
                    {
                        Toast.makeText(this, "网络请求失败了呀！", Toast.LENGTH_LONG).show()
                        it.printStackTrace()
                    }
                )
        }
        change_lock_name.setOnClickListener {
            changeNameDialog()
        }
        lock_record.setOnClickListener {
            LockOpenRecordActivity.startLockOpenRecordActivity(this, token, lockId)
        }
        lock_alarm_record.setOnClickListener {
            LockAlarmActivity.startLockAlarmActivity(this, token, lockId)
        }
        delete_lock.setOnClickListener {
            deleteLock()
        }
    }

    private fun deleteLock() {
        disposable = NetworkHelper.getRetrofit().delLock(token, lockId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.result == 0) {
                        Toast.makeText(this, "删除成功！", Toast.LENGTH_LONG)
                            .show()
                        DataCenter.removeLock(lockId.toInt())
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

    private fun changeNameDialog() {
        val editText = EditText(this)
        val builder = AlertDialog.Builder(this).setTitle("请输入新的锁名称").setView(editText)
            .setPositiveButton(
                "确定"
            ) { _, _ ->
                NetworkHelper.getRetrofit().changeLockName(token, lockId, editText.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Toast.makeText(
                                this,
                                ErrorCode.getErrorInfo(it.result),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        },
                        {
                            Toast.makeText(this, "网络请求失败了呀！", Toast.LENGTH_LONG).show()
                            it.printStackTrace()
                        }
                    )
            }
            .setNegativeButton(
                "取消"
            ) { dialog, _ -> dialog?.dismiss() }
        builder.create().show()
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

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    companion object {
        fun startDetail(context: Context, token: String, lockId: String) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("lockId", lockId)
            context.startActivity(intent)
        }
    }

}
