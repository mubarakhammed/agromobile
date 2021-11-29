package com.mubarak.agromobile;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.List;

public class NoteAdapter extends  RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<NoteList> listitems;
    private Context context;

    public NoteAdapter(List<NoteList> listitems, int list_item, Context context) {
        this.listitems = listitems;
        this.context = context;
    }
    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.notelist, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        final NoteList listHomeItem = listitems.get(position);

        holder.title.setText(listHomeItem.getNote());
        holder.description.setText(listHomeItem.getDescription());
        holder.youtubelink = listHomeItem.getYoutubelink();
        holder.pdffile = listHomeItem.getPdflink();
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

        private TextView title, description, descriptionHeading;
        private String youtubelink, pdffile;
        private ImageView youtube, pdf;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.noteTitle);
            description = itemView.findViewById(R.id.noteDescription);

            descriptionHeading = itemView.findViewById(R.id.descriptionHeading);
            descriptionHeading.setPaintFlags(descriptionHeading.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

            youtube = itemView.findViewById(R.id.loadYoutube);
            pdf = itemView.findViewById(R.id.downloadPDF);

            youtube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchYoutubeVideo(context, youtubelink);
                }
            });

            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                    myWebLink.setData(Uri.parse(pdffile));
                    myWebLink.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myWebLink);
                    Log.d("See Me", "onClick: " + pdffile);
                }
            });


        }
    }

    public static void watchYoutubeVideo(Context context, String link){

        Uri webpage = Uri.parse(link);

        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            webpage = Uri.parse("http://" + link);
        }
        Intent appIntent = new Intent(Intent.ACTION_VIEW, webpage);
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                webpage);
        try {
            appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(webIntent);
        }
    }
}
