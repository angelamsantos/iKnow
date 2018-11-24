package com.santos.angela.iknow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
    }

    /**@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }**/
    public void signup(View v){

        EditText etemail, etpass, etconfpass, etcno, etaddress;

        etemail = (EditText)findViewById(R.id.etEmail);
        etpass = (EditText)findViewById(R.id.etPass);
        etconfpass = (EditText)findViewById(R.id.etConfPass);
        etcno = (EditText)findViewById(R.id.etCNo);
        etaddress = (EditText)findViewById(R.id.etAddress);

        final String email = etemail.getText().toString();
        final String pass = etpass.getText().toString();
        final String confpass = etconfpass.getText().toString();
        final String cno = etcno.getText().toString();
        final String address = etaddress.getText().toString();

        if (pass.equals(confpass)){
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("4ITF", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                                Map newPost = new HashMap();
                                newPost.put("email", email);
                                newPost.put("password", pass);
                                newPost.put("cno", cno);
                                newPost.put("address", address);

                                current_user_db.setValue(newPost);

                                Intent i = new Intent(getBaseContext(), home.class);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("4ITF", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(sign_up.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
        else {
            Toast.makeText(sign_up.this, "Passwords do not match...", Toast.LENGTH_SHORT).show();
        }
    }
}
