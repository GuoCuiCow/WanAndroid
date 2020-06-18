package com.example.myapplication.ui.dashboard

import android.annotation.SuppressLint
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.data.User

class UserAdapter(layoutResId: Int, data: MutableList<User>?) :
    BaseQuickAdapter<User, BaseViewHolder>(layoutResId, data) {
    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: User) {
        holder.getView<TextView>(R.id.textView).text = item.firstName + item.lastName
    }
}