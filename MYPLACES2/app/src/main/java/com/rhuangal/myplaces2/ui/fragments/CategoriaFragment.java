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
import com.rhuangal.myplaces2.data.network.entity.CategoriaResponse;
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
 * Use the {@link CategoriaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnNavListener mListener;

    private ListView lvCategoria;
    private CategoriaAdapter categoriaAdapter;

    public CategoriaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriaFragment newInstance(String param1, String param2) {
        CategoriaFragment fragment = new CategoriaFragment();
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
        return inflater.inflate(R.layout.fragment_categoria, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<CategoriaResponse> listCategorias = new ArrayList<>();

        lvCategoria = (ListView) getView().findViewById(R.id.lvCategoria);

        Call<List<CategoriaResponse>> call= ApiClient.getMyApiClient().getCategorias();
        final ProgressDialog dialog;
        /**
         * Progress Dialog for User Interaction
         */
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Cargando Categorias");
        dialog.setMessage("Espere por favor");
        dialog.show();
        call.enqueue(new Callback<List<CategoriaResponse>>() {
            @Override
            public void onResponse(Call<List<CategoriaResponse>> call, Response<List<CategoriaResponse>> response) {
                dialog.dismiss();
                if(response!=null){
                    List<CategoriaResponse> categorias =null;
                    if(response.isSuccessful()){
                        categorias = response.body();
                        if(categorias!=null){
                            Log.i("CONSOLE", "TAMAÑO DE CAT: "+categorias.size());
                            for(CategoriaResponse cat: categorias) {
                                listCategorias.add(cat);
                            }
                            categoriaAdapter=  new CategoriaAdapter(getContext(), listCategorias);
                            lvCategoria.setAdapter(categoriaAdapter);
                        }
                    }else{
                        Log.v("CONSOLE", "error "+categorias);
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<List<CategoriaResponse>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(),
                        "error "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        lvCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),
                        "Categoria Id "+id,Toast.LENGTH_LONG).show();
                RecomendadosFragment recfragment = new RecomendadosFragment();
                Bundle args = new Bundle();
                args.putInt("id_cat", listCategorias.get(position).getId_cat());
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
