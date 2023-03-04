package com.lee.bookdiary.search

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseFragment
import com.lee.bookdiary.databinding.SearchFragmentBinding
import com.lee.bookdiary.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment:BaseFragment<SearchFragmentBinding,SearchViewModel>(){
    override val layoutId = R.layout.search_fragment
    override val viewModel: SearchViewModel by viewModels()

    private lateinit var searchAdapter: SearchAdapter

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }
    override fun initViews() {
        super.initViews()
        setBookInfoRecyclerview()
        setKeyboardSearchEditText()
    }

    override fun initObserve() {
        super.initObserve()

        viewModel.deleteClick.observe(this){
            dataBinding.etSearch.setText("")
            dataBinding.recyclerVwBookInfo.isVisible = false
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).
            showSoftInput(dataBinding.etSearch,0)
        }

        viewModel.searchClick.observe(this){
            if (dataBinding.etSearch.text.isNotEmpty()) {
                viewModel.searchBook(dataBinding.etSearch.text.toString().trim())
                dataBinding.recyclerVwBookInfo.isVisible = true
                dataBinding.groupSearch.isVisible = false
            } else {
                dataBinding.recyclerVwBookInfo.isVisible = false
                dataBinding.groupSearch.isVisible = true
                dataBinding.groupError.isVisible = false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                searchAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.saveFavoritePosition.collectLatest {
                if (it == -1) return@collectLatest
                searchAdapter.snapshot()[it]?.favorite = true
                searchAdapter.notifyItemChanged(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.deleteFavoritePosition.collectLatest {
                if (it == -1) return@collectLatest
                searchAdapter.snapshot()[it]?.favorite = false
                searchAdapter.notifyItemChanged(it)
            }
        }

    }
    private fun setKeyboardSearchEditText(){
        dataBinding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                viewModel.onSearchCLick()
                (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).
                    hideSoftInputFromWindow(dataBinding.etSearch.windowToken,0)
            }
            true
        }
    }

    private fun setBookInfoRecyclerview(){
        dataBinding.recyclerVwBookInfo.layoutManager = LinearLayoutManager(requireContext())
        dataBinding.recyclerVwBookInfo.itemAnimator = null

        searchAdapter = SearchAdapter() { bookInfo, position, _ ->
            //Todo 자세히 보기 activity 추가하기
            /*val intent = Intent(requireContext(), ::class.java)
            intent.putExtra("bookInfo", bookInfo)
            intent.putExtra("position", position)
             intent 보내기*/
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchAdapter.loadStateFlow.collectLatest { loadStates ->
                // 검색 결과가 없을때
                if (loadStates.append.endOfPaginationReached) {
                    dataBinding.twNoResult.isVisible = searchAdapter.itemCount < 1
                } else {
                    dataBinding.twNoResult.isVisible = false
                }
                // 검색 중 오류가 발생했을때
                dataBinding.groupError.isVisible = loadStates.refresh is LoadState.Error

                // 검색 로딩 중일때
                dataBinding.progressSearch.isVisible = loadStates.refresh is LoadState.Loading
            }
        }
        dataBinding.recyclerVwBookInfo.adapter = searchAdapter
    }
}