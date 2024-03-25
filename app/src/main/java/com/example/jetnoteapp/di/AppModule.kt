package com.example.jetnoteapp.di

import android.content.Context
import androidx.room.Room
import com.example.jetnoteapp.data.NoteDatabase
import com.example.jetnoteapp.data.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Se para especificar en qué componente de Hilt debe instalarse el módulo
@InstallIn(SingletonComponent::class) // Las dependencias proporcionadas estarán disponibles en toda la aplicación y compartirán una única instancia
class AppModule {

    @Singleton // se indica que dicha dependencia debe tener una instancia única durante toda la vida de la aplicación
    /**
     * @Provides es una anotación de Dagger que se usa en métodos dentro de un módulo para indicar que ese método es el proveedor de una dependencia. El objeto retornado por el método se utilizará para satisfacer las dependencias en otras partes de la aplicación donde esa clase o interfaz se requiera.
     */
    @Provides
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDao = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "notes_db"
        )
            .fallbackToDestructiveMigration()
            .build()

}