package com.restauran.features.main.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.restauran.databinding.ItemResturantBinding
import com.restauran.network.API.model.Restaurant
import com.restauran.utils.ToolUtils

class RestaurantsAdapter(
    private var mActivity: Activity,
    private var list: ArrayList<Restaurant>,
    private var listener: OnClickListener,
) : RecyclerView.Adapter<RestaurantsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemResturantBinding =
            ItemResturantBinding.inflate(mActivity.layoutInflater)
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
        holder.binding.rootLayout.setOnClickListener {
            listener.onItemClick(getItem(position), holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItem(pos: Int): Restaurant {
        return list[pos]
    }

    fun remove(location: Int) {
        if (location >= list.size) return
        list.removeAt(location)
        notifyItemRemoved(location)
    }

    fun add(mc: Restaurant) {
        list.add(mc)
        notifyItemInserted(list.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAll() {
        list.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(list: ArrayList<Restaurant>) {
        for (mc in list) {
            add(mc)
        }
        notifyDataSetChanged()
    }

    class MyViewHolder(
        var rootView: View,
        var binding: ItemResturantBinding,
        var context: Context
    ) : RecyclerView.ViewHolder(rootView) {
        fun bind(item: Restaurant, binding: ItemResturantBinding) {
            item?.image?.let { imgUrl -> ToolUtils.setImg(context, imgUrl, binding.imageView) }
            binding.tvTitle.text = item.businessname.toString()
            binding.tvDescription.text = item.address.toString()
            item.reviews?.let { rating -> binding.rating.rating = rating.toFloat() }
        }
    }

    interface OnClickListener {
        fun onItemClick(item: Restaurant, position: Int)
    }

}