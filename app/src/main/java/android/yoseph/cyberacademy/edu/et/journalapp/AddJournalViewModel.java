package android.yoseph.cyberacademy.edu.et.journalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.yoseph.cyberacademy.edu.et.journalapp.database.AppDatabase;
import android.yoseph.cyberacademy.edu.et.journalapp.database.JournalEntry;


// COMPLETED (5) Make this class extend ViewModel
public class AddJournalViewModel extends ViewModel {

    // COMPLETED (6) Add a journal member variable for the JournalEntry object wrapped in a LiveData
    private LiveData<JournalEntry> journal;

    // COMPLETED (8) Create a constructor where you call loadJournalById of the journalDao to initialize the journals variable
    // Note: The constructor should receive the database and the journalId
    public AddJournalViewModel(AppDatabase database, int journalId) {
        journal = database.journalDao().loadJournalById(MainActivity.mCurrentSignInUser, journalId);
    }

    // COMPLETED (7) Create a getter for the journal variable
    public LiveData<JournalEntry> getJournal() {
        return journal;
    }
}
