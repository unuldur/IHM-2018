package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ihm.tydrichova.upmc.fr.ihmclient.model.Plat;

/**
 * Created by Magdalena on 5/10/2018.
 */

public class PlatArrayAdapter extends ArrayAdapter<Plat> {
    private final int resource;

    public PlatArrayAdapter(Context context, int resource, List<Plat> objects){
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout platView;

        Plat plat = getItem(position);

        //Inflate the view:
        if(convertView == null){
            platView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, platView, true);
        }
        else{
            platView = (LinearLayout) convertView;
        }
        TextView platName = (TextView)platView.findViewById(R.id.listText);
        TextView platPrice = (TextView)platView.findViewById(R.id.listPrice);

        platName.setText(plat.getName());
        platPrice.setText(plat.getPrix()+" EUR");

        return platView;
    }
}

