package com.lee.bookdiary.pickup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lee.bookdiary.BR
import com.lee.bookdiary.base.BaseViewHolder
import com.lee.bookdiary.databinding.PickupRecyclerBinding
import com.lee.bookdiary.pickup.data.PickupBookEntity

class PickupAdapter(private val bookList: List<PickupBookEntity>): RecyclerView.Adapter<BaseViewHolder>() {
    private val items = mutableListOf<PickupBookEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val bind = PickupRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val book = bookList[position]
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: PickupRecyclerBinding): BaseViewHolder(binding.root){
        override fun onBindViewHolder(data: Any?) {
            binding.setVariable(BR.data,data)
        }
    }

}