package com.rccreation.ravindra.sairamproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.MyViewHolder> {

    ArrayList<String> trainname;
    ArrayList<String> trainnum;
    ArrayList<String> time;
    ArrayList<String> arr;
    ArrayList<String> dep;
    ArrayList<String> from;
    ArrayList<String> to;
    ArrayList<String> day;
    ArrayList<String> runs;
    Context context;
    public CustomAdapter2(BetStation betStation, ArrayList<String> trainname, ArrayList<String> trainnum, ArrayList<String> time, ArrayList<String> arr, ArrayList<String> dep, ArrayList<String> from, ArrayList<String> to, ArrayList<String> day, ArrayList<String> runs) {
        this.context = context;
        this.trainname = trainname;
        this.trainnum = trainnum;
        this.arr= arr;
        this.dep = dep;
        this.time = time;
        this.from = from;
        this.to = to;
        this.day = day;
        this.runs = runs;

   }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.betstations, parent, false );
        MyViewHolder vh = new MyViewHolder( v ); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.trainnames.setText( trainname.get( position ) );
        holder.trainnums.setText( trainnum.get( position ) );
        holder.times.setText( time.get( position ) );
        holder.arrs.setText( arr.get( position ) );
        holder.deps.setText( dep.get( position ) );
        holder.froms.setText( from.get( position ) );
        holder.tos.setText( to.get( position ) );
        holder.dayy.setText( day.get( position ) );
        holder.run.setText( runs.get( position ) );




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
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trainnames,trainnums,times,arrs,deps,froms,tos,dayy,run;// init the item view's

        public MyViewHolder(View itemView) {
            super( itemView );

            // get the reference of item view's
            trainnames = (TextView) itemView.findViewById( R.id.stname );
            trainnums = (TextView) itemView.findViewById( R.id.stnum );
            times = (TextView) itemView.findViewById( R.id.tim );
            arrs= (TextView) itemView.findViewById( R.id.arrtime );
            deps= (TextView) itemView.findViewById( R.id.deptime );
            froms= (TextView) itemView.findViewById( R.id.stfrom );
            tos= (TextView) itemView.findViewById( R.id.stto );
            dayy= (TextView) itemView.findViewById( R.id.dayyy );
            run= (TextView) itemView.findViewById( R.id.ru );


        }
    }
}