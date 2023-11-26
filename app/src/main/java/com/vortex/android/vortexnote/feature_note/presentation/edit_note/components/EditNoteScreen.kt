package com.vortex.android.vortexnote.feature_note.presentation.edit_note.components

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vortex.android.vortexnote.R
import com.vortex.android.vortexnote.feature_note.domain.model.Note
import com.vortex.android.vortexnote.feature_note.presentation.edit_note.EditNoteEvent
import com.vortex.android.vortexnote.feature_note.presentation.edit_note.EditNoteViewModel
import com.vortex.android.vortexnote.feature_note.presentation.edit_note.UiEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EditNoteScreen(
    navController: NavController,
    //Необходимо передавать цвет фона отдельно,
    //так как выполнение блока init во ViewModel происходит позднее, чем инициализируется аниматор
    noteColor: Int,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)//Проверка на создание заметки
        )
    }
    
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = context.getString(event.messageInt)
                    )
                }
                is UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                viewModel.onEvent(EditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.action_save_note)
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(noteBackgroundAnimatable.value)
            .padding(16.dp)
            .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(16.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 6.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else {
                                    Color.Transparent
                                }.also { Log.d("test", "$colorInt") },
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(EditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintEditTextField(
                text = titleState.text,
                hintInt = titleState.hint,
                onValueChange = { text ->
                    viewModel.onEvent(EditNoteEvent.EnteredTitle(text))
                },
                onFocusChange = { focusState ->
                    viewModel.onEvent(EditNoteEvent.FocusEditTitle(focusState))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintEditTextField(
                text = contentState.text,
                hintInt = contentState.hint,
                onValueChange = { text ->
                    viewModel.onEvent(EditNoteEvent.EnteredContent(text))
                },
                onFocusChange = { focusState ->
                    viewModel.onEvent(EditNoteEvent.FocusEditContent(focusState))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}


