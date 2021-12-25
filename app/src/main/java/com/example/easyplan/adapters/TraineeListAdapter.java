package com.example.easyplan.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyplan.data.Trainee;
import com.example.easyplan.R;
import com.example.easyplan.activities.MakePlanActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TraineeListAdapter extends RecyclerView.Adapter<TraineeListAdapter.TraineeViewHolder> {
    private ArrayList<Trainee> trainees;
    private ArrayList<String> traineesID;
    private String trainer_id;
    int[] logos;
    private StorageReference storageReference;

    public TraineeListAdapter(ArrayList<Trainee> trainees, ArrayList<String> traineesID, String trainer_id){
        this.trainees = trainees;
        this.traineesID = traineesID;
        this.trainer_id = trainer_id;

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
        holder.trainee_list_image.setImageResource(logos[position % 4]);
        holder.trainee_list_name.setText(this.trainees.get(position).getName());
        holder.id = this.traineesID.get(position);
        holder.trainer_id = this.trainer_id;

        storageReference = FirebaseStorage.getInstance().getReference("images/"+holder.id);
        try {
            File local_file = File.createTempFile("tempfile", ".jpg" );
            storageReference.getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(local_file.getAbsolutePath());
                    holder.trainee_list_image.setImageBitmap(bitmap);
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

    @Override
    public int getItemCount() {
        return this.trainees.size();
    }

    public static class TraineeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trainee_list_name;
        private CircleImageView trainee_list_image;
        private ImageView trainee_list_mail;
        private CheckBox trainee_list_check;
        int[] logos;
        private String id;
        private String trainer_id;

        public TraineeViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.trainee_list_name = view.findViewById(R.id.trainee_list_name);
            this.trainee_list_image = view.findViewById(R.id.trainee_list_image);
            this.trainee_list_mail = view.findViewById(R.id.trainee_list_mail);
            this.trainee_list_check = view.findViewById(R.id.trainee_list_check);
            this.id = "";
            this.trainer_id = "";

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
            intent.putExtra("trainer id", trainer_id);

            view.getContext().startActivity(intent);
        }
    }
}