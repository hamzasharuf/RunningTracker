package com.hamzasharuf.runningtracker.utils.enums

enum class SortType(val typeName: String) {
    DATE("Date"),
    RUNNING_TIME("Running Time"),
    AVG_SPEED("Average Speed"),
    DISTANCE("Distance"),
    CALORIES_BURNED("Calories Burned");

    companion object {
        fun getItemAt(position: Int) = values().get(position)
    }
}