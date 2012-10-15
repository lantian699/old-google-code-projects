package com.picture.drawing.core;

import java.io.IOException;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class FileManager {

	Context context;

	public FileManager(Context context) {
		this.context = context;

	}

	public void storeImageMedia(Bitmap bitmap, String tags) {
		ContentValues values = new ContentValues();
		values.put(Images.Media.TITLE, "title");
		values.put(Images.Media.BUCKET_ID, "test");
		values.put(Images.Media.DESCRIPTION, tags);
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		OutputStream outstream = null;

		try {
			outstream = context.getContentResolver().openOutputStream(uri, "rwt");
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
			outstream.close();
		} catch (Exception e) {
		} finally {
			if (outstream != null) {
				try {
					outstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// File file = getOutputMediaFile();
		// try {
		// ExifInterface exifFile = new ExifInterface(file.getAbsolutePath());
		//
		// exifFile.setAttribute(ExifInterface.TAG_IMAGE_LENGTH,
		// String.valueOf(bitmap.getWidth()));
		// exifFile.setAttribute(ExifInterface.TAG_MAKE, "Lionel Anjuère");
		// exifFile.saveAttributes();
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	// /** Create a File for saving an image */
	// private static File getOutputMediaFile() {
	// // To be safe, you should check that the SDCard is mounted
	// // using Environment.getExternalStorageState() before doing this.
	//
	// File mediaStorageDir = new
	// File(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath(), "alstom");
	// // This location works best if you want the created images to be shared
	// // between applications and persist after your app has been uninstalled.
	//
	// // Create the storage directory if it does not exist
	// if (!mediaStorageDir.exists()) {
	// if (!mediaStorageDir.mkdirs()) {
	// return null;
	// }
	// }
	// // Create a media file name
	// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
	// Date());
	// File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	// "IMG_" + timeStamp + ".jpg");
	// return mediaFile;
	// }

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index);
		cursor.close();
		return path;
	}

	public Uri createPictureInMediaStore() {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, "New Picture");
		values.put(MediaStore.Images.Media.DESCRIPTION, "unknow");
		return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}

}
