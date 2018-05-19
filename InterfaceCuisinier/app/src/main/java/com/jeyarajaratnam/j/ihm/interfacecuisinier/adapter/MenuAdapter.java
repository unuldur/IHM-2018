package com.jeyarajaratnam.j.ihm.interfacecuisinier.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeyarajaratnam.j.ihm.interfacecuisinier.CommandState;
import com.jeyarajaratnam.j.ihm.interfacecuisinier.Menu;
import com.jeyarajaratnam.j.ihm.interfacecuisinier.R;

import java.util.ArrayList;

/**
 * Created by Jehyanka on 14/05/2018.
 */

public class MenuAdapter extends ArrayAdapter<Menu> {

    public MenuAdapter(Context context, ArrayList<Menu> menus) {
        super(context, 0, menus);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos=position;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_dish,parent, false);
        }

        MenuViewHolder viewHolder = (MenuViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MenuViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content_tv);
            viewHolder.waitingTime = (TextView) convertView.findViewById(R.id.waiting_tv);
            viewHolder.commandNb = (TextView) convertView.findViewById(R.id.nb_tv);
            viewHolder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
            viewHolder.details_ll = (LinearLayout) convertView.findViewById(R.id.details_ll);
            viewHolder.title_ll = (LinearLayout) convertView.findViewById(R.id.title_ll);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Menu menu = getItem(position);

        final LinearLayout titleLL=viewHolder.title_ll;
        final LinearLayout detailsLL=viewHolder.details_ll;
        final TextView content=viewHolder.content;
        final ImageView arrowImg=viewHolder.arrow;
        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(menu.getName());
        viewHolder.content.setText(menu.getContent());
        viewHolder.waitingTime.setText(menu.getWaitingTime()+" min");
        viewHolder.commandNb.setText("C0"+menu.getNumber());
        if(content.getText().length()>0)
            arrowImg.setVisibility(View.VISIBLE);

        Drawable drawa=getContext().getResources().getDrawable(R.drawable.blue_background,null);
        Drawable drawa2=getContext().getResources().getDrawable(R.drawable.details_blue_bg,null);
        if(menu.getState().equals(CommandState.ORDERED)) {
            drawa = getContext().getResources().getDrawable(R.drawable.green_background, null);
            drawa2=getContext().getResources().getDrawable(R.drawable.details_green_bg,null);
        }
        viewHolder.title_ll.setBackground(drawa);

        viewHolder.details_ll.setBackground(drawa2);
        viewHolder.details_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Menu d= getItem(pos);

                if(content.getVisibility()==View.GONE) {

                    arrowImg.setImageDrawable(getContext().getDrawable(R.drawable.arrow_haut));
                    if (content.getText().length() > 0)
                        content.setVisibility(View.VISIBLE);
                }
                else {
                    content.setVisibility(View.GONE);
                    arrowImg.setImageDrawable(getContext().getDrawable(R.drawable.arrow_bas));
                }

            }
        });
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Menu d= getItem(pos);

                String title="C"+d.getNumber();
                String positive="PREPARER";
                String negative="ANNULER";
                final boolean preparing=d.getState().equals(CommandState.PREPARING);
                if(preparing)
                    positive="PRÊT";

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                builder.setTitle(title)
                        .setMessage(d.getName())
                        .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            public void onClick(DialogInterface dialog, int which) {
                                if (preparing) {
                                    remove(d);
                                }
                                else{
                                    d.setState(CommandState.PREPARING);

                                    titleLL.setBackground(getContext().getResources().getDrawable(R.drawable.blue_background,null));
                                    detailsLL.setBackground(getContext().getResources().getDrawable(R.drawable.details_blue_bg,null));;
                                }
                            }
                        })
                        .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });



        return convertView;
    }

    private class MenuViewHolder{
        public TextView name;
        TextView content;
        TextView waitingTime;
        TextView commandNb;
        LinearLayout details_ll;
        LinearLayout title_ll;
        ImageView arrow;
    }
}
