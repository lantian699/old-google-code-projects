package com.picture.drawing.ui.utils;

import java.util.List;

import android.gesture.Gesture;
import android.gesture.GestureStroke;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class GestureBitmap {
	private static final boolean BITMAP_RENDERING_ANTIALIAS = true;
	private static final boolean BITMAP_RENDERING_DITHER = true;

	/**
	 * Creates a bitmap of the gesture with a transparent background.
	 * 
	 * @param width
	 * @param height
	 * @param inset
	 * @param color
	 * @return the bitmap
	 */
	public static Bitmap toBitmap(int width, int height, int inset, int color,
			Gesture gesture, float strokeWidth) {
		final Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);

		final Paint paint = new Paint();
		paint.setAntiAlias(BITMAP_RENDERING_ANTIALIAS);
		paint.setDither(BITMAP_RENDERING_DITHER);
		paint.setColor(color);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);

		final Path path = toPath(gesture.getStrokes());
		final RectF bounds = new RectF();
		path.computeBounds(bounds, true);

		final float sx = (width - 2 * inset) / bounds.width();
		final float sy = (height - 2 * inset) / bounds.height();
		final float scale = sx > sy ? sy : sx;
		paint.setStrokeWidth(strokeWidth / scale);

		path.offset(-bounds.left + (width - bounds.width() * scale) / 2.0f,
				-bounds.top + (height - bounds.height() * scale) / 2.0f);

		canvas.translate(inset, inset);
		canvas.scale(scale, scale);

		canvas.drawPath(path, paint);

		return bitmap;
	}

	public static Path toPath(List<GestureStroke> strokes) {
		Path path = new Path();

		final int count = strokes.size();

		for (int i = 0; i < count; i++) {
			path.addPath(strokes.get(i).getPath());
		}

		return path;
	}
}