package com.lee.bookdiary.pickup.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lee.bookdiary.pickup.data.PickupBookEntity

@Dao
interface PickDao{
   @Insert
   fun insertPickupBook(book: PickupBookEntity)

   // 찾기
   @Query("SELECT * FROM book_table WHERE id = :id")
   fun findPickupBookTable(id: Int): PickupBookEntity

   //삭제
   @Query("DELETE FROM book_table WHERE id = :id")
   fun deletePickupBookTable(id: Int)

   // 전체 반환
   @Query("SELECT * FROM book_table")
   fun getAllPickupBookTable(): List<PickupBookEntity>
}