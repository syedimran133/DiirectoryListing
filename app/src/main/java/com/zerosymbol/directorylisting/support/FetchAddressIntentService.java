package com.zerosymbol.directorylisting.support;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import com.zerosymbol.directorylisting.constants.AppConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {

    private static final String TAG = "FetchAddressIS";
    protected ResultReceiver receiver;

    public FetchAddressIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }
    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.IPrefLoc.RESULT_DATA_KEY, message);
        receiver.send(resultCode, bundle);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent == null) {
            return;
        }
        String errorMessage = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(
                AppConstants.IPrefLoc.LOCATION_DATA_EXTRA);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // CatchI/O problems network or other .
            errorMessage = "I/O problems network or other";
            Log.e("Loc error :: ", errorMessage);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "Invalid latitude or longitude values.";
            Log.e(TAG, errorMessage);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "Handle case where no address was found.";
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(AppConstants.IPrefLoc.FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, "address_found");
            deliverResultToReceiver(AppConstants.IPrefLoc.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));
        }
    }
}
