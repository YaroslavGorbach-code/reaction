package yaroslavgorbach.reaction.feature.esercises.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.android.material.animation.AnimationUtils.lerp
import kotlinx.coroutines.launch
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.data.exercises.local.model.Exercise
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.common.ui.buttons.SecondaryMediumButton
import yaroslavgorbach.reaction.feature.common.ui.theme.*
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesActions
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesUiMassage
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesViewState
import yaroslavgorbach.reaction.feature.esercises.presentation.ExercisesViewModel
import yaroslavgorbach.reaction.feature.onboarding.ShowOnboardingDialog
import yaroslavgorbach.reaction.utill.findActivity
import kotlin.math.absoluteValue

@ExperimentalMaterialApi
@Composable
fun Exercises(
    openExerciseStartTimer: (exerciseName: ExerciseName) -> Unit,
) {
    Exercises(
        viewModel = hiltViewModel(),
        openExerciseStartTimer = openExerciseStartTimer,
    )
}

@ExperimentalMaterialApi
@Composable
internal fun Exercises(
    viewModel: ExercisesViewModel,
    openExerciseStartTimer: (exerciseName: ExerciseName) -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    Exercises(state = viewState.value, onMessageShown = viewModel::clearMessage, actioner = { action ->
        when (action) {
            is ExercisesActions.OpenExerciseStartTimer -> openExerciseStartTimer(action.exerciseName)
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

    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

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
            is ExercisesUiMassage.ShowStatistics -> {
                coroutineScope.launch {
                    modalSheetState.show()
                }
            }
        }

        onMessageShown(uiMessage.id)
    }

    ModalBottomSheetLayout(sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            StatisticsBottomShitContent(state.statisticsState)
        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {

                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "Your\n" + "Exercises",
                        style = AppTypography.h3,
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .weight(1f),
                        color = EerieBlack
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_main_screen_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 40.dp)
                            .size(75.dp)
                            .align(Bottom)
                    )
                }

                LinearProgressIndicator(
                    progress = state.overAllProgress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 21.dp, start = 20.dp, end = 135.dp)
                )

                Text(
                    text = "Overall progress",
                    modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                    style = AppTypography.body3
                )

                HorizontalPager(
                    modifier = Modifier.padding(top = 42.dp),
                    count = state.exercises.size,
                    contentPadding = PaddingValues(horizontal = 48.dp)
                ) { page ->
                    val exercise = state.exercises[page]

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
                                .padding(top = 42.dp, bottom = 24.dp),
                            shape = RoundedCornerShape(30.dp)
                        ) {
                            ExerciseItem(exercise, showStatisticsPrompt = {
                                actioner(ExercisesActions.ShowStatisticsPrompt(exercise.name))
                            }, showUnavailablePrompt = {
                                actioner(ExercisesActions.ShowExerciseIsNotAvailableDialog(exercise.name))
                            })
                        }

                        if (exercise.isAvailable) {
                            SecondaryMediumButton(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .width(163.dp), text = "Start Exercise"
                            ) {
                                actioner(ExercisesActions.OpenExerciseStartTimer(exerciseName = exercise.name))
                            }
                        }
                    }
                }
            }
        })
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


@ExperimentalMaterialApi
@Composable
fun ExercisesPreview() {
    ReactionTheme {
        Exercises(state = ExercisesViewState(
            exercises = listOf(Exercise.Empty),
        ), onMessageShown = {}, actioner = {})
    }
}