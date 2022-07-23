package com.android.developmentchallenge.util.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.adapter.BaseAdapterRecyclerView
import com.android.developmentchallenge.databinding.ItemHomeForecastBinding
import com.android.developmentchallenge.model.ForecastModel

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class AdapterHomeForecast:BaseAdapterRecyclerView<ForecastModel,AdapterHomeForecast.ItemViewHolder>() {

    var listener :OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemHomeForecastBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_home_forecast,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            listener?.onClick(this@AdapterHomeForecast.dataList[position])
        }
    }

    inner class ItemViewHolder(
        val binding: ItemHomeForecastBinding
    ) : BaseAdapterRecyclerView.ViewHolderViewModel<ForecastModel>(
        binding = binding
    ) {
        override fun bind(item: ForecastModel, position: Int) {
            super.bind(item, position)
            binding.item = item
            binding.executePendingBindings()
        }
    }

    // interface Click on item
    interface OnItemClickListener {
        fun onClick(model: ForecastModel)
    }

}