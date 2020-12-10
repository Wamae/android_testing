package com.example.android.architecture.blueprints.todoapp.tasks

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.IDefaultTasksRepository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class TasksFragmentTest{
    private lateinit var repository: IDefaultTasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun clickTask_navigateToDetailFragmentOne() = runBlockingTest {
        repository.saveTask(Task("TITLE","DESCRIPTION",false,"1"))
        repository.saveTask(Task("TITLE_2","DESCRIPTION_2",false,"2"))
        // GIVEN - On the home screen
        val scenario  = launchFragmentInContainer<TasksFragment>(Bundle(), R.style.AppTheme)
        val navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!,navController)
        }
        // WHEN - Click on the first list item
        onView(withId(R.id.tasks_list))
                .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("TITLE")), click()))
        // THEN - Verify that we navigate to the first detail screen
        verify(navController).navigate(TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment("1"))
    }

    @Test
    fun clickAddTaskButton_navigateToAddEditFragment(){
        // GIVEN - on tasks fragment
        val scenario = launchFragmentInContainer <TasksFragment>(Bundle(),R.style.AppTheme)
        val navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!,navController)
        }
        // WHEN - The FAB is clicked
        onView(withId(R.id.add_task_fab)).perform(click())
        // THEN - Verify it navigates to the add screen
        verify(navController).navigate(TasksFragmentDirections
                .actionTasksFragmentToAddEditTaskFragment(null,
                getApplicationContext<Context>().getString(R.string.add_task)))
    }
}