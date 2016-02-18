package com.slide531.retrorx;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public class EarthquakeService {

	private static String baseUrl = "http://earthquake.usgs.gov/fdsnws/event/1/";
	private EarthquakeAPI earthquakeAPI;

	public EarthquakeService() {
		this(baseUrl);
	}

	public EarthquakeService(String baseUrl) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();

		earthquakeAPI = retrofit.create(EarthquakeAPI.class);
	}

	public EarthquakeAPI getAPI() {
		return earthquakeAPI;
	}

	public interface EarthquakeAPI {

		@GET("query?format=geojson")
		Observable<FeatureCollection> recentEarthquakes(@Query("starttime") String starttime, @Query("endtime") String endtime);
	}
}
