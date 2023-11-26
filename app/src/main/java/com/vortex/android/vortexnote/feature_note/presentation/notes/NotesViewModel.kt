package com.vortex.android.vortexnote.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vortex.android.vortexnote.feature_note.domain.model.Note
import com.vortex.android.vortexnote.feature_note.domain.use_case.NotesUseCases
import com.vortex.android.vortexnote.feature_note.domain.util.OrderType
import com.vortex.android.vortexnote.feature_note.domain.util.SortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var lastDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(SortBy.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.Order -> {
                if (state.value.sortBy::class == event.sortBy::class &&
                    state.value.sortBy.orderType == event.sortBy.orderType
                ) {
                    return
                } else notesUseCases.getNotes(event.sortBy)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCases.deleteNote(event.note)
                    lastDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCases.addNote(lastDeletedNote ?: return@launch)
                    lastDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(sortBy: SortBy) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotes(sortBy)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    sortBy = sortBy
                )
            }
            .launchIn(viewModelScope)
    }
}