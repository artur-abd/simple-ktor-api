package com.task.storage

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column


/**
 * Representation of user's info in database
 *
 * @param id id of user's info in database
 * @property [name] user's name
 * @property [surname] user's name
 * @property [patronymic] user's patronymic, can be null if user haven't got
 * @property [phoneNumber] user's phone number, must be unique
 * @property [email] user's email, must be unique
 */
class User(id: EntityID<Int>) : IntEntity(id) {

    var name by Users.name
    var surname by Users.surname

    var patronymic by Users.patronymic

    var phoneNumber by Users.phoneNumber

    var email by Users.email

    companion object : IntEntityClass<User>(Users)
}


object Users : IntIdTable() {
    val name: Column<String> = varchar("name", 255)
    val surname: Column<String> = varchar("surname", 255)
    val patronymic: Column<String?> = varchar("patronymic", 255).nullable()
    val phoneNumber: Column<String> = varchar("phone_number", 50).uniqueIndex()
    val email: Column<String> = varchar("email", 320).uniqueIndex()
}