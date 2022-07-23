package com.android.developmentchallenge.util.binding

import android.widget.BaseAdapter
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.GridView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

@BindingAdapter("adapterRecyclerView")
fun setAdapterRecyclerView(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}