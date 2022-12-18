package com.task.models

import com.task.storage.User
import kotlinx.serialization.Serializable

@Serializable
/**
 * [User] class serializable representation
 */
data class UserSerializable(
    val name: String,
    val surname: String,
    val patronymic: String?,
    val phoneNumber: String,
    val email: String,
    val id: Int = 0
) {
    constructor(user: User) : this(
        user.name,
        user.surname,
        user.patronymic,
        user.phoneNumber,
        user.email,
        user.id.value
    )
}