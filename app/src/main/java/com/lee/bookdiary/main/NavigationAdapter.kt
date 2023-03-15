package com.lee.bookdiary.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lee.bookdiary.BR
import com.lee.bookdiary.base.BaseViewHolder
import com.lee.bookdiary.databinding.NavigationRecyclerBinding

class NavigationAdapter(private val callBack: (Navigation) -> (Unit)): RecyclerView.Adapter<BaseViewHolder>() {
    enum class Navigation(
        val title: String,
        val isLineVisible: Boolean = false
    ){
        SEARCH("검색", true
        ),
        PICKUP("찜목록",true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val bind = NavigationRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindViewHolder(Navigation.values()[position])
    }

    override fun getItemCount() = Navigation.values().size

    inner class ViewHolder(private val binding: NavigationRecyclerBinding): BaseViewHolder(binding.root){
        override fun onBindViewHolder(data: Any?) {
            if (data !is Navigation) return
            binding.setVariable(BR.data,data)
            binding.root.isVisible = true
            binding.root.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
            binding.root.setOnClickListener {
                callBack.invoke(data)
            }
        }
    }

}