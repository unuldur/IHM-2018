package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import ihm.tydrichova.upmc.fr.ihmclient.model.Plat;

/**
 * Created by Unuldur on 13/05/2018.
 */

public class NoteAdapter extends ArrayAdapter<Plat> {
    public NoteAdapter(@NonNull Context context, int resource, @NonNull List<Plat> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Plat p = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notation_row, parent, false);
        }
        TextView tv = convertView.findViewById(R.id.name_plat_notation);
        RatingBar rb = convertView.findViewById(R.id.ratingBar);
        rb.setMax(5);
        rb.setNumStars(5);
        if (p != null) {
            tv.setText(p.getName());
        }
        return convertView;
    }
}
