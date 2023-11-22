package com.vortex.android.vortexnote.feature_note.domain.use_case

import com.vortex.android.vortexnote.feature_note.domain.model.Note
import com.vortex.android.vortexnote.feature_note.domain.repository.NoteRepository
import com.vortex.android.vortexnote.feature_note.domain.util.OrderType
import com.vortex.android.vortexnote.feature_note.domain.util.SortBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(
        sortBy: SortBy = SortBy.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(sortBy.orderType) {
                is OrderType.Ascending -> {
                    when (sortBy) {
                        is SortBy.Title ->  notes.sortedBy { it.title.lowercase() }
                        is SortBy.Date ->   notes.sortedBy { it.timestamp }
                        is SortBy.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (sortBy) {
                        is SortBy.Title ->  notes.sortedByDescending { it.title.lowercase() }
                        is SortBy.Date ->   notes.sortedByDescending { it.timestamp }
                        is SortBy.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}