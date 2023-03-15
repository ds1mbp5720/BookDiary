package com.lee.bookdiary.pickup.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lee.bookdiary.BR
import com.lee.bookdiary.base.BaseViewHolder
import com.lee.bookdiary.databinding.PickupRecyclerBinding
import com.lee.bookdiary.pickup.data.PickupBookEntity
import java.util.*

class PickupAdapter(): RecyclerView.Adapter<BaseViewHolder>(), ItemHelperCallBack.OnItemMoveListener {
    private val items = mutableListOf<PickupBookEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val bind = PickupRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val book = items[position]
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun clearList(){
        items.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(item: List<PickupBookEntity>){
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: PickupRecyclerBinding): BaseViewHolder(binding.root){
        override fun onBindViewHolder(data: Any?) {
            binding.setVariable(BR.data,data)
        }
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: ViewHolder)
    }

    interface OnEndDragListener {
        fun onEndDrag()
    }
    private lateinit var dragStartListener: OnStartDragListener
    private lateinit var dragEndListener: OnEndDragListener

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onEndDrag() {
        dragEndListener.onEndDrag()
    }
    fun startDrag(listener: OnStartDragListener) {
        this.dragStartListener = listener
    }

    fun endDrag(listener: OnEndDragListener) {
        this.dragEndListener = listener
    }
}