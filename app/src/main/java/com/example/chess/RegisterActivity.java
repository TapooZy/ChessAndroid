package com.example.chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.Manifest;


public class RegisterActivity extends AppCompatActivity {
    EditText email, password;
    Button register;
    FirebaseAuth auth;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS},101);

            }
        }
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        register = findViewById(R.id.btnRegister);
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(this::register);
        intent = new Intent(RegisterActivity.this, LoginActivity.class);
    }

    private void register(View view) {
        String Email=email.getText().toString().trim();
        String Password = password.getText().toString();
        if (Email.isEmpty())
            email.setError("email is empty");
        else if (Password.isEmpty())
            password.setError("pass is  empty");
        else {
            auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        makeNotification();
                        Toast.makeText(RegisterActivity.this, "Account created",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
            });
        }
    }

    public void makeNotification(){
        String chanelId="CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),chanelId);
        builder.setSmallIcon(R.mipmap.chess_title)
                .setContentText("registered successfully")
                .setContentTitle("Thank you for registering ya gever/et")
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager=
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=notificationManager.getNotificationChannel(chanelId);
            if (notificationChannel == null){
                int importance=NotificationManager.IMPORTANCE_HIGH;
                notificationChannel=new NotificationChannel(chanelId,"register notification", importance);
                notificationChannel.setLightColor(R.color.dark_square);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }

        }
        notificationManager.notify(0,builder.build());
    }
}