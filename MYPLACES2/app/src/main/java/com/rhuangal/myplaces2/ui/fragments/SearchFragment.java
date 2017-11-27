package com.rhuangal.myplaces2.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.rhuangal.myplaces2.R;
import com.rhuangal.myplaces2.data.network.ApiClient;
import com.rhuangal.myplaces2.data.network.entity.Favoritos;
import com.rhuangal.myplaces2.data.network.entity.FavoritosResponse;
import com.rhuangal.myplaces2.data.network.entity.RecomendadosResponse;
import com.rhuangal.myplaces2.data.prefs.PreferencesHelper;
import com.rhuangal.myplaces2.ui.adapters.RecomendadosAdapter;
import com.rhuangal.myplaces2.ui.listeners.OnNavListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mPlaceDetailsText;
    private TextView mPlaceAttribution;
    private Button btnSavePlace;

    private Favoritos favoritos;

    private OnNavListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Open the autocomplete activity when the button is clicked.
        View viewContainer = inflater.inflate(R.layout.fragment_search, container, false);
        Button openButton = (Button) viewContainer.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAutocompleteActivity();
            }
        });
        //dbHelper = new AppDbHelper(new DbOpenHelper(getContext()));

        // Retrieve the TextViews that will display details about the selected place.
        mPlaceDetailsText = (TextView) viewContainer.findViewById(R.id.place_details);
        mPlaceAttribution = (TextView) viewContainer.findViewById(R.id.place_attribution);
        btnSavePlace = (Button)viewContainer.findViewById(R.id.btnSavePlace);
        btnSavePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favoritos != null){
                    savePlace();
                } else{
                    Toast.makeText(getContext(), "Debe buscar un lugar", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return viewContainer;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNavListener) {
            mListener = (OnNavListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNavListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(getActivity());
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e(TAG, message);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                Log.i(TAG, "Place Selected: " + place.getName());

                // Format the place's details and display them in the TextView.
                mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress(), place.getPhoneNumber(),
                        place.getWebsiteUri()));

                favoritos = new Favoritos();
                favoritos.setId(place.getId());
                favoritos.setAddress(place.getAddress().toString());
                favoritos.setLat(place.getLatLng().latitude);
                favoritos.setLon(place.getLatLng().longitude);
                favoritos.setName(place.getName().toString());
                favoritos.setOwnerId(PreferencesHelper.getIdSession(getContext()));
                //place.getLatLng().l
                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
                    mPlaceAttribution.setText("");
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }

    /**
     * Helper method to format information about a place nicely.
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    private void savePlace(){
        Call<FavoritosResponse> call= ApiClient.getMyApiClient().saveFavoritos(favoritos);
        final ProgressDialog dialog;
        /**
         * Progress Dialog for User Interaction
         */
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Guardado favorito Recomendados");
        dialog.setMessage("Espere por favor");
        dialog.show();
        call.enqueue(new Callback<FavoritosResponse>() {
            @Override
            public void onResponse(Call<FavoritosResponse> call, Response<FavoritosResponse> response) {
                dialog.dismiss();
                if(response!=null){
                    FavoritosResponse favresponse =null;
                    if(response.isSuccessful()){
                        favresponse = response.body();
                        if(favresponse!=null){
                            Toast.makeText(getContext(), "Se guardo correctamente", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Log.v("CONSOLE", "error "+favresponse);
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<FavoritosResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(),
                        "error "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }




}
