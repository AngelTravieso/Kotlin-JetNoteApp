package com.example.jetnoteapp.repository

import com.example.jetnoteapp.data.NoteDatabaseDao
import com.example.jetnoteapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabaseDao: NoteDatabaseDao) {
    suspend fun addNote(note: Note) = noteDatabaseDao.insert(note)
    suspend fun updateNote(note: Note) = noteDatabaseDao.update(note)
    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note)
    suspend fun deleteAllNotes() = noteDatabaseDao.deleteAll()

    /**
     *
     * suspend: Esta es una palabra clave de Kotlin que indica que la función es una coroutine y puede ser suspendida sin bloquear el hilo en el que se ejecuta. Las coroutines permiten escribir código asíncrono de una manera más secuencial y legible.
     *
     * Flow<List<Note>>: Flow es un tipo que representa un flujo de datos que puede emitir múltiples valores con el tiempo. En este caso, el flujo emite objetos List<Note>, que son listas de notas.
     *
     * flowOn(Dispatchers.IO): flowOn es un operador de Flow que cambia el contexto de la coroutine en el que se ejecutan las emisiones del flujo. Dispatchers.IO es un contexto optimizado para operaciones de entrada/salida, como operaciones de red o de base de datos. Al usar flowOn(Dispatchers.IO), se asegura que las operaciones de la base de datos no bloqueen el hilo principal y se ejecuten en un hilo adecuado para este tipo de tareas.
     *
     * conflate(): conflate es un operador de Flow que se utiliza para lidiar con situaciones donde el emisor es más rápido que el receptor. Cuando se usa conflate, si el emisor produce valores más rápido de lo que el receptor puede recogerlos, se descartan los valores intermedios, y solo se procesa el último valor emitido. Esto es útil para casos en los que solo te interesa el último valor actualizado y no necesitas procesar cada valor emitido por el flujo.
     *
     * getAllNotes() es proporcionar un flujo de listas de notas desde una base de datos, el cual se ejecuta en un contexto de I/O para no bloquear el hilo principal y usa conflate para asegurarse de que solo se procese el último valor emitido en caso de que haya una acumulación de valores no procesados. Este patrón es común en aplicaciones que utilizan arquitecturas reactivas y desean mostrar los datos más recientes, como la última lista de notas en una aplicación de notas.
     */
    suspend fun getAllNotes(): Flow<List<Note>> =
        noteDatabaseDao.getNotes().flowOn(Dispatchers.IO).conflate()
}