package com.zerosymbol.directorylisting.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.alium.nibo.autocompletesearchbar.NiboAutocompleteSVProvider;
import com.alium.nibo.autocompletesearchbar.NiboPlacesAutoCompleteSearchView;
import com.alium.nibo.autocompletesearchbar.NiboSearchSuggestionItem;
import com.alium.nibo.models.NiboSelectedOriginDestination;
import com.alium.nibo.models.NiboSelectedPlace;
import com.alium.nibo.placepicker.NiboPlacePickerActivity;
import com.alium.nibo.utils.NiboConstants;
import com.alium.nibo.utils.NiboStyle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.databinding.FragmentAboutusBinding;
import com.zerosymbol.directorylisting.support.AppSingle;

public class FragmentAboutus extends BaseFragment implements NiboAutocompleteSVProvider,GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private FragmentAboutusBinding binding;
    private GoogleApiClient mGoogleApiClient;
    private NiboPlacesAutoCompleteSearchView mAutocompletesearchbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aboutus, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(true);
        AppSingle.getInstance().getActivity().setTitle("About Us");

        mGoogleApiClient = new GoogleApiClient
                .Builder(AppSingle.getInstance().getActivity())
                .enableAutoManage(AppSingle.getInstance().getActivity(), 0, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        launchPickerFragment();
        return binding.getRoot();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onHomeButtonClicked() {

    }

    @Override
    public NiboPlacesAutoCompleteSearchView.SearchListener getSearchListener() {
        return new NiboPlacesAutoCompleteSearchView.SearchListener() {
            @Override
            public boolean onSuggestion(NiboSearchSuggestionItem niboSearchSuggestionItem) {
                Toast.makeText(AppSingle.getInstance().getActivity(), "PLACE NAME:" + niboSearchSuggestionItem.getFullTitle() + " PLACE ID: " + niboSearchSuggestionItem.getPlaceID(), Toast.LENGTH_SHORT).show();
                mAutocompletesearchbar.closeSearch();
                return false;
            }

            @Override
            public void onSearchCleared() {

            }

            @Override
            public void onSearchTermChanged(String term) {

            }

            @Override
            public void onSearch(String query) {

            }

            @Override
            public void onSearchEditOpened() {

            }

            @Override
            public void onSearchEditClosed() {

            }

            @Override
            public boolean onSearchEditBackPressed() {
                return false;
            }

            @Override
            public void onSearchExit() {

            }
        };
    }

    @Override
    public boolean getShouldUseVoice() {
        return false;
    }

    private void launchPickerFragment() {
        Intent intent = new Intent(AppSingle.getInstance().getActivity(), NiboPlacePickerActivity.class);
        NiboPlacePickerActivity.NiboPlacePickerBuilder config = new NiboPlacePickerActivity.NiboPlacePickerBuilder()
                .setSearchBarTitle("Search for an area")
                .setConfirmButtonTitle("Pick here bish")
                .setMarkerPinIconRes(R.drawable.ic_map_marker_def)
                .setStyleEnum(NiboStyle.DEFAULT);
        //.setStyleFileID(R.raw.retro);
        NiboPlacePickerActivity.setBuilder(config);
        startActivityForResult(intent, 300);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 300) {
            NiboSelectedPlace selectedPlace = data.getParcelableExtra(NiboConstants.SELECTED_PLACE_RESULT);
            Toast.makeText(AppSingle.getInstance().getActivity(), selectedPlace.getPlaceAddress(), Toast.LENGTH_LONG).show();
        } else if (resultCode == Activity.RESULT_OK && requestCode == 200) {
            NiboSelectedOriginDestination selectedOriginDestination = data.getParcelableExtra(NiboConstants.SELECTED_ORIGIN_DESTINATION_RESULT);
            Toast.makeText(AppSingle.getInstance().getActivity(), selectedOriginDestination.getOriginFullName() + " - " + selectedOriginDestination.getDestinationFullName(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AppSingle.getInstance().getActivity(), "Error getting results", Toast.LENGTH_LONG).show();
        }
    }
}
