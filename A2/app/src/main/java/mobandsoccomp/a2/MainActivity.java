package mobandsoccomp.a2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button button2;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        addListenerOnButton();
    }

    public class ResponseReceiver extends BroadcastReceiver {
     //   public static final String ACTION_RESP = "com.mamlambo.intent.action.MESSAGE_PROCESSED";
        @Override
        public void onReceive(Context context, Intent intent) {

            String text = intent.getStringExtra("abcdef");

            Log.d("Tag", "Message recieved!");


            textView.setText(text);
        }

    }

    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);

       /* imageButton.setOnTouchListener(new View.OnTouchListener() {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    vibrator.vibrate(3000);
                    return true;
                }
                else {
                    vibrator.cancel();
                }
                return false;
            }
        });*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(getBaseContext(), vib_service.class));


            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(), vib_service.class));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ResponseReceiver receiver;
@Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        receiver = new ResponseReceiver();
        // filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction("mobandsoccomp.a2.ACTION_Magnetometer");
        registerReceiver(receiver, filter);

    }
    @Override
    public void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

}
