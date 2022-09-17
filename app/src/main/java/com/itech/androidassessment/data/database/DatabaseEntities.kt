package com.itech.androidassessment.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itech.androidassessment.data.model.User

@Entity
data class DatabaseUser constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    var thumbnail: String?
    )


/**
 * Map DatabaseVideos to domain entities
 */
fun List<DatabaseUser>.asDomainModel(): List<User> {
    return map {
        User(
            id = it.id,
            name = it.name,
            username = it.username,
            email = it.email,
            phone = it.phone,
            website = it.website,
            thumbnail = null
        )
    }
}