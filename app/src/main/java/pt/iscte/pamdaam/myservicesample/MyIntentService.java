package pt.iscte.pamdaam.myservicesample;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    private static String TAG = "DAAM SERVICE";

    public static final String ACTION_FOO = "COMPUTE.PHRASE.LENGHT";
    public static final String NOTIFICATION = "pt.iscte.pamdaam.mainactivity";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Intent Service has been called!");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String phrase = intent.getStringExtra("PHRASE");
                handleActionFoo(phrase);
            }
        }
    }

    private void handleActionFoo(String phrase) {
        int lenght = 0;

        if(phrase!=null) lenght = phrase.length();
        else lenght = 0;

        Log.d(TAG, "Lenght = " + lenght);

        Intent resultIntent = new Intent(NOTIFICATION);
        resultIntent.putExtra("LENGHT", lenght);
        sendBroadcast(resultIntent);
    }

}
