package yaroslavgorbach.reaction.feature.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val White30 = Color(0x4DFFFFFF)
val Black30 = Color(0x4D000000)

// light
val BgMainLight = Color(0xFFFFFFFF)
val ColorPrimaryLight = Color(0xFF1E1E1E)
val ColorSecondaryLight = Color(0xFFD8D8D8)
val TextColorSecondaryLight = Color(0xFFCFCFCF)
val TextColorPrimaryLight = Color(0xFF1E1E1E)
val StatisticCardsBgLight = Color(0xFFE4E4E4)
val ExerciseCardBgLight = Color(0xFFE0E0E0)

// dark
val BgMainDark = Color(0xFF141414)
val ColorPrimaryDark = Color(0xFF313131)
val ColorSecondaryDark = Color(0xFF6D6D6D)
val TextColorSecondaryDark = Color(0xFFEBEBEB)
val TextColorPrimaryDark = Color(0xFFD8D8D8)
val StatisticCardsBgDark = ColorPrimaryDark
val ExerciseCardBgDark = Color(0xFF2B2B2B)


@Composable
fun Colors.textSecondary(): Color {
    return if (isSystemInDarkTheme()) {
        TextColorSecondaryDark
    } else {
        TextColorSecondaryLight
    }
}

@Composable
fun Colors.exerciseCardBg(): Color {
    return if (isSystemInDarkTheme()) {
        ExerciseCardBgDark
    } else {
        ExerciseCardBgLight
    }
}

@Composable
fun Colors.textPrimary(): Color {
    return if (isSystemInDarkTheme()) {
        TextColorPrimaryDark
    } else {
        TextColorPrimaryLight
    }
}

@Composable
fun Colors.statisticsCardBg(): Color {
    return if (isSystemInDarkTheme()) {
        StatisticCardsBgDark
    } else {
        StatisticCardsBgLight
    }
}

@Composable
fun Colors.overAllProgressColor(): Color {
    return if (isSystemInDarkTheme()) {
        White30
    } else {
        Black30
    }
}