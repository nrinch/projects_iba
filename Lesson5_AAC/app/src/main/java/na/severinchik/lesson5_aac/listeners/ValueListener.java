package na.severinchik.lesson5_aac.listeners;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import na.severinchik.lesson5_aac.R;
import na.severinchik.lesson5_aac.utils.SharedPreferencesKeys;
import na.severinchik.lesson5_aac.entity.Transaction;
import na.severinchik.lesson5_aac.repository.TransactionRepository;

public class ValueListener extends Worker {

    private static final int NOTIFICATION_ID = 0;
    private static final String NOTIFICATION_NAME = "appName";
    private static final String NOTIFICATION_CHANNEL = "appName_channel_01";
    private static final String NOTIFICATION_WORK = "appName_notification_work";
    private static final String TAG = "ValueListener";
    SharedPreferences sharedPreferences;
    Context context;

    public ValueListener(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        Log.d(TAG, "ValueListener: ");
        sharedPreferences = context.getSharedPreferences(SharedPreferencesKeys.SHARED_PREFERENCE_NAME, context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Result doWork() {
        int saved_value = sharedPreferences.getInt(SharedPreferencesKeys.SAVE_VALUE, 0);
        int alarm_value = sharedPreferences.getInt(SharedPreferencesKeys.ALARMVALUE, 0);
        Log.d(TAG, "doWork: saved_value" + saved_value);
        Log.d(TAG, "doWork: alarm_value" + alarm_value);

        TransactionRepository transactionRepository = new TransactionRepository(context);
        List<Transaction> list = transactionRepository.getListTransactions();
        float result = getSummary(list);
        Log.d(TAG, "doWork: result" + result);

        if (result > alarm_value) {
            Log.d(TAG, "doWork: < save");
            sendNotification(context.getString(R.string.alarm_balance));
        } else if (result < saved_value) {
            Log.d(TAG, "doWork: <alarm");
            sendNotification(context.getString(R.string.save_balance));
        }
        return Worker.Result.success();
    }


    private float getSummary(List<Transaction> transactions) {
        float result = 0;
        for (Transaction t : transactions) {
            if (t.type) {
                result += t.value;
            } else result -= t.value;
        }
        return result;
    }

    private void sendNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel
                    = new NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder builder = new NotificationCompat
                    .Builder(getApplicationContext(),NOTIFICATION_CHANNEL)
                    .setContentTitle("Hey!")
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.ic_launcher);


            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}
