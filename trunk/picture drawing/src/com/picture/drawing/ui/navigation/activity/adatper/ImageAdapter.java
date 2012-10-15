package com.picture.drawing.ui.navigation.activity.adatper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.picture.drawing.ui.navigation.activity.R;

public class ImageAdapter extends BaseAdapter {

	Context context;

	public ImageAdapter(Context context) {
		this.context = context;

	}

	private int[] colorsArray;

	@Override
	public int getCount() {
		return colorsArray.length;
	}

	@Override
	public Object getItem(int position) {
		return colorsArray[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (contentView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			contentView = inflater.inflate(R.layout.color_framework, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.setImageView((ImageView) contentView.findViewById(R.id.colorSpace));
			contentView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) contentView.getTag();
		}
		viewHolder.imageView.setBackgroundColor(colorsArray[position]);
		return contentView;
	}

	private class ViewHolder {
		private ImageView imageView;

		public void setImageView(ImageView imageView) {
			this.imageView = imageView;
		}

	}

	public void setColorsArray(int[] colorsArray) {
		this.colorsArray = colorsArray;
	}

}
