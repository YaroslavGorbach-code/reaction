package yaroslavgorbach.reaction.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.R

@Composable
fun ShowOnboardingDialog(onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.about_app)) },
        text = { Text(stringResource(id = R.string.onboarding_text)) },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp), horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), onClick = onDismiss
                ) {
                    Text(stringResource(id = R.string.start))
                }
            }
        })
}
