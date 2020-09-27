package com.hks.roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hks.roomdatabase.model.User

@Dao
// 데이타베이스 조작에 관련된 기능 DAO
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>
}