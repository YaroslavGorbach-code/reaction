package yaroslavgorbach.reaction.domain.settings

import yaroslavgorbach.reaction.data.settings.repo.RepoSettings
import javax.inject.Inject

class ChangeIsFirstAppOpenToFalseInteractor @Inject constructor(private val repoSettings: RepoSettings) {
    suspend operator fun invoke() {
        repoSettings.changeIsFirsAppOpen(false)
    }
}