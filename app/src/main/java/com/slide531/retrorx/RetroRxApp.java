package com.slide531.retrorx;

import android.app.Application;

import com.slide531.retrorx.EarthquakeService;

public class RetroRxApp extends Application {

	private static EarthquakeService earthquakeService = new EarthquakeService();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public static EarthquakeService getEarthquakeService(){
		return earthquakeService;
	}
}
