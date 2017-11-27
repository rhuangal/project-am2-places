package com.rhuangal.myplaces2.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rhuangal.myplaces2.R;
import com.rhuangal.myplaces2.data.network.ApiClient;
import com.rhuangal.myplaces2.data.network.entity.FavoritosResponse;
import com.rhuangal.myplaces2.data.prefs.PreferencesHelper;
import com.rhuangal.myplaces2.ui.adapters.FavoritosAdapter;
import com.rhuangal.myplaces2.ui.listeners.OnNavListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.rhuangal.myplaces2.ui.listeners.OnNavListener} interface
 * to handle interaction events.
 * Use the {@link FavoritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnNavListener mListener;

    private ListView lvFavoritos;

    public FavoritosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritosFragment newInstance(String param1, String param2) {
        FavoritosFragment fragment = new FavoritosFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<FavoritosResponse> listCategorias = new ArrayList<>();

        lvFavoritos = (ListView) getView().findViewById(R.id.lvFavoritos);

        Call<List<FavoritosResponse>> call= ApiClient.getMyApiClient().getFavoritos("ownerId="+ PreferencesHelper.getIdSession(getContext()));
        final ProgressDialog dialog;
        /**
         * Progress Dialog for User Interaction
         */
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Cargando Favoritos");
        dialog.setMessage("Espere por favor");
        dialog.show();
        call.enqueue(new Callback<List<FavoritosResponse>>() {
            @Override
            public void onResponse(Call<List<FavoritosResponse>> call, Response<List<FavoritosResponse>> response) {
                dialog.dismiss();
                if(response!=null){
                    List<FavoritosResponse> Favoritos =null;
                    if(response.isSuccessful()){
                        Favoritos = response.body();
                        if(Favoritos!=null){
                            Log.i("CONSOLE", "TAMAÑO DE CAT: "+Favoritos.size());
                            for(FavoritosResponse rec: Favoritos) {
                                listCategorias.add(rec);
                            }
                            FavoritosAdapter favoritosAdapter =  new FavoritosAdapter(getContext(), listCategorias);
                            lvFavoritos.setAdapter(favoritosAdapter);
                        }
                    }else{
                        Log.v("CONSOLE", "error "+Favoritos);
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<List<FavoritosResponse>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(),
                        "error "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        lvFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),
                        "Recomendado Id "+id,Toast.LENGTH_LONG).show();

                PointOnMapFragment recfragment = new PointOnMapFragment();
                Bundle args = new Bundle();
                args.putDouble("lat", listCategorias.get(position).getLat());
                args.putDouble("lon", listCategorias.get(position).getLon());
                args.putString("name", listCategorias.get(position).getName());
                recfragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, recfragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

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
