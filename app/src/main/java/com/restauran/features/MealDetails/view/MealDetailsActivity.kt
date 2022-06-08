package com.restauran.features.MealDetails.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import com.restauran.R
import com.restauran.databinding.ActivityMealDetailsBinding
import com.restauran.features.bass.BaseActivity
import com.restauran.network.API.model.Menu
import com.restauran.network.API.model.SizeBean
import com.restauran.network.API.model.Toppings
import com.restauran.utils.AppShareMethods
import com.restauran.utils.ConstantApp
import com.restauran.utils.NavigationUtils
import com.restauran.utils.ToolUtils
import com.restauran.utils.dialogs.DialogUtils
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MealDetailsActivity : BaseActivity(), MealDetailsView {
    companion object {
        var item: Menu? = null
        fun newInstance(mActivity: Context?, fromWhere: Int, item: Menu): Intent {
            val intent = Intent(mActivity, MealDetailsActivity::class.java)
            intent.putExtra(ConstantApp.FROM_WHERE, fromWhere)
            this.item = item
            return intent
        }
    }

    private lateinit var binding: ActivityMealDetailsBinding
    var sizeSelected: SizeBean? = null
    var toppingsSelected: SizeBean? = null
    var ingredientsSelected: Toppings? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPresenter()
        initView()
        initListener()

    }

    private fun initListener() {
        binding.ibBack.setOnClickListener { onBackPressed() }
        binding.btnApply.setOnClickListener {
            Log.e("btnApply", "setOnClickListener")
            DialogUtils.showAlertDialogWithListener(this@MealDetailsActivity,
                getString(R.string.alert),
                getString(R.string.are_you_sure_this_is_your_order),
                getString(R.string.check_out),
                getString(R.string.cancel),
                object : DialogUtils.OnClickListener {
                    override fun onOkClick() {
                        DialogUtils.showAlertDialogWithListener(this@MealDetailsActivity,
                            getString(R.string.order_number),
                            getString(R.string.the_order_number),
                            getString(R.string.ok),
                            "",
                            object : DialogUtils.OnClickListener {
                                override fun onOkClick() {
                                    NavigationUtils.goToViewInvoice(this@MealDetailsActivity)
                                }

                                override fun onCancelClick() {
                                }
                            })

                    }

                    override fun onCancelClick() {

                    }
                })
        }
    }

    private fun initView() {
        item?.let {
            binding.tvTitle.text = it.menuname.toString()
            binding.tvDescription.text = it.description.toString()
            item?.images?.let { imgUrl ->
                imgUrl.forEach {
                    ToolUtils.setImg(
                        this,
                        it,
                        binding.ivImg
                    )
                }
            }
        }
        initStaticListData()
    }

    /**Must be coming from API but i add it as a static data*/
    private fun initStaticListData() {
        var statitListdata = ArrayList<SizeBean>()
        statitListdata.add(SizeBean(10, "Small", 11.9))
        statitListdata.add(SizeBean(14, "Medium", 19.9))
        statitListdata.add(SizeBean(13, "Large", 18.9))
        statitListdata.add(SizeBean(12, "XLarge", 25.9))
        addSizes(binding.allSize, statitListdata, statitListdata[0])
        var statitListdataToppings = ArrayList<SizeBean>()
        statitListdataToppings.add(SizeBean(10, "Cheese", 11.9))
        statitListdataToppings.add(SizeBean(14, "Olives", 19.9))
        statitListdataToppings.add(SizeBean(13, "Chicken", 18.9))
        addToppings(binding.allToppings, statitListdataToppings, statitListdataToppings[0])
        var addIngredientsList = ArrayList<Toppings>()
        addIngredientsList.add(Toppings(10, "2 tablespoons butter"))
        addIngredientsList.add(Toppings(14, "500 g chicken livers cleaned"))
        addIngredientsList.add(Toppings(13, "3-4 tablespoons peri-peri sauce"))
        addIngredientsList.add(Toppings(18, "3-4 tablespoons peri-peri sauce"))
        addIngredientsList.add(Toppings(19, "juice of 1 lemon"))
        addIngredientsList.add(Toppings(12, "salt & pepper to taste"))
        addIngredients(binding.allIngredient, addIngredientsList, addIngredientsList[0])

    }

    private fun initPresenter() {
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


    private fun addSizes(allSize: LinearLayout, sizes: ArrayList<SizeBean>, sizeBean: SizeBean) {
        val symbols = DecimalFormatSymbols(Locale.US)
        val formatter = DecimalFormat("#0.00", symbols)
        sizeSelected = sizeBean
        allSize.removeAllViews()
        val inflater = LayoutInflater.from(this)
        for (i in sizes.indices) {
            val view = inflater.inflate(R.layout.item_ingredient, null, false)
            val tvName = view.findViewById<TextView>(R.id.tv_name)
            val tvPrice = view.findViewById<TextView>(R.id.tv_price)
            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
            view.setOnClickListener { view1: View? ->
                checkBox.isChecked = !checkBox.isChecked
            }
            tvPrice.text = String.format(
                Locale.ENGLISH, this.getString(R.string.priceFormat),
                formatter.format(sizes[i].price)
            )
            tvName.setText(sizes[i].name)
            checkBox.tag = sizes[i]
            allSize.addView(view)
            if (sizes[i].id === sizeBean.id) {
                checkBox.isChecked = true
            } else {
                checkBox.isChecked = false
            }
            checkBox.setOnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
                val item = checkBox.tag as SizeBean
                addSizes(allSize, sizes, item)
            }
        }
        allSize.scrollTo(0, 0)
    }

    private fun addToppings(allSize: LinearLayout, sizes: ArrayList<SizeBean>, sizeBean: SizeBean) {
        val symbols = DecimalFormatSymbols(Locale.US)
        val formatter = DecimalFormat("#0.00", symbols)
        toppingsSelected = sizeBean
        allSize.removeAllViews()
        val inflater = LayoutInflater.from(this)
        for (i in sizes.indices) {
            val view = inflater.inflate(R.layout.item_ingredient, null, false)
            val tvName = view.findViewById<TextView>(R.id.tv_name)
            val tvPrice = view.findViewById<TextView>(R.id.tv_price)
            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
            view.setOnClickListener { view1: View? ->
                checkBox.isChecked = !checkBox.isChecked
            }
            tvPrice.text = String.format(
                Locale.ENGLISH, this.getString(R.string.priceFormat),
                formatter.format(sizes[i].price)
            )
            tvName.setText(sizes[i].name)
            checkBox.tag = sizes[i]
            allSize.addView(view)
            if (sizes[i].id === sizeBean.id) {
                checkBox.isChecked = true
            } else {
                checkBox.isChecked = false
            }
            checkBox.setOnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
                val item = checkBox.tag as SizeBean
                addToppings(allSize, sizes, item)
            }
        }
        allSize.scrollTo(0, 0)
    }

    private fun addIngredients(
        allSize: LinearLayout, sizes: ArrayList<Toppings>, sizeBean: Toppings
    ) {
        ingredientsSelected = sizeBean
        allSize.removeAllViews()
        val inflater = LayoutInflater.from(this)
        for (i in sizes.indices) {
            val view = inflater.inflate(R.layout.item_ingredient, null, false)
            val tvName = view.findViewById<TextView>(R.id.tv_name)
            val tvPrice = view.findViewById<TextView>(R.id.tv_price)
            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
            view.setOnClickListener { view1: View? ->
                checkBox.isChecked = !checkBox.isChecked
            }
            tvPrice.visibility = View.GONE
            tvName.setText(sizes[i].name)
            checkBox.tag = sizes[i]
            allSize.addView(view)
            if (sizes[i].id === sizeBean.id) {
                checkBox.isChecked = true
            } else {
                checkBox.isChecked = false
            }
            checkBox.setOnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
                val item = checkBox.tag as Toppings
                addIngredients(allSize, sizes, item)
            }
        }
        allSize.scrollTo(0, 0)
    }
}