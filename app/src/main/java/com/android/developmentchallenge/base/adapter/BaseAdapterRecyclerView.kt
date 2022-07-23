package com.android.developmentchallenge.base.adapter

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.developmentchallenge.util.ext.clone

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

abstract class BaseAdapterRecyclerView<M, S : BaseAdapterRecyclerView.ViewHolderViewModel<M>> :
    RecyclerView.Adapter<S>() {
    // Data list for view
    var dataList: ArrayList<M> = arrayListOf()

    final override fun getItemCount(): Int {
        return this@BaseAdapterRecyclerView.dataList.count()
    }

    /**
     * Clear all old items (if available) and set new data into [RecyclerView]
     *
     * @param newItems List<M>?
     */
    @SuppressLint("NotifyDataSetChanged")
    fun updateNewItems(newItems: ArrayList<M>?) {
        this@BaseAdapterRecyclerView.dataList.clear()
        if (newItems != null) {
            this@BaseAdapterRecyclerView.dataList = newItems
        }
        this@BaseAdapterRecyclerView.notifyDataSetChanged()
    }

    /**
     * Clear all old items (if available) and set new data into [RecyclerView]
     * This method to clone data, keep origin data have not change
     *
     * @param newItems List<M>?
     */
    @SuppressLint("NotifyDataSetChanged")
    fun updateItemsClone(newItems: ArrayList<M>?) {
        this@BaseAdapterRecyclerView.dataList.clear()
        newItems?.forEach {
            val cloneItem = clone(it!!)
            this@BaseAdapterRecyclerView.dataList.add(cloneItem)
        }
        this@BaseAdapterRecyclerView.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: S, position: Int) {
        holder.bind(this@BaseAdapterRecyclerView.dataList[position], position)
    }

    abstract class ViewHolderViewModel<M>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Override this method to update data when holder inflated from adapter
         *
         * @param item M
         * @param position Int
         */
        open fun bind(item: M, position: Int) = Unit

    }

}