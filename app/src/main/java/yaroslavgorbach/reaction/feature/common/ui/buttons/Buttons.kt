package yaroslavgorbach.reaction.feature.common.ui.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaroslavgorbach.reaction.feature.common.ui.theme.AppTypography.Companion.popinsSemiBold
import yaroslavgorbach.reaction.feature.common.ui.theme.EerieBlack
import yaroslavgorbach.reaction.feature.common.ui.theme.LightSilver
import yaroslavgorbach.reaction.feature.common.ui.theme.White

@Composable
fun PrimaryLargeButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = EerieBlack),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = popinsSemiBold,
            color = White
        )
    }
}

@Composable
fun PrimaryMediumButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = EerieBlack),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = popinsSemiBold,
            color = White
        )
    }
}

@Composable
fun PrimarySmallButton(modifier: Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = EerieBlack),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = popinsSemiBold,
            color = White
        )
    }
}

@Composable
fun SecondaryLargeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = LightSilver),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = popinsSemiBold,
            color = EerieBlack
        )
    }
}

@Composable
fun SecondaryMediumButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = LightSilver),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = popinsSemiBold,
            color = EerieBlack
        )
    }
}

@Composable
fun SecondarySmallButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = LightSilver),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = popinsSemiBold,
            color = EerieBlack
        )
    }
}

@Composable
fun OutlineMediumButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = EerieBlack),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = popinsSemiBold,
            color = EerieBlack
        )
    }
}

@Composable
fun OutlineLargeButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    OutlinedButton(
        border = BorderStroke(width = 1.dp, color = EerieBlack),
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = popinsSemiBold,
            color = EerieBlack
        )
    }
}