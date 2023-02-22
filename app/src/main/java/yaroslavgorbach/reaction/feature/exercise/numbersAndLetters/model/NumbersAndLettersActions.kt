package yaroslavgorbach.reaction.feature.exercise.numbersAndLetters.model

import android.app.Activity
import yaroslavgorbach.reaction.feature.exercise.common.model.YesNoChoseVariations

sealed class NumbersAndLettersActions {
    class Chose(val chose: YesNoChoseVariations) : NumbersAndLettersActions()
    object Back : NumbersAndLettersActions()
    object Repeat : NumbersAndLettersActions()
    class FinishExercise(val activity: Activity) : NumbersAndLettersActions()
}