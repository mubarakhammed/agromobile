package com.mubarak.agromobile.seperate;


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

import com.bumptech.glide.Glide;
import com.mubarak.agromobile.R;

import java.util.ArrayList;
import java.util.List;



public class CustomRecyclerview extends RecyclerView.Adapter<CustomRecyclerview.ViewHolder> {

    Context context;
    List<Repo> arrayList;
    public static String yt, pd;

    public CustomRecyclerview(Context context, List<Repo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notelist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Repo repo = arrayList.get(position);

        holder.title.setText(repo.getTitle());
        holder.description.setText(repo.getDescription());



        holder.youtubelink = repo.getYoutube_link();
        holder.pdffile = repo.getPdf_link();

//        Glide.with(context)
//                .load(repo.getThumbnail())
//                .into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description, descriptionHeading, ty, dp;
        private String youtubelink, pdffile;
        private ImageView youtube, pdf;
        public ViewHolder(View itemView) {
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
                    Log.d("See Me", "onClick: " + youtubelink);
                    watchYoutubeVideo(context, youtubelink);

                }
            });

            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("See Me", "onClick: " + pdffile);
                    Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                    myWebLink.setData(Uri.parse(pdffile));
                    myWebLink.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myWebLink);

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

