package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyplan.data.FirebaseData;
import com.example.easyplan.data.Trainer;
import com.example.easyplan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterTrainerActivity extends AppCompatActivity {

    private ImageView register_trainer_image;
    private Button register_trainer_btn;
    private CircleImageView personal_image;
    private EditText register_trainer_full_name, register_trainer_address, register_trainer_education;
    private EditText register_trainer_personal_page, register_trainer_age, register_trainer_cost, register_trainer_days;
    private RadioButton register_trainer_male_radio, register_trainer_female_radio;
    private CheckBox register_trainer_cardio, register_trainer_fitness, register_trainer_muscle, register_trainer_menu;
    private FirebaseAuth mAuth;
    private String email, password, gender;
    private EditText register_trainer_phone_number;

    private Uri imageUri;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_trainer);

        this.email = getIntent().getStringExtra("email");
        this.password = getIntent().getStringExtra("password");

        mAuth = FirebaseAuth.getInstance();
        initFields();
    }


//////**********************************************////////////
    public void initFields() {
        register_trainer_btn = (Button) findViewById(R.id.register_trainer_btn);
        register_trainer_full_name = (EditText) findViewById(R.id.register_trainer_full_name);
        register_trainer_address = (EditText) findViewById(R.id.register_trainer_address);
        register_trainer_education = (EditText) findViewById(R.id.register_trainer_education);
        register_trainer_personal_page = (EditText) findViewById(R.id.register_trainer_personal_page);
        register_trainer_age = (EditText) findViewById(R.id.register_trainer_age);
        register_trainer_cost = (EditText) findViewById(R.id.register_trainer_cost);
        register_trainer_male_radio = (RadioButton) findViewById(R.id.register_trainer_male_radio);
        register_trainer_female_radio = (RadioButton) findViewById(R.id.register_trainer_female_radio);
        register_trainer_cardio = (CheckBox) findViewById(R.id.register_trainer_cardio);
        register_trainer_fitness = (CheckBox) findViewById(R.id.register_trainer_fitness);
        register_trainer_muscle = (CheckBox) findViewById(R.id.register_trainer_muscle);
        register_trainer_menu = (CheckBox) findViewById(R.id.register_trainer_menu);
        personal_image = (CircleImageView) findViewById(R.id.personal_trainer_image);
        register_trainer_image = (ImageView) findViewById(R.id.register_trainer_image);
        register_trainer_phone_number = (EditText) findViewById(R.id.register_trainer_phone_number);
    }

//////**********************************************////////////
///// Once presses this button, the process begins:
///// Checks for errors - prints with dialogs if any,
///// else, create the user (Auth) and register (save personal data).
///// then move to the homepage.
//////**********************************************////////////
    public void register_trainer(View view) {
        String error_information = checkInputs();
        if(error_information.isEmpty()) {
            createUser();
        }
        else {
            errorDialog(error_information);
        }
    }

    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading file...");
        progressDialog.show();

        if(imageUri != null) {
            storageReference = FirebaseStorage.getInstance().getReference("images/" + mAuth.getUid());
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    errorDialog("Couldn't upload the image.");
                }
            });
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
                            Intent move = new Intent(RegisterTrainerActivity.this, TrainerHomepageActivity.class);
                            move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(move);
                            RegisterTrainerActivity.this.finish();
                        } else {
                            errorDialog("Authentication failed.");
                        }
                    }
                });
    }


//////**********************************************////////////
    private void registerUser() {
        uploadImage();
        String name = register_trainer_full_name.getText().toString();
        String address = register_trainer_address.getText().toString();
        int age = Integer.parseInt(register_trainer_age.getText().toString());
        int cost = Integer.parseInt(register_trainer_cost.getText().toString());
        String education = register_trainer_education.getText().toString();
        String personal_page = register_trainer_personal_page.getText().toString();
        String phone = register_trainer_phone_number.getText().toString();
        String email = mAuth.getCurrentUser().getEmail().toString();
        String token = FirebaseMessaging.getInstance().getToken().toString();

        ArrayList<String> targets = new ArrayList<>();
        if (register_trainer_cardio.isChecked()) {
            targets.add("Cardio");
        }
        if (register_trainer_fitness.isChecked()) {
            targets.add("Fitness");
        }
        if (register_trainer_muscle.isChecked()) {
            targets.add("Muscle");
        }
        if (register_trainer_menu.isChecked()) {
            targets.add("Menu Nutrition");
        }

        FirebaseData firebaseData = new FirebaseData();
        Trainer trainer = new Trainer(name, address, gender, "Trainer", education, personal_page, "",  age, cost, 3, 0, targets, new HashMap<String, String>(), token, phone, email);
        firebaseData.createTrainer(trainer);

    }


