package kz.abcsoft.handledemo2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = "myLogs" ;

    private final int STATUS_NONE = 0 ;
    private final int STATUS_CONNECTING = 1 ;
    private final int STATUS_CONNECTED = 2 ;

    Handler h;

    TextView tvStatus ;
    ProgressBar pbConnect ;
    Button btnConnect ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView)findViewById(R.id.tvStatus) ;
        btnConnect = (Button)findViewById(R.id.btnConnect) ;
        pbConnect = (ProgressBar)findViewById(R.id.pbConnect) ;

        h = new Handler(){
           public void handleMessage(Message msg){
               switch(msg.what){
                   case STATUS_NONE:
                       btnConnect.setEnabled(true);
                       tvStatus.setText("Not connected");
                       break ;
                   case STATUS_CONNECTING:
                       btnConnect.setEnabled(false);
                       pbConnect.setVisibility(View.VISIBLE);
                       tvStatus.setText("Connecting");
                       break ;
                   case STATUS_CONNECTED:
                       pbConnect.setVisibility(View.GONE);
                       tvStatus.setText("Connected") ;
                       break ;
               }
           }
        } ;
        h.sendEmptyMessage(STATUS_NONE) ;
    }

    public void onClick(View v){
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {

                    h.sendEmptyMessage(STATUS_CONNECTING);
                    TimeUnit.SECONDS.sleep(2);


                    h.sendEmptyMessage(STATUS_CONNECTED);
                    TimeUnit.SECONDS.sleep(3);


                    h.sendEmptyMessage(STATUS_NONE);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
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
}
