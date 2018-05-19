package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.Context;
import android.graphics.ColorSpace;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ihm.tydrichova.upmc.fr.ihmclient.model.Allergene;

/**
 * Created by Magdalena on 5/10/2018.
 */

public class FilterAdapter  extends BaseAdapter {

    private Context context;
    public static ArrayList<Allergene> allergenes;


    public FilterAdapter(Context context, ArrayList<Allergene> modelArrayList) {

        this.context = context;
        this.allergenes = modelArrayList;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return allergenes.size();
    }

    @Override
    public Object getItem(int position) {
        return allergenes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int itemIndex, View convertView, ViewGroup viewGroup) {

        ListViewItemViewHolder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (ListViewItemViewHolder) convertView.getTag();
        }else
        {
            convertView = View.inflate(context, R.layout.filter_row_layout, null);

            CheckBox listItemCheckbox = (CheckBox) convertView.findViewById(R.id.ingredientCheckBox);

            TextView listItemText = (TextView) convertView.findViewById(R.id.listText);

            viewHolder = new ListViewItemViewHolder(convertView);

            viewHolder.setItemCheckbox(listItemCheckbox);

            viewHolder.setItemTextView(listItemText);

            convertView.setTag(viewHolder);


        }

        Allergene allergene = allergenes.get(itemIndex);
        viewHolder.getItemCheckbox().setChecked(false);
        viewHolder.getItemTextView().setText(allergene.toString());

        return convertView;
    }

    private class ListViewItemViewHolder extends RecyclerView.ViewHolder {

        private CheckBox itemCheckbox;

        private TextView itemTextView;

        public ListViewItemViewHolder(View itemView) {
            super(itemView);
        }

        public CheckBox getItemCheckbox() {
            return itemCheckbox;
        }

        public void setItemCheckbox(CheckBox itemCheckbox) {
            this.itemCheckbox = itemCheckbox;
        }

        public TextView getItemTextView() {
            return itemTextView;
        }

        public void setItemTextView(TextView itemTextView) {
            this.itemTextView = itemTextView;
        }
    }


}
