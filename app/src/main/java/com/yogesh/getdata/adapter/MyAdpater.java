package com.yogesh.getdata.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yogesh.getdata.R;
import com.yogesh.getdata.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class MyAdpater extends RecyclerView.Adapter<MyAdpater.ViewHolder> {
    Context context;
    List<User> list  =  new ArrayList<>();

    public MyAdpater(Context context,List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.desine_of_recyle_lyout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);
        holder.name.setText(user.getName());
        holder.last.setText(user.getLast());
        holder.father.setText(user.getFather());
        holder.mother.setText(user.getMother());
        holder.email.setText(user.getEmail());
        holder.number.setText(user.getNumber());
        holder.gender.setText(user.getGender());
        holder.address.setText(user.getAddress());
        holder.country.setText(user.getCountry());
        holder.date.setText(user.getCreateDate()==null?"":user.getCreateDate().getTime()%100000000000L+"");


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  name,last,father,mother,email,number,address,gender,country,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tname);
          last = itemView.findViewById(R.id.textlast);
            father = itemView.findViewById(R.id.textfather);
            mother = itemView.findViewById(R.id.textmother);
           email = itemView.findViewById(R.id.textemail);
           number = itemView.findViewById(R.id.textnumber);
           address = itemView.findViewById(R.id.texaddress);
            gender = itemView.findViewById(R.id.textgender);
          country = itemView.findViewById(R.id.textCountry);
            date = itemView.findViewById(R.id.textData);

        }
    }
}
