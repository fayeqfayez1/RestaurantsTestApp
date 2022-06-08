package com.restauran.features.main.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restauran.R
import com.restauran.databinding.ActivityMainBinding
import com.restauran.features.bass.BaseActivity
import com.restauran.features.main.adapter.RestaurantsAdapter
import com.restauran.features.main.presenter.MainPresenter
import com.restauran.network.API.model.Restaurant
import com.restauran.utils.AppShareMethods
import com.restauran.utils.ConstantApp.FROM_MAIN
import com.restauran.utils.ConstantApp.FROM_WHERE
import com.restauran.utils.NavigationUtils
import com.restauran.utils.dialogs.DialogUtils

class MainActivity : BaseActivity(), MainView, RestaurantsAdapter.OnClickListener {

    companion object {
        fun newInstance(mActivity: Context?, fromWhere: Int): Intent {
            val intent = Intent(mActivity, MainActivity::class.java)
            intent.putExtra(FROM_WHERE, fromWhere)
            return intent
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    private var adapter: RestaurantsAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPresenter()
        initView()
    }

    private fun initPresenter() {
        presenter = MainPresenter(this, this)
        presenter.getRestaurantData()
    }

    private fun initView() {
    }

    override fun setRestaurantsDataView(data: List<Restaurant>) {
        data.map { Log.e("setRestaurantsDataView", "${it.address}") }
        adapter = RestaurantsAdapter(this, data as ArrayList<Restaurant>, this)
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

    override fun onItemClick(item: Restaurant, position: Int) {
        NavigationUtils.goToMenu(this, FROM_MAIN)
    }
}