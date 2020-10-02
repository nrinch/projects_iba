package na.severinchik.lesson5_aac.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.telephony.SmsMessage;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SmsListener extends BroadcastReceiver {

    private String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private Intent smsIntent;

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        if (intent.getAction() != null && intent.getAction().equalsIgnoreCase(SMS_RECEIVED)) {
            try {
                if (bundle != null) {
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    for (int i = 0; i < pdusObj.length; i++) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                        long time = currentMessage.getTimestampMillis();
                        String message = currentMessage.getDisplayMessageBody();

                        smsIntent = new Intent("read");
                        smsIntent.putExtra("message", message);
                        smsIntent.putExtra("sender", phoneNumber);
                        smsIntent.putExtra("time",time);


                        LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
                    }
                }
            } catch (Exception e) {
                Log.e("SMSReader", "Exception" + e.toString());
            }
        }
    }
}
