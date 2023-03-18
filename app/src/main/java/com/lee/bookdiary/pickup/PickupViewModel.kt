package com.lee.bookdiary.pickup

import android.app.Application
import androidx.lifecycle.LiveData
import com.lee.bookdiary.base.BaseViewModel
import com.lee.bookdiary.base.SingleLiveEvent
import com.lee.bookdiary.pickup.data.PickupBookEntity
import com.lee.bookdiary.pickup.data.PickupBookRoomDatabase
import com.lee.bookdiary.pickup.repository.PickupBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PickupViewModel @Inject constructor(application: Application): BaseViewModel(application) {
    private val repository = PickupBookRepository(application)

    fun insertPickupBook(pickupBook:PickupBookEntity){
        repository.insertPickupBook(pickupBook)
    }
    fun deletePickupBook(id:Int){
        repository.deletePickupBook(id)
    }

    private val _myPickupList = SingleLiveEvent<List<PickupBookEntity>>()
    val myPickupList: LiveData<List<PickupBookEntity>> get() = _myPickupList

    fun getPickupBookList(db: PickupBookRoomDatabase) {
        CoroutineScope(Dispatchers.Main).launch {
            val bookList =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    db.pickDao().getAllPickupBookTable()
                }
            _myPickupList.value = bookList
        }
    }

    private val _deleteClick = SingleLiveEvent<PickupBookEntity>()
    val deleteClick: LiveData<PickupBookEntity> get() = _deleteClick
    fun onDeleteClick(pickupBook: PickupBookEntity) {
        _deleteClick.value = pickupBook
    }
}