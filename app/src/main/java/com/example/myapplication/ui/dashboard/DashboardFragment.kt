package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.User
import com.example.myapplication.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {
    var users = mutableListOf<User>()
    private val _adapter by lazy { UserAdapter(R.layout.item_article, users) }
    private val _viewModel: DashboardViewModel by viewModel()
    override fun getLayoutId() = R.layout.fragment_dashboard
    override fun initView() {
        super.initView()

        recyclerView.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initData() {
        super.initData()
        _viewModel.allWords.observe(viewLifecycleOwner, Observer {
            _adapter.setNewInstance(it.toMutableList())
        })
    }
}
