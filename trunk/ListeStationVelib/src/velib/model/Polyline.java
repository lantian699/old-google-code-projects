package velib.model;


import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;


public class Polyline extends Overlay {
	List<GeoPoint> points;
	Paint paint;


	public Polyline(List<GeoPoint> points, int couleur) {
		this.points = points;
		paint = new Paint();
		paint.setColor(couleur); 
		paint.setAlpha(150);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(5);
	}

	
	public Polyline(List<GeoPoint> points, Paint paint) {
		this.points = points;
		this.paint = paint;
	}


	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		draw(canvas, mapView, shadow);
		return false;
	}

	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		if (!shadow) {
			Projection projection = mapView.getProjection();
			if (points != null) {
				if (points.size() >= 2) {
					Point start = projection.toPixels(points.get(0), null);// ��Ҫת�����
					for (int i = 1; i < points.size(); i++) {
						Point end = projection.toPixels(points.get(i), null);
						canvas.drawLine(start.x, start.y, end.x, end.y, paint);// ���Ƶ�canvas�ϼ���
						start = end;
					}
				}
			}
		}
	}
}
