package yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import yaroslavgorbach.reaction.R
import yaroslavgorbach.reaction.feature.common.ui.theme.ReactionTheme
import yaroslavgorbach.reaction.data.exercise.numbersLetters.model.NumberAndLetterTaskVariant
import yaroslavgorbach.reaction.data.exercises.local.model.ExerciseName
import yaroslavgorbach.reaction.feature.exercise.common.mapper.ExerciseNameToInstructionResMapper
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseResult
import yaroslavgorbach.reaction.feature.exercise.common.ui.ExerciseTopBar
import yaroslavgorbach.reaction.feature.exercise.common.ui.YesNoBottomButtons
import yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.model.NumbersAndLettersActions
import yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.model.NumbersAndLettersViewState
import yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.presentation.NumbersAndLettersViewModel
import yaroslavgorbach.reaction.utill.TimerCountDown


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@InternalCoroutinesApi
@Composable
fun NumbersAndLettersExercise(
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    NumbersAndLettersExercise(
        viewModel = hiltViewModel(),
        onBackClick = onBackClick,
        onRepeatExerciseClick = onRepeatExerciseClick
    )
}

@InternalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun NumbersAndLettersExercise(
    viewModel: NumbersAndLettersViewModel,
    onBackClick: () -> Unit,
    onRepeatExerciseClick: () -> Unit,
) {
    val viewState = viewModel.state.collectAsState()

    NumbersAndLettersExercise(
        state = viewState.value,
    ) { action ->
        when (action) {
            is NumbersAndLettersActions.Back -> onBackClick()
            is NumbersAndLettersActions.Repeat -> onRepeatExerciseClick()
            else -> viewModel.submitAction(action)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun NumbersAndLettersExercise(
    state: NumbersAndLettersViewState,
    actioner: (NumbersAndLettersActions) -> Unit,
) {
    if (state.finishExerciseState.isFinished) {
        ExerciseResult(
            finishExerciseState = state.finishExerciseState,
            onBackClick = { actioner(NumbersAndLettersActions.Back) },
            onRepeatExercise = { actioner(NumbersAndLettersActions.Repeat) }
        )
    } else {
        Box(Modifier.fillMaxSize()) {
            when (state.timerState) {
                TimerCountDown.TimerState.Finish -> {
                    actioner(NumbersAndLettersActions.FinishExercise)
                }
                is TimerCountDown.TimerState.Tick -> {
                    ExerciseTopBar(
                        modifier = Modifier.align(Alignment.TopCenter),
                        instruction = stringResource(
                            id = ExerciseNameToInstructionResMapper.map(
                                exerciseName = ExerciseName.NUMBERS_AND_LETTERS
                            )
                        ),
                        timeProgress = state.timerState.timeUntilFinishedProgress,
                        onBack = { actioner(NumbersAndLettersActions.Back) }
                    )
                }
            }

            CroiseVariants(state)
            YesNoBottomButtons { variant -> actioner(NumbersAndLettersActions.Chose(variant)) }
        }
    }
}

@Composable
private fun BoxScope.CroiseVariants(state: NumbersAndLettersViewState) {
    Row(
        modifier = Modifier.Companion
            .align(Alignment.Center)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .weight(0.5f)
                .padding(end = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.is_even_number),
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            if (state.numberAndLetter.taskVariant == NumberAndLetterTaskVariant.EVEN_NUMBER) {
                Text(
                    text = state.numberAndLetter.toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        }

        Column(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .weight(0.5f)
                .padding(start = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.is_letter_vowel),
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            if (state.numberAndLetter.taskVariant == NumberAndLetterTaskVariant.VOWEL_LETTER) {
                Text(
                    text = state.numberAndLetter.toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }

        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ExercisesPreview() {
    ReactionTheme {
        NumbersAndLettersExercise(state = NumbersAndLettersViewState.Test, actioner = {})
    }
}