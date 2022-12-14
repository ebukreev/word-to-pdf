package dev.bukreev.plugins

import dev.bukreev.Converter
import dev.bukreev.utils.isValidWordDocument
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files

fun Application.configureRouting() {
    routing {
        post("/api/convert") {
            call.receiveMultipart().forEachPart { part ->
                if (part is PartData.FileItem) {
                    val fileName = part.originalFileName as String

                    if (fileName.isValidWordDocument()) {
                        val fileBytes = part.streamProvider().readBytes()

                        withContext(Dispatchers.IO) {
                            val wordFile = File(fileName)
                            wordFile.writeBytes(fileBytes)

                            try {
                                val pdfFile = Converter.convertWordToPdf(wordFile)

                                call.response.header("Content-Disposition",
                                    "attachment; filename=\"${pdfFile.name}\"")
                                call.respondFile(pdfFile)

                                Files.deleteIfExists(wordFile.toPath())
                                Files.deleteIfExists(pdfFile.toPath())
                            } catch (e: IllegalStateException) {
                                call.respondText("Can't convert uploaded file, try it later",
                                    status = HttpStatusCode.InternalServerError)
                            }
                        }
                    } else {
                        call.respondText("Word file expected", status = HttpStatusCode.Forbidden)
                    }
                }

                part.dispose()
            }
        }
    }
}
