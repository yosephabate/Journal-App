package android.yoseph.cyberacademy.edu.et.journalapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.yoseph.cyberacademy.edu.et.journalapp.database.AppDatabase;
import android.yoseph.cyberacademy.edu.et.journalapp.database.JournalEntry;


import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<JournalEntry>> journals;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the journals from the DataBase");
        journals = database.journalDao().loadAllJournals(MainActivity.mCurrentSignInUser);
    }

    public LiveData<List<JournalEntry>> getJournals() {
        return journals;
    }
}
