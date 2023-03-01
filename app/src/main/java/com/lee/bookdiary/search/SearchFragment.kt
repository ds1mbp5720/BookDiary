package com.lee.bookdiary.search

import androidx.fragment.app.viewModels
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseFragment
import com.lee.bookdiary.databinding.SearchFragmentBinding

class SearchFragment:BaseFragment<SearchFragmentBinding,SearchViewModel>(){
    override val layoutId = R.layout.search_fragment
    override val viewModel: SearchViewModel by viewModels()

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }
    override fun initViews() {
        super.initViews()
    }

    override fun initObserve() {
        super.initObserve()
    }
}