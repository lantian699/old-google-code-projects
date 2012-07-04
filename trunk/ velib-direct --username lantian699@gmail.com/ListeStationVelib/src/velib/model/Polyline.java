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

/**
 * Goolge��ͼ֮Polylineʵ��
 * 
 * @author westyi
 */
public class Polyline extends Overlay {
	List<GeoPoint> points;
	Paint paint;

	/**
	 * ���캯��ʹ��GeoPoint List����Polyline
	 * 
	 * @param points
	 *            GeoPoint��List
	 */
	public Polyline(List<GeoPoint> points, int couleur) {
		this.points = points;
		paint = new Paint();
		paint.setColor(couleur); 
		paint.setAlpha(150);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(5);
	}

	/**
	 * ʹ��GeoPoint��List��Paint����������Polyline
	 * 
	 * @param points
	 *            GeoPoint��List�����еĹյ�
	 * @param paint
	 *            Paint�����������ƻ�����ʽ
	 */
	public Polyline(List<GeoPoint> points, Paint paint) {
		this.points = points;
		this.paint = paint;
	}

	/**
	 * Google�ĵ�˵���draw��������animatedʱ���õģ�Ҳ���ǿ��ƶ���Ч��ֱ�ӵ�����һ��draw��������
	 */
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		draw(canvas, mapView, shadow);
		return false;
	}

	/**
	 * �����߻��Ƴ��� ֻ�轫�߻��Ƶ�canvas�ϼ��ɣ���Ҫ��Ҫת����γ�ȵ���Ļ���
	 */
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		if (!shadow) {// ���ǻ���shadow��
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
