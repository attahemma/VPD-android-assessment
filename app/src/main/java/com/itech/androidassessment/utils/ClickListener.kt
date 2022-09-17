package com.itech.androidassessment.utils

import android.view.View
import com.itech.androidassessment.data.database.DatabaseUser
import com.itech.androidassessment.data.model.User

interface ClickListener {
    fun onItemClick(view: View, user: DatabaseUser)
}