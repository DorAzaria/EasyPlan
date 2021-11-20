package com.example.easyplan.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyplan.R;
import com.example.easyplan.activities.TrainerHomepageActivity;

import java.util.List;

public class TrainerListAdapter extends RecyclerView.Adapter<TrainerListAdapter.ViewHolder> {
    private List<String> data;
    int[] logos;

    public TrainerListAdapter(List<String> data){
        this.data = data;
        logos = new int[4];
        logos[0] = R.drawable.trainer1_logo;
        logos[1] = R.drawable.trainer2_logo;
        logos[2] = R.drawable.trainer3_logo;
        logos[3] = R.drawable.trainer4_logo;
    }

    @Override
    public TrainerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trainer_view, parent, false);
        return new ViewHolder(rowItem);
    }


    @Override
    public void onBindViewHolder(TrainerListAdapter.ViewHolder holder, int position) {

        holder.trainer_list_name.setText(this.data.get(position));
        holder.trainer_list_profile_image.setImageResource(logos[position % 4]);

        if(position == 0) {
            holder.trainer_list_rate.setText("5.0");
        }
        if(position == 1 || position == 2) {
            holder.trainer_list_rate.setText("4.0");
        }
        else {
            holder.trainer_list_rate.setText("3.5");
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trainer_list_name, trainer_list_rate;
        private ImageView trainer_list_profile_image, trainer_list_menu, trainer_list_fitness,trainer_list_cardio,trainer_list_muscle;
        private CardView specialCard;
        int[] logos;

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

            logos = new int[4];
            logos[0] = R.drawable.trainer1_logo;
            logos[1] = R.drawable.trainer2_logo;
            logos[2] = R.drawable.trainer3_logo;
            logos[3] = R.drawable.trainer4_logo;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), TrainerHomepageActivity.class);
            intent.putExtra("name", this.trainer_list_name.getText());
            intent.putExtra("image", Integer.toString(logos[getLayoutPosition() % 4]));
            view.getContext().startActivity(intent);
        }
    }
}