package yaroslavgorbach.reaction.feature.esercises.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.android.material.animation.AnimationUtils.lerp
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.common.ui.buttons.SecondaryMediumButton
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesUiMassage
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesViewState
import yaroslavgorbach.reaction.feature.esercises.presentation.ExercisesViewModel
import yaroslavgorbach.reaction.utill.findActivity
import kotlin.math.absoluteValue

@ExperimentalMaterialApi
@Composable
fun Exercises(
    openDescription: (exerciseName: ExerciseName) -> Unit,
) {
    Exercises(
        viewModel = hiltViewModel(),
        openDescription = openDescription,
    )
}

@ExperimentalMaterialApi
@Composable
internal fun Exercises(
    viewModel: ExercisesViewModel,
    openDescription: (exerciseName: ExerciseName) -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    Exercises(state = viewState.value, onMessageShown = viewModel::clearMessage, actioner = { action ->
        when (action) {
            is ExercisesActions.OpenDetails -> openDescription(action.exerciseName)
            else -> viewModel.submitAction(action)
        }
    })
}

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@Composable
internal fun Exercises(
    state: ExercisesViewState,
    actioner: (ExercisesActions) -> Unit,
    onMessageShown: (id: Long) -> Unit,
) {
    if (state.exerciseAvailabilityDialogState.isVisible) {
        ShowExerciseAvailableDialog(onDismiss = {
            actioner(ExercisesActions.HideExerciseIsNotAvailableDialog)
        }, onShowAd = {
            actioner(
                ExercisesActions.RequestShowRewordAd(
                    state.exerciseAvailabilityDialogState.exerciseName
                )
            )
            actioner(ExercisesActions.HideExerciseIsNotAvailableDialog)
        })
    }

    if (state.isOnboardingDialogVisible) {
        ShowOnboardingDialog(onDismiss = {
            actioner(ExercisesActions.HideOnboardingDialog)
        })
    }

    state.message?.let { uiMessage ->
        when (uiMessage.message) {
            is ExercisesUiMassage.ShowRewardAd -> {
                actioner(
                    ExercisesActions.ShowRewordAd(
                        activity = requireNotNull(LocalContext.current.findActivity()),
                        exerciseName = uiMessage.message.exerciseName
                    )
                )
            }
        }

        onMessageShown(uiMessage.id)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = state.exercises.size,
            contentPadding = PaddingValues(horizontal = 48.dp),
            modifier = Modifier.align(Center)
        ) { page ->
            Box(modifier = Modifier
                .wrapContentSize()
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                }) {
                Card(
                    Modifier
                        .height(500.dp)
                        .padding(top = 42.dp, bottom = 24.dp)
                    , shape = RoundedCornerShape(30.dp)
                ) {

                    val exercise = state.exercises[page]
                    ExerciseItem(exercise) { isAvailable ->
                        if (isAvailable) {
                            actioner(ExercisesActions.OpenDetails(exerciseName = exercise.name))
                        } else {
                            actioner(ExercisesActions.ShowExerciseIsNotAvailableDialog(exerciseName = exercise.name))
                        }
                    }
                }

                SecondaryMediumButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .width(163.dp), text = "Start Exercise"
                ) {

                }
            }
        }
    }
}

@Composable
private fun ShowExerciseAvailableDialog(onDismiss: () -> Unit, onShowAd: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.exercise_is_unavailable)) },
        text = { Text(stringResource(id = R.string.exercise_is_unavailable_explanation)) },
        buttons = {
            Column(
                modifier = Modifier.padding(all = 8.dp), verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(4.dp), onClick = onDismiss
                ) {
                    Text(stringResource(id = R.string.good))
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(4.dp), onClick = onShowAd
                ) {
                    Text(stringResource(id = R.string.open_by_ad))
                }
            }
        })
}

@Composable
private fun ShowOnboardingDialog(onDismiss: () -> Unit) {
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

@ExperimentalMaterialApi
@Composable
fun ExercisesPreview() {
    ReactionTheme {
        Exercises(state = ExercisesViewState(
            exercises = listOf(Exercise.Empty),
        ), onMessageShown = {}, actioner = {})
    }
}