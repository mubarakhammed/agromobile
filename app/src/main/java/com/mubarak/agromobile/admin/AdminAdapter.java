package com.mubarak.agromobile.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mubarak.agromobile.NoteList;
import com.mubarak.agromobile.R;

import java.util.List;

public class AdminAdapter extends  RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    private List<Listing> listitems;
    private Context context;
    public static  String idp;

    public AdminAdapter(List<Listing> listitems, int list_item, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.admin_note_list, parent, false);


        return new AdminAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {
        final Listing listHomeItem = listitems.get(position);

        holder.title.setText(listHomeItem.getNote());
        holder.description.setText(listHomeItem.getDescription());
        holder.youtube.setText(listHomeItem.getYoutubelink());
        holder.pdf.setText(listHomeItem.getPdflink());
        holder.noteId = listHomeItem.getId();


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

        private TextView title, description, youtube, pdf;
        private Button update;
        private String noteId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.adminnoteTitle);
            description = itemView.findViewById(R.id.adminnoteDescription);
            youtube = itemView.findViewById(R.id.adminyoutubeLink);
            pdf = itemView.findViewById(R.id.adminpdfLink);
            update = itemView.findViewById(R.id.updateNote);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idp = noteId;
                    Intent n_act = new Intent(context, AdminUpdateNote.class);
                    n_act.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(n_act);

                }
            });

        }
    }
}