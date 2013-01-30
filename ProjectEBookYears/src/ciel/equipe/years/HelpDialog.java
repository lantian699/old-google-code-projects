package ciel.equipe.years;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ListView;

public class HelpDialog extends AlertDialog {  
	
	
	ListView mainlist;
    public HelpDialog(Context context) {  
        super(context);  
        final View view = getLayoutInflater().inflate(R.layout.help_dialog,  
                null);  
        
  
        setButton("关闭", (OnClickListener) null);  
        setIcon(R.drawable.icon);  
        setTitle("软件作者:CIEL EQUIPE "+"\nlantian699@gmail.com");
        
        setView(view); 
    }
}