package dev.bukreev

import dev.bukreev.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Test
    fun testApiConvert() = testApplication {
        application {
            configureRouting()
        }
        client.get("/api/convert").apply {
            assertEquals(HttpStatusCode.MethodNotAllowed, status)
        }
    }

    @Test
    fun testBadFile() = testApplication {
        application {
            configureRouting()
        }
        val pdfFile = withContext(Dispatchers.IO) {
            File.createTempFile("test", "pdf")
        }
        client.post("/api/convert") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("file", pdfFile.readBytes(), Headers.build {
                            append(HttpHeaders.ContentDisposition, "filename=\"${pdfFile.name}\"")
                        })
                    }
                )
            )
        }.apply {
            assertEquals(HttpStatusCode.Forbidden, status)
            assertEquals("Word file expected", bodyAsText())
        }
    }
}