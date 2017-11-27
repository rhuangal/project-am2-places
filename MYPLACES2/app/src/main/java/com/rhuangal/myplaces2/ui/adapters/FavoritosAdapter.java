package com.rhuangal.myplaces2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhuangal.myplaces2.R;
import com.rhuangal.myplaces2.data.network.entity.FavoritosResponse;
import com.rhuangal.myplaces2.data.network.entity.RecomendadosResponse;

import java.util.List;

/**
 * Created by rober on 27-Nov-17.
 */

public class FavoritosAdapter extends BaseAdapter {

    private Context context;
    private List<FavoritosResponse> listaFavoritos;

    public FavoritosAdapter(Context context, List<FavoritosResponse> listaFavoritos) {
        this.context = context;
        this.listaFavoritos = listaFavoritos;
    }

    @Override
    public int getCount() {
        return listaFavoritos.size();
    }

    @Override
    public FavoritosResponse getItem(int position) {
        return listaFavoritos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.row_recomendados, null);
            FavoritosAdapter.ViewHolder holder = new FavoritosAdapter.ViewHolder();
            holder.tvLugarNombre = (TextView)v.findViewById(R.id.tvLugarNombre);
            holder.tvLugarDireccion = (TextView)v.findViewById(R.id.tvLugarDireccion);
            v.setTag(holder);
        }
        FavoritosResponse favoritos = listaFavoritos.get(position);
        if(favoritos != null) {
            FavoritosAdapter.ViewHolder holder = (FavoritosAdapter.ViewHolder)v.getTag();
            holder.tvLugarNombre.setText(favoritos.getName());
            holder.tvLugarDireccion.setText(favoritos.getAddress());
        }

        return v;
    }

    private class ViewHolder {
        ImageView ivRecomendados;
        TextView tvLugarNombre;
        TextView tvLugarDireccion;
    }
}
