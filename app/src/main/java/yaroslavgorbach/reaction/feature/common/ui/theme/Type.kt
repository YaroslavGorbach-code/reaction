package yaroslavgorbach.reaction.feature.common.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.R


class AppTypography {
    companion object {
        val popinsBold = FontFamily(fonts = listOf(Font(resId = R.font.poppins_bold)))
        val popinsSemiBold = FontFamily(fonts = listOf(Font(resId = R.font.poppins_semi_bold)))
        val popinsMedium = FontFamily(fonts = listOf(Font(resId = R.font.poppins_medium)))
        val popinsRegular = FontFamily(fonts = listOf(Font(resId = R.font.poppins_regular)))

        val h1: TextStyle = TextStyle(fontFamily = popinsBold, fontSize = 38.sp)
        val h2: TextStyle = TextStyle(fontFamily = popinsSemiBold, fontSize = 30.sp)
        val h3: TextStyle = TextStyle(fontFamily = popinsSemiBold, fontSize = 22.sp)
        val h4: TextStyle = TextStyle(fontFamily = popinsSemiBold, fontSize = 18.sp)
        val h5: TextStyle = TextStyle(fontFamily = popinsSemiBold, fontSize = 16.sp)
        val h6: TextStyle = TextStyle(fontFamily = popinsBold, fontSize = 12.sp)
        val subtitle1: TextStyle = TextStyle(fontFamily = popinsMedium, fontSize = 16.sp)
        val subtitle2: TextStyle = TextStyle(fontFamily = popinsSemiBold, fontSize = 16.sp)
        val subtitle3: TextStyle = TextStyle(fontFamily = popinsMedium, fontSize = 14.sp)
        val subtitle4: TextStyle = TextStyle(fontFamily = popinsSemiBold, fontSize = 12.sp, letterSpacing = 0.02.sp)
        val subtitle5: TextStyle = TextStyle(fontFamily = popinsSemiBold, fontSize = 10.sp, letterSpacing = 0.02.sp)
        val subtitle6: TextStyle = TextStyle(fontFamily = popinsSemiBold, fontSize = 8.sp, letterSpacing = 0.02.sp)
        val subtitle7: TextStyle = TextStyle(fontFamily = popinsSemiBold, fontSize = 6.sp, letterSpacing = 0.02.sp)
        val body1: TextStyle = TextStyle(fontFamily = popinsRegular, fontSize = 16.sp)
        val body2: TextStyle = TextStyle(fontFamily = popinsRegular, fontSize = 14.sp)
        val body3: TextStyle = TextStyle(fontFamily = popinsRegular, fontSize = 12.sp)
        val body4: TextStyle = TextStyle(fontFamily = popinsRegular, fontSize = 10.sp)
        val body5: TextStyle = TextStyle(fontFamily = popinsRegular, fontSize = 8.sp)
        val body6: TextStyle = TextStyle(fontFamily = popinsRegular, fontSize = 6.sp)
    }
}