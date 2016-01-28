package mobandsoccomp.a2;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kushal on 25/01/2016.
 */
public class vib_service extends Service implements SensorEventListener{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String PARAM_IN_MSG = "Metalpower: ";
    public static final String PARAM_OUT_MSG = "omsg";
    private SensorManager mSensorManager;
    private Sensor mMagnetometer;
    private Vibrator vibrator;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        double metalpower = Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));

        String text = "Metalpower (in microTesla): " + Double.toString(metalpower);
        Log.d("TAG", text);
        if (metalpower > 70)
        {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);}
        Intent broadcastIntent = new Intent();
      //  broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.setAction("mobandsoccomp.a2.ACTION_Magnetometer");
        broadcastIntent.putExtra("abcdef",text);
        sendBroadcast(broadcastIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service starting", Toast.LENGTH_SHORT).show();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_FASTEST);
        return START_STICKY;
    }

   /* final static String MY_ACTION = "MY_ACTION";
    public class MyThread extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            for(int i=0; i<10; i++){
                try {
                    Thread.sleep(5000);
                    Intent intent = new Intent();
                    intent.setAction(MY_ACTION);
                    sendBroadcast(intent);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            stopSelf();
        }

    } */

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        mSensorManager.unregisterListener(this);
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
