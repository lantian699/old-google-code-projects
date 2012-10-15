package com.picture.drawing.ui.navigation.activity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.picture.drawing.core.FileManager;
import com.picture.drawing.ui.navigation.activity.adatper.ImageAdapter;
import com.picture.drawing.ui.navigation.activity.listener.GesturesPerformedListener;
import com.picture.drawing.ui.utils.BitmapHelper;

public class PictureDrawingActivity extends Activity {

	private static final int CAPTURE_CODE = 1;

	private static final String FILE = "file";
	private static final String MAX_HEIGHT_IMAGE = "maxHeight";
	private static final String MAX_WIDTH_IMAGE = "mawWidth";
	private static final String IMAGE_URI = "imageUri";

	private static final int PICTURE_SELECTION_CODE = 0;

	private static final String TAGS = "tags";

	GesturesPerformedListener gesturesPerformedListener;

	FileManager fileManager;

	ImageAdapter imageAdapter;

	ImageView backgroundImage;

	GestureOverlayView drawingGesture;

	ListView colorGallery;

	RelativeLayout imageLayout;

	private int imageMaxWidth;
	private int imageMaxHeight;
	private File pictureFile;

	private Uri currentImageUri;

	private AlertDialog alderDialog;

	private String tags;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.picture_drawing, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_save) {
			handleSaveButton();
			return true;
		} else {
			return false;
		}
	}

	protected void handleSaveButton() {
		Bitmap gestureBitmap = drawingGesture.getDrawingCache();
		if (gestureBitmap == null) {
			Toast.makeText(this, "aucune information sur l'image", Toast.LENGTH_LONG).show();
			return;
		}
		if (pictureFile == null) {
			Toast.makeText(this, "aucune image sélectionnée", Toast.LENGTH_LONG).show();
			return;
		}
		fileManager.storeImageMedia(gestureBitmap, tags);
		resetView();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_drawing);
		if (savedInstanceState != null) {
			pictureFile = (File) savedInstanceState.getSerializable(FILE);
			currentImageUri = (Uri) savedInstanceState.getParcelable(IMAGE_URI);
			imageMaxWidth = savedInstanceState.getInt(MAX_WIDTH_IMAGE);
			imageMaxHeight = savedInstanceState.getInt(MAX_HEIGHT_IMAGE);
			tags = savedInstanceState.getString(TAGS);
		} else {
			tags = "";
		}

		initiateView();

		fileManager = new FileManager(this);

		imageAdapter = new ImageAdapter(this);
		afterViews();
	}

	private void initiateView() {
		backgroundImage = ((ImageView) findViewById(R.id.backgroundImage));
		drawingGesture = ((GestureOverlayView) findViewById(R.id.drawingGesture));
		imageLayout = ((RelativeLayout) findViewById(R.id.imageLayout));
		colorGallery = ((ListView) findViewById(R.id.colorGallery));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(FILE, pictureFile);
		outState.putParcelable(IMAGE_URI, currentImageUri);
		outState.putInt(MAX_HEIGHT_IMAGE, imageMaxHeight);
		outState.putInt(MAX_WIDTH_IMAGE, imageMaxWidth);
		outState.putString(TAGS, tags);
	}

	// TODO déplacer la gesture avec un long click
	public void afterViews() {

		drawingGesture.setDrawingCacheEnabled(true);
		drawingGesture.addOnGesturePerformedListener(new GesturesPerformedListener());
		createColorGallery();
	}

	private void createColorGallery() {
		int[] colorsArray = { Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW };
		imageAdapter.setColorsArray(colorsArray);
		colorGallery.setAdapter(imageAdapter);
		colorGallery.setDividerHeight(0);
		colorGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> viewAdapter, View view, int position, long arg3) {
				int color = (Integer) imageAdapter.getItem(position);
				drawingGesture.setGestureColor(color);
				drawingGesture.refreshDrawableState();
				resetGesture();
			}
		});
	}

	public void tagButtonClicked(View view) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("ajouter un tag sur l'image");
		final EditText editText = new EditText(this);
		editText.setText(tags);
		builder.setView(editText);
		builder.setPositiveButton("Sauvegarder le tag", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				tags = editText.getText().toString();
				alderDialog.dismiss();
			}
		});
		alderDialog = builder.create();
		alderDialog.show();
	}

	protected void resetView() {
		resetGesture();
		removeImageViewBitmap();
	}

	// TODO use to move seleciton if needed ( on long click)
	// private byte[] extratDrawByteArrayFromGesture() {
	// float gestureWidth = drawingGesture.getGesture().getBoundingBox().right -
	// drawingGesture.getGesture().getBoundingBox().left;
	// float gestureHeight = drawingGesture.getGesture().getBoundingBox().bottom
	// - drawingGesture.getGesture().getBoundingBox().top;
	// Bitmap gestureBitmap = GestureBitmap.toBitmap((int)
	// Math.abs(gestureWidth), (int) Math.abs(gestureHeight), 8,
	// drawingGesture.getGestureColor(), drawingGesture.getGesture(), 6);
	// ByteArrayOutputStream bos = new ByteArrayOutputStream();
	//
	// gestureBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
	// byte[] draw = bos.toByteArray();
	// return draw;
	// }

	public void choosePictureClicked(View view) {
		imageMaxWidth = imageLayout.getWidth();
		imageMaxHeight = imageLayout.getHeight();
		Intent choosePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(choosePictureIntent, PICTURE_SELECTION_CODE);
	}

	public void takePictureClicked(View view) {
		imageMaxWidth = imageLayout.getWidth();
		imageMaxHeight = imageLayout.getHeight();
		if (isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			currentImageUri = fileManager.createPictureInMediaStore();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri);
			startActivityForResult(intent, CAPTURE_CODE);
		} else {
			Log.i("takePictureClicked", "impossile de prendre une photo");
			Toast.makeText(this, "impossible de faire une photo sur cet appareil", Toast.LENGTH_LONG).show();
		}
	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == CAPTURE_CODE) {
				retreivePictureFile(currentImageUri);
			} else if (requestCode == PICTURE_SELECTION_CODE) {
				Uri currentImageUri = data.getData();
				retreivePictureFile(currentImageUri);
			}
		}
		// the picture is display in onResume() with the pictureFile
	}

	/**
	 * retreive picture path with uri content in data
	 * 
	 * @param data
	 */
	private void retreivePictureFile(Uri currentImageUri) {
		String path = fileManager.getRealPathFromURI(currentImageUri);
		pictureFile = new File(path);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (pictureFile != null) {
			displayBackgroundImage(pictureFile.getPath());
			// displayBackgroundImage(pictureFile.getPath());
			resetGesture();
		} else {
			removeImageViewBitmap();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		removeImageViewBitmap();
	}

	/**
	 * to remove bitmap from imageView else it's cause out of memory exception
	 */
	private void removeImageViewBitmap() {
		backgroundImage.setImageResource(android.R.drawable.ic_menu_search);
	}

	private void resetGesture() {
		drawingGesture.clear(false);
		drawingGesture.destroyDrawingCache();
	}

	private void displayBackgroundImage(String filePath) {
		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);
			String orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
			Log.i("picture orientation", "orientation : " + orientation);
			// FIXME rotate image
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap brackgroundPictureBitmap = BitmapHelper.retreiveBitmapFromPath(imageMaxWidth, imageMaxHeight, filePath);
		backgroundImage.setImageBitmap(brackgroundPictureBitmap);

	}
}
