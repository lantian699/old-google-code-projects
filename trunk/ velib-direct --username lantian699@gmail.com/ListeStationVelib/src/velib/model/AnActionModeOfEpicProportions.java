package velib.model;

import android.content.Context;
import android.widget.Toast;

import com.actionbarsherlock.ActionBarSherlock.OnOptionsItemSelectedListener;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import essai.cnam.R;

public class AnActionModeOfEpicProportions implements ActionMode.Callback {
	
	private Context context;
	
	public AnActionModeOfEpicProportions(Context context){
		
		this.context = context;
		
	}
	
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        //Used to put dark icons on light action bar
       /* boolean isLight = true;

        menu.add("Save")
            .setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);*/
;

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        
    	
    	
    	
    	mode.finish();
        return true;
    }
    
    

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    	
    	
    }


}