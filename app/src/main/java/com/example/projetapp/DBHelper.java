package com.example.projetapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table "users"
        db.execSQL("CREATE TABLE users(username TEXT PRIMARY KEY, password TEXT, radioButtonInfo TEXT, ville TEXT)");

        // Création de la table "annonce"
        db.execSQL("CREATE TABLE annonce(titre TEXT PRIMARY KEY, categorie TEXT, secteur TEXT, description TEXT, ville TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Suppression des tables existantes
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS annonce");

        // Recréation des tables
        onCreate(db);
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public Boolean insertData(String username, String password, String radioButtonInfo, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("radioButtonInfo", radioButtonInfo);
        contentValues.put("ville", city);

        long result = db.insert("users", null, contentValues);
        return result != -1;
    }

    public boolean insert(String titre, String category, String secteur, String description, String ville) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("titre", titre);
        contentValues.put("categorie", category);
        contentValues.put("secteur", secteur);
        contentValues.put("description", description);
        contentValues.put("ville", ville);

        try {
            Log.d("Insertion", "Titre : " + titre + ", Catégorie : " + category + ", Secteur : " + secteur + ", Description : " + description + ", Ville : " + ville);

            long result = db.insertOrThrow("annonce", null, contentValues);

            if (result == -1) {
                Log.e("DBHelper", "Erreur d'insertion : Échec de l'insertion des données");
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            Log.e("DBHelper", "Erreur d'insertion SQL : ", e);
            return false;
        } catch (Exception e) {
            Log.e("DBHelper", "Erreur d'insertion : ", e);
            return false;
        }
    }

    public int countAnnouncementsByCity(String city) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM annonce WHERE ville = ?", new String[]{city});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;


    }
}