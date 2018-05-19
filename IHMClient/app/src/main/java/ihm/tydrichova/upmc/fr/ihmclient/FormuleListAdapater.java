package ihm.tydrichova.upmc.fr.ihmclient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ihm.tydrichova.upmc.fr.ihmclient.model.Categorie;
import ihm.tydrichova.upmc.fr.ihmclient.model.Formule;
import ihm.tydrichova.upmc.fr.ihmclient.model.Ingredient;
import ihm.tydrichova.upmc.fr.ihmclient.model.Plat;

/**
 * Created by Unuldur on 28/01/2018.
 */

public class FormuleListAdapater extends BaseExpandableListAdapter{

    private Context context;
    private List<Formule> expandableListTitle;
    private Map<Formule, List<RadioGroup>> lists = new HashMap<>();
    private Map<RadioGroup, List<RadioButton>> mapRadio = new HashMap<>();
    private Map<Formule, Boolean> does = new HashMap<>();
    private Menu menu;
    public FormuleListAdapater(Context context, List<Formule> expandableListTitle, Menu menu) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        for (Formule f: expandableListTitle) {
            List<RadioGroup> lr = new ArrayList<>();
            for (int i = 0; i < f.getCompo().keySet().size(); i++) {
                RadioGroup rg = new RadioGroup(context);
                mapRadio.put(rg, new ArrayList<RadioButton>());
                lr.add(rg);
            }
            lists.put(f, lr);
            does.put(f, false);
        }
        this.menu = menu;
    }



    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        int position = -1;
        Map<Categorie, Set<Plat>> compo  = expandableListTitle.get(listPosition).getCompo();
        Object[] cats = compo.keySet().toArray();
        int catsPos = -1;
        while(position != expandedListPosition){
            position ++;
            catsPos ++;
            if(position == expandedListPosition){
                return new Pair<>("- " + ((Categorie)cats[catsPos]).getName(), new Pair<>(true, catsPos));
            }
            int size = compo.get(cats[catsPos]).size();
            if(expandedListPosition > position && expandedListPosition <= position + size){
                return new Pair<>("       - " + compo.get(cats[catsPos]).toArray()[expandedListPosition - position - 1].toString(), new Pair<>(false, catsPos));
            }
            position += size;
        }
        return new Pair<>("- " + ((Categorie)cats[0]).getName(), new Pair<>(true, catsPos));
    }

    @Override
    public int getChildrenCount(int listPosition) {
        int size = 0;
        for (Map.Entry<Categorie, Set<Plat>> entry:expandableListTitle.get(listPosition).getCompo().entrySet()){
            size += entry.getValue().size() + 1;
        }

        return size;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Pair<String, Pair<Boolean, Integer>> p = (Pair<String, Pair<Boolean, Integer>>)getChild(listPosition, expandedListPosition);
        final String expandedListText = p.first;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.menu_item, null);
        }
        LinearLayout ll = convertView.findViewById(R.id.linearLayoutMenuItem);
        if(p.second.first){
            TextView tv = new TextView(convertView.getContext());
            tv.setText(expandedListText);
            if(!does.get(expandableListTitle.get(listPosition))){
                ll.addView(tv);
            }
        }else{
            RadioButton rb =new RadioButton(convertView.getContext());
            rb.setText(expandedListText);

            if(!does.get(expandableListTitle.get(listPosition))) {
                RadioGroup rg = lists.get(expandableListTitle.get(listPosition)).get(p.second.second);
                rb.setId(rg.getChildCount());
                rg.addView(rb);
                mapRadio.get(rg).add(rb);
                if (rg.getParent() == null) {
                    ll.addView(lists.get(expandableListTitle.get(listPosition)).get(p.second.second));
                }
            }
        }
        if(expandedListPosition == getChildrenCount(listPosition) - 1){
            does.put(expandableListTitle.get(listPosition), true);
        }
        return convertView;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final Formule formule = (Formule) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.menu_group, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.name_text_view);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(formule.getName());
        TextView prixTextView = convertView.findViewById(R.id.prix);
        prixTextView.setText(String.valueOf(formule.getPrix()));
        Button b = convertView.findViewById(R.id.button_menu_group);
        b.setFocusable(false);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                List<RadioGroup> rgs = lists.get(formule);
                String description = "";
                for(RadioGroup rg : rgs){
                    if(rg.getCheckedRadioButtonId() == -1) return;
                    description += mapRadio.get(rg).get(rg.getCheckedRadioButtonId()).getText() + "\n";
                }
                Log.d("lalala", description);
                MainActivity.SessionData.addPlat(new Plat(formule.getName(), formule.getPrix(), description, new HashMap<Ingredient, Boolean>(), false));
                menu.findItem(R.id.menu_price).setTitle(((Float) MainActivity.SessionData.getPrix()).toString());
                menu.findItem(R.id.menu_commande).setEnabled(MainActivity.SessionData.getPrix() > 0);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setMessage(formule.getName()+" a été ajouté(e) à la commande");

                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "Consulter la commande",  new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(view.getContext(), CommandeActivity.class);
                                view.getContext().startActivity(intent);
                            }
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
