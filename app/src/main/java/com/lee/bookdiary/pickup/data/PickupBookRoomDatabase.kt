package com.lee.bookdiary.pickup.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lee.bookdiary.pickup.dao.PickDao

/**
 * room db 가져오는 역할
 */
@Database(entities = [(PickupBookEntity::class)], exportSchema = false, version = 1)
abstract class PickupBookRoomDatabase: RoomDatabase() {
    abstract fun timetableDao(): PickDao
    companion object {
        private lateinit var INSTANCE: PickupBookRoomDatabase
        internal  fun getDatabase(context: Context): PickupBookRoomDatabase{
            if (!this::INSTANCE.isInitialized){
                synchronized(PickupBookRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PickupBookRoomDatabase::class.java,
                        "timetable_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}