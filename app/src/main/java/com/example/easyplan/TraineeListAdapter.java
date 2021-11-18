package com.example.easyplan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

        holder.trainee_list_name.setText(this.data.get(position));
        holder.trainee_list_image.setImageResource(logos[position % 4]);

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trainee_list_name;
        private ImageView trainee_list_image, trainee_list_mail;
        private CheckBox trainee_list_check;
        int[] logos;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.trainee_list_name = view.findViewById(R.id.trainee_list_name);
            this.trainee_list_image = view.findViewById(R.id.trainee_list_image);
            this.trainee_list_mail = view.findViewById(R.id.trainee_list_mail);
            this.trainee_list_check = view.findViewById(R.id.trainee_list_check);

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