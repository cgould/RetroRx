package com.slide531.retrorx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

	private CompositeSubscription subscriptions = new CompositeSubscription();

	private RecyclerView recyclerView;
	private RecentEarthquakeAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);

		adapter = new RecentEarthquakeAdapter();
		recyclerView.setAdapter(adapter);
    }

	@Override
	protected void onPause() {
		super.onPause();
		if (subscriptions != null ) {
			subscriptions.unsubscribe();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if ( subscriptions == null || subscriptions.isUnsubscribed()) {
			subscriptions = new CompositeSubscription();
		}

		EarthquakeService service = RetroRxApp.getEarthquakeService();
		subscriptions.add(
			service.getAPI().recentEarthquakes("2016-02-16", "2016-02-17")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<FeatureCollection>() {
					@Override
					public void onCompleted() {
						Log.d("retrorx", "Retrofit call completed");
					}

					@Override
					public void onError(Throwable e) {
						Log.d("retrorx", "Retrofit call error:" + e.getMessage());
					}

					@Override
					public void onNext(FeatureCollection featureCollection) {
						ArrayList<Feature> features = featureCollection.getFeatures();
						adapter.setEarthquakes(features);
						adapter.notifyDataSetChanged();
					}
				})
		);
	}
}
