package yaroslavgorbach.reaction.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
            text = "Welcome to our concentration and " + "attention improvement application!"
        )

        Text(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .weight(1f),
            textAlign = TextAlign.Justify,
            style = body1,
            text = "We're excited that you've chosen" +
                    " to take this step towards enhancing " +
                    "your mental abilities. Our" +
                    " application is designed to" +
                    " provide you with a range of" +
                    " exercises and techniques to " +
                    "improve your concentration and " +
                    "focus, and we're confident that" +
                    " with regular use, you'll see" +
                    " a significant improvement " +
                    "in your cognitive abilities."
        )

        PrimaryLargeButton(
            text = "Start",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 20.dp, end = 20.dp)
        ) {
            onStart()
        }
    }
}
