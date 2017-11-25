package com.rhuangal.myplaces2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhuangal.myplaces2.R;
import com.rhuangal.myplaces2.data.network.entity.CategoriaResponse;

import java.util.List;

/**
 * Created by rober on 24-Nov-17.
 */

public class CategoriaAdapter extends BaseAdapter {

    private Context context;
    private List<CategoriaResponse> listaCategoria;

    public CategoriaAdapter(Context context, List<CategoriaResponse> listaCategoria) {
        this.context = context;
        this.listaCategoria = listaCategoria;
    }

    @Override
    public int getCount() {
        return listaCategoria.size();
    }

    @Override
    public CategoriaResponse getItem(int position) {
        return listaCategoria.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaCategoria.get(position).getId_cat();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.row_categoria, null);
            ViewHolder holder = new ViewHolder();
            holder.tvCatNombre = (TextView)v.findViewById(R.id.tvCatNombre);
            holder.ivCategoria = (ImageView)v.findViewById(R.id.ivCategoria);
            holder.tvCatDes = (TextView)v.findViewById(R.id.tvCatDes);

            v.setTag(holder);
        }
        CategoriaResponse categoria = listaCategoria.get(position);
        if(categoria != null) {
            ViewHolder holder = (ViewHolder)v.getTag();
            holder.tvCatDes.setText(categoria.getDes_cat());
            holder.tvCatNombre.setText(categoria.getNom_cat());
            int ico_id = v.getResources().getIdentifier(categoria.getIco_cat(), "mipmap", context.getPackageName());
            holder.ivCategoria.setImageResource(ico_id);
        }

        return v;
    }

    private class ViewHolder {
        ImageView ivCategoria;
        TextView tvCatDes;
        TextView tvCatNombre;
    }
}
