package com.assist.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assist.FriendModel;
import com.assist.R;

public class FriendAdapter extends BaseAdapter {

	Context mContext;
	private ArrayList<FriendModel> listFriend;

	public FriendAdapter(Context mContext, ArrayList<FriendModel> listFriend) {
		this.mContext = mContext;
		this.listFriend = listFriend;
	}

	@Override
	public int getCount() {
		return listFriend.size();
	}

	@Override
	public FriendModel getItem(int position) {
		return listFriend.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder mViewHolder;

		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.row_friend, null);
			mViewHolder = new ViewHolder();
			mViewHolder.mTitleTextView = (TextView) convertView
					.findViewById(R.id.row_friend_txtName);
			mViewHolder.img = (ImageView) convertView
					.findViewById(R.id.row_friend_imgFriend);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.mTitleTextView.setText(listFriend.get(position).getName());

		return convertView;

	}

	class ViewHolder {
		TextView mTitleTextView;
		ImageView img;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
