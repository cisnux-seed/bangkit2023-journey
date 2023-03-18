package dev.cisnux.mynotesapprepository.repository

import android.app.Application
import androidx.lifecycle.LiveData
import dev.cisnux.mynotesapprepository.database.Note
import dev.cisnux.mynotesapprepository.database.NoteDao
import dev.cisnux.mynotesapprepository.database.NoteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private val mNotesDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAlNotes()

    fun insert(note: Note){
        executorService.execute {
            mNotesDao.insert(note)
        }
    }

    fun delete(note: Note){
        executorService.execute {
            mNotesDao.delete(note)
        }
    }

    fun update(note: Note){
        executorService.execute {
            mNotesDao.update(note)
        }
    }
}