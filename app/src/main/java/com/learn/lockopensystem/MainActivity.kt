package com.learn.lockopensystem

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.learn.lockopensystem.http.NetworkHelper
import com.learn.lockopensystem.model.DataCenter
import com.learn.lockopensystem.model.ErrorCode
import com.learn.lockopensystem.model.getresult.Lock
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var dispose: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMyLocks()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_add_lock,
                R.id.get_new_alarm, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    // 登录成功获取数据
    private fun getMyLocks() {
//        val locks: MutableLiveData<MutableList<Lock>> = MutableLiveData()
//        val listLocks = ArrayList<Lock>()
//        val lock1 = Lock(1, "我的锁", "2019/12/17 17:34:10", 1, "2019/12/16 2:47:14")
//        val lock2 = Lock(2, "我的锁锁", "2019/12/18 17:34:10", 1, "2019/12/18 2:47:14")
//        val lock3 = Lock(3, "123", "2019/12/19 17:34:10", 1, "2019/12/19 2:47:14")
//        listLocks.add(lock1)
//        listLocks.add(lock2)
//        listLocks.add(lock3)
//        locks.value = listLocks
//        DataCenter.locks = locks
        val locks: MutableLiveData<MutableList<Lock>> = MutableLiveData()
        DataCenter.oauthResult?.token?.let { token ->
            NetworkHelper.getRetrofit().getuserlock(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.result == 0) {
                            locks.value = it.lock as ArrayList<Lock>
                            DataCenter.locks = locks
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

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        dispose?.dispose()
        super.onDestroy()
    }
}
