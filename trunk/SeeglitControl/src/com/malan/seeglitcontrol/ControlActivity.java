package com.malan.seeglitcontrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.malan.seeglitcontrol.network.HostBean;

public class ControlActivity extends Activity implements OnClickListener, Runnable{
	
    private static final Integer TCP_CONTROL_PORT = 5000;

	private Button 	 	btnConnect, btnDisconnect, btnUp, btnDown, btnLeft, btnRight;
    private ToggleButton tgLightOnOff;
    private EditText 	editPeerIp, editPeerPort;
    private TextView 	textPrompt;
    private String 	 	cmd;
	private Timer 		timerCheck;
	private Integer		isSocketConnected;
	private Socket 		mySocket;
	private OutputStream mySocketOutStream;
	private PrintWriter mySocketWriter;
	private TextView console;
	private Button btnClear;
	private Context context;
	private WifiManager wifimanage;
	private HostBean host;
//	private receiveCmd rcvCmd;
	
	
	private static ExecutorService SINGLE_TASK_EXECUTOR;  
    private static ExecutorService LIMITED_TASK_EXECUTOR;  
    private static ExecutorService FULL_TASK_EXECUTOR;  

	
	static {  
        SINGLE_TASK_EXECUTOR = (ExecutorService) Executors.newSingleThreadExecutor();  
        LIMITED_TASK_EXECUTOR = (ExecutorService) Executors.newFixedThreadPool(7);  
        FULL_TASK_EXECUTOR = (ExecutorService) Executors.newCachedThreadPool();  
    }; 
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        
        host = (HostBean) getIntent().getParcelableExtra(ActivityDiscovery.HOST_IP);
        context = this;
  
        
        wifimanage=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        
        isSocketConnected = 0;
        GetAllControl();
        SetControlFun();
        
        new Thread(this).start();

//        rcvCmd = new receiveCmd();
//        if(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB)
//        rcvCmd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        else
//        	rcvCmd.execute();
//      
    }
    
//    @Override
//    protected void onPause() {
//    	// TODO Auto-generated method stub
//    	super.onResume();
//    	
//    	if(rcvCmd != null){
//    		rcvCmd.cancel(true);
//    		rcvCmd = null;
//    	}
//    }

	
    private void GetAllControl()
    {
    	btnConnect    	= (Button) findViewById(R.id.connect);
    	btnDisconnect 	= (Button) findViewById(R.id.disconnect);
    	btnUp         	= (Button) findViewById(R.id.up);
    	btnDown       	= (Button) findViewById(R.id.down);
    	btnLeft       	= (Button) findViewById(R.id.left);
    	btnRight      	= (Button) findViewById(R.id.right);
    	editPeerIp		= (EditText)findViewById(R.id.peerip);
    	editPeerPort	= (EditText)findViewById(R.id.peerport);
    	textPrompt    	= (TextView)findViewById(R.id.prompt);
    	tgLightOnOff    = (ToggleButton)findViewById(R.id.lightOnOff);
    	console 		= (TextView)findViewById(R.id.console);
    	btnClear = (Button)findViewById(R.id.clear);
    }
    private void SetControlFun()
    {
//    	btnConnect.setOnClickListener(onBtnConnectClick);
    	btnDisconnect.setOnClickListener(onBtnDisconnectClick);
    	btnUp.setOnClickListener(this);
    	btnDown.setOnClickListener(this);
    	btnLeft.setOnClickListener(this);
    	btnRight.setOnClickListener(this);
    	btnClear.setOnClickListener(this);
    }

//    private Button.OnClickListener onBtnConnectClick = new Button.OnClickListener()
//    {
//		public void onClick(View v) 
//		{
//			SocketConnect();
//			
//		}
//    };
	
    private Button.OnClickListener onBtnDisconnectClick = new Button.OnClickListener()
    {
		public void onClick(View v) 
		{
    		SocketClose();
			textPrompt.setText("disconnect from server.");
		}
    };
    
