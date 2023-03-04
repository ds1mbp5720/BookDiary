package com.lee.bookdiary.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lee.bookdiary.base.BaseViewModel
import com.lee.bookdiary.base.SingleLiveEvent
import com.lee.bookdiary.data.BookInfo
import com.lee.bookdiary.data.usecase.GetBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getBookListUserCase: GetBookListUseCase , application: Application): BaseViewModel(application) {
    private val _deleteClick = SingleLiveEvent<Unit>()
    val deleteClick: LiveData<Unit> get() =  _deleteClick
    private val _searchClick = SingleLiveEvent<Unit>()
    val searchClick: LiveData<Unit> get() =  _searchClick
    private val _saveFavoritePosition = MutableStateFlow(-1)
    val saveFavoritePosition: StateFlow<Int> = _saveFavoritePosition
    private val _deleteFavoritePosition = MutableStateFlow(-1)
    val deleteFavoritePosition: StateFlow<Int> = _deleteFavoritePosition
    private val searchBookFlow = MutableSharedFlow<String>()
    private var prevSearchString = ""

    private fun getBookList(searchString: String): Flow<PagingData<BookInfo>> {
        return getBookListUserCase(searchString)
    }

    fun searchBook(searchString: String) {
        if (prevSearchString == searchString)
            return
        prevSearchString = searchString
        viewModelScope.launch {
            searchBookFlow.emit(searchString)
        }
    }
    val pagingDataFlow = searchBookFlow
        .flatMapLatest {
            getBookList(it)
        }.cachedIn(viewModelScope)

    fun onDeleteCLick() {
        _deleteClick.call()
    }
    fun onSearchCLick() {
        _searchClick.call()
    }
    fun saveFavorite(position: Int) {
        _saveFavoritePosition.value = position
    }

    fun deleteFavorite(position: Int) {
        _deleteFavoritePosition.value = position
    }


}