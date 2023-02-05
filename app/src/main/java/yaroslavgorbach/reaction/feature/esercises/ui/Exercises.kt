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
            StatisticsBottomShitContent()
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

                // TODO: add real progress here
                LinearProgressIndicator(
                    progress = 0.5f,
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
private fun StatisticsBottomShitContent() {
    val graphsHeight = 220.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(text = "Statistics", style = AppTypography.h3, modifier = Modifier.padding(top = 20.dp, start = 20.dp))

        Text(text = "Mon, 19.03", style = AppTypography.subtitle5, modifier = Modifier.padding(start = 20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp, start = 20.dp, top = 23.dp)
        ) {
            Box(modifier = Modifier.weight(0.8f).height(graphsHeight)) {
                Column(modifier = Modifier.align(Center)) {
                    Column(
                        modifier = Modifier.background(color = StatisticCardsBg, shape = RoundedCornerShape(12))
                    ) {
                        Text(
                            text = "Correct",
                            modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                            style = AppTypography.subtitle4.copy(platformStyle = PlatformTextStyle(includeFontPadding = false))
                        )

                        Text(
                            text = "32% of correct answers",
                            modifier = Modifier.padding(start = 20.dp, top = 8.dp),
                            style = AppTypography.body4
                        )

                        // TODO: real data add
                        LinearProgressIndicator(
                            backgroundColor = White,
                            progress = 0.5f,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(top = 8.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(modifier = Modifier.background(color = StatisticCardsBg, shape = RoundedCornerShape(12))) {
                        Text(
                            text = "Average",
                            modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                            style = AppTypography.subtitle4.copy(platformStyle = PlatformTextStyle(includeFontPadding = false))
                        )

                        Text(
                            text = "Time to answer 0.4s",
                            modifier = Modifier.padding(start = 20.dp, top = 8.dp),
                            style = AppTypography.body4
                        )

                        // TODO: real data add
                        LinearProgressIndicator(
                            backgroundColor = White,
                            progress = 0.9f,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(top = 8.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(graphsHeight)
                    .background(color = StatisticCardsBg, shape = RoundedCornerShape(12))
            ) {
                Text(
                    text = "Weekly progress",
                    modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                    style = AppTypography.subtitle4.copy(platformStyle = PlatformTextStyle(includeFontPadding = false))
                )
                Text(
                    text = "01.12.22 - 0.7.12.22",
                    modifier = Modifier.padding(start = 20.dp),
                    style = AppTypography.body5
                )

                Row(modifier = Modifier.padding(top = 12.dp, start = 20.dp, end = 20.dp)) {
                    VerticalProgress(
                        progress = 0.7f, modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f), "Mon"
                    )

                    VerticalProgress(
                        progress = 0.8f, modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f), "Tue"
                    )

                    VerticalProgress(
                        progress = 0.9f, modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f), "Wed"
                    )

                    VerticalProgress(
                        progress = 0.1f, modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f), "Thu"
                    )

                    VerticalProgress(
                        progress = 0.4f, modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f), "Fri"
                    )

                    VerticalProgress(
                        progress = 0.2f, modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f), "Sat"
                    )

                    VerticalProgress(
                        progress = 0.6f, modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f), "Sun"
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@Composable
fun VerticalProgress(
    progress: Float, modifier: Modifier = Modifier, label: String
) {
    val mProgress = animateFloatAsState(targetValue = progress * 100 / 100)

    Column(modifier) {

        Column(
            modifier = Modifier
                .background(color = White, shape = RoundedCornerShape(12.dp))
                .width(14.dp)
                .height(138.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(if ((1 - mProgress.value) == 0f) 0.0001f else 1f - mProgress.value)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .weight(mProgress.value)
                    .fillMaxWidth()
                    .background(color = EerieBlack, shape = RoundedCornerShape(12.dp))
            )
        }
        Text(
            text = label,
            style = AppTypography.subtitle7,
            modifier = Modifier
                .padding(top = 3.dp)
                .align(CenterHorizontally),
            textAlign = TextAlign.Center
        )
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