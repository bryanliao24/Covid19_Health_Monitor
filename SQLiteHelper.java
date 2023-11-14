package com.example.chanluliao_project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "project1.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_SYMPTOMS = "Project1";;
    public static final String COLUMN_ID = "Symptom_ID";
    public static final String COLUMN_HEART = "Heart_Rate";
    public static final String COLUMN_RESPIRATORY= "Respiratory";
    public static final String COLUMN_NAUSEA = "Nausea";
    public static final String COLUMN_HEADACHE = "Headache";
    public static final String COLUMN_DIARRHEA = "Diarrhea";
    public static final String COLUMN_SORE_THROAT = "Sore_Throat";
    public static final String COLUMN_FEVER = "Fever";
    public static final String COLUMN_MUSCLE_ACHE = "Muscle_Ache";
    public static final String COLUMN_LOSS_OF_SMELL_OR_TASTE = "Loss_of_Smell_or_Taste";
    public static final String COLUMN_COUGH = "Cough";
    public static final String COLUMN_SHORTNESS_OF_BREATH = "Shortness_of_Breath";
    public static final String COLUMN_FEELING_TIRED = "Feeling_Tired";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSymptomsTable = "CREATE TABLE " + TABLE_SYMPTOMS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HEART + " INT, " +
                COLUMN_RESPIRATORY + " INT, " +
                COLUMN_NAUSEA + " REAL, " +
                COLUMN_HEADACHE + " REAL, " +
                COLUMN_DIARRHEA + " REAL, " +
                COLUMN_SORE_THROAT + " REAL, " +
                COLUMN_FEVER + " REAL, " +
                COLUMN_MUSCLE_ACHE + " REAL, " +
                COLUMN_LOSS_OF_SMELL_OR_TASTE + " REAL, " +
                COLUMN_COUGH + " REAL, " +
                COLUMN_SHORTNESS_OF_BREATH + " REAL, " +
                COLUMN_FEELING_TIRED + " REAL)";
        db.execSQL(createSymptomsTable);
        // initialize all the symptoms to zero
        insertZeroRatings(db);
    }

    private void insertZeroRatings(SQLiteDatabase db) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_HEART, 0);
        initialValues.put(COLUMN_RESPIRATORY, 0);
        initialValues.put(COLUMN_NAUSEA, 0.0);
        initialValues.put(COLUMN_HEADACHE, 0.0);
        initialValues.put(COLUMN_DIARRHEA, 0.0);
        initialValues.put(COLUMN_SORE_THROAT, 0.0);
        initialValues.put(COLUMN_FEVER, 0.0);
        initialValues.put(COLUMN_MUSCLE_ACHE, 0.0);
        initialValues.put(COLUMN_LOSS_OF_SMELL_OR_TASTE, 0.0);
        initialValues.put(COLUMN_COUGH, 0.0);
        initialValues.put(COLUMN_SHORTNESS_OF_BREATH, 0.0);
        initialValues.put(COLUMN_FEELING_TIRED, 0.0);

        db.insert(TABLE_SYMPTOMS, null, initialValues);
    }

    public void updateHRandRR(String symptomName, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(symptomName, rating);

        db.update(TABLE_SYMPTOMS, values, null, null);
        db.close();
    }

    public void updateSymptomRating(String symptomName, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(getColumnNameForSymptom(symptomName), rating);

//        String whereClause = COLUMN_SYMPTOM_NAME + " = ?";
//        String[] whereArgs = {symptomName};

        db.update(TABLE_SYMPTOMS, values, null, null);
        db.close();
    }

    private String getColumnNameForSymptom(String symptomName) {
        // Map symptom names to their corresponding column names
        switch (symptomName) {
            case "Heart Rate":
                return COLUMN_HEART;
            case "Respiratory":
                return COLUMN_RESPIRATORY;
            case "Nausea":
                return COLUMN_NAUSEA;
            case "Headache":
                return COLUMN_HEADACHE;
            case "Diarrhea":
                return COLUMN_DIARRHEA;
            case "Sore Throat":
                return COLUMN_SORE_THROAT;
            case "Fever":
                return COLUMN_FEVER;
            case "Muscle Ache":
                return COLUMN_MUSCLE_ACHE;
            case "Loss of Smell or Taste":
                return COLUMN_LOSS_OF_SMELL_OR_TASTE;
            case "Cough":
                return COLUMN_COUGH;
            case "Shortness of Breath":
                return COLUMN_SHORTNESS_OF_BREATH;
            case "Feeling Tired":
                return COLUMN_FEELING_TIRED;
            default:
                throw new IllegalArgumentException("Invalid symptom name: " + symptomName);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYMPTOMS);
        onCreate(db);
    }
//
//    public void insertRating(String Symptom, float rating) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_ITEM, Symptom);
//        values.put(COLUMN_RATING, rating);
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//    }
//    public void updateRating(String Symptom, float rating) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_RATING, rating);
//        db.update(TABLE_NAME, values, COLUMN_ITEM + " = ?", new String[]{Symptom});
//        db.close();
//    }
}

