package com.lee.bookdiary.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lee.bookdiary.R
import com.lee.bookdiary.data.BookInfo
import com.lee.bookdiary.databinding.SearchRecyclerBinding

class SearchAdapter (
    private val itemClick: (BookInfo, Int, ImageView) -> Unit
) : PagingDataAdapter<BookInfo, SearchAdapter.PagingViewHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = SearchRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it, position) }
    }

    inner class PagingViewHolder(private val binding: SearchRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookInfo: BookInfo, position: Int) = with(binding) {
            twTitle.text = bookInfo.title
            twAuthor.text = if (bookInfo.authors.isNotEmpty()) bookInfo.authors[0] else null
            twPublisher.text = bookInfo.publisher
            twPrice.text =  String.format(root.context.getString(R.string.won), bookInfo.price) //bookInfo.price.toString() + "원"
            twStatus.text = bookInfo.status

            Glide.with(root)
                .load(bookInfo.thumbnail)
                .placeholder(R.drawable.ic_baseline_menu_book_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(iwThumbnail)

            constraintlayoutBook.setOnClickListener { itemClick(bookInfo, position, iwThumbnail) }

            when (bookInfo.favorite) {
                true -> iwBookmark.setImageResource(R.drawable.ic_baseline_star_24)
                false -> iwBookmark.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
        }

    }
    companion object{
        private val diffCallback = object : DiffUtil.ItemCallback<BookInfo>() {
            override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
                return oldItem.title == newItem.title
            }
            override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}