package com.jibril.malaikat.layoutsatu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnlogin,btnsign;
    private String email,password;
    private EditText et_email,et_password;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private ProgressDialog progressDialog;
    private com.jibril.malaikat.layoutsatu.PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Masuk Akun");

        //activity yang dilewati ketika sudah login(gak perlu login lagi)
        prefManager = new com.jibril.malaikat.layoutsatu.PrefManager(this);
        if(!prefManager.isFirstTimeLaunch()){
            prefManager.setIsFirstTimeLaunch(false);
            sendTo(getApplicationContext(), DasboardActivity.class);
            finish();
        }

        mAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btnlogin = findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");



//        btnlogin = findViewById(R.id.btn_login);
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Mengambil NIlai INput user
//
//                email = et_email.getText().toString();
//                password = et_password.getText().toString();
//
//                if (TextUtils.isEmpty(email)) {
//                    et_email.setError("Username Tidak Boleh Kosong");
//                }else if (TextUtils.isEmpty(password)) {
//                    et_password.setError("Password Tidak Boleh Kosong");
//                } else {
//                    Toast.makeText(LoginActivity.this, "LOGIN BERHASIL", Toast.LENGTH_SHORT).show();
//                    Intent m = new Intent(getApplicationContext(), DasboardActivity.class);
//                    startActivity(m);
//                }
//            }
//        });

        btnsign = findViewById(R.id.btn_sign);
        btnsign.setOnClickListener(this);
//        btnsign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent m = new Intent(getApplicationContext(),RegisterActivity.class);
//                startActivity(m);
//            }
//        });

    }

    private void sendTo(Context ctx, Class kelas){
        Intent i = new Intent(ctx, kelas);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign:
                sendTo(getApplicationContext(), RegisterActivity.class);
                break;
            case R.id.btn_login:
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    progressDialog.setMessage("Mohon tunggu sebentar...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    loginUser(email, password);
                }else{
                    Toast.makeText(this, "Mohon maaf data harus diisi semua...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private  void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();

                    String current_user_id = mAuth.getCurrentUser().getUid();
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                    mUserDatabase.child(current_user_id).child("deivicetoken").setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            //set pref manager
                            prefManager.setIsFirstTimeLaunch(false);
                            sendTo(getApplicationContext(), DasboardActivity.class);
                            finish();
                        }
                    });
                }else{

                    progressDialog.hide();

                    String task_result = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, "Error : "+task_result, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
