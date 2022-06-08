package com.restauran.features.ViewInvoice.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.restauran.databinding.ActivityViewInvoiceBinding
import com.restauran.features.bass.BaseActivity
import com.restauran.utils.ConstantApp

class ViewInvoiceActivity : BaseActivity(), ViewInvoiceView {
    companion object{  fun newInstance(mActivity: Context?, fromWhere: Int): Intent {
        val intent = Intent(mActivity, ViewInvoiceActivity::class.java)
        intent.putExtra(ConstantApp.FROM_WHERE, fromWhere)
        return intent
    }}



    private lateinit var binding: ActivityViewInvoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ibBack.setOnClickListener { onBackPressed() }
    }
}