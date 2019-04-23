package com.rccreation.ravindra.sairamproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class CustomAdapter1 extends RecyclerView.Adapter<CustomAdapter1.MyViewHolder> {

    ArrayList<String> trainname;
    ArrayList<String> scharr;
    ArrayList<String> schdep;
    ArrayList<String> actarr;
    ArrayList<String> actdep;



    Context context;




    public CustomAdapter1(ArrayList<String> trainname, ArrayList<String> scharr, ArrayList<String> schdep, ArrayList<String> actarr, ArrayList<String> actdep) {
        this.context = context;
        this.trainname = trainname;
        this.scharr = scharr;
        this.schdep= schdep;
        this.actarr = actarr;
        this.actdep = actdep;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.lives, parent, false );
        MyViewHolder vh = new MyViewHolder( v ); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.trainnames.setText( trainname.get( position ) );
        holder.scharrs.setText( scharr.get( position ) );
        holder.schdeps.setText( schdep.get( position ) );
        holder.actarrs.setText( actarr.get( position ) );
        holder.actdeps.setText( actdep.get( position ) );


        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText( context, trainname.get( position ), Toast.LENGTH_SHORT ).show();
            }
        } );

    }


    @Override
    public int getItemCount() {
        return trainname.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trainnames,scharrs,schdeps,actarrs,actdeps;// init the item view's

        public MyViewHolder(View itemView) {
            super( itemView );

            // get the reference of item view's
            trainnames = (TextView) itemView.findViewById( R.id.trainnamess );
            scharrs = (TextView) itemView.findViewById( R.id.sarr );
            schdeps = (TextView) itemView.findViewById( R.id.sdep );
            actarrs= (TextView) itemView.findViewById( R.id.aarr );
            actdeps= (TextView) itemView.findViewById( R.id.adep );

        }
    }
}