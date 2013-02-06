package com.picture.drawing.ui.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;

public class BitmapHelper {

	public static Bitmap mergeBitmaps(Bitmap backgroundBitmap, Bitmap... bitmapToMerge) {
		Bitmap mergedPicture = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mergedPicture);
		canvas.drawBitmap(backgroundBitmap, new Matrix(), null);
		for (int i = 0; i < bitmapToMerge.length; i++) {
			canvas.drawBitmap(bitmapToMerge[i], new Matrix(), null);
		}
		canvas.save();
		return mergedPicture;
	}

	// TODO make it work
	public static Bitmap rotateBitmap(Bitmap bitmap, String orientation) {
		int rotate = 0;
		switch (Integer.valueOf(orientation)) {
		case ExifInterface.ORIENTATION_ROTATE_270:
			rotate -= 90;
		case ExifInterface.ORIENTATION_ROTATE_180:
			rotate -= 90;
		case ExifInterface.ORIENTATION_ROTATE_90:
			rotate -= 90;
		}
		Canvas canvas = new Canvas(bitmap);
		Matrix rotator = new Matrix();
		rotator.postRotate(rotate);
		Paint paint = new Paint();
		canvas.drawBitmap(bitmap, rotator, paint);
		canvas.save();
		return bitmap;
	}

	public static Bitmap retreiveBitmapFromPath(int tragetWidth, int targetHeight, String filePath) {
		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoH / targetHeight, photoW / tragetWidth);

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
		return bitmap;
	}
}
