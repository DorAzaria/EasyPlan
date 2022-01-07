package com.example.easyplan.View.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyplan.Controller.FirebaseData;
import com.example.easyplan.Model.Trainee;
import com.example.easyplan.R;
import com.example.easyplan.View.MakePlanActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TraineeListAdapter extends RecyclerView.Adapter<TraineeListAdapter.TraineeViewHolder> {
    private ArrayList<Trainee> trainees;
    private ArrayList<String> traineesID;
    private FirebaseData model;
    private String trainer_id;

    public TraineeListAdapter(ArrayList<Trainee> trainees, ArrayList<String> traineesID, String trainer_id){
        this.trainees = trainees;
        this.traineesID = traineesID;
        this.trainer_id = trainer_id;
        this.model = new FirebaseData();
    }

    @Override
    public TraineeListAdapter.TraineeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trainee_view, parent, false);
        return new TraineeViewHolder(rowItem);
    }


    @Override
    public void onBindViewHolder(TraineeListAdapter.TraineeViewHolder holder, int position) {
        Trainee trainee = trainees.get(position);

        holder.trainee_list_name.setText(trainee.getName());
        holder.id = this.traineesID.get(position);
        holder.trainer_id = this.trainer_id;

        holder.trainee_list_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_trainer = trainee.getPhone_number();
                Intent move = new Intent(Intent.ACTION_DIAL);
                move.setData(Uri.parse("tel:"+phone_trainer));
                holder.itemView.getContext().startActivity(move);
            }
        });


        model.getPlanReference(holder.id).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String trainee_plan_date = snapshot.child("date").getValue(String.class);
                if (trainee_plan_date != null) {
                holder.plan_date.setText(trainee_plan_date);
                }
                else {
                    holder.plan_date.setText("The plan has not yet been defined");
                    holder.trainee_list_check.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        try {
            File local_file = File.createTempFile("tempfile", ".jpg" );
            model.getStorageReference(holder.id).getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
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

        holder.trainee_list_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Send email", "");


                model.getUserReference(model.getID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String trainer_name = snapshot.child("name").getValue(String.class);
                        // status is the id of the trainer.

                        String[] TO = {trainee.getEmail()};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");


                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New message from " + trainer_name + " || EasyPlan");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi "+trainee.getName()+",\n");

                        try {
                            holder.itemView.getContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            Log.i("Finished sending email...", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(holder.itemView.getContext(),
                                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.trainees.size();
    }

    public static class TraineeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trainee_list_name , plan_date;
        private CircleImageView trainee_list_image;
        private ImageView trainee_list_mail, trainee_list_phone;
        private CardView trainee_list_check;
        private String id;
        private String trainer_id;

        public TraineeViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.trainee_list_name = view.findViewById(R.id.trainee_list_name);
            this.trainee_list_image = view.findViewById(R.id.trainee_list_image);
            this.trainee_list_mail = view.findViewById(R.id.trainee_list_mail);
            this.trainee_list_check = view.findViewById(R.id.trainee_list_check);
            this.trainee_list_check.setVisibility(View.GONE);
            this.trainee_list_phone = view.findViewById(R.id.trainee_list_phone);
            this.plan_date = view.findViewById(R.id.plan_date);

            this.id = "";
            this.trainer_id = "";
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