package com.example.easyplan.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.easyplan.Data.Trainee;
import com.example.easyplan.R;
import com.example.easyplan.activities.MakePlanActivity;

import java.util.ArrayList;
import java.util.List;

public class TraineeListAdapter extends RecyclerView.Adapter<TraineeListAdapter.TraineeViewHolder> {
    private ArrayList<Trainee> trainees;
    private ArrayList<String> traineesID;
    int[] logos;

    public TraineeListAdapter(ArrayList<Trainee> trainees, ArrayList<String> traineesID){
        this.trainees = trainees;
        this.traineesID = traineesID;
        logos = new int[4];
        logos[0] = R.drawable.trainer1_logo;
        logos[1] = R.drawable.trainer2_logo;
        logos[2] = R.drawable.trainer3_logo;
        logos[3] = R.drawable.trainer4_logo;
    }

    @Override
    public TraineeListAdapter.TraineeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trainee_view, parent, false);
        return new TraineeViewHolder(rowItem);
    }


    @Override
    public void onBindViewHolder(TraineeListAdapter.TraineeViewHolder holder, int position) {

        holder.trainee_list_name.setText(this.trainees.get(position).getName());
        holder.trainee_list_image.setImageResource(logos[position % 4]);
        holder.id = this.traineesID.get(position);

    }

    @Override
    public int getItemCount() {
        return this.trainees.size();
    }

    public static class TraineeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trainee_list_name;
        private ImageView trainee_list_image, trainee_list_mail;
        private CheckBox trainee_list_check;
        int[] logos;
        private String id;

        public TraineeViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.trainee_list_name = view.findViewById(R.id.trainee_list_name);
            this.trainee_list_image = view.findViewById(R.id.trainee_list_image);
            this.trainee_list_mail = view.findViewById(R.id.trainee_list_mail);
            this.trainee_list_check = view.findViewById(R.id.trainee_list_check);
            this.id = "";

            logos = new int[4];
            logos[0] = R.drawable.trainer1_logo;
            logos[1] = R.drawable.trainer2_logo;
            logos[2] = R.drawable.trainer3_logo;
            logos[3] = R.drawable.trainer4_logo;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MakePlanActivity.class);
            intent.putExtra("trainee id from firebase", id);
            view.getContext().startActivity(intent);
        }
    }
}