package com.lee.bookdiary.pickup.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lee.bookdiary.BR
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseViewHolder
import com.lee.bookdiary.databinding.PickupRecyclerBinding
import com.lee.bookdiary.main.NavigationAdapter
import com.lee.bookdiary.pickup.PickupViewModel
import com.lee.bookdiary.pickup.data.PickupBookEntity
import java.util.*

class PickupAdapter: RecyclerView.Adapter<BaseViewHolder>(), ItemHelperCallBack.OnItemMoveListener {
    private val items = mutableListOf<PickupBookEntity>()
    private lateinit var viewModel: PickupViewModel
    fun setViewModel(pickupViewModel: PickupViewModel){
        viewModel = pickupViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val bind = PickupRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindViewHolder(items[position])
    }
    fun getItems() = items
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
    fun addItem(item: PickupBookEntity){
        items.add(item)
        notifyItemInserted(items.size)
    }
    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(private val binding: PickupRecyclerBinding): BaseViewHolder(binding.root){
        override fun onBindViewHolder(data: Any?) {
            if (data !is PickupBookEntity) return
            with(binding){
                twTitle.text = data.title
                twAuthor.text = if (data.authors!!.isNotEmpty()) data.authors[0] else null
                twPublisher.text = data.publisher
                twPrice.text =  String.format(root.context.getString(R.string.won), data.price)
                twStatus.text = data.status

                Glide.with(root)
                    .load(data.thumbnail)
                    .placeholder(R.drawable.ic_baseline_menu_book_24)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(iwThumbnail)
                iwBookmark.setOnClickListener {
                    viewModel.onDeleteClick(data)
                }
            }
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