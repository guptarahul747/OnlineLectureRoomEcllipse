package com.onlinelectureroom.adapter;

import java.util.ArrayList;
import com.example.onlinelectureroom.R;
import com.onlinelectureroom.net.EventListApi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<EventListApi> {

	private Context context;
	private ArrayList<EventListApi> arrayList;

	public EventListAdapter(Context context, ArrayList<EventListApi> arrayList) {
		super(context, 0, 0, arrayList);

		this.context = context;
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {

		return arrayList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		EventListApi events = arrayList.get(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			
			view = LayoutInflater.from(context).inflate(
					R.layout.event_list_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.txtName = (TextView)view.findViewById(R.id.txtName);
			viewHolder.txtDate = (TextView)view.findViewById(R.id.txtDate);
			viewHolder.txtBranch = (TextView)view.findViewById(R.id.txtBranch);
			
			view.setTag(viewHolder);
			
		}else {
			
			viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.txtName.setText(events.getName());
		viewHolder.txtDate.setText(events.getDate());
		viewHolder.txtBranch.setText(events.getBranch());
		
		return view;
	}
	
	private class ViewHolder {
		
		private TextView txtName;
		private TextView txtBranch;
		private TextView txtDate;
	}
}