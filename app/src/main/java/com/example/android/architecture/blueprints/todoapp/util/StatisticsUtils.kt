package com.example.android.architecture.blueprints.todoapp.util

import com.example.android.architecture.blueprints.todoapp.data.Task

class StatisticsUtils {
    internal fun getActiveAndCompletedStats(tasks: List<Task>?): StatsResult {

        val totalTasks = tasks!!.size
        val numberOfActiveTasks = tasks.count { it.isActive }
        val activePercent = 100 * numberOfActiveTasks / totalTasks
        val completePercent = 100 * (totalTasks - numberOfActiveTasks) / totalTasks

        return StatsResult(
                activeTasksPercent = activePercent.toFloat(),
                completedTasksPercent = completePercent.toFloat()
        )

    }

    data class StatsResult(val activeTasksPercent: Float, val completedTasksPercent: Float)
}