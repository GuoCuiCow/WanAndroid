package com.example.myapplication.ui.home.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.data.HomeBean

class ArticleAdapter(layoutResId: Int, data: MutableList<HomeBean>?) :
    BaseQuickAdapter<HomeBean, BaseViewHolder>(layoutResId, data),LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: HomeBean) {
        holder.getView<TextView>(R.id.textView).text = item.chapterName
    }
}