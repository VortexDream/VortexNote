package com.vortex.android.vortexnote.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vortex.android.vortexnote.ui.theme.BlueSky
import com.vortex.android.vortexnote.ui.theme.LightYellow
import com.vortex.android.vortexnote.ui.theme.RedOrange
import com.vortex.android.vortexnote.ui.theme.RedPink
import com.vortex.android.vortexnote.ui.theme.VioletSky

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightYellow, VioletSky, BlueSky, RedPink)
    }
}
