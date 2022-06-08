package com.restauran.features.splash.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.restauran.R
import com.restauran.databinding.ActivitySplahBinding
import com.restauran.features.bass.BaseActivity
import com.restauran.utils.AppShareMethods
import com.restauran.utils.AppSharedData
import com.restauran.utils.ConstantApp.FROM_SPLASH
import com.restauran.utils.ConstantApp.SPLASH_TIME_OUT
import com.restauran.utils.NavigationUtils
import com.restauran.utils.dialogs.DialogUtils

class SplashActivity : BaseActivity(), SplashView {
    private val TAG = SplashActivity::class.simpleName
    private lateinit var binding: ActivitySplahBinding
    private var handler: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplahBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initTimer()
    }

    private fun initTimer() {
        handler = Handler()
        initTimer(handler)
    }

    private fun initTimer(handler: Handler?) {
        this.handler = handler
        this.handler?.postDelayed(Runnable { this.navToNextPage() }, SPLASH_TIME_OUT)
    }

    private fun navToNextPage() {
        if (AppSharedData.isUserLogin() == true) NavigationUtils.goToMain(this, FROM_SPLASH)
        else NavigationUtils.goToLogIn(this, FROM_SPLASH)
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