package org.com.cnc.maispreco.common;

import android.app.Activity;
import android.content.Context;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class TrackerGoogle {
	private GoogleAnalyticsTracker tracker;
	private Activity activity;

	public TrackerGoogle(Activity activity) {
		this.activity = activity;

	}

	private void open() {
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession("A-27166080-1", this.activity);
		// tracker.trackEvent("Clicks", // Category
		// "Button", // Action
		// "clicked", // Label
		// 77); // Value

		tracker.trackEvent("maipreco.android", // Category
				"clicked", // Action
				"clicked", // Label
				77); // Value
	}

	public void home() {
		open();
		// Add a Custom Variable to this pageview, with name of "Medium" and
		// value "MobileApp" and
		// scope of session-level.
		tracker.setCustomVar(1, "Navigation Type", "Button click", 2);
		// Track a page view. This is probably the best way to track which parts
		// of your application
		// are being used.
		// E.g.
		// tracker.trackPageView("/help"); to track someone looking at the help
		// screen.
		// tracker.trackPageView("/level2"); to track someone reaching level 2
		// in a game.
		// tracker.trackPageView("/uploadScreen"); to track someone using an
		// upload screen.
		tracker.trackPageView("/");
		tracker.dispatch();
		close();
	}

	private void close() {
		tracker.stopSession();
	}

	public static void homeTracker(Activity activity) {
		GoogleAnalyticsTracker tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession("A-27166080-1", activity);
		// Category // Action // Label // Value
		tracker.trackEvent("maipreco.android", "clicked", "clicked", 77);
		tracker.setCustomVar(1, "Medium", "Mobile App");
		tracker.trackPageView("/");
		tracker.dispatch();
		tracker.stopSession();
	}

	public static void homeTracker(Context activity, String pATH) {
		GoogleAnalyticsTracker tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession("A-27166080-1", activity);
		// Category // Action // Label // Value
		tracker.trackEvent("maipreco.android", "clicked", "clicked", 77);
		tracker.setCustomVar(1, "Medium", "Mobile App");
		tracker.trackPageView(pATH);
		tracker.dispatch();
		tracker.stopSession();
	}
	
	public static void websiteTracker(Activity activity, String pATH) {
		GoogleAnalyticsTracker tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession("A-27166080-1", activity);
		// Category // Action // Label // Value
		tracker.trackEvent("outgoing_organic", "REPLACE_BY_THE_OFFER_NAME_THAT_BROUGHT_HERE", "BLANK_INFO", -1);
		tracker.setCustomVar(1, "Medium", "Mobile App");
		tracker.trackPageView(pATH);
		tracker.dispatch();
		tracker.stopSession();
	}
}
