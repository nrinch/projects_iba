package na.severinchik.lesson5_aac;

import android.app.Application;
import android.os.AsyncTask;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import na.severinchik.lesson5_aac.dao.TransactioDao;
import na.severinchik.lesson5_aac.entity.Transaction;

@androidx.room.Database(entities = {Transaction.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    //public TransactioDao transactioDao;
    public abstract TransactioDao transactioDao();
    private static Database INSTANCE;

    public static Database getINSTANCE(Application application) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(application.getBaseContext(),
                            Database.class,
                            "database")
//                            .addCallback(mDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    ;


    private static RoomDatabase.Callback mDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(SupportSQLiteDatabase database) {
            super.onOpen(database);
            new AsyncInitDatabase(INSTANCE).execute();

        }
    };

    static class AsyncInitDatabase extends AsyncTask<Void,Void,Void>{
        private TransactioDao transactioDao;

        AsyncInitDatabase(Database database){
            transactioDao = database.transactioDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            transactioDao.insert(new Transaction(
                    "Fuel",-1,false,3.12f
            ));
            return null;
        }
    }

}
