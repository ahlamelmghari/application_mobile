package com.example.projetapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Form1Activity extends AppCompatActivity {

    private EditText titre, description;
    private Button post;
    private DBHelper DB;
    private Spinner spinnerCategory, spinnerSector, spinnerVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaire);

        // Initialisation des vues
        titre = findViewById(R.id.title);
        description = findViewById(R.id.description);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerSector = findViewById(R.id.spinnerSector);
        spinnerVille = findViewById(R.id.spinnerville);
        post = findViewById(R.id.post);

        DB = new DBHelper(this);

        // Récupération des options à partir des ressources et création de l'adaptateur
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnercategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Appliquer l'adaptateur aux Spinners
        spinnerCategory.setAdapter(adapter);
        spinnerSector.setAdapter(adapter);
        spinnerVille.setAdapter(adapter);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titreText = titre.getText().toString().trim();
                String category = spinnerCategory.getSelectedItem().toString().trim();
                String descriptionText = description.getText().toString().trim();
                String ville = spinnerVille.getSelectedItem().toString().trim();
                String secteur = spinnerSector.getSelectedItem().toString().trim();

                if (titreText.isEmpty() || category.isEmpty() || descriptionText.isEmpty() || secteur.isEmpty() || ville.isEmpty()) {
                    Toast.makeText(Form1Activity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean insertStatus = DB.insert(titreText, category, secteur, descriptionText, ville);
                if (insertStatus) {
                    Toast.makeText(Form1Activity.this, "Données insérées avec succès", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Form1Activity.this, Post.class);
                    intent.putExtra("city", ville);
                    startActivity(intent);
                } else {
                    Toast.makeText(Form1Activity.this, "Échec de l'insertion des données", Toast.LENGTH_SHORT).show();
                }
            }
        });



        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinnercategory, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

// Pour spinnerSector
        ArrayAdapter<CharSequence> sectorAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinnersector, android.R.layout.simple_spinner_item);
        sectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSector.setAdapter(sectorAdapter);

// Pour spinnerVille
        ArrayAdapter<CharSequence> villeAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinerville, android.R.layout.simple_spinner_item);
        villeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVille.setAdapter(villeAdapter);
    }
}