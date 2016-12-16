package br.com.wgbn.meuguia.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.wgbn.meuguia.Models.Lugar;
import br.com.wgbn.meuguia.R;

/**
 * Adapter para a ListView de lugares
 */
public class LugaresAdapter extends ArrayAdapter<Lugar> {

    private final Context context;
    private final ArrayList<Lugar> mLugares;

    public LugaresAdapter(Context context, ArrayList<Lugar> values) {
        super(context, -1, values);
        this.context = context;
        this.mLugares = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // views
        View rowView = inflater.inflate(R.layout.lista_layout, parent, false);
        TextView nome = (TextView) rowView.findViewById(R.id.nome);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem);

        // item
        Lugar lugar = mLugares.get(position);

        // preenche
        nome.setText(lugar.nome);
        Picasso.with(context).load(lugar.imagem).into(imagem);

        return rowView;
    }

}
