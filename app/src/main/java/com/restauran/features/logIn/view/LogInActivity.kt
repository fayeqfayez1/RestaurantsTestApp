package com.restauran.features.logIn.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.restauran.R
import com.restauran.databinding.ActivityLogInBinding
import com.restauran.features.bass.BaseActivity
import com.restauran.features.logIn.presenter.LogInPresenter
import com.restauran.utils.AppShareMethods
import com.restauran.utils.ConstantApp.FROM_WHERE
import com.restauran.utils.dialogs.DialogUtils

class LogInActivity : BaseActivity(), LogInView {

    companion object {
        fun newInstance(mActivity: Context?, fromWhere: Int): Intent {
            val intent = Intent(mActivity, LogInActivity::class.java)
            intent.putExtra(FROM_WHERE, fromWhere)
            return intent
        }
    }


    private lateinit var binding: ActivityLogInBinding
    private lateinit var presenter: LogInPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.rootLayout)
        initView()
        initPresenter()
    }

    private fun initView() {
        binding.btnSignin.setOnClickListener {
            presenter.validateSignIn(
                binding.edEmail,
                binding.edPassword
            )
        }
    }

    private fun initPresenter() {
        presenter = LogInPresenter(this, this)
    }

    override fun showErrorMessage(massage: String?) {
        massage?.let { msg -> AppShareMethods.showSnackBar(this, binding.root, msg) }
    }

    override fun showErrorDialog(
        massage: String?,
        okMsg: String?,
        cancelMsg: String?,
        action: String?,
    ) {
        DialogUtils.showAlertDialog(
            this,
            getString(R.string.alert),
            massage,
            okMsg,
            cancelMsg,
            action
        )
    }
}