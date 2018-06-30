package android.yoseph.cyberacademy.edu.et.journalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.yoseph.cyberacademy.edu.et.journalapp.database.AppDatabase;
import android.yoseph.cyberacademy.edu.et.journalapp.database.TaskEntry;


// COMPLETED (5) Make this class extend ViewModel
public class AddTaskViewModel extends ViewModel {

    // COMPLETED (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
    private LiveData<TaskEntry> task;

    // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(MainActivity.mCurrentSignInUser, taskId);
    }

    // COMPLETED (7) Create a getter for the task variable
    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
