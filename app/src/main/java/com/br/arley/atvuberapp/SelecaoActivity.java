package com.br.arley.atvuberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelecaoActivity extends AppCompatActivity {

    ImageView imgPassageiro, imgMotorista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao);

        imgMotorista = findViewById(R.id.motorista_img);
        imgPassageiro = findViewById(R.id.passageiro_img);

        imgPassageiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelecaoActivity.this, PassageiroActivity.class));
            }
        });

        imgMotorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelecaoActivity.this, MotoristaActivity.class));
            }
        });
    }
}