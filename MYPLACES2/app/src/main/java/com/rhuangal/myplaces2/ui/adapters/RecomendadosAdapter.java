package com.rhuangal.myplaces2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhuangal.myplaces2.R;
import com.rhuangal.myplaces2.data.network.entity.RecomendadosResponse;

import java.util.List;

/**
 * Created by rober on 24-Nov-17.
 */

public class RecomendadosAdapter extends BaseAdapter{

    private Context context;
    private List<RecomendadosResponse> listaRecomendados;

    public RecomendadosAdapter(Context context, List<RecomendadosResponse> listaRecomendados) {
        this.context = context;
        this.listaRecomendados = listaRecomendados;
    }

    @Override
    public int getCount() {
        return listaRecomendados.size();
    }

    @Override
    public RecomendadosResponse getItem(int position) {
        return listaRecomendados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaRecomendados.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.row_recomendados, null);
            RecomendadosAdapter.ViewHolder holder = new RecomendadosAdapter.ViewHolder();
            holder.tvLugarTipo = (TextView)v.findViewById(R.id.tvLugarTipo);
            holder.tvLugarNombre = (TextView)v.findViewById(R.id.tvLugarNombre);
            holder.tvLugarDireccion = (TextView)v.findViewById(R.id.tvLugarDireccion);
            v.setTag(holder);
        }
        RecomendadosResponse recomendados = listaRecomendados.get(position);
        if(recomendados != null) {
            RecomendadosAdapter.ViewHolder holder = (RecomendadosAdapter.ViewHolder)v.getTag();
            holder.tvLugarTipo.setText(recomendados.getType());
            holder.tvLugarNombre.setText(recomendados.getName());
            holder.tvLugarDireccion.setText(recomendados.getAddress());
        }

        return v;
    }

    private class ViewHolder {
        ImageView ivRecomendados;
        TextView tvLugarNombre;
        TextView tvLugarDireccion;
        TextView tvLugarTipo;
    }
    
}
