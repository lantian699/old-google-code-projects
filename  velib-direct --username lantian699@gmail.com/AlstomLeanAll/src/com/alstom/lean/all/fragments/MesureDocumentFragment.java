package com.alstom.lean.all.fragments;

import org.vudroid.core.DecodeService;
import org.vudroid.core.DecodeServiceBase;
import org.vudroid.core.DocumentView;
import org.vudroid.core.GoToPageDialog;
import org.vudroid.core.ViewerPreferences;
import org.vudroid.core.models.CurrentPageModel;
import org.vudroid.core.models.DecodingProgressModel;
import org.vudroid.core.models.ZoomModel;
import org.vudroid.core.views.PageViewZoomControls;
import org.vudroid.pdfdroid.codec.PdfContext;

import com.alstom.lean.all.R;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MesureDocumentFragment extends Fragment{

	
	
	private static final int MENU_EXIT = 0;
    private static final int MENU_GOTO = 1;
    private static final int MENU_FULL_SCREEN = 2;
    private static final int DIALOG_GOTO = 0;
    private static final String DOCUMENT_VIEW_STATE_PREFERENCES = "DjvuDocumentViewState";
    private DecodeService decodeService;
    private DocumentView documentView;
    private ViewerPreferences viewerPreferences;
    private Toast pageNumberToast;
    private CurrentPageModel currentPageModel;
	private Uri pdfUri;
	private FrameLayout frameLayout;

    /**
     * Called when the activity is first created.
     */
    
    public MesureDocumentFragment(Uri uri){
    	this.pdfUri = uri;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initDecodeService();
        final ZoomModel zoomModel = new ZoomModel();
        final DecodingProgressModel progressModel = new DecodingProgressModel();
        progressModel.addEventListener(this);
        currentPageModel = new CurrentPageModel();
        currentPageModel.addEventListener(this);
        documentView = new DocumentView(getActivity(), zoomModel, progressModel, currentPageModel);
        zoomModel.addEventListener(documentView);
        documentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
//        decodeService.setContentResolver(getContentResolver());
        decodeService.setContainerView(documentView);
        documentView.setDecodeService(decodeService);
        decodeService.open(pdfUri);

        viewerPreferences = new ViewerPreferences(getActivity());
//        setFullScreen();
        frameLayout = createMainContainer();
        frameLayout.addView(documentView);
        frameLayout.addView(createZoomControls(zoomModel));
        

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(DOCUMENT_VIEW_STATE_PREFERENCES, 0);
        documentView.goToPage(sharedPreferences.getInt(pdfUri.toString(), 0));
        documentView.showDocument();

        viewerPreferences.addRecent(pdfUri);
    }

    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		 /*ImageView view = new ImageView(getActivity());
		 view.setImageResource(R.drawable.img_mesure_exemple);*/
		 
    	
		return frameLayout;
	}
    
    
   /* public void decodingProgressChanged(final int currentlyDecoding)
    {
       new Runnable()
        {
            public void run()
            {

             //  getWindow().setFeatureInt(Window.FEATURE_INDETERMINATE_PROGRESS, currentlyDecoding == 0 ? 10000 : currentlyDecoding);
                
            }
        };
    }*/

    public void currentPageChanged(int pageIndex)
    {
        final String pageText = (pageIndex + 1) + "/" + decodeService.getPageCount();
        if (pageNumberToast != null)
        {
            pageNumberToast.setText(pageText);
        }
        else
        {
            pageNumberToast = Toast.makeText(getActivity(), pageText, 300);
        }
        pageNumberToast.setGravity(Gravity.TOP | Gravity.LEFT,0,0);
        pageNumberToast.show();
        saveCurrentPage();
    }

    private void setWindowTitle()
    {
        final String name = pdfUri.getLastPathSegment();
        getActivity().getWindow().setTitle(name);
    }
    
 /*   @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setWindowTitle();
    }*/

    private void setFullScreen()
    {
        if (viewerPreferences.isFullScreen())
        {
            getActivity().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else
        {
        	getActivity().getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        }
    }

    private PageViewZoomControls createZoomControls(ZoomModel zoomModel)
    {
        final PageViewZoomControls controls = new PageViewZoomControls(getActivity(), zoomModel);
        controls.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        zoomModel.addEventListener(controls);
        return controls;
    }

    private FrameLayout createMainContainer()
    {
        return new FrameLayout(getActivity());
    }

    private void initDecodeService()
    {
        if (decodeService == null)
        {
            decodeService = createDecodeService();
        }
    }

    protected DecodeService createDecodeService(){
    	
    	return new DecodeServiceBase(new PdfContext());
    }

    @Override
	public void onStop()
    {
        super.onStop();
    }

    @Override
	public void onDestroy() {
        decodeService.recycle();
        decodeService = null;
        super.onDestroy();
    }

    private void saveCurrentPage()
    {
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(DOCUMENT_VIEW_STATE_PREFERENCES, 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(pdfUri.toString(), documentView.getCurrentPage());
        editor.commit();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, MENU_EXIT, 0, "Exit");
        menu.add(0, MENU_GOTO, 0, "Go to page");
        final MenuItem menuItem = menu.add(0, MENU_FULL_SCREEN, 0, "Full screen").setCheckable(true).setChecked(viewerPreferences.isFullScreen());
        setFullScreenMenuItemText(menuItem);
        return true;
    }*/

    private void setFullScreenMenuItemText(MenuItem menuItem)
    {
        menuItem.setTitle("Full screen " + (menuItem.isChecked() ? "on" : "off"));
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case MENU_EXIT:
                System.exit(0);
                return true;
            case MENU_GOTO:
                showDialog(DIALOG_GOTO);
                return true;
            case MENU_FULL_SCREEN:
                item.setChecked(!item.isChecked());
                setFullScreenMenuItemText(item);
                viewerPreferences.setFullScreen(item.isChecked());

                finish();
                startActivity(getIntent());
                return true;
        }
        return false;
    }*/

    /*@Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DIALOG_GOTO:
                return new GoToPageDialog(this, documentView, decodeService);
        }
        return null;
    }*/
	
	
	
}
