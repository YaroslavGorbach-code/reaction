package yaroslavgorbach.reaction.feature.exercise.common.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.feature.common.ui.buttons.PrimaryLargeButton
import yaroslavgorbach.reaction.feature.exercise.common.model.YesNoChoseVariations

@Composable
fun YesNoBottomButtons(modifier: Modifier = Modifier, onClick: (variant: YesNoChoseVariations) -> Unit) {
    Row(modifier = modifier) {
        PrimaryLargeButton(
            text = stringResource(id = R.string.no),
            onClick = { onClick(YesNoChoseVariations.NO) },
            modifier = Modifier.weight(0.5f)
        )

        PrimaryLargeButton(
            modifier = Modifier.weight(0.5f)
                .padding(start = 16.dp),
            text = stringResource(id = R.string.yes),
            onClick = { onClick(YesNoChoseVariations.YES) }
        )
    }
}