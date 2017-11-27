package com.rhuangal.myplaces2.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rhuangal.myplaces2.R;
import com.rhuangal.myplaces2.ui.listeners.OnNavListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNavListener} interface
 * to handle interaction events.
 * Use the {@link PointOnMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointOnMapFragment extends Fragment implements OnMapReadyCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_LAT = "lat";
    private static final String ARG_LON = "lon";
    private static final String ARG_NAME = "name";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private double lat;
    private double lon;
    private String name;

    MapView mMapView;

    private GoogleMap googleMap;

    private OnNavListener mListener;

    public PointOnMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PointOnMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PointOnMapFragment newInstance(String param1, String param2) {
        PointOnMapFragment fragment = new PointOnMapFragment();
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
            lat = getArguments().getDouble(ARG_LAT);
            lon = getArguments().getDouble(ARG_LON);
            name = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_point_on_map, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {

            MapsInitializer.initialize(getActivity().getApplicationContext());

        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title(name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f));

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

}
