/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

package com.malan.seeglitcontrol;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.malan.seeglitcontrol.library.UserFunctions;
import com.malan.seeglitcontrol.network.HardwareAddress;
import com.malan.seeglitcontrol.network.HostBean;
import com.malan.seeglitcontrol.network.NetInfo;
import com.malan.seeglitcontrol.spreadsheet.activity.SpreadsheetMapperActivity;
import com.malan.seeglitcontrol.utils.Help;
import com.malan.seeglitcontrol.utils.Prefs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

final public class ActivityDiscovery extends ActivityNet implements OnItemClickListener {

    private final String TAG = "ActivityDiscovery";
    
    public final static long VIBRATE = (long) 250;
    public final static int SCAN_PORT_RESULT = 1;
    public static final int MENU_SCAN_SINGLE = 0;
    private static final int REQUEST_CODE_NAT = 5;
    
    public static final int MENU_OPTIONS = 1;
    public static final int MENU_HELP = 2;
    private static final int MENU_EXPORT = 3;
    
	public static final String HOST_IP = "host_ip";
	
    private static LayoutInflater mInflater;
    private int currentNetwork = 0;
    private long network_ip = 0;
    private long network_start = 0;
    private long network_end = 0;
    
    
    private List<HostBean> hosts = null;
    private HostsAdapter adapter;
    private Button btn_discover;
    private AbstractDiscovery mDiscoveryTask = null;

	private ListView listView;

	private UserFunctions userFunctions;

	private static ActivityDiscovery mDiscover;

    // private SlidingDrawer mDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.discovery);
        
        
        
