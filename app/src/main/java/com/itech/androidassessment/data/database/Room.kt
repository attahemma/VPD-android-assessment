
package com.itech.androidassessment.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.itech.androidassessment.data.model.User


@Dao
interface UserDao {
    @Query("select * from databaseuser")
    fun getUsers(): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( users: List<DatabaseUser>)

    @Update
    fun updateUser(currentUser: DatabaseUser)

    @Query("SELECT * FROM databaseuser WHERE id =:id LIMIT 1")
    fun findByUserId(id: String?): DatabaseUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: DatabaseUser)


}



@Database(entities = [DatabaseUser::class], version = 1)
abstract class UsersDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}

private lateinit var INSTANCE: UsersDatabase

fun getDatabase(context: Context): UsersDatabase {
    synchronized(UsersDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java,
                    "users").build()
        }
    }
    return INSTANCE
}
