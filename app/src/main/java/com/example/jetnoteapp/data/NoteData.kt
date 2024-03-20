package com.example.jetnoteapp.data

import com.example.jetnoteapp.model.Note

class NotesDataSource{
    fun loadNotes(): List<Note> {
        return listOf(
            Note(title = "A good day", description = "We went on a vacation")
        )
    }
}