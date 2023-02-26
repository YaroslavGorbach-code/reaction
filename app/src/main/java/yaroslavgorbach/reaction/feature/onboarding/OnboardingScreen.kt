package yaroslavgorbach.reaction.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.feature.common.ui.buttons.PrimaryLargeButton
import yaroslavgorbach.reaction.feature.common.ui.theme.AppTypography.Companion.body1
import yaroslavgorbach.reaction.feature.common.ui.theme.AppTypography.Companion.h4

@Composable
fun ShowOnboarding(onStart: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_app_onboarding),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 42.dp)
                .size(155.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            modifier = Modifier
                .padding(top = 68.dp, start = 24.dp, end = 24.dp),
            textAlign = TextAlign.Center,
            style = h4,
            text = stringResource(id = R.string.onboarding_title_text)
        )

        Text(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .weight(1f),
            textAlign = TextAlign.Justify,
            style = body1,
            text = stringResource(id = R.string.onboarding_message_text)
        )

        PrimaryLargeButton(
            text = stringResource(id = R.string.exercise_result_start_btn_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 20.dp, end = 20.dp)
        ) {
            onStart()
        }
    }
}
