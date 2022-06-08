package com.restauran.features.menu.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.restauran.R
import com.restauran.databinding.ItemMealBinding
import com.restauran.network.API.model.Menu
import com.restauran.utils.ToolUtils
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MenuAdapter(
    private var mActivity: Activity,
    private var list: ArrayList<Menu>,
    private var listener: OnClickListener,
) : RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemMealBinding =
            ItemMealBinding.inflate(mActivity.layoutInflater)
        val view = binding.root
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(0, 10, 0, 5)
        view.layoutParams = lp
        return MyViewHolder(view, binding, mActivity)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position), holder.binding)
        holder.binding.tvAdd.setOnClickListener {
            listener.onItemClick(getItem(position), holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItem(pos: Int): Menu {
        return list[pos]
    }

    fun remove(location: Int) {
        if (location >= list.size) return
        list.removeAt(location)
        notifyItemRemoved(location)
    }

    fun add(mc: Menu) {
        list.add(mc)
        notifyItemInserted(list.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAll() {
        list.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(list: ArrayList<Menu>) {
        for (mc in list) {
            add(mc)
        }
        notifyDataSetChanged()
    }

    class MyViewHolder(
        var rootView: View,
        var binding: ItemMealBinding,
        var context: Context
    ) : RecyclerView.ViewHolder(rootView) {
        private val symbols = DecimalFormatSymbols(Locale.US)
        private val formatter = DecimalFormat("#0.00", symbols)
        fun bind(item: Menu, binding: ItemMealBinding) {
            /*         item?.images?.let { imgUrl ->
                         imgUrl.forEach { ToolUtils.setImgWithProgress(context, it, binding.imageView,binding.progress) }
                     }*/
            binding.tvTitle.text = item.menuname.toString()
            binding.tvDescription.text = item.description.toString()
            binding.tvPrice.text =
                String.format(
                    Locale.ENGLISH, context.getString(R.string.priceFormat), formatter.format(50.5)
                )
            binding.tvQuantity.text = String.format(
                Locale.ENGLISH,
                context.getString(R.string.quantityFormat),
                formatter.format(3.2)
            )
        }
    }

    interface OnClickListener {
        fun onItemClick(item: Menu, position: Int)
    }


}