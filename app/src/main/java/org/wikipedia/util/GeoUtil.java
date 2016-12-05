package org.wikipedia.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.feed.announcement.GeoIPCookieUnmarshaller;

public final class GeoUtil {

    public static void sendGeoIntent(@NonNull Activity activity,
                                     @NonNull Location location,
                                     @Nullable String placeName) {
        String geoStr = "geo:";
        geoStr += Double.toString(location.getLatitude()) + ","
                + Double.toString(location.getLongitude());
        if (!TextUtils.isEmpty(placeName)) {
            geoStr += "?q=" + Uri.encode(placeName);
        }
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(geoStr)));
        } catch (ActivityNotFoundException e) {
            FeedbackUtil.showMessage(activity, R.string.error_no_maps_app);
        }
    }

    @Nullable public static String getGeoIPCountry() {
        try {
            return GeoIPCookieUnmarshaller.unmarshal(WikipediaApp.getInstance()).country();
        } catch (IllegalArgumentException e) {
            // For our purposes, don't care about malformations in the GeoIP cookie for now.
            return null;
        }
    }

    private GeoUtil() {
    }
}
