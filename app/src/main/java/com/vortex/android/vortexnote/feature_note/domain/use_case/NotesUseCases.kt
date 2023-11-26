package com.vortex.android.vortexnote.feature_note.domain.use_case

//Вся бизнес логика должна быть расположена в этй папке, на ViewModel оставить только приведение данных в удобваримый вид
//Этот файл используется для объединения всех фич в один дата класс для более лаконичного кода
data class NotesUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote
)
