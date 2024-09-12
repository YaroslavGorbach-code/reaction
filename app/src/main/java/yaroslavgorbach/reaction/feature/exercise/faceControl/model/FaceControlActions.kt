package yaroslavgorbach.reaction.feature.exercise.faceControl.model

import android.app.Activity
import yaroslavgorbach.reaction.data.exercise.faceControl.model.Face

sealed class FaceControlActions {
    class FaceClick(val face: Face) : FaceControlActions()
    object Back : FaceControlActions()
    object Repeat : FaceControlActions()
    class FinishExercise(val activity: Activity) : FaceControlActions()
}