        userFunctions = new UserFunctions();
        try {
			if(!userFunctions.isUserLoggedIn(this)){

				// user is not logged in show login screen
				Intent login = new Intent(getApplicationContext(), LoginActivity.class);
				login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(login);
				// Closing dashboard screen
				finish();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        copyAssets();
        mInflater = LayoutInflater.from(context);
        
        
        mDiscover = ActivityDiscovery.this;

        // Discover
        btn_discover = (Button) findViewById(R.id.btn_discover);
        btn_discover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startDiscovering();
            }
        });


        // Hosts list
        adapter = new HostsAdapter(context);
        listView = (ListView) findViewById(R.id.output);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(findViewById(R.id.list_empty));
        
        
    }

    
    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        for(String filename : files) {
        	if(filename.endsWith("db")){
            InputStream in = null;
            OutputStream out = null;
            try {
              in = assetManager.open(filename);
              
              File dir = new File(HardwareAddress.PATH);
              if(!dir.exists())
            	  dir.mkdirs();
              
              File outFile = new File(HardwareAddress.PATH, filename);
              out = new FileOutputStream(outFile);
              copyFile(in, out);
              in.close();
              in = null;
              out.flush();
              out.close();
              out = null;
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }       
        	}
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }


    protected void setInfo() {
        // Info
        ((TextView) findViewById(R.id.info_ip)).setText(info_ip_str);
        ((TextView) findViewById(R.id.info_in)).setText(info_in_str);
        ((TextView) findViewById(R.id.info_mo)).setText(info_mo_str);

        // Scan button state
        if (mDiscoveryTask != null) {
            setButton(btn_discover, R.drawable.cancel, false);
            btn_discover.setText(R.string.btn_discover_cancel);
            btn_discover.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cancelTasks();
                }
            });
        }

        if (currentNetwork != net.hashCode()) {
            Log.i(TAG, "Network info has changed");
            currentNetwork = net.hashCode();

            // Cancel running tasks
            cancelTasks();
        } else {
            return;
        }

        // Get ip information
        network_ip = NetInfo.getUnsignedLongFromIp(net.ip);
        if (prefs.getBoolean(Prefs.KEY_IP_CUSTOM, Prefs.DEFAULT_IP_CUSTOM)) {
            // Custom IP
            network_start = NetInfo.getUnsignedLongFromIp(prefs.getString(Prefs.KEY_IP_START,
                    Prefs.DEFAULT_IP_START));
            network_end = NetInfo.getUnsignedLongFromIp(prefs.getString(Prefs.KEY_IP_END,
                    Prefs.DEFAULT_IP_END));
        } else {
            // Custom CIDR
            if (prefs.getBoolean(Prefs.KEY_CIDR_CUSTOM, Prefs.DEFAULT_CIDR_CUSTOM)) {
                net.cidr = Integer.parseInt(prefs.getString(Prefs.KEY_CIDR, Prefs.DEFAULT_CIDR));
            }
            // Detected IP
            int shift = (32 - net.cidr);
            if (net.cidr < 31) {
                network_start = (network_ip >> shift << shift) + 1;
                network_end = (network_start | ((1 << shift) - 1)) - 1;
            } else {
                network_start = (network_ip >> shift << shift);
                network_end = (network_start | ((1 << shift) - 1));
            }
            // Reset ip start-end (is it really convenient ?)
            Editor edit = prefs.edit();
            edit.putString(Prefs.KEY_IP_START, NetInfo.getIpFromLongUnsigned(network_start));
            edit.putString(Prefs.KEY_IP_END, NetInfo.getIpFromLongUnsigned(network_end));
            edit.commit();
        }
    }

    protected void setButtons(boolean disable) {
        if (disable) {
            setButtonOff(btn_discover, R.drawable.disabled);
        } else {
            setButtonOn(btn_discover, R.drawable.discover);
        }
    }

    protected void cancelTasks() {
        if (mDiscoveryTask != null) {
            mDiscoveryTask.cancel(true);
            mDiscoveryTask = null;
        }
    }

    // Listen for Activity results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCAN_PORT_RESULT:
                if (resultCode == RESULT_OK) {
                    // Get scanned ports
                    if (data != null && data.hasExtra(HostBean.EXTRA)) {
                        HostBean host = data.getParcelableExtra(HostBean.EXTRA);
                        if (host != null) {
                            hosts.set(host.position, host);
                        }
                    }
                }
                break;
                
            case REQUEST_CODE_NAT:
            	if(resultCode == RESULT_OK){
            		
            	}
            	
            	break;
            default:
                break;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final HostBean host = hosts.get(position);
        
        if(host.deviceType == HostBean.TYPE_CAMERA){
			Intent intent = new Intent();
			intent.setClass(this, ControlActivity.class);
			intent.putExtra(HOST_IP, host);
			startActivity(intent);
        }else{
        	Toast.makeText(ActivityDiscovery.this, getString(R.string. discover_no_camera), Toast.LENGTH_SHORT).show();
        }

    }

    static class ViewHolder {
        TextView host;
        TextView mac;
        TextView vendor;
        ImageView logo;
        ImageView arrow;
    }

    // Custom ArrayAdapter
    private class HostsAdapter extends ArrayAdapter<Void> {
        public HostsAdapter(Context context) {
            super(context, R.layout.list_host, R.id.list);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_host, null);
                holder = new ViewHolder();
                holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
                holder.host = (TextView) convertView.findViewById(R.id.list);
                holder.mac = (TextView) convertView.findViewById(R.id.mac);
                holder.vendor = (TextView) convertView.findViewById(R.id.vendor);
                holder.logo = (ImageView) convertView.findViewById(R.id.logo);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final HostBean host = hosts.get(position);
            holder.arrow.setVisibility(View.GONE);
            if (host.deviceType == HostBean.TYPE_GATEWAY) {
                holder.logo.setImageResource(R.drawable.router);
            } 
            else if (host.deviceType == HostBean.TYPE_CAMERA) {
                holder.logo.setImageResource(R.drawable.camera);
                holder.arrow.setVisibility(View.VISIBLE);
            }else if (host.isAlive == 1 || !host.hardwareAddress.equals(NetInfo.NOMAC)) {
                holder.logo.setImageResource(R.drawable.computer);
            } else {
                holder.logo.setImageResource(R.drawable.computer_down);
            }
            if (host.hostname != null && !host.hostname.equals(host.ipAddress)) {
                holder.host.setText(host.hostname + " (" + host.ipAddress + ")");
            } else {
                holder.host.setText(host.ipAddress);
            }
            if (!host.hardwareAddress.equals(NetInfo.NOMAC)) {
                holder.mac.setText(host.hardwareAddress);
                if(host.nicVendor != null){
                    holder.vendor.setText(host.nicVendor);
                } else {
                    holder.vendor.setText(R.string.info_unknown);
                }
                holder.mac.setVisibility(View.VISIBLE);
                holder.vendor.setVisibility(View.VISIBLE);
            } else {
                holder.mac.setVisibility(View.GONE);
                holder.vendor.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    /**
     * Discover hosts
     */
    private void startDiscovering() {
        int method = 0;
        try {
            method = Integer.parseInt(prefs.getString(Prefs.KEY_METHOD_DISCOVER,
                    Prefs.DEFAULT_METHOD_DISCOVER));
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
        }
      
//        method = 1;
       
        switch (method) {
            case 1:
            	initList();
            	Intent intent = new Intent();
            	intent.setClass(this, SpreadsheetMapperActivity.class);
            	startActivityForResult(intent, REQUEST_CODE_NAT);
                break;
            case 2:
                // Root
                break;    
            case 0:
            default:
                mDiscoveryTask = new DefaultDiscovery(ActivityDiscovery.this);
                mDiscoveryTask.setNetwork(network_ip, network_start, network_end);
                mDiscoveryTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                
                makeToast(R.string.discover_start);
                setProgressBarVisibility(true);
                setProgressBarIndeterminateVisibility(true);
                initList();
        }
        
 
        btn_discover.setText(R.string.btn_discover_cancel);
        setButton(btn_discover, R.drawable.cancel, false);
        btn_discover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelTasks();
            }
        });
      
    }
    
    public static ActivityDiscovery get(){
    	
    	return mDiscover;
    }

    public void stopDiscovering() {
        Log.e(TAG, "stopDiscovering()");
        mDiscoveryTask = null;
        setButtonOn(btn_discover, R.drawable.discover);
        btn_discover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startDiscovering();
            }
        });
        setProgressBarVisibility(false);
        setProgressBarIndeterminateVisibility(false);
        btn_discover.setText(R.string.btn_discover);
    }

    private void initList() {
        // setSelectedHosts(false);
        adapter.clear();
        hosts = new ArrayList<HostBean>();
    }

    public void addHost(HostBean host) {
        host.position = hosts.size();
        hosts.add(host);
        adapter.add(null);
    }

