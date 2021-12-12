package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyplan.data.FirebaseData;
import com.example.easyplan.data.Trainee;
import com.example.easyplan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

//////**********************************************////////////
////// This activity manages the trainee registration.
///// Once presses this button 'Go to my profile' , the process begins:
///// Checks for errors - prints with dialogs if any,
///// else, create the user (Auth) and register (save personal data in Real-time DB).
///// then move to the homepage.
//////**********************************************////////////
public class RegisterTraineeActivity extends AppCompatActivity {

    private Button register_trainee_btn;
    private ImageView register_trainee_image, register_trainee_upload;
    private CircleImageView personal_image;
    private EditText register_trainee_full_name, register_trainee_address, register_trainee_age;
    private EditText register_trainee_height,register_trainee_weight;
    private RadioButton register_trainee_male_radio, register_trainee_female_radio;
    private FirebaseAuth mAuth;
    FirebaseData firebaseData;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_trainee);
        //get email and password from register by type activity
        this.email = getIntent().getStringExtra("email");
        this.password = getIntent().getStringExtra("password");
        mAuth = FirebaseAuth.getInstance();
        initFields();
    }


    private void initFields() {
        register_trainee_btn = (Button) findViewById(R.id.register_trainee_btn);
        register_trainee_image = (ImageView) findViewById(R.id.register_trainee_image);
        register_trainee_upload = (ImageView) findViewById(R.id.register_trainee_upload);
        personal_image = findViewById(R.id.personal_image);
        register_trainee_full_name = (EditText) findViewById(R.id.register_trainee_full_name2);
        register_trainee_address = (EditText) findViewById(R.id.register_trainee_address);
        register_trainee_age = (EditText) findViewById(R.id.register_trainee_age);
        register_trainee_height = (EditText) findViewById(R.id.register_trainee_height);
        register_trainee_weight = (EditText) findViewById(R.id.register_trainee_weight);
        register_trainee_male_radio = (RadioButton) findViewById(R.id.register_trainee_male_radio);
        register_trainee_female_radio = (RadioButton) findViewById(R.id.register_trainee_female_radio);
    }


//////**********************************************////////////
///// Once presses this button, the process begins:
///// Checks for errors - prints with dialogs if any,
///// else, create the user (Auth) and register (save personal data).
///// then move to the homepage.
//////**********************************************////////////
    public void register_trainee(View view) {
        String error_information = checkInputs();
        if(error_information.isEmpty()) {
            createUser();
        }
        else {
            errorDialog(error_information);
        }
    }


//////**********************************************////////////
    private void createUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            registerUser();
                            Intent move = new Intent(RegisterTraineeActivity.this, TraineeHomepageActivity.class);
                            startActivity(move);
                        }
                        else {
                           errorDialog("Authentication failed.");
                        }
                    }
                });
    }


//////**********************************************////////////
    private void registerUser() {
        String name = register_trainee_full_name.getText().toString();
        String address = register_trainee_address.getText().toString();
        String age = register_trainee_age.getText().toString();
        String height = register_trainee_height.getText().toString();
        String weight = register_trainee_weight.getText().toString();
        String gender;
        if (register_trainee_female_radio.isChecked()) {
            gender = "female";
        } else gender = "male";

        firebaseData = new FirebaseData();
        Trainee trainee = new Trainee(name, address, height, weight, gender, "Trainee", "", "",  Integer.parseInt(age));
        firebaseData.createTrainee(trainee);
    }


//////**********************************************////////////
////// checkInputs collects all errors as String and returns the errors.
////// Checks empty cases and more...
//////**********************************************////////////
    public String checkInputs() {
        String errors = "";

        String name = register_trainee_full_name.getText().toString();
        String address = register_trainee_address.getText().toString();
        String age =  register_trainee_age.getText().toString();
        String height = register_trainee_height.getText().toString();
        String weight = register_trainee_weight.getText().toString();


        if(TextUtils.isEmpty(name)) {
            register_trainee_full_name.setError("Please insert your name.");
            errors += "Insert your name \n";
        }

        if(name.length() > 18) {
            register_trainee_full_name.setError("Your name is too long (max 18 letters)");
            errors += "Your name is too long (max 18 letters) \n";
        }

        if(TextUtils.isEmpty(address)) {
            register_trainee_address.setError("Please insert your address.");
            errors += "Insert your address \n";
        }

        if(address.length() > 25) {
            register_trainee_address.setError("Your address is too long (max 25 letters)");
            errors += "Your address is too long (max 25 letters) \n";
        }

        if(TextUtils.isEmpty(age)) {
            register_trainee_age.setError("Please insert your age.");
            errors += "Insert your age \n";
        }

        if(!TextUtils.isEmpty(age) && Integer.parseInt(age) > 120) {
            register_trainee_age.setError("Age over 120 isn't allowed.");
            errors += "Age over 120 isn't allowed. \n";
        }

        if(!TextUtils.isEmpty(age) && Integer.parseInt(age) < 16) {
            register_trainee_age.setError("Age under 16 isn't allowed.");
            errors += "Age under 16 isn't allowed. \n";
        }

        if(TextUtils.isEmpty(height)) {
            register_trainee_height.setError("Please insert your height.");
            errors += "Insert your height \n";
        }

        if(!TextUtils.isEmpty(height) && Integer.parseInt(height) > 250) {
            register_trainee_height.setError("Height over 250 isn't allowed.");
            errors += "Height over 250 isn't allowed. \n";
        }

        if(!TextUtils.isEmpty(height) && Integer.parseInt(height) < 50) {
            register_trainee_height.setError("Height under 50 isn't allowed.");
            errors += "Height under 50 isn't allowed. \n";
        }

        if(TextUtils.isEmpty(weight)) {
            register_trainee_weight.setError("Please insert your weight.");
            errors += "Insert your weight \n";
        }

        if(!TextUtils.isEmpty(weight) && Integer.parseInt(weight) > 250) {
            register_trainee_weight.setError("Weight over 250 isn't allowed.");
            errors += "Weight over 250 isn't allowed. \n";
        }

        if(!TextUtils.isEmpty(weight) && Integer.parseInt(weight) < 30) {
            register_trainee_weight.setError("Weight under 30 isn't allowed.");
            errors += "Weight under 30 isn't allowed. \n";
        }

        if(!register_trainee_female_radio.isChecked() && !register_trainee_male_radio.isChecked()) {
            errors += "Insert your gender \n";
        }

        return errors;
    }

    public void uploadImage(View v) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent , 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            register_trainee_image.setVisibility(View.GONE);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                personal_image.setImageURI(imageUri);
            }
            catch (IOException e) {e.printStackTrace();}

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference riversRef = storageRef.child("images/" + mAuth.getUid());
            riversRef.putFile(imageUri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //////**********************************************////////////
////// Dialog pattern, gets a string and prints it. usually to print errors.
//////**********************************************////////////
    private void errorDialog(String error) {
        final Dialog dialog = new Dialog(RegisterTraineeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_error);

        TextView errors = dialog.findViewById(R.id.dialog_error_text);
        Button ok_btn = dialog.findViewById(R.id.dialog_ok);

        errors.setText(error);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}