//    private ToggleButton.OnCheckedChangeListener onBtnLightOnOffListener = new ToggleButton.OnCheckedChangeListener()
//    {
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) 
//		{
//			if (isChecked)
//			{
//				cmd = "4";
//				textPrompt.setText(cmd);
//			}
//			else
//			{
//				cmd = "4";
//				textPrompt.setText(cmd);
//			}
//			SocketWrite();
//		}
//    };
//    
    
    
    
    
    
    private Button.OnTouchListener OnBtnTouch = new Button.OnTouchListener()
    {
		public boolean onTouch(View v, MotionEvent event) 
		{
			switch (event.getAction()) 
			{
				case MotionEvent.ACTION_DOWN:
			   	{
					if (v.equals(btnUp))   		cmd = "1";
					if (v.equals(btnDown))   	cmd = "2";
					if (v.equals(btnLeft))   	cmd = "3";
					if (v.equals(btnRight))   	cmd = "5";
			   		break;
			   	}
			   	case MotionEvent.ACTION_UP:
			   	{
			   		cmd = " ";
			    	break;
			   	}
			   	default:
			   		break;
			}
			SocketWrite();
			return false;
		}
    };
	private InputStream mySocketInStream;
	private BufferedReader in;
	private String content = "";
    
	
    /////////////////////////////////////////////////////////////////////////////
 
	private void SocketConnect()
    {
    	try
    	{
    		SocketClose();	
//        	if (TextUtils.isEmpty(editPeerIp.getText()))
//    				return;  	
//        	if (TextUtils.isEmpty(editPeerPort.getText()))
//    				return;
    		String  ip  = host.ipAddress;
    		Integer	port= Integer.valueOf("5000");	
    		if (port == 0)	return;
    		
    		InetAddress addr = InetAddress.getByName(ip);
    		SocketAddress my_sockaddr = new InetSocketAddress(addr, port);
    		int timeoutMs = 500;
  
    		mySocket = new Socket();
    		mySocket.connect(my_sockaddr, timeoutMs);
    		
    		
    		//mySocket = new Socket(ip, port);
    		mySocketOutStream = mySocket.getOutputStream();
    		mySocketWriter = new PrintWriter(mySocketOutStream, true);
    		MySetTimer();
    		isSocketConnected = 1;
    		
    	}
    	catch (UnknownHostException e) 
    	{
    	    e.printStackTrace();
    	} catch (IOException e) 
    	{
    	    e.printStackTrace();
    	}
//		if (isSocketConnected == 1)
//			Toast.makeText(this, getString(R.string. connect_success), Toast.LENGTH_SHORT).show();
//		else
//			Toast.makeText(this, getString(R.string. connect_failed), Toast.LENGTH_SHORT).show();
    }
    private void SocketClose()
    {
    	try
    	{
    		if (isSocketConnected == 1)
    		{
    			MyKillTimer();
    			mySocketInStream.close();
    			mySocketOutStream.close();
    			mySocket.close();
    			textPrompt.setText("disconnect from server.");
    		}
    	}
    	catch (UnknownHostException e) 
    	{
    	    e.printStackTrace();
    	} catch (IOException e) 
    	{
    	    e.printStackTrace();
    	}
    	isSocketConnected = 0;
    }
    
    private void SocketWrite()
    {
    	Integer iFinished = 1;
    	try
    	{
    		
    		if (isSocketConnected == 0)
    			new Thread(this).start();;
    		
        	if ((isSocketConnected == 1) && (cmd != null) && (cmd.length() > 0))
        	{
        		iFinished = 0;
        		mySocketOutStream = mySocket.getOutputStream();
        		mySocketWriter = new PrintWriter(mySocketOutStream, false);
        		mySocketWriter.print(cmd);
        		mySocketWriter.print((char)0x00);
        		mySocketWriter.flush();
        		cmd = "";
        		iFinished = 1;
        	}
    	}
    	catch (UnknownHostException e) 
    	{
   	    e.printStackTrace();
    	} catch (IOException e) 
    	{
    		e.printStackTrace();
    	}
    	if (iFinished == 0)
    	{
    		//SocketClose();
    	}
    } 
      
  
    private void SocketRead(){
    	
    	try {  
    		
    		if (isSocketConnected == 0)
    			SocketConnect();
    		
    		mySocketInStream = mySocket.getInputStream();
    		in = new BufferedReader(new InputStreamReader(mySocketInStream, "UTF-8"));  
    		
            while (true) {  
                if (mySocket.isConnected()) {  
                    if (!mySocket.isInputShutdown() ) { 
                    	
                    	//System.out.println(in.read());
                    	content += String.valueOf((char) in.read()); 
                    	
                       mHandler.sendMessage(new Message());
 
                    }  
                }  
           }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
 
    	
    }
    
    
    public Handler mHandler = new Handler() {  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            
            if(content != null)
            console.setText(content);  
        }  
    };  
    
    /////////////////////////////////////////////////////////////////////////////
    private void MySetTimer()
    {
		timerCheck = new Timer();
		timerCheck.schedule( 
				new TimerTask() 
				{
					@Override
					public void run() 
					{
						Message msg = new Message();
						msg.what = 10;
						MyOnTimer.sendMessage(msg);
					}
				} , 0, 10);    		
    }
    
    private void MyKillTimer()
    {
    	timerCheck.cancel();
    }
	final Handler MyOnTimer = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			super.handleMessage(msg);
			if (msg.what==10) 
			{
				SocketWrite();
			} ;
		}
	};
	
	
//	private class receiveCmd extends AsyncTask<Void, Void, Void>{
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			
//			
//			SocketRead();
//			return null;
//			
//			
//			
//				
//			
//		}
//		
//		
//	}
	
	
	
	


	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.up:
			cmd = "1";
			break;

		case R.id.down:
			cmd = "2";
			break;

		case R.id.left:
			cmd = "7";
			break;

		case R.id.right:
			cmd = "8";
			break;

		default:
			break;
		}

		if (v.getId() != R.id.clear) {
			SocketWrite();
		} else {

			console.setText("");
			content = "";
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		SocketRead();
	}


}