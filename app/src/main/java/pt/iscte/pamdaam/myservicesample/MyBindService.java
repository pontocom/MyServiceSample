package pt.iscte.pamdaam.myservicesample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBindService extends Service {
    private static String TAG = "DAAM SERVICE";

    private IBinder myBinder = new MyBinder();

    public MyBindService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myBinder;
    }

    public class MyBinder extends Binder {
        MyBindService getService() {
            return MyBindService.this;
        }
    }

    public String getTime(){
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());



    }
}
