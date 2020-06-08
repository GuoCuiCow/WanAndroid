package com.example.myapplication.ui.home.fragment

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.HomeBean
import com.example.myapplication.ui.home.adapter.ArticleAdapter
import com.example.myapplication.ui.home.viewmodel.HomeViewModel
import com.example.myapplication.ui.home.viewmodel.Result
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    private val TAG = "HomeFragment"

    private val _viewModel: HomeViewModel by viewModel()
    private val articlesList = mutableListOf<HomeBean>()
    private val articleAdapter by lazy { ArticleAdapter(R.layout.item_article, articlesList) }

    override fun getLayoutId() = R.layout.fragment_home

    override fun initView() {
        super.initView()
        recyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initData() {
        super.initData()
        _viewModel.getArticleList().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Error -> {
                    Log.d(TAG, "onError,Result's hash =" + it.hashCode().toString())
                }
                is Result.Loading -> {
                    Log.d(TAG, "onLoading,Result's hash =" + it.hashCode().toString())
                }
                is Result.Success -> {
                    Log.d(TAG, "onSuccess,Result's hash =" + it.hashCode().toString())
                    it.response.data.datas.let { articles ->
                        articleAdapter.setNewInstance(articles)
                    }
                }
            }


        })
    }
}
