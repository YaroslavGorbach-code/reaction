package yaroslavgorbach.reaction.feature.esercises.ui

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import yaroslavgorbach.reaction.feature.common.ui.pagerIndicator.PagerIndicator
import yaroslavgorbach.reaction.feature.common.ui.theme.AppTypography
import yaroslavgorbach.reaction.feature.common.ui.theme.EerieBlack
import yaroslavgorbach.reaction.feature.common.ui.theme.StatisticCardsBg
import yaroslavgorbach.reaction.feature.common.ui.theme.White
import yaroslavgorbach.reaction.feature.esercises.model.ExercisesViewState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StatisticsBottomShitContent(state: List<ExercisesViewState.StatisticState>) {
    val graphsHeight = 220.dp

    Log.v("dsaasdasd", state.toString())
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = "Statistics",
            style = AppTypography.h3,
            modifier = Modifier.padding(top = 20.dp, start = 20.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp, start = 20.dp, top = 23.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.8f)
                    .height(graphsHeight)
            ) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = StatisticCardsBg,
                                shape = RoundedCornerShape(12)
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Correct",
                            modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                            style = AppTypography.subtitle4.copy(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )

                        Text(
                            text = "${
                                state.getOrNull(pagerState.currentPage)?.correctPercent?.times(
                                    100f
                                )?.toInt()
                            }% of correct answers",
                            modifier = Modifier.padding(start = 20.dp, top = 8.dp),
                            style = AppTypography.body4
                        )

                        LinearProgressIndicator(
                            backgroundColor = White,
                            progress = state.getOrNull(pagerState.currentPage)?.correctPercent
                                ?: 0.0f,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(top = 8.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .background(
                                color = StatisticCardsBg, shape = RoundedCornerShape(12)
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Average",
                            modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                            style = AppTypography.subtitle4.copy(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )

                        Text(
                            text = "Time to answer\n${state.getOrNull(pagerState.currentPage)?.averageTimeToAnswerSeconds}s",
                            modifier = Modifier.padding(start = 20.dp, top = 8.dp, bottom = 16.dp),
                            style = AppTypography.body4
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .height(graphsHeight)
                        .background(color = StatisticCardsBg, shape = RoundedCornerShape(12))
                ) {

                    Text(
                        text = "Weekly wins",
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp),
                        style = AppTypography.subtitle4.copy(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )

                    Text(
                        text = state.getOrNull(pagerState.currentPage)?.period.toString(),
                        modifier = Modifier.padding(start = 20.dp),
                        style = AppTypography.body5
                    )

                    HorizontalPager(count = state.size, state = pagerState) {
                        Row(modifier = Modifier.padding(top = 12.dp, start = 20.dp, end = 20.dp)) {
                            VerticalProgress(
                                progress = state.getOrNull(it)?.bars?.find { it.dayOfWeek == 1 }?.percentOfWins
                                    ?: 0f,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .weight(1f),
                                "Sun"
                            )

                            VerticalProgress(
                                progress = state.getOrNull(it)?.bars?.find { it.dayOfWeek == 2 }?.percentOfWins
                                    ?: 0f,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .weight(1f),
                                "Mon"
                            )

                            VerticalProgress(
                                progress = state.getOrNull(it)?.bars?.find { it.dayOfWeek == 3 }?.percentOfWins
                                    ?: 0f,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .weight(1f),
                                "Tue"
                            )

                            VerticalProgress(
                                progress = state.getOrNull(it)?.bars?.find { it.dayOfWeek == 4 }?.percentOfWins
                                    ?: 0f,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .weight(1f),
                                "Wed"
                            )

                            VerticalProgress(
                                progress = state.getOrNull(it)?.bars?.find { it.dayOfWeek == 5 }?.percentOfWins
                                    ?: 0f,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .weight(1f),
                                "Thu"
                            )

                            VerticalProgress(
                                progress = state.getOrNull(it)?.bars?.find { it.dayOfWeek == 6 }?.percentOfWins
                                    ?: 0f,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .weight(1f),
                                "Fri"
                            )

                            VerticalProgress(
                                progress = state.getOrNull(it)?.bars?.find { it.dayOfWeek == 7 }?.percentOfWins
                                    ?: 0f,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .weight(1f),
                                "Sat"
                            )
                        }
                    }
                }

                PagerIndicator(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 9.dp),
                    pagerState = pagerState
                )
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
                    .weight(if ((mProgress.value) == 0f) 0.0001f else mProgress.value)
                    .fillMaxWidth()
                    .background(color = EerieBlack, shape = RoundedCornerShape(12.dp))
            )
        }
        Text(
            text = label,
            style = AppTypography.subtitle7,
            modifier = Modifier
                .padding(top = 3.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}
