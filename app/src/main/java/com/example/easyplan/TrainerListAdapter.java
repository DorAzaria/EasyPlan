package com.example.easyplan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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

        holder.textView.setText(this.data.get(position));
        holder.imageView.setImageResource(logos[position % 4]);

        if(position == 0) {
            holder.specialCard.setVisibility(View.VISIBLE);
            holder.specialText.setVisibility(View.VISIBLE);
        }
        if(position == 1 || position == 2) {
            holder.specialCard.setVisibility(View.VISIBLE);
            holder.specialText.setVisibility(View.VISIBLE);
            holder.specialText.setText("Recommended");
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView, specialText;
        private ImageView imageView;
        private CardView specialCard;
        int[] logos;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.textView = view.findViewById(R.id.trainer_list_name);
            this.specialText = view.findViewById(R.id.trainer_list_special_detail);
            this.imageView = view.findViewById(R.id.profile_image);
            this.specialCard = view.findViewById(R.id.trainer_list_special_card);
            this.specialCard.setVisibility(View.INVISIBLE);
            this.specialText.setVisibility(View.INVISIBLE);
            logos = new int[4];
            logos[0] = R.drawable.trainer1_logo;
            logos[1] = R.drawable.trainer2_logo;
            logos[2] = R.drawable.trainer3_logo;
            logos[3] = R.drawable.trainer4_logo;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), TrainerHomepageActivity.class);
            intent.putExtra("name", this.textView.getText());
            intent.putExtra("image", Integer.toString(logos[getLayoutPosition() % 4]));
            view.getContext().startActivity(intent);
        }
    }
}