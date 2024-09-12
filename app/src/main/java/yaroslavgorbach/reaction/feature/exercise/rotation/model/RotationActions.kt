package yaroslavgorbach.reaction.feature.exercise.rotation.model

import android.app.Activity
import yaroslavgorbach.reaction.feature.exercise.common.model.YesNoChoseVariations

sealed class RotationActions {
    class OnChose(val chose: YesNoChoseVariations) : RotationActions()
    object Back : RotationActions()
    object Repeat : RotationActions()
    class FinishExercise(val activity: Activity) : RotationActions()
}