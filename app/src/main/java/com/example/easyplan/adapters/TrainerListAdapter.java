package com.example.easyplan.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyplan.data.Trainer;
import com.example.easyplan.R;
import com.example.easyplan.activities.TrainerHomepageActivity;

import java.util.List;

public class TrainerListAdapter extends RecyclerView.Adapter<TrainerListAdapter.ViewHolder> {
    private List<Trainer> trainers;
    private List <String> trainers_ids;

    public TrainerListAdapter(List<Trainer> data , List <String> ids){
       this.trainers = data;
       this.trainers_ids = ids;
    }

    @Override
    public TrainerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trainer_view, parent, false);
        return new ViewHolder(rowItem);
    }


    @Override
    public void onBindViewHolder(TrainerListAdapter.ViewHolder holder, int position) {
        Trainer trainer = trainers.get(position);
        holder.trainer_id = trainers_ids.get(position);
        List<String> targets_to_show = trainer.getTargets();
        if(targets_to_show.contains("Fitness")) holder.trainer_list_fitness.setVisibility(View.VISIBLE);
        if(targets_to_show.contains("Cardio")) holder.trainer_list_cardio.setVisibility(View.VISIBLE);
        if(targets_to_show.contains("Menu Nutrition")) holder.trainer_list_menu.setVisibility(View.VISIBLE);
        if(targets_to_show.contains("Muscle")) holder.trainer_list_muscle.setVisibility(View.VISIBLE);
        holder.trainer_list_name.setText(trainer.getName());
    }

    @Override
    public int getItemCount() {
        return this.trainers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trainer_list_name, trainer_list_rate;
        private ImageView trainer_list_profile_image, trainer_list_menu, trainer_list_fitness,trainer_list_cardio,trainer_list_muscle;
        private CardView specialCard;
        private String trainer_id;


        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.trainer_list_name = view.findViewById(R.id.trainer_list_name);
            this.trainer_list_rate = view.findViewById(R.id.trainer_list_rate);
            this.trainer_list_profile_image = view.findViewById(R.id.trainer_list_profile_image);
            this.specialCard = view.findViewById(R.id.trainer_list_special_card);
            this.trainer_list_menu = view.findViewById(R.id.trainer_list_menu);
            this.trainer_list_fitness = view.findViewById(R.id.trainer_list_fitness);
            this.trainer_list_cardio = view.findViewById(R.id.trainer_list_cardio);
            this.trainer_list_muscle = view.findViewById(R.id.trainer_list_muscle);
            trainer_list_menu.setVisibility(View.GONE);
            trainer_list_fitness.setVisibility(View.GONE);
            trainer_list_cardio.setVisibility(View.GONE);
            trainer_list_muscle.setVisibility(View.GONE);
            trainer_id = "";
        }

        @Override
        public void onClick(View view) {
            Intent move = new Intent(view.getContext(), TrainerHomepageActivity.class);
            move.putExtra("trainer id from firebase" , trainer_id);
            view.getContext().startActivity(move);
        }
    }
}