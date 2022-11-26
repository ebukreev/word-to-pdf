package dev.bukreev.plugins

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

fun Application.configureRouting() {
    routing {
        post("/api/convert") {
            call.receiveMultipart().forEachPart { part ->
                if (part is PartData.FileItem) {
                    val fileName = part.originalFileName as String

                    val fileBytes = part.streamProvider().readBytes()

                    withContext(Dispatchers.IO) {
                        val file = File.createTempFile(fileName, UUID.randomUUID().toString())
                        file.writeBytes(fileBytes)
                    }
                }

                part.dispose()
            }
        }
    }
}
