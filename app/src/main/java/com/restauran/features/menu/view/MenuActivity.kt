package com.restauran.features.menu.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restauran.R
import com.restauran.databinding.ActivityMenuBinding
import com.restauran.features.MealDetails.view.MealDetailsActivity
import com.restauran.features.bass.BaseActivity
import com.restauran.features.main.adapter.RestaurantsAdapter
import com.restauran.features.menu.adapter.MenuAdapter
import com.restauran.features.menu.prsenter.MenuPresenter
import com.restauran.network.API.model.Menu
import com.restauran.utils.AppShareMethods
import com.restauran.utils.ConstantApp
import com.restauran.utils.ConstantApp.FROM_OTHERS
import com.restauran.utils.dialogs.DialogUtils

class MenuActivity : BaseActivity(), MenuView, MenuAdapter.OnClickListener {
    companion object {
        fun newInstance(mActivity: Context?, fromWhere: Int): Intent {
            val intent = Intent(mActivity, MenuActivity::class.java)
            intent.putExtra(ConstantApp.FROM_WHERE, fromWhere)
            return intent
        }
    }

    private lateinit var binding: ActivityMenuBinding
    private lateinit var presenter: MenuPresenter
    private var adapter: MenuAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPresenter()
        initView()
    }

    private fun initView() {
    }

    private fun initPresenter() {
        presenter = MenuPresenter(this, this)
        presenter.getMenuData()
    }

    override fun setMenusDataView(data: ArrayList<Menu>) {
        data.map { Log.e("setRestaurantsDataView", "${it.menuname}") }
        adapter = MenuAdapter(this, data, this)
        mLayoutManager = LinearLayoutManager(applicationContext)
        binding.rvList.layoutManager = mLayoutManager
        binding.rvList.adapter = adapter
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

    override fun onItemClick(item: Menu, position: Int) {
        val intent: Intent = MealDetailsActivity.newInstance(this, FROM_OTHERS, item)
        startActivity(intent)
    }
}