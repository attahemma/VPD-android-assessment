package com.itech.androidassessment.data.api

import com.itech.androidassessment.data.database.DatabaseUser
import com.itech.androidassessment.data.model.Address
import com.itech.androidassessment.data.model.Company
import com.itech.androidassessment.data.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val users: List<NetworkUser>)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class NetworkUser(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    @Json(ignore = true)
    val address: Address? = null,
    val phone: String,
    val website: String,
    @Json(ignore = true)
    val company: Company? = null
)

/**
 * Convert Network results to database objects
 */
fun NetworkUserContainer.asDomainModel(): List<User> {
    return users.map {
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


/**
 * Convert Network results to database objects
 */
fun NetworkUserContainer.asDatabaseModel(): List<DatabaseUser> {
    return users.map {
        DatabaseUser(
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