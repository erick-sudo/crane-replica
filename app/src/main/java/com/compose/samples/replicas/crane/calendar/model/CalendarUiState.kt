package com.compose.samples.replicas.crane.calendar.model

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class CalendarUiState(
    val selectedStartDate: LocalDate? = null,
    val selectedEndDate: LocalDate? = null,
    val animateDirection: AnimationDirection? = null
) {

    val numberSelectedDays: Float
        get() {
            if(selectedStartDate == null) return 0f
            if(selectedEndDate == null) return 1f
            return ChronoUnit.DAYS.between(selectedStartDate, selectedEndDate.plusDays(1)).toFloat()
        }

    val hasSelectedDates: Boolean
        get() {
            return selectedStartDate != null || selectedEndDate != null
        }

    val selectedDatesFormatted: String
        get() {
            if(selectedStartDate == null) return ""
            var output = selectedStartDate.format(SHORT_DATE_FORMAT)
            if(selectedEndDate != null) {
                output += " - ${selectedEndDate.format(SHORT_DATE_FORMAT)}"
            }
            return output
        }

    fun hasSelectedPeriodOverlap(start: LocalDate, end: LocalDate): Boolean {
        if(!hasSelectedDates) return false
        if(selectedStartDate == null && selectedEndDate == null) return false
        if(selectedStartDate == start || selectedStartDate == end) return true
        if(selectedEndDate == null) {
            return !selectedStartDate!!.isBefore(start) && !selectedStartDate.isAfter(end)
        }
        return !end.isBefore(selectedStartDate) && !start.isAfter(selectedEndDate)
    }

    fun isDateInSelectedPeriod(date: LocalDate): Boolean {
        if (selectedStartDate == null) return false
        if (selectedStartDate == date) return true
        if (selectedEndDate == null) return false
        if(date.isBefore(selectedStartDate) || date.isAfter(selectedEndDate)) return false
        return true
    }

    fun getNumberSelectedDaysInWeek(currentWeekStartDate: LocalDate, month: YearMonth): Int {
        var countSelected = 0;
        var currentDate = currentWeekStartDate
        for(i in 0 until CalendarState.DAYS_IN_WEEK) {
            if(isDateInSelectedPeriod(currentDate) && currentDate.month == month.month) {
                countSelected++
            }
            currentDate = currentDate.plusDays(1)
        }
        return countSelected
    }

    companion object {
        private val SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd")
    }
}