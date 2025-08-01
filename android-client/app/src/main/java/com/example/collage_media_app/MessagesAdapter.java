package com.example.collage_media_app;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MessagesAdapter extends ArrayAdapter<ChatMessage> {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"HH:mm:ss", Locale.US);
	public MessagesAdapter(Context context) {
		super(context, R.layout.row_message);
	}
	private class ViewHolder {
		TextView labelUsername, labelMessage, labelTime;
		View containerSender, containerMessage;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=createOrReuseView(convertView,parent);
		final ViewHolder holder=(ViewHolder) view.getTag();
		ChatMessage messageToShow = getItem(position);
		holder.labelMessage.setText(messageToShow.getMessage());
		LayoutParams messageParams = (LayoutParams) holder.containerMessage
				.getLayoutParams();
		if (messageToShow.isSystemMessage()) {
			messageParams.gravity = Gravity.RIGHT;
			holder.containerMessage
					.setBackgroundResource(R.drawable.background_system_message);
			holder.containerSender.setVisibility(View.GONE);
		} else {
			holder.containerSender.setVisibility(View.VISIBLE);
			holder.labelUsername.setText(messageToShow.getUsername()+":");
			holder.labelTime
					.setText(messageToShow.getDate());
			if (messageToShow.isIncomingMessage()) {
				messageParams.gravity = Gravity.RIGHT;
				holder.containerMessage
						.setBackgroundResource(R.drawable.background_incoming_message);
			} else {
				messageParams.gravity = Gravity.LEFT;
				holder.containerMessage
						.setBackgroundResource(R.drawable.background_outgoing_message);
			}
		}

		return view;
	}

	private View createOrReuseView(View convertView, ViewGroup parent) {
		final View view;
		final ViewHolder holder;
		if (convertView == null) {
			// Create the row
			view = LayoutInflater.from(getContext()).inflate(
					R.layout.row_message, parent, false);
			holder = new ViewHolder();
			holder.labelUsername = (TextView) view
					.findViewById(R.id.label_username);
			
			holder.labelMessage = (TextView) view
					.findViewById(R.id.label_message);
			holder.labelTime = (TextView) view.findViewById(R.id.label_date);
			holder.containerSender = view.findViewById(R.id.container_sender);
			holder.containerMessage = view.findViewById(R.id.container_message);
			// Save the holder reference
			view.setTag(holder);
		} else {
			// Recover the saved holder
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		return view;
	}
	
	

}
