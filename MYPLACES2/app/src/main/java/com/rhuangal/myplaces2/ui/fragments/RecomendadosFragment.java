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
import com.rhuangal.myplaces2.data.network.entity.RecomendadosResponse;
import com.rhuangal.myplaces2.ui.adapters.CategoriaAdapter;
import com.rhuangal.myplaces2.ui.adapters.RecomendadosAdapter;
import com.rhuangal.myplaces2.ui.listeners.OnNavListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNavListener} interface
 * to handle interaction events.
 * Use the {@link RecomendadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecomendadosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_ID_CAT = "id_cat";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int id_cat;

    private OnNavListener mListener;

    private ListView lvRecomendados;
    private RecomendadosAdapter recomendadosAdapter;

    public RecomendadosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecomendadosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecomendadosFragment newInstance(String param1, String param2) {
        RecomendadosFragment fragment = new RecomendadosFragment();
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
            id_cat = getArguments().getInt(ARG_ID_CAT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recomendados, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<RecomendadosResponse> listCategorias = new ArrayList<>();

        lvRecomendados = (ListView) getView().findViewById(R.id.lvRecomendados);

        Call<List<RecomendadosResponse>> call= ApiClient.getMyApiClient().getRecomendadosByCat("id_cat="+String.valueOf(id_cat));
        final ProgressDialog dialog;
        /**
         * Progress Dialog for User Interaction
         */
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Cargando Categorias");
        dialog.setMessage("Espere por favor");
        dialog.show();
        call.enqueue(new Callback<List<RecomendadosResponse>>() {
            @Override
            public void onResponse(Call<List<RecomendadosResponse>> call, Response<List<RecomendadosResponse>> response) {
                dialog.dismiss();
                if(response!=null){
                    List<RecomendadosResponse> recomendados =null;
                    if(response.isSuccessful()){
                        recomendados = response.body();
                        if(recomendados!=null){
                            Log.i("CONSOLE", "TAMAÑO DE CAT: "+recomendados.size());
                            for(RecomendadosResponse rec: recomendados) {
                                listCategorias.add(rec);
                            }
                            recomendadosAdapter=  new RecomendadosAdapter(getContext(), listCategorias);
                            lvRecomendados.setAdapter(recomendadosAdapter);
                        }
                    }else{
                        Log.v("CONSOLE", "error "+recomendados);
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<List<RecomendadosResponse>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(),
                        "error "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        lvRecomendados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),
                        "Recomendado Id "+id,Toast.LENGTH_LONG).show();

                PointOnMapFragment recfragment = new PointOnMapFragment();
                Bundle args = new Bundle();
                args.putDouble("lat", listCategorias.get(position).getLat());
                args.putDouble("lon", listCategorias.get(position).getLng());
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
