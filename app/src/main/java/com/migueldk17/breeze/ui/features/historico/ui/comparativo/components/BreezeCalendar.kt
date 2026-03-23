package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.migueldk17.breeze.ui.utils.traduzData
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun BreezeCalendar(
    yearMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelect: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberCalendarState(
        startMonth = yearMonth,
        endMonth = yearMonth,
        firstVisibleMonth = yearMonth,
    )
    val mesTraduzido = traduzData(yearMonth.month.name.lowercase().replaceFirstChar { it.uppercase()})

    HorizontalCalendar(
        state = state,
        monthHeader = {
            Text(
                text = mesTraduzido +
                " de ${yearMonth.year}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        },
        dayContent = { day ->
            val date = day.date
            val isSelected = date == selectedDate

            if (day.position == DayPosition.MonthDate) {

                Box(
                    modifier = modifier
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else Color.Transparent
                        )
                        .clickable {
                            onDateSelect(date)
                        }
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date.dayOfMonth.toString(),
                        color = if (isSelected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurface

                    )
                }

            }
        }
    )
}