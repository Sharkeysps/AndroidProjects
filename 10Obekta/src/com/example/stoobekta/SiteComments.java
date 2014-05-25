package com.example.stoobekta;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.stoobekta.helpers.HttpPersister;
import com.example.stoobekta.helpers.InternetConnectionChecker;
import com.example.stoobekta.models.CommentModel;
import com.example.stoobekta.models.CommentsModel;
import com.example.stoobekta.models.DetailedSiteInfoModel;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class SiteComments {

	public static SiteCommentsFragment returnCommentFragment() {
		return new SiteCommentsFragment();
	}

	public static class SiteCommentsFragment extends Fragment implements
			android.view.View.OnClickListener {

		private ArrayAdapter<CommentModel> adapter;
		private ListView commentsListView;
		private DetailedSiteInfoModel model;

		private EditText commentText;
		private Button sendCommentBtn;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_site_comments,
					container, false);

			String jsonStr = getActivity().getIntent().getStringExtra("model");
			model = new Gson().fromJson(jsonStr, DetailedSiteInfoModel.class);

			commentsListView = (ListView) rootView
					.findViewById(R.id.lv_comments);

			commentText = (EditText) rootView.findViewById(R.id.commentText);
			sendCommentBtn = (Button) rootView.findViewById(R.id.sendComment);

			sendCommentBtn.setOnClickListener(this);

			if (InternetConnectionChecker.isOnline(getActivity())) {
				GetAllComments();
			} else {
				UserNotification();
			}

			return rootView;
		}

		private void GetAllComments() {
			HttpPersister.Get(getActivity(), "comment/" + model.Number, null,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							Gson gson = new Gson();
							CommentsModel receivedCommentsModel = gson
									.fromJson(response, CommentsModel.class);

							Collections.reverse(receivedCommentsModel.Comments);
							FillCommentsList((ArrayList<CommentModel>) receivedCommentsModel.Comments);

						}
					});
		}

		private void FillCommentsList(ArrayList<CommentModel> receivedComments) {

			try {
				adapter = new ArrayAdapter<CommentModel>(getActivity(),
						android.R.layout.simple_list_item_1, receivedComments);

				commentsListView.setAdapter(adapter);
			} catch (Exception ex) {
				Log.d("d", ex.getMessage());
			}
		}

		private void UserNotification() {
			new AlertDialog.Builder(getActivity())
					.setTitle("Липсва интернет връзка")
					.setMessage(
							"За да може да видите коментарите и предстоящите събития трябва да имате активна интернет връзка")
					.setPositiveButton("Добре",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// continue with delete
								}
							}).show();
		}

		@Override
		public void onClick(View v) {
			StringEntity serializedEntity=null;
			
			if (commentText.getText().toString()!=null) {
				JSONObject jdata = new JSONObject();

				try {
					jdata.put("Comment", commentText.getText().toString());
					jdata.put("SiteID", model.Number);
					jdata.put("UserName","Test");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					serializedEntity = new StringEntity(jdata.toString());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpPersister.Post(getActivity(),"comment", serializedEntity,
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(String response) {
								commentText.setText("");
								GetAllComments();
								
							}
						});
			}

		}

	}

}
