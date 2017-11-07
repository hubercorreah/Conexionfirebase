package com.example.kellyhuber.conexionfirebase;
//j17BsFncwkG2DrgCYbDkmfbQrXc= android

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView mensajeTextView;
    private EditText mensaEditText;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mensaseRef = ref.child("mensajes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }

        mensajeTextView=(TextView) findViewById(R.id.mensajeTextView);
        mensaEditText= (EditText) findViewById(R.id.mensajeEditText);
    }



    @Override
    protected void onStart() {
        super.onStart();
        mensaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                mensajeTextView.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void modificar(View view) {
        String mensaje=mensaEditText.getText().toString();
        mensaseRef.setValue(mensaje);
        mensaEditText.setText("");
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity(intent);
    }




    public void cerrar(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }





}
