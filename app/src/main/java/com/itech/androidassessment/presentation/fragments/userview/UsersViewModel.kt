package com.itech.androidassessment.presentation.fragments.userview

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.itech.androidassessment.data.database.getDatabase
import com.itech.androidassessment.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.IOException

class UsersViewModel(application: Application) : AndroidViewModel(application)  {

    private val usersRepository: UserRepository = UserRepository(getDatabase(application))

    val userlist = usersRepository.users

    private var _eventNetworkError = MutableLiveData<Boolean>(false)


    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        //if (!application.applicationContext.getSharedPreferences("users", Context.MODE_PRIVATE).getBoolean("loaded", false)){
            refreshDataFromRepository()
//            val sharedPref = application.applicationContext.getSharedPreferences("users", Context.MODE_PRIVATE)
//            val editor = sharedPref.edit()
//            editor.putBoolean("loaded",true)
//            editor.apply()
        //}
    }

    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                usersRepository.refreshVideos()

            } catch (networkError: IOException){
                if(userlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true

            }
        }
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(var app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UsersViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
