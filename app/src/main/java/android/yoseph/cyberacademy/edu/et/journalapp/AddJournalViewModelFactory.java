package android.yoseph.cyberacademy.edu.et.journalapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.yoseph.cyberacademy.edu.et.journalapp.database.AppDatabase;


// COMPLETED (1) Make this class extend ViewModel ViewModelProvider.NewInstanceFactory
public class AddJournalViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // COMPLETED (2) Add two member variables. One for the database and one for the journalId
    private final AppDatabase mDb;
    private final int mJournalId;

    // COMPLETED (3) Initialize the member variables in the constructor with the parameters received
    public AddJournalViewModelFactory(AppDatabase database, int journalId) {
        mDb = database;
        mJournalId = journalId;
    }

    // COMPLETED (4) Uncomment the following method
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddJournalViewModel(mDb, mJournalId);
    }
}
