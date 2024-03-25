package com.example.jetnoteapp.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnoteapp.data.NotesDataSource
import com.example.jetnoteapp.model.Note
import com.example.jetnoteapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    //private var noteList = mutableStateListOf<Note>()
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged().collect { listOfNotes ->
                if (listOfNotes.isNotEmpty()) {
                    Log.d("Empty", "Empty list")
                } else {
                    _noteList.value = listOfNotes
                }
            }
        }

        //noteList.addAll(NotesDataSource().loadNotes())
    }

    suspend fun addNote(note: Note) = viewModelScope.launch { repository.addNote(note) }

    suspend fun updateNote(note: Note) = viewModelScope.launch { repository.updateNote(note) }

    suspend fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }

    suspend fun deleteAllNotes() = viewModelScope.launch { repository.deleteAllNotes() }

}