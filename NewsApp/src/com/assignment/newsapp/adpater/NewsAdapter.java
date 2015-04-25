package com.assignment.newsapp.adpater;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.newsapp.NewsDetailActivity;
import com.assignment.newsapp.R;
import com.assignment.newsapp.model.NewsModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewsAdapter extends BaseAdapter {
	private LayoutInflater linf;

	private Context mcontext;
	DisplayImageOptions options;
	private ArrayList<NewsModel> listData;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	public NewsAdapter(Context m1, ArrayList<NewsModel> listData) {
		mcontext = m1;
		linf = LayoutInflater.from(mcontext);
		this.listData = listData;

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = new ViewHolder();
		final int position = pos;
		if (convertView == null) {
			view = linf.inflate(R.layout.row_news, null);

			holder.txtDate = (TextView) view
					.findViewById(R.id.row_news_txtTime);
			holder.txtDetail = (TextView) view
					.findViewById(R.id.row_news_txtDetail);
			holder.txtTitle = (TextView) view
					.findViewById(R.id.row_news_txtTitle);

			holder.imgNews = (ImageView) view.findViewById(R.id.row_news_imgBg);

			// View b = view.findViewById(R.id.btnSelectDrink2);
			// b.setVisibility(View.GONE);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.txtDate.setText(listData.get(position).getDate());
		holder.txtDetail.setText(listData.get(position).getDetail());
		holder.txtTitle.setText(listData.get(position).getTitle());
		Log.e("", "ur:" + listData.get(position).getImageUrl());
		imageLoader.displayImage(listData.get(position).getImageUrl(),
				holder.imgNews, options);

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(mcontext,
						NewsDetailActivity.class);
				intent.putExtra("EXTRA_URL", listData.get(position).getGuId());
				intent.putExtra("EXTRA_ID", listData.get(position).getId());
				mcontext.startActivity(intent);
			}
		});
		return view;
	}

	static class ViewHolder {

		ImageView imgNews;
		TextView txtTitle, txtDetail, txtDate;
	}

}
