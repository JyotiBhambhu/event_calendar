package com.jyoti.eventcalenderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jyoti.eventcalender.eventcalendar.EventCalendarScreen
import com.jyoti.eventcalender.eventcalendar.model.CalendarUIState
import com.jyoti.eventcalender.eventcalendar.model.EventCalendarState
import com.jyoti.eventcalender.eventcalendar.model.EventUIConfig
import com.jyoti.eventcalender.eventcalendar.model.UIEvent
import com.jyoti.eventcalender.util.UIText
import com.jyoti.eventcalenderapp.ui.theme.EventCalenderAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: EventCalendarViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventCalenderAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    EventCalendarScreen(state) { calenderIntent ->
                        println(
                            calenderIntent
                        )
                        viewModel.onIntent(calenderIntent)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EventCalenderAppTheme {
        Greeting("Android")
    }
}