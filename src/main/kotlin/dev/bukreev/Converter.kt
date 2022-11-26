package dev.bukreev

import java.io.File

object Converter {
    fun convertWordToPdf(wordFile: File): File {

        val process =
            Runtime.getRuntime().exec("libreoffice --convert-to pdf ${wordFile.absolutePath}")

        if (process.waitFor() != 0) {
            throw IllegalStateException()
        }

        return File(wordFile.absolutePath.replaceAfterLast('.', "pdf"))
    }
}