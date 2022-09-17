package com.itech.androidassessment.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.itech.androidassessment.data.api.NetworkUser
import com.itech.androidassessment.data.api.NetworkUserContainer
import com.itech.androidassessment.data.api.UsersNetwork
import com.itech.androidassessment.data.api.asDatabaseModel
import com.itech.androidassessment.data.database.DatabaseUser
import com.itech.androidassessment.data.database.UsersDatabase
import com.itech.androidassessment.data.database.asDomainModel
import com.itech.androidassessment.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val usersDatabase: UsersDatabase) {
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val userlist: List<NetworkUser> = UsersNetwork.users.getUsers()
            val networkUserContainer = NetworkUserContainer(userlist)
            usersDatabase.userDao.insertAll(networkUserContainer.asDatabaseModel())
        }
    }

    val users: LiveData<List<DatabaseUser>> = usersDatabase.userDao.getUsers()

    suspend fun user(uId:String?): DatabaseUser? = usersDatabase.userDao.findByUserId(uId)

    suspend fun updateUser(user:DatabaseUser) = usersDatabase.userDao.updateUser(user)

    suspend fun addUser(user:DatabaseUser){
        usersDatabase.userDao.insert(user)
    }
}