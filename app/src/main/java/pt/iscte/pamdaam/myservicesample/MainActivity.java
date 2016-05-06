package pt.iscte.pamdaam.myservicesample;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected EditText etPhrase;
    protected TextView tvChars, tvTimestamp;
    protected Button btnStartForeground;

    protected MyBindService myBindService;
    protected boolean mBounded;

    private static String TAG = "DAAM SERVICE";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Something received?!?!?");
            tvChars.setText("Number of characters is: " + intent.getIntExtra("LENGHT",0));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPhrase = (EditText) findViewById(R.id.etPhrase);
        tvChars = (TextView) findViewById(R.id.tvCountChars);
        tvTimestamp = (TextView) findViewById(R.id.tvTimestamp);
        btnStartForeground = (Button) findViewById(R.id.btnStartForeground);

        registerReceiver(receiver, new IntentFilter(MyIntentService.NOTIFICATION));
    }

    public void clickStartService(View view) {
        startService(new Intent(this, MyService.class));
    }

    public void clickStopService(View view) {
        stopService(new Intent(this, MyService.class));
    }

    public void clickStartIntentService(View view) {
        Intent myIntent = new Intent(this, MyIntentService.class);
        myIntent.setAction("COMPUTE.PHRASE.LENGHT");
        myIntent.putExtra("PHRASE", etPhrase.getText().toString());
        startService(myIntent);
    }

    public void clickStartForeground(View view) {
        Intent service = new Intent(this, MyForegroundService.class);
        if(!MyForegroundService.IS_SERVICE_RUNNING) {
            service.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            MyForegroundService.IS_SERVICE_RUNNING = true;
            btnStartForeground.setText("Stop Foreground");
        } else {
            service.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            MyForegroundService.IS_SERVICE_RUNNING=false;
            btnStartForeground.setText("Start Foreground");
        }
        startService(service);
    }

    public void clickShowTime(View view) {
        tvTimestamp.setText(myBindService.getTime());
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "Service is connected", Toast.LENGTH_SHORT).show();
            mBounded = true;
            MyBindService.MyBinder mLocalBinder = (MyBindService.MyBinder) service;
            myBindService = mLocalBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
            mBounded = false;
            myBindService = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent mIntent = new Intent(this, MyBindService.class);
        bindService(mIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBounded) {
            unbindService(serviceConnection);
            mBounded=false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(MyIntentService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
