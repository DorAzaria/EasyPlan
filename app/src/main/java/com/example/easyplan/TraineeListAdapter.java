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

public class TraineeListAdapter extends RecyclerView.Adapter<TraineeListAdapter.ViewHolder> {
    private List<String> data;
    int[] logos;

    public TraineeListAdapter(List<String> data){
        this.data = data;
        logos = new int[4];
        logos[0] = R.drawable.trainer1_logo;
        logos[1] = R.drawable.trainer2_logo;
        logos[2] = R.drawable.trainer3_logo;
        logos[3] = R.drawable.trainer4_logo;
    }

    @Override
    public TraineeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trainee_view, parent, false);
        return new ViewHolder(rowItem);
    }


    @Override
    public void onBindViewHolder(TraineeListAdapter.ViewHolder holder, int position) {

        holder.textView.setText(this.data.get(position));
        holder.imageView.setImageResource(logos[position % 4]);

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView imageView;
        int[] logos;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.textView = view.findViewById(R.id.list_trainee_view_profile_name);
            this.imageView = view.findViewById(R.id.list_trainee_view_profile_image);

            logos = new int[4];
            logos[0] = R.drawable.trainer1_logo;
            logos[1] = R.drawable.trainer2_logo;
            logos[2] = R.drawable.trainer3_logo;
            logos[3] = R.drawable.trainer4_logo;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MakePlanActivity.class);
//            intent.putExtra("name", this.textView.getText());
//            intent.putExtra("image", Integer.toString(logos[getLayoutPosition() % 4]));
            view.getContext().startActivity(intent);
        }
    }
}