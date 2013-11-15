package com.example.stoobekta;

import java.io.IOException;
import java.io.InputStream;

import android.R.string;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stoobekta.helpers.GetResourceIdHelper;
import com.example.stoobekta.models.DetailedSiteInfoModel;
import com.google.gson.Gson;

public class SiteInfo {

	public static SiteInfoFragment getSiteInfoFragment() {
		return new SiteInfoFragment();
	}

	public static class SiteInfoFragment extends Fragment {

		private ImageView imgView;
		private String sitePictureUrl;
		
		private TextView obektName;
		private TextView obektWorkHours;
		private TextView obektTicketAdult;
		private TextView obektTicketChild;
		private TextView obektDescription;

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_site_info,
					container, false);

			String jsonStr = getActivity().getIntent().getStringExtra("model");
			DetailedSiteInfoModel model = new Gson().fromJson(jsonStr,
					DetailedSiteInfoModel.class);
			
			LoadInfo(model, rootView);
			
			return rootView;
		}

		private void LoadInfo(DetailedSiteInfoModel model,View currentView) {
			sitePictureUrl = "obekt" + String.valueOf(model.Number);
			int obektId=GetResourceIdHelper.getId(sitePictureUrl, R.drawable.class);
			
			try{
			imgView = (ImageView) currentView.findViewById(R.id.obektImg);
			imgView.setImageResource(obektId);
			
			obektName=(TextView)currentView.findViewById(R.id.obektName);
			obektName.setText(model.Name);
			
			obektWorkHours=(TextView)currentView.findViewById(R.id.workHours);
			obektWorkHours.setText("Работно време:"+model.WorkingHours);
			
			obektTicketAdult=(TextView)currentView.findViewById(R.id.ticketAdult);
			obektTicketAdult.setText("Билет(възрастен):"+model.TicketAdult);
			
			obektTicketChild=(TextView)currentView.findViewById(R.id.ticketChild);
			obektTicketChild.setText("Билет(дете):"+model.TicketChild);
			
			obektDescription=(TextView)currentView.findViewById(R.id.obektDesc);
			obektDescription.setText(model.Description);
			
			}catch(Exception ex){
				Log.d("d",ex.getMessage());
			}
			
		}

	}
}
