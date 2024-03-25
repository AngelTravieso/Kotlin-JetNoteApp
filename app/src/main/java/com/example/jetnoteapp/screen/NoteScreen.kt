package com.example.jetnoteapp.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetnoteapp.R
import com.example.jetnoteapp.components.NoteButton
import com.example.jetnoteapp.components.NoteInputText
import com.example.jetnoteapp.data.NotesDataSource
import com.example.jetnoteapp.model.Note
import com.example.jetnoteapp.utils.formatDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
) {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    // get context
    val context = LocalContext.current

    Column(modifier = Modifier.padding(6.dp)) {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        }, actions = {
            Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "Icon")
        }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFDADFE3)))

        // Content
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NoteInputText(modifier = Modifier.padding(
                top = 9.dp,
                bottom = 8.dp,
            ), text = title, label = "Title", onTextChange = {
                if (it.all { char ->
                        char.isLetter() || char.isWhitespace()
                    }) title = it
            })

            NoteInputText(modifier = Modifier.padding(
                top = 9.dp,
                bottom = 8.dp,
            ), text = description, label = "Add a note", onTextChange = {
                if (it.all { char ->
                        char.isLetter() || char.isWhitespace()
                    }) description = it
            })

            NoteButton(text = "Save", onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    //save/add to list

                    onAddNote(Note(title = title, description = description))
                    title = ""
                    description = ""

                    // Show Toast
                    Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                }
            })

            Divider(modifier = Modifier.padding(10.dp))
            LazyColumn {
                items(notes) { note ->
                    NoteRow(note = note, onNoteClicked = {
                        onRemoveNote(note)
                    })
                }
            }

        }

    }
}

@SuppressLint("NewApi")
@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit,
) {
    Surface(
        modifier
            .padding(4.dp)
            .clip(
                RoundedCornerShape(topEndPercent = 33, bottomStartPercent = 33)
            )
            .fillMaxWidth(),
        color = Color(0xFDE1E0FA),
        shadowElevation = 6.dp,
    ) {
        Column(
            modifier
                .clickable { onNoteClicked(note) }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Text(text = note.description, style = MaterialTheme.typography.titleSmall)
            Text(
                text = formatDate(note.entryDate.time),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesScreenPreview() {
    NotesScreen(notes = NotesDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {})
}