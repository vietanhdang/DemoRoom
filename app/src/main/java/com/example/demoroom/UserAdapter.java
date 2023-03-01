package com.example.demoroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> listUser;
    private IClickItemUser iClickItemUser;


    public interface IClickItemUser{
        void updateUser(User user);
    }
    public void setData(List<User> listUser){
        this.listUser = listUser;
        notifyDataSetChanged();
    }


    public UserAdapter(IClickItemUser iClickItemUser) {
        this.iClickItemUser = iClickItemUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = listUser.get(position);
        if(user == null){
            return;
        }else{
            holder.tv_username.setText(user.getUsername());
            holder.tv_address.setText(user.getAddress());
            holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemUser.updateUser(user);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(listUser != null)
            return listUser.size();
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_username;
        private TextView tv_address;
        private Button btnUpdate;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_address = itemView.findViewById(R.id.tv_address);
            btnUpdate = itemView.findViewById(R.id.btn_update);
        }
    }
}
