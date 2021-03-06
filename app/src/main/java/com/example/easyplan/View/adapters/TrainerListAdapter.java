package com.example.easyplan.View.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyplan.Controller.FirebaseData;
import com.example.easyplan.Model.Trainer;
import com.example.easyplan.R;
import com.example.easyplan.View.TrainerHomepageActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrainerListAdapter extends RecyclerView.Adapter<TrainerListAdapter.ViewHolder> {
    private List<Trainer> trainers;
    private FirebaseData model;






    /// THIS CONSTRUCTOR GETS ALL TRAINERS AS ARRAYLIST AND ALSO THEIR IDS
    public TrainerListAdapter(List<Trainer> data ){
       this.trainers = data;
       this.model = new FirebaseData();
    }






    /// FOR EACH TRAINER, INIT A NEW VIEW USING INFLATE METHOD.
    @Override
    public TrainerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trainer_view, parent, false);
        return new ViewHolder(rowItem);
    }







    /// FOR EACH VIEW (TRAINER) SET THE RELEVANT DATA
    @Override
    public void onBindViewHolder(TrainerListAdapter.ViewHolder holder, int position) {
        Trainer trainer = trainers.get(position);
        holder.trainer_id = trainer.getNotifications();
        List<String> targets_to_show = trainer.getTargets();
        if(targets_to_show.contains("Fitness")) holder.trainer_list_fitness.setVisibility(View.VISIBLE);
        if(targets_to_show.contains("Cardio")) holder.trainer_list_cardio.setVisibility(View.VISIBLE);
        if(targets_to_show.contains("Menu Nutrition")) holder.trainer_list_menu.setVisibility(View.VISIBLE);
        if(targets_to_show.contains("Muscle")) holder.trainer_list_muscle.setVisibility(View.VISIBLE);
        holder.trainer_list_name.setText(trainer.getName());
        holder.trainer_list_rate.setText(new DecimalFormat("#.#").format(trainer.getRate()));

        try {
            File local_file = File.createTempFile("tempfile", ".jpg" );
            model.getStorageReference(holder.trainer_id).getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(local_file.getAbsolutePath());
                    holder.trainer_list_profile_image.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }






    /// GET TOTAL NUMBER OF TRAINERS
    @Override
    public int getItemCount() {
        return this.trainers.size();
    }






    /// INIT ALL RELEVANT OBJECTS
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trainer_list_name, trainer_list_rate;
        private ImageView trainer_list_menu, trainer_list_fitness,trainer_list_cardio,trainer_list_muscle;
        private CardView specialCard;
        private String trainer_id;
        private CircleImageView trainer_list_profile_image;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.trainer_list_name = view.findViewById(R.id.trainer_list_name);
            this.trainer_list_rate = view.findViewById(R.id.trainer_list_rate);
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
            ((Activity) view.getContext()).finish();
        }
    }
}