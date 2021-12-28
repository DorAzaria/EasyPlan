package com.example.easyplan.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyplan.activities.TraineeHomepageActivity;
import com.example.easyplan.data.Trainee;
import com.example.easyplan.R;
import com.example.easyplan.activities.MakePlanActivity;
import com.example.easyplan.data.Trainer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private String trainer_id;
    private StorageReference storageReference;
    private DatabaseReference reference;






/// THIS CONSTRUCTOR GETS ALL TRAINEES WHOS MEMBERSHIPPED WITH THE TRAINER AS ARRAYLIST
/// IT ALSO GETS THE TRAINEE ID (EACH ONE OF THEM) AND THE CURRENT TRAINER ID.
    public TraineeListAdapter(ArrayList<Trainee> trainees, ArrayList<String> traineesID, String trainer_id){
        this.trainees = trainees;
        this.traineesID = traineesID;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        this.trainer_id = trainer_id;

    }






//// FOR EACH TRAINEE, INIT A NEW VIEW USING INFLATE METHOD.
    @Override
    public TraineeListAdapter.TraineeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trainee_view, parent, false);
        return new TraineeViewHolder(rowItem);
    }






/// FOR EACH VIEW (TRAINEE) SET THE RELEVANT DATA: (IMAGE, NAME, DATE, PHONE CALL AND EMAIL).
    @Override
    public void onBindViewHolder(TraineeListAdapter.TraineeViewHolder holder, int position) {
        holder.trainee_list_name.setText(this.trainees.get(position).getName());
        holder.id = this.traineesID.get(position);
        holder.trainer_id = this.trainer_id;
        reference = database.getReference("Plans/" + holder.id);
        Trainee trainee = trainees.get(position);



        /// Anonymous class 'setOnClick..' for setting the PHONE NUMBER
                holder.trainee_list_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone_trainee = trainee.getPhone_number();
                        Intent move = new Intent(Intent.ACTION_DIAL);
                        move.setData(Uri.parse("tel:"+phone_trainee));
                        holder.itemView.getContext().startActivity(move);
                    }
                });




        /// Setting the date where the plan has made - if it's not made - print it on screen.
                 reference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String trainee_plan_date = snapshot.child("date").getValue(String.class);
                        if (trainee_plan_date != null) {
                        holder.plan_date.setText(trainee_plan_date);
                        }
                        else {
                            holder.plan_date.setText("The plan has not yet been defined");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





        /// If the plan isn't made yet, then show the "NEW" plan in the card
                reference = database.getReference("Users/"+trainer_id+"/my_trainees/"+ holder.id);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getValue(String.class).equals("false")) {
                            holder.trainee_list_check.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





        //// GET THE IMAGE OF THE TRAINEE
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


        //// SET THE MAIL OPTION AND MAKE A AUTOMATIC TITLE FOR THE MAIL "Hi, <Trainee>"
                holder.trainee_list_mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("Send email", "");

                        DatabaseReference getStatusReference = database.getReference("Users/"+mAuth.getUid()); /// GET THE TRAINER NAME

                        getStatusReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String trainer_name = snapshot.child("name").getValue(String.class);
                                // status is the id of the trainer.
                                DatabaseReference getTrainerNameReference = database.getReference("Users/"+holder.id); /// GET THE TRAINEE NAME AND EMAIL ADDRESS

                                getTrainerNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String email_trainee = snapshot.child("email").getValue(String.class);
                                        String name = snapshot.child("name").getValue(String.class);

                                        String[] TO = {email_trainee};
                                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                        emailIntent.setData(Uri.parse("mailto:"));
                                        emailIntent.setType("text/plain");


                                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New message from " + trainer_name + " || EasyPlan");
                                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi "+name+",\n");

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
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

    }






/// GET TOTAL NUMBER OF TRAINEES
    @Override
    public int getItemCount() {
        return this.trainees.size();
    }






/// INIT ALL RELEVANT OBJECTS
    public static class TraineeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trainee_list_name , plan_date;
        private CircleImageView trainee_list_image;
        private ImageView trainee_list_mail, trainee_list_phone;
        private String id;
        private String trainer_id;
        private CardView trainee_list_check;

        public TraineeViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.trainee_list_name = view.findViewById(R.id.trainee_list_name);
            this.trainee_list_image = view.findViewById(R.id.trainee_list_image);
            this.trainee_list_mail = view.findViewById(R.id.trainee_list_mail);
            this.trainee_list_phone = view.findViewById(R.id.trainee_list_phone);
            this.plan_date = view.findViewById(R.id.plan_date);
            this.trainee_list_check = view.findViewById(R.id.trainee_list_check);
            this.trainee_list_check.setVisibility(View.GONE);
            this.id = "";
            this.trainer_id = "";
        }


        //// SEND THE TRAINEE ID AND TRAINER ID TO THE MAKEPLAN ACTIVITY USING PUTEXTRA
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MakePlanActivity.class);
            intent.putExtra("trainee id from firebase", id);
            intent.putExtra("trainer id", trainer_id);

            view.getContext().startActivity(intent);
        }
    }






}