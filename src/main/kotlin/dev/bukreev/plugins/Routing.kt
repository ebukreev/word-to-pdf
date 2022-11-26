package dev.bukreev.plugins

import dev.bukreev.Converter
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

fun Application.configureRouting() {
    routing {
        post("/api/convert") {
            call.receiveMultipart().forEachPart { part ->
                if (part is PartData.FileItem) {
                    val fileName = part.originalFileName as String

                    val fileBytes = part.streamProvider().readBytes()

                    withContext(Dispatchers.IO) {
                        val file = File(fileName)
                        file.writeBytes(fileBytes)

                        val pdfFile = Converter.convertWordToPdf(file)
                    }
                }

                part.dispose()
            }
        }
    }
}
