package com.vortex.android.vortexnote.feature_note.presentation.edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vortex.android.vortexnote.R
import com.vortex.android.vortexnote.feature_note.domain.model.InvalidNoteContentException
import com.vortex.android.vortexnote.feature_note.domain.model.InvalidNoteTitleException
import com.vortex.android.vortexnote.feature_note.domain.model.Note
import com.vortex.android.vortexnote.feature_note.domain.use_case.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    //Не объединяем все в один поток, так как текстовые поля обновляются после каждого
    //введенного символа, что будет вызывать Recompose всего интерфейса, дел

    //State и mutableStateOf - это аналоги StateFlow и MutableStateFlow для Compose
    private val _noteColor = mutableIntStateOf(Note.noteColors[2].toArgb())//VioletSky
    val noteColor: State<Int> = _noteColor

    //Не рекомендуется передавать контекст во вьюмодель, так как это приводит к утечкам памяти,
    //Вместо getString используем просто ссылки на строки, которые уже переведем в текст во View слое
    private val _noteTitle = mutableStateOf(EditNoteState(
        hint = R.string.edit_note_title_hint
    ))
    val noteTitle: State<EditNoteState> = _noteTitle

    private val _noteContent = mutableStateOf(EditNoteState(
        hint = R.string.edit_note_content_hint
    ))
    val noteContent: State<EditNoteState> = _noteContent

    //Используем sharedFlow, так как UiEvent класс отвечает за одиночные события,
    // для которых не нужно сохранение стейта
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null //Если передать null в качестве id, то создаст новую заметку

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    notesUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteColor.intValue = note.color
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        Log.d("ViewModel", "${noteColor.value}")
                    }
                }
            }
        }
    }

    fun onEvent(event: EditNoteEvent) {
        when(event) {
            is EditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.text
                )
            }
            is EditNoteEvent.FocusEditTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is EditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.text
                )
            }
            is EditNoteEvent.FocusEditContent -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is EditNoteEvent.ChangeColor -> {
                _noteColor.intValue = event.color
            }
            is EditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        notesUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteTitleException) { //Проверка на корректность введенной заметки
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                               messageInt = R.string.invalid_note_title
                            )
                        )
                    } catch (e: InvalidNoteContentException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                messageInt = R.string.invalid_note_content
                            )
                        )
                    }
                }
            }
        }
    }
}