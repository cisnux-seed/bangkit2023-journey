package dev.cisnux.myreadwritefile

import android.content.Context
import java.io.BufferedReader

internal object FileHelper {
    fun writeFile(fileModel: FileModel, context: Context) {
        // only create a new file if it is not exist
        context.openFileOutput(fileModel.fileName, Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

    fun readFromFile(context: Context, filename: String): FileModel {
        val fileModel = FileModel()
        fileModel.fileName = filename
        fileModel.data = context.openFileInput(filename).bufferedReader().use(BufferedReader::readText)
        return fileModel
    }
}