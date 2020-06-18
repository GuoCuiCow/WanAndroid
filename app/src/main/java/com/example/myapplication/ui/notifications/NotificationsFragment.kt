package com.example.myapplication.ui.notifications

import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.User
import kotlinx.android.synthetic.main.fragment_notifications.*
import org.koin.android.viewmodel.ext.android.viewModel

class NotificationsFragment : BaseFragment() {

    private val _viewModel: NotificationsViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_notifications

    override fun initView() {
        super.initView()
        floatingActionButton.setOnClickListener {
            val id = id_text.text.toString()
            val firstNameText = first_name_text.text.toString()
            val lastNameText = last_name_text.text.toString()
            
            if (id.isEmpty()) {
                id_layout.error = "id不能为空"
                return@setOnClickListener
            }
            if (firstNameText.isEmpty()) {
                first_name_layout.error = "姓不能为空"
                return@setOnClickListener
            }
            if (lastNameText.isEmpty()) {
                last_name_layout.error = "名字不能为空"
                return@setOnClickListener
            }


            val user =User(id.toInt(),firstNameText, lastNameText)
            _viewModel.insert(user)
        }
    }
}
