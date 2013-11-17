package com.example.stoobekta;

import java.util.ArrayList;
import java.util.Collections;

import com.example.stoobekta.db.SitesDB;
import com.example.stoobekta.helpers.HttpPersister;
import com.example.stoobekta.models.CommentModel;
import com.example.stoobekta.models.CommentsModel;
import com.example.stoobekta.models.DetailedSiteInfoModel;
import com.example.stoobekta.models.EventModel;
import com.example.stoobekta.models.SiteEventsModel;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.R.integer;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SiteEvents {

	public static SiteEventsFragment getSiteEventsFragment() {
		return new SiteEventsFragment();
	}

	public static class SiteEventsFragment extends Fragment {

		private ArrayAdapter<EventModel> adapter;
		private ListView eventsListView;
		private DetailedSiteInfoModel model;

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_site_events,
					container, false);

			String jsonStr = getActivity().getIntent().getStringExtra("model");
			model = new Gson().fromJson(jsonStr, DetailedSiteInfoModel.class);

			eventsListView = (ListView) rootView.findViewById(R.id.lv_events);

			GetAllEvents();

			eventsListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View item, int position, long id) {

							EventModel selected = adapter.getItem(position);
							
							String message;
							if(selected.DateOfEvent==null){
								message=selected.Description+" ще се проведе в "+model.Name;
							}
							else{
								message=selected.Description+" ще се проведе в "
							+model.Name+" от " +selected.DateOfEvent+ "часа";

							}
							EventNotification(message);

						}
					});

			return rootView;
		}
		
		
		private void EventNotification(String message) {
			new AlertDialog.Builder(getActivity())
					.setTitle("Информация за събитието")
					.setMessage(
							message)
					.setPositiveButton("Добре",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// continue with delete
								}
							}).show();
		}

		private void GetAllEvents() {
			HttpPersister.Get(getActivity(), "event/" + model.Number, null,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							Gson gson = new Gson();
							SiteEventsModel receivedEventsModel = gson
									.fromJson(response, SiteEventsModel.class);

							FillEventList((ArrayList<EventModel>) receivedEventsModel.Events);

						}
					});

		}

		private void FillEventList(ArrayList<EventModel> events) {

			try {
				adapter = new ArrayAdapter<EventModel>(getActivity(),
						android.R.layout.simple_list_item_1, events);

				eventsListView.setAdapter(adapter);
			} catch (Exception ex) {
				Log.d("d", ex.getMessage());
			}
		}

	}
}