//    public static void scanSingle(final Context ctxt, String ip) {
//        // Alert dialog
//        View v = LayoutInflater.from(ctxt).inflate(R.layout.scan_single, null);
//        final EditText txt = (EditText) v.findViewById(R.id.ip);
//        if (ip != null) {
//            txt.setText(ip);
//        }
//        AlertDialog.Builder dialogIp = new AlertDialog.Builder(ctxt);
//        dialogIp.setTitle(R.string.scan_single_title);
//        dialogIp.setView(v);
//        dialogIp.setPositiveButton(R.string.btn_scan, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dlg, int sumthin) {
//                // start scanportactivity
//                Intent intent = new Intent(ctxt, ActivityPortscan.class);
//                intent.putExtra(HostBean.EXTRA_HOST, txt.getText().toString());
//                try {
//                    intent.putExtra(HostBean.EXTRA_HOSTNAME, (InetAddress.getByName(txt.getText()
//                            .toString()).getHostName()));
//                } catch (UnknownHostException e) {
//                    intent.putExtra(HostBean.EXTRA_HOSTNAME, txt.getText().toString());
//                }
//                ctxt.startActivity(intent);
//            }
//        });
//        dialogIp.setNegativeButton(R.string.btn_discover_cancel, null);
//        dialogIp.show();
//    }

//    private void export() {
//        final Export e = new Export(ctxt, hosts);
//        final String file = e.getFileName();
//
//        View v = mInflater.inflate(R.layout.dialog_edittext, null);
//        final EditText txt = (EditText) v.findViewById(R.id.edittext);
//        txt.setText(file);
//
//        AlertDialog.Builder getFileName = new AlertDialog.Builder(this);
//        getFileName.setTitle(R.string.export_choose);
//        getFileName.setView(v);
//        getFileName.setPositiveButton(R.string.export_save, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dlg, int sumthin) {
//                final String fileEdit = txt.getText().toString();
//                if (e.fileExists(fileEdit)) {
//                    AlertDialog.Builder fileExists = new AlertDialog.Builder(ActivityDiscovery.this);
//                    fileExists.setTitle(R.string.export_exists_title);
//                    fileExists.setMessage(R.string.export_exists_msg);
//                    fileExists.setPositiveButton(R.string.btn_yes,
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if (e.writeToSd(fileEdit)) {
//                                        makeToast(R.string.export_finished);
//                                    } else {
//                                        export();
//                                    }
//                                }
//                            });
//                    fileExists.setNegativeButton(R.string.btn_no, null);
//                    fileExists.show();
//                } else {
//                    if (e.writeToSd(fileEdit)) {
//                        makeToast(R.string.export_finished);
//                    } else {
//                        export();
//                    }
//                }
//            }
//        });
//        getFileName.setNegativeButton(R.string.btn_discover_cancel, null);
//        getFileName.show();
//    }

    // private List<String> getSelectedHosts(){
    // List<String> hosts_s = new ArrayList<String>();
    // int listCount = list.getChildCount();
    // for(int i=0; i<listCount; i++){
    // CheckBox cb = (CheckBox) list.getChildAt(i).findViewById(R.id.list);
    // if(cb.isChecked()){
    // hosts_s.add(hosts.get(i));
    // }
    // }
    // return hosts_s;
    // }
    //    
    // private void setSelectedHosts(Boolean all){
    // int listCount = list.getChildCount();
    // for(int i=0; i<listCount; i++){
    // CheckBox cb = (CheckBox) list.getChildAt(i).findViewById(R.id.list);
    // if(all){
    // cb.setChecked(true);
    // } else {
    // cb.setChecked(false);
    // }
    // }
    // }

    // private void makeToast(String msg) {
    // Toast.makeText(getApplicationContext(), (CharSequence) msg,
    // Toast.LENGTH_SHORT).show();
    // }

    public void makeToast(int msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void setButton(Button btn, int res, boolean disable) {
        if (disable) {
            setButtonOff(btn, res);
        } else {
            setButtonOn(btn, res);
        }
    }

    private void setButtonOff(Button b, int drawable) {
        b.setClickable(false);
        b.setEnabled(false);
        b.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
    }

    private void setButtonOn(Button b, int drawable) {
        b.setClickable(true);
        b.setEnabled(true);
        b.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
    }
    
    
    

	
}
