package com.example.fillmedicalrecord.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.models.StudentModel;
import com.example.fillmedicalrecord.interfaces.ItemHomeListener;
import com.example.fillmedicalrecord.models.user_data;

import java.util.ArrayList;
import java.util.Objects;

public class admin_home_adapter extends RecyclerView.Adapter<admin_home_adapter.MyWorkHolder> {

    ArrayList<StudentModel> list;
    ArrayList<user_data> data_list;
    private final ItemHomeListener itemListener;
    Activity activity;

    public admin_home_adapter(ArrayList<StudentModel> students_list, Activity activity, ItemHomeListener itemListener,ArrayList<user_data> data_list) {
        this.list = students_list;
        this.data_list = data_list;
        this.activity = activity;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public MyWorkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_info, null, false);
        return new MyWorkHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWorkHolder holder, @SuppressLint("RecyclerView") int position) {
        StudentModel c = list.get(position);
        user_data user = new user_data() ;

        holder.first_nm.setText(c.getFirst_name());
        holder.last_nm.setText(c.getLast_name());
        holder.address.setText(c.getAddress());
        holder.add_date.setText(c.getAdd_Date());
        holder.health.setText(c.getHealth_conditions());
        holder.height.setText(c.getHight());
        holder.phone.setText(c.getPhone_number());
        holder.allergies.setText(c.getAllergies());
        holder.email.setText(c.getUser_email());
        holder.device_id.setText(c.getDevice_id());
        holder.section.setText(c.getSection());
        holder.mclass.setText(c.getMclass());
        holder.blood_type.setText(c.getBlood_type());
        holder.wight.setText(c.getWight());
        holder.allergies.setText(c.getAllergies());

        for (int i = 0; i < data_list.size(); i++){
            if (Objects.equals(data_list.get(i).getWristband_id(), c.getDevice_id())){
                user = data_list.get(i);
                holder.body_temp.setText(user.getBody_temp());
                holder.heart_rate.setText(user.getHeart_rate());
                break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyWorkHolder extends RecyclerView.ViewHolder {
        TextView first_nm, last_nm, address, mclass, section, height, wight, email, device_id, add_date, heart_rate, body_temp, health, phone, allergies,blood_type;

        CardView cardView;

        public MyWorkHolder(@NonNull View itemView) {
            super(itemView);

            first_nm = itemView.findViewById(R.id.first_name);
            last_nm = itemView.findViewById(R.id.last_name);
            address = itemView.findViewById(R.id.address);
            mclass = itemView.findViewById(R.id.mclass);
            section = itemView.findViewById(R.id.section);
            height = itemView.findViewById(R.id.hight);
            health = itemView.findViewById(R.id.health_conditions);
            heart_rate = itemView.findViewById(R.id.heart_rate);
            wight = itemView.findViewById(R.id.wight);
            email = itemView.findViewById(R.id.user_email);
            device_id = itemView.findViewById(R.id.device_id);
            add_date = itemView.findViewById(R.id.add_date);
            body_temp = itemView.findViewById(R.id.body_temp);
            phone = itemView.findViewById(R.id.phone_number);
            allergies = itemView.findViewById(R.id.allergies);
            blood_type = itemView.findViewById(R.id.blood_type);

            cardView = itemView.findViewById(R.id.main_card);

            cardView.setOnClickListener(view -> itemListener.onItemHomeClick(getAdapterPosition()));

        }
    }

}