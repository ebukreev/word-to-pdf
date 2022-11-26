package dev.bukreev

import java.io.File

object Converter {
    fun convertWordToPdf(wordFile: File): File {

        Runtime.getRuntime().exec("libreoffice --convert-to pdf ${wordFile.absolutePath}")

        return File(wordFile.absolutePath.replaceAfterLast('.', "pdf"))
    }
}