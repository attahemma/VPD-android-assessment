package com.itech.androidassessment.presentation.fragments.profile

import android.app.Application
import androidx.lifecycle.*
import com.itech.androidassessment.data.database.DatabaseUser
import com.itech.androidassessment.data.database.getDatabase
import com.itech.androidassessment.data.repository.UserRepository
import com.itech.androidassessment.presentation.fragments.userview.UsersViewModel
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class ProfileViewModel(application: Application) : AndroidViewModel(application)  {
    //private var id by Delegates.notNull<Long>()
    private val usersRepository: UserRepository = UserRepository(getDatabase(application))
    //private var user:DatabaseUser by lazy()
    val _user = MutableLiveData<DatabaseUser?>()
    val user:LiveData<DatabaseUser?>
                get() = _user

    val _message = MutableLiveData<String?>()
    val message:LiveData<String?>
        get() = _message

    fun getUserDB(id:Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val userById = usersRepository.user(id.toString())
            _user.postValue(userById)
        }
    }

//    fun getUser(id:Long):LiveData<DatabaseUser?>{
//        getUserDB(id)
//        return user
//    }
    fun saveUser(user:DatabaseUser){
        CoroutineScope(Dispatchers.IO).launch {
            usersRepository.addUser(user)
            _message.postValue("Added Successfully")
        }
    }

    fun updateUser(user: DatabaseUser){
        CoroutineScope(Dispatchers.IO).launch {
            usersRepository.updateUser(user)
            _message.postValue("Profile Image Uploaded")
        }
    }

    fun <T> LiveData<T>.toMutableLiveData(): MutableLiveData<T> {
        val mediatorLiveData = MediatorLiveData<T>()
        mediatorLiveData.addSource(this) {
            mediatorLiveData.value = it
        }
        return mediatorLiveData
    }


    class Factory(var app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}