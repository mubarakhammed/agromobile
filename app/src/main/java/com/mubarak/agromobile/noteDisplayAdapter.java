package com.mubarak.agromobile;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class noteDisplayAdapter extends RecyclerView.Adapter<noteDisplayAdapter.ViewHolder> {

    private List<noteDisplayList> listitems;
    private Context context;


    public noteDisplayAdapter(List<noteDisplayList> listitems, int list_item, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public noteDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.final_note_list, parent, false);


        return new noteDisplayAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull noteDisplayAdapter.ViewHolder holder, int position) {
        final noteDisplayList listHomeItem = listitems.get(position);
        holder.title.setText(listHomeItem.getTitle());
        holder.pdflink = listHomeItem.getPdfLink();
        holder.description.setText(listHomeItem.getDescription());
    }


    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description;
        private String pdflink;
        private ImageView pdf;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.finalnoteTitle);
            description = itemView.findViewById(R.id.notedesc);
            pdf = itemView.findViewById(R.id.finaldownloadPDF);
            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                    myWebLink.setData(Uri.parse(pdflink));
                    myWebLink.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myWebLink);
                    Log.d("See Me", "onClick: " + pdflink);
                }
            });

        }
    }
}
