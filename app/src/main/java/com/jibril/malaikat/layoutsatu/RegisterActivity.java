package com.jibril.malaikat.layoutsatu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

//    Button mbtn;
//    TextView txt;
//    EditText rg_username, rg_email, rg_phone, rg_password;
//    String username, email, phone, password;

    private Button mbtn;
    private TextView txt;
    private EditText rg_username, rg_email, rg_phone, rg_password;
    private String username, email, phone, password;
    private DatabaseReference mDatabase;

    //progras dialog
    private ProgressDialog mRegProgress;

    //firebase auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("REGISTER");

        mRegProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mbtn = findViewById(R.id.btn_sign);
        txt = findViewById(R.id.txt);

        rg_email = findViewById(R.id.rg_email);
        rg_username = findViewById(R.id.rg_username);
        rg_phone = findViewById(R.id.rg_phone);
        rg_password = findViewById(R.id.rg_password);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Mengambil nilai input user
                email = rg_email.getText().toString();
                username = rg_username.getText().toString();
                phone = rg_phone.getText().toString();
                password = rg_password.getText().toString();

                //cepet
                if(!TextUtils.isEmpty(username) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(phone) || !TextUtils.isEmpty(password)){
                    mRegProgress.setMessage("Mohon Bersabar Ini Ujian....");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(username,email,phone,password);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "mohon maaf data harus di isi semua", Toast.LENGTH_SHORT).show();
                }

                //lama
//                if (TextUtils.isEmpty(email)) {
//                    rg_email.setError("Email Tidak Boleh Kosong");
//                }
//
//                if (TextUtils.isEmpty(username)) {
//                    rg_username.setError("Username Tidak Boleh Kosong");
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    rg_password.setError("Username Tidak Boleh Kosong");
//                }
//
//                if (TextUtils.isEmpty(phone)) {
//                    rg_phone.setError("Username Tidak Boleh Kosong");
//                } else {
//                    Toast.makeText(RegisterActivity.this, "REGISTER BERHASIL", Toast.LENGTH_SHORT).show();
//                    Intent m = new Intent(getApplicationContext(), RegisterActivity.class);
//                    startActivity(m);
//                }
            }
        });
    }

    private void register_user(final String username, final String email, final String phone, final String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("nama", username);
                    userMap.put("email", email);
                    userMap.put("phone", phone);
                    userMap.put("password", password);
                    userMap.put("device_token", device_token);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mRegProgress.dismiss();
                                Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(mainIntent);
                                finish();

                                Toast.makeText(RegisterActivity.this, "Register Berhasil...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    String task_result = task.getException().getMessage().toString();

                    mRegProgress.hide();
                    Toast.makeText(RegisterActivity.this, "Registrasi Tidak Berhasil, Silahkan Coba Kembali..."+ task_result, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
//        public void validasi (){
//            etRgemail = findViewById(R.id.rg_email);
//            etRgUserName = findViewById(R.id.rg_username);
//            etRgphone = findViewById(R.id.rg_phone);
//            etRgpassword = findViewById(R.id.rg_password);
//            //Mengambil nilai input user
//            sRgemail = etRgemail .getText().toString();
//            sRgUserName = etRgUserName .getText().toString();
//            sRgphone = etRgphone .getText().toString();
//            sRgpassword = etRgpassword .getText().toString();
//
//            if(TextUtils.isEmpty(sRgemail)){
//                etRgemail.setError("Email Tidak Boleh Kosong");
//            }else if(TextUtils.isEmpty(sRgUserName)){
//                etRgUserName.setError("Username Tidak Boleh Kosong");
//            }else if(TextUtils.isEmpty(sRgpassword)){
//                etRgpassword.setError("Username Tidak Boleh Kosong");
//            }else if(TextUtils.isEmpty(sRgphone)){
//                etRgphone.setError("Username Tidak Boleh Kosong");
//            }
//
//        }

//        txt = findViewById(R.id.txt);
//        txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent n = new Intent(getApplicationContext(),LoginActivity.class);
//                startActivity(n);
//            }
//        });


//    private void showDialog(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                this);
//
//        // set title dialog
//        alertDialogBuilder.setTitle("Keluar dari aplikasi?");
//
//        // set pesan dari dialog
//        alertDialogBuilder
//                .setMessage("Klik Ya untuk keluar!")
//                .setIcon(R.mipmap.ic_launcher)
//                .setCancelable(false)
//                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        // jika tombol diklik, maka akan menutup activity ini
//                        RegisterActivity.this.finish();
//                    }public void validasi (){
//    etRgemail = findViewById(R.id.rg_email);
//    etRgUserName = findViewById(R.id.rg_username);
//    etRgphone = findViewById(R.id.rg_phone);
//    etRgpassword = findViewById(R.id.rg_password);
//    //Mengambil nilai input user
//    sRgemail = etRgemail .getText().toString();
//}
//                })
//                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // jika tombol ini diklik, akan menutup dialog
//                        // dan tidak terjadi apa2
//                        dialog.cancel();
//                    }
//                });
//
//        // membuat alert dialog dari builder
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // menampilkan alert dialog);
//        alertDialog.show();
//    }

//    mbtn.setOnClickListener(this);






//public class login extends AppCompatActivity {
//
//    private boolean check (String etRgUsername){
//    etRgUsername = findViewById(R.id.rg_username);
//
//        if(TextUtils.isEmpty(etRgUsername)){
//            Toast.makeText(etRgUsername.this, "Masukkan Username", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        return true;
//    }
//}