package com.task

import com.task.models.UserSerializable
import com.task.storage.DatabaseFactory
import com.task.storage.User
import com.task.storage.Users
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class UserRouteTest {
    val testUser = UserSerializable("ivan", "ivanov", null, "+7777777777", "123@mail.ru")
    var userId = 0

    @BeforeTest
    fun prepareData() {
        DatabaseFactory.init()
        transaction {
            Users.deleteAll()
        }
    }

    @Test
    fun userFind() = testApplication {
        transaction {
            userId = User.new {
                name = testUser.name
                surname = testUser.surname
                patronymic = testUser.patronymic
                phoneNumber = testUser.phoneNumber
                email = testUser.email
            }.id.value
        }
        val response = client.get("/user/$userId")
        assertEquals(Json.encodeToString(testUser), response.bodyAsText())
        assertEquals(response.status, HttpStatusCode.OK)
    }

    @Test
    fun userAdd() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/user") {
            contentType(ContentType.Application.Json)
            setBody(testUser)
        }
        assertEquals(response.status, HttpStatusCode.OK)
        assert(response.bodyAsText().startsWith("User added with id="))
    }


}