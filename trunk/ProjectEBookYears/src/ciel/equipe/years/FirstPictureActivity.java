package ciel.equipe.years;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


public class FirstPictureActivity extends Activity {
    private Handler mHandler;
    ImageView imageview;
    int alpha = 255;//透明值255 
    int b = 0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reletive);
        
        mHandler = new Handler();
        imageview = (ImageView) this.findViewById(R.id.ImageView02);
        //textview = (TextView) this.findViewById(R.id.TextView01);
       // Log.v("ColaBox", "ColaBox start ...");
        imageview.setAlpha(alpha);
        
        
        new Thread(new Runnable() {
            public void run() {
                initApp(); //初始化程序
                
                while (b < 2) {
                    try {
                        if (b == 0) {
                            Thread.sleep(1500);
                            b = 1;
                        } else {
                            Thread.sleep(50);
                        }
                        updateApp();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
       
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                imageview.setAlpha(alpha);
                imageview.invalidate();
            }
        };
    }
    public void updateApp() {
        alpha -= 5;
        if (alpha <= 0) {
            b = 2;
            Intent in = new Intent(this,CatalogActivity.class);
            
            startActivity(in);
            this.finish();
        }
        mHandler.sendMessage(mHandler.obtainMessage());
    }
    
    public void initApp(){
        
    }
    
    

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
     @Override
     public void onReceive(Context arg0, Intent arg1) {
      finish(); 
     }
    };

    @Override
    protected void onResume() {
     super.onResume();
     IntentFilter filter = new IntentFilter();
     filter.addAction("ExitApp");
     this.registerReceiver(broadcastReceiver, filter);
    }

    
}