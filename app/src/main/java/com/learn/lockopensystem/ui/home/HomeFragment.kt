package com.learn.lockopensystem.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.lockopensystem.R
import com.learn.lockopensystem.model.DataCenter
import com.learn.lockopensystem.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: MyLockAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.locks?.observe(
            viewLifecycleOwner, Observer {
                my_lock.layoutManager = LinearLayoutManager(context)
                it?.let {
                    adapter = MyLockAdapter(it)
                    my_lock.adapter = adapter
                    adapter.setOnClickListener(object : MyLockAdapter.OnClickListener{
                        override fun setClickListener(lockId: String) {
                            DetailActivity.startDetail(context!!, DataCenter.oauthResult!!.token, lockId)
                        }
                    })
                }

            }
        )

        return root
    }
}