package com.onlinelectureroom.ui;

import java.util.ArrayList;

import com.example.onlinelectureroom.R;
import com.onlinelectureroom.adapter.EventListAdapter;
import com.onlinelectureroom.net.EventListApi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EventListFragment extends Fragment implements OnItemClickListener {
	
	private ListView listView;
	private EventListAdapter listAdapter;
	private ArrayList<EventListApi> arrayList = new ArrayList<EventListApi>();
	
	public static Fragment newInstance(String tab) {

		EventListFragment fragment = new EventListFragment();
		Bundle bundle = new Bundle();
		bundle.putString("data", tab);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.event_listview, container,false);
		
		initViews(view);
		return view;
	}

	private void initViews(View view) {

		listView = (ListView)view.findViewById(R.id.listView);
		
		listAdapter = new EventListAdapter(getActivity(), arrayList);
		listView.setAdapter(listAdapter);
		
		listView.setOnItemClickListener(this);
		 getEvents();
	}



	private void getEvents() {

		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
}
