package com.mubarak.agromobile.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mubarak.agromobile.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserList> listitems;
    private Context context;
    public static  String idp;

    public UserAdapter(List<UserList> listitems, int list_item, Context context) {
        this.listitems = listitems;
        this.context = context;
    }
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.userlist, parent, false);


        return new UserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final UserList listHomeItem = listitems.get(position);

        holder.firstname.setText(listHomeItem.getFirstname());
        holder.lastname.setText(listHomeItem.getLastname());
        holder.email.setText(listHomeItem.getEmail());
        holder.username.setText(listHomeItem.getUsername());
        holder.institution.setText(listHomeItem.getInstitution());

        holder.id = listHomeItem.getId();

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
        private TextView firstname, lastname, email, username, institution;
        private String id;
        private Button messages;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstname = itemView.findViewById(R.id.firstname);
            lastname = itemView.findViewById(R.id.lastname);
            email = itemView.findViewById(R.id.user_email);
            username = itemView.findViewById(R.id.user_username);
            institution = itemView.findViewById(R.id.user_institution);

            messages = itemView.findViewById(R.id.chats);

            messages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

        }
    }
}
