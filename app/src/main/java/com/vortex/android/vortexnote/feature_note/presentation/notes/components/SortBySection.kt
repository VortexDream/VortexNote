package com.vortex.android.vortexnote.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vortex.android.vortexnote.R
import com.vortex.android.vortexnote.feature_note.domain.util.OrderType
import com.vortex.android.vortexnote.feature_note.domain.util.SortBy

@Composable
fun SortBySection(
    modifier: Modifier = Modifier,
    sortBy: SortBy = SortBy.Date(OrderType.Descending),
    onSortingChange: (SortBy) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_title),
                selected = sortBy is SortBy.Title,
                onSelect = { onSortingChange(SortBy.Title(sortBy.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_date),
                selected = sortBy is SortBy.Date,
                onSelect = { onSortingChange(SortBy.Date(sortBy.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_color),
                selected = sortBy is SortBy.Color,
                onSelect = { onSortingChange(SortBy.Color(sortBy.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.sort_ascending),
                selected = sortBy.orderType is OrderType.Ascending,
                onSelect = { onSortingChange(sortBy.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_descending),
                selected = sortBy.orderType is OrderType.Descending,
                onSelect = { onSortingChange(sortBy.copy(OrderType.Descending)) }
            )
        }
    }
}

@Preview
@Composable
fun SortBySectionPreview() {
    SortBySection(onSortingChange = {})
}