package com.vortex.android.vortexnote.feature_note.presentation.notes.components

import android.text.format.DateFormat
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.vortex.android.vortexnote.R
import com.vortex.android.vortexnote.feature_note.domain.model.Note

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    foldedCornerSize: Dp = 32.dp,
    onDeleteClick: () -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = modifier
    ) {

        //Полотно заметки
        Canvas(
            modifier = Modifier.matchParentSize(),
        ) {
            //Форма заметки с отрезанным уголком
            val clipPath = Path().apply {
                lineTo(size.width - foldedCornerSize.toPx(), 0f)
                lineTo(size.width, foldedCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                //Полотно заметки с закругленными краями
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                //Подогнутый уголок
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(note.color, Color.Black.toArgb(), 0.2f)
                    ),
                    topLeft = Offset(size.width - foldedCornerSize.toPx(), -10f),
                    size = Size(foldedCornerSize.toPx() + 10f,foldedCornerSize.toPx() + 10f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, end = 48.dp)
        ) {
            //Заголовок
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            //Содержимое заметки
            Text(
                text = note.content,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            val dateFormatter = remember {
                DateFormat.getDateFormat(context)
            }
            val timeFormatter = remember {
                DateFormat.getTimeFormat(context)
            }
            val date = "${dateFormatter.format(note.timestamp) } " +
                    timeFormatter.format(note.timestamp)
            //Дата
            Text(
                text = date,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        //Удалить
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.action_delete)
            )
        }
    }
}