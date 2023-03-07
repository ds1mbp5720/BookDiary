package com.lee.bookdiary.pickup.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.lee.bookdiary.pickup.dao.PickDao
import com.lee.bookdiary.pickup.data.PickupBookEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PickupBookRepository(application: Application) {
    private var coroutineScope = CoroutineScope(Dispatchers.IO)
    private var pickDao: PickDao
    val allPickupBooks: LiveData<PickupBookEntity>

    init {
        val db: com.lee.bookdiary.pickup.data.PickupBookRoomDatabase = com.lee.bookdiary.pickup.data.PickupBookRoomDatabase.getDatabase(application)
        pickDao = db.timetableDao()
        allPickupBooks = pickDao.getAllPickupBookTable()
    }
    fun insertPickupBook(pickupBook: PickupBookEntity){
        coroutineScope.launch(Dispatchers.IO){
            asyncInsert(pickupBook)
        }
    }
    private fun asyncInsert(pickupBook: PickupBookEntity){
        pickDao?.insertPickupBook(pickupBook)
    }

    fun deletePickupBook(id: Int){
        coroutineScope.launch(Dispatchers.IO){
            asyncDelete(id)
        }
    }
    private fun asyncDelete(id: Int){
        pickDao?.deletePickupBookTable(id)
    }
}