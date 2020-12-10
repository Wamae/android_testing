package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTasksRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class TasksViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var tasksRepository: FakeTasksRepository
    lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setupViewModel() {
        tasksRepository = FakeTasksRepository()
        val task1 = Task("Title1", "Description1")
        val task2 = Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)
        tasksRepository.addTasks(task1, task2, task3)

        tasksViewModel = TasksViewModel(tasksRepository)
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // Given a fresh ViewModel
        // When adding a new task
        tasksViewModel.addNewTask()

        // Then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // Given a fresh ViewModel
        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)
        // Then the "Add task" action is visible
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))
    }
}