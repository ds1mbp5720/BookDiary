package com.lee.bookdiary.pickup

import android.app.Application
import androidx.lifecycle.LiveData
import com.lee.bookdiary.base.BaseViewModel
import com.lee.bookdiary.pickup.data.PickupBookEntity
import com.lee.bookdiary.pickup.repository.PickupBookRepository

class PickupViewModel(application: Application): BaseViewModel(application) {
    private val repository = PickupBookRepository(application)
    private val allPickupBooks: LiveData<PickupBookEntity> = repository.allPickupBooks

    fun insertPickupBook(pickupBook:PickupBookEntity){
        repository.insertPickupBook(pickupBook)
    }
    fun DeletePickupBook(id:Int){
        repository.deletePickupBook(id)
    }
}