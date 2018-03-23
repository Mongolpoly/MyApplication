package com.example.java.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends BaseAdapter {

    ArrayList<ArrayList> listItem;
    Context mContext;

    public CustomList(Context mContext, ArrayList<ArrayList> listItem) {
        this.mContext = mContext;
        this.listItem = listItem;
    }

    public int getCount() {
        return listItem.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    public View getView(int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_custom_list, viewGroup, false);
        RelativeLayout rl = (RelativeLayout) row.findViewById(R.id.rl);
        TextView idsala = (TextView) row.findViewById(R.id.idsala);
        TextView nombresala = (TextView) row.findViewById(R.id.sala);
        TextView jugadores = (TextView) row.findViewById(R.id.jugadores);
        TextView numsala = (TextView) row.findViewById(R.id.numsala);
        row.setTag(listItem.get(position).get(0).toString());
        idsala.setText(listItem.get(position).get(0).toString());
        nombresala.setText(listItem.get(position).get(1).toString());
        if(position!=0) {
            numsala.setText(String.valueOf(position));
            jugadores.setText(listItem.get(position).get(3).toString() + "/" + listItem.get(position).get(2).toString());
            rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.listado));
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rl.setBackground(ContextCompat.getDrawable(mContext,R.drawable.newgamebutton));
            }else{
                rl.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.newgamebutton));
            }
            numsala.setText("");
            jugadores.setText("");
        }
        return row;
    }
}