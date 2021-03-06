package com.example.enduser.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enduser.Model.User;
import com.example.enduser.Model.Util;
import com.example.enduser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.editTextName)
    EditText editextName;

    @BindView(R.id.editTextPhone)
    EditText editTextPhone;

    @BindView(R.id.editTextEmail)
    EditText editTextEmail;

    @BindView(R.id.editTextPassword)
    EditText editTextPassword;

    @BindView(R.id.buttonRegister)
    Button buttonRegister;

    @BindView(R.id.textViewLogin)
    TextView txtLogin;

    @BindView(R.id.textViewPhoneAuth)
     TextView txtPhoneAuth;

    User user;

    FirebaseAuth auth;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;

    void initViews(){

        buttonRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        txtPhoneAuth.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        user = new User();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = auth.getCurrentUser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);
        initViews();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonRegister) {
            //Get the data from UI and put it into User Object
            user.name = editextName.getText().toString();
            user.email = editTextEmail.getText().toString();
            user.password = editTextPassword.getText().toString();
            user.phone = editTextPhone.getText().toString();

            if(Util.isInternetConnected(this)) {
                progressDialog.show();
                registerUser();
            }else{

                Toast.makeText(this,"Please Connect to Internet and Try Again",Toast.LENGTH_LONG).show();
            }

        }
switch (v.getId()){

          case  R.id.textViewPhoneAuth:
            PhoneAuth();
              Toast.makeText(this,"Phone Authentication",Toast.LENGTH_LONG).show();
              break;

    case  R.id.textViewLogin:
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this,"Login Here",Toast.LENGTH_LONG).show();
        break;

}

    }

    void PhoneAuth(){
        Intent intent = new Intent(RegistrationActivity.this, PhoneAuthActivity.class);
        startActivity(intent);
        finish();
    }

    void registerUser() {

        auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            Toast.makeText(RegistrationActivity.this, user.name + " created ", Toast.LENGTH_LONG).show();
                            // progressDialog.dismiss();
                               /* Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();*/

                            saveUserInCloudDB();
                        }
                    }
                });
    }

    void saveUserInCloudDB() {

        db.collection("Customers").add(user)
                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isComplete()) {
                            Toast.makeText(RegistrationActivity.this, user.name + "Registered Sucessfully", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Intent intent = new Intent(RegistrationActivity.this, PhoneAuthActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
    }
