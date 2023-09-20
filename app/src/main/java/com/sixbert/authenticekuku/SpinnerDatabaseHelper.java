package com.sixbert.authenticekuku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SpinnerDatabaseHelper extends SQLiteOpenHelper {

    String DBName;

    String DBPath;
    Context mcontext;
//Constructor
    public SpinnerDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.DBName = name;
        this.mcontext = context;

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {
            assert context != null;
            this.DBPath = context.getFilesDir() + "/databse/";
        }

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void checkDB() {
        SQLiteDatabase checKDb = null;

        String filePath = DBPath + DBName;

        File file = new File(filePath);

        if (file.isFile() && file.exists()) {
            Toast.makeText(mcontext, "", Toast.LENGTH_SHORT).show();
        } else {
            copyDatabase();

        }
    }
    private void copyDatabase(){
        try {
            InputStream ios = mcontext.getAssets().open(DBName);

            File directory = new File(DBPath);
            if(!directory.exists()) {
                directory.mkdir();
            }
            OutputStream os = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                os = Files.newOutputStream(Paths.get(DBPath + DBName));
            }

            byte[] buffer = new byte[1024];
            int length;

            while ((length = ios.read(buffer))>0){
                assert os != null;
                os.write(buffer,0, length);
            }
            assert os != null;
            os.flush();
            ios.close();
            os.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public SQLiteDatabase openDatabase(String DBName){
        String filePath = DBPath+ DBName;
        return SQLiteDatabase.openDatabase(filePath, null,0);
    }


}
