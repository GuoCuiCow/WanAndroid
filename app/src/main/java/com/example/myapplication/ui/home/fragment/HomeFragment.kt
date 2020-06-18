package com.example.myapplication.ui.home.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.HomeBean
import com.example.myapplication.ui.home.adapter.ArticleAdapter
import com.example.myapplication.ui.home.viewmodel.HomeViewModel
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
        articleAdapter.apply {
            loadMoreModule.apply {
                setOnLoadMoreListener {
                    _viewModel.loadMore()
                }
            }
        }
        recyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(context)
        }



    }

    override fun initData() {
        super.initData()
        _viewModel.list.observe(viewLifecycleOwner, Observer {
            articlesList.addAll(it.data.datas)
            articleAdapter.notifyDataSetChanged()
        })
        _viewModel.loadMore()
    }
}
