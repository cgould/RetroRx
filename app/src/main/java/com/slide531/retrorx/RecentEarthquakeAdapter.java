package com.slide531.retrorx;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecentEarthquakeAdapter extends RecyclerView.Adapter {

	private ArrayList<Feature> earthquakes = new ArrayList<>();

	public static class EarthquakeViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		public TextView title;
		public EarthquakeViewHolder(View v) {
			super(v);
			title = (TextView)v.findViewById(R.id.title);
		}

		public void bind(Feature earthquake) {
			title.setText(earthquake.getProperties().getTitle());
		}
	}

	@Override
	public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.recent_earthquake_item, parent, false);
		EarthquakeViewHolder vh = new EarthquakeViewHolder(v);
		return vh;

	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		((EarthquakeViewHolder)holder).bind(earthquakes.get(position));
	}

	@Override
	public int getItemCount() {
		return earthquakes.size();
	}

	public ArrayList<Feature> getEarthquakes() {
		return earthquakes;
	}

	public void setEarthquakes(ArrayList<Feature> earthquakes) {
		this.earthquakes = earthquakes;
	}

}