//////**********************************************////////////
////// checkInputs collects all errors as String and returns the errors.
////// Checks empty cases and more...
//////**********************************************////////////
    public String checkInputs() {
        String errors = "";

        String name = register_trainer_full_name.getText().toString();
        String address = register_trainer_address.getText().toString();
        String age =  register_trainer_age.getText().toString();
        String cost = register_trainer_cost.getText().toString();
        String education = register_trainer_education.getText().toString();
        String personal_page = register_trainer_personal_page.getText().toString();
        String phone = register_trainer_phone_number.getText().toString();

        if(TextUtils.isEmpty(name)) {
            register_trainer_full_name.setError("Please insert your name.");
            errors += "Insert your name \n";
        }

        if(name.length() > 18) {
            register_trainer_full_name.setError("Your name is too long (max 18 letters)");
            errors += "Your name is too long (max 18 letters) \n";
        }

        if(TextUtils.isEmpty(address)) {
            register_trainer_address.setError("Please insert your address.");
            errors += "Insert your address \n";
        }

        if(address.length() > 25) {
            register_trainer_address.setError("Your address is too long (max 25 letters)");
            errors += "Your address is too long (max 25 letters) \n";
        }

        if(TextUtils.isEmpty(age)) {
            register_trainer_age.setError("Please insert your age.");
            errors += "Insert your age \n";
        }

        if(TextUtils.isEmpty(cost)) {
            register_trainer_age.setError("Please insert your cost.");
            errors += "Insert your cost \n";
        }

        if(!TextUtils.isEmpty(age) && Integer.parseInt(age) > 120) {
            register_trainer_age.setError("Age over 120 isn't allowed.");
            errors += "Age over 120 isn't allowed. \n";
        }

        if(!TextUtils.isEmpty(cost) && Integer.parseInt(cost) > 9000) {
            register_trainer_age.setError("Cost over 9000 isn't allowed.");
            errors += "Cost over 9000 isn't allowed. \n";
        }

        if(TextUtils.isEmpty(education)) {
            register_trainer_education.setError("Please insert your education.");
            errors += "Insert your education \n";
        }

        if(TextUtils.isEmpty(personal_page)) {
            register_trainer_personal_page.setError("Please insert your personal page.");
            errors += "Insert your personal page \n";
        }

        if(TextUtils.isEmpty(phone)) {
            register_trainer_personal_page.setError("Please insert your phone number.");
            errors += "Insert your phone number \n";
        }

        int check_multichecks = 0;
        if (register_trainer_cardio.isChecked()) {
            check_multichecks++;
        }
        if (register_trainer_fitness.isChecked()) {
            check_multichecks++;
        }
        if (register_trainer_muscle.isChecked()) {
            check_multichecks++;
        }
        if (register_trainer_menu.isChecked()) {
            check_multichecks++;
        }

        if(check_multichecks == 0) {
            errors += "You must check at least one \n";
        }

        if(gender.isEmpty()) {
            errors += "You must choose a gender \n";
        }

        return errors;
    }


//////**********************************************////////////
////// Dialog pattern, gets a string and prints it. usually to print errors.
//////**********************************************////////////
    private void errorDialog(String error) {
        final Dialog dialog = new Dialog(RegisterTrainerActivity.this);
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.register_trainer_male_radio:
                if (checked)
                    gender = "Male";
                    break;
            case R.id.register_trainer_female_radio:
                if (checked)
                    gender = "Female";
                    break;
        }
    }

    public void uploadImage(View view) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent , 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 &&  data != null && data.getData() != null && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            register_trainer_image.setVisibility(View.GONE);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                personal_image.setImageURI(imageUri);
            }
            catch (IOException e) {e.printStackTrace();}

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}