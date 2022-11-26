package dev.bukreev

import java.io.File

object Converter {
    fun convertWordToPdf(wordFile: File): File {

        val process =
            Runtime.getRuntime().exec("libreoffice --convert-to pdf ${wordFile.absolutePath}")

        process.waitFor()

        return File(wordFile.absolutePath.replaceAfterLast('.', "pdf"))
    }
}