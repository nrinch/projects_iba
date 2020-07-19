package com.example.lesson_4_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button read_IS = findViewById(R.id.read_internal_st);
        Button writeIS = findViewById(R.id.write_internal_st);
        Button readExt = findViewById(R.id.read_external_st);
        Button writeExt = findViewById(R.id.write_external_st);
        output = findViewById(R.id.output_textView);

        writeIS.setOnClickListener(v -> writeToInternalStorage());
        read_IS.setOnClickListener(v -> readFromInternalStorage());

        writeExt.setOnClickListener(v->writeToExternalStorage());
        readExt.setOnClickListener(view ->readFromExternalStorage() );
    }

    private void writeToInternalStorage() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("readme.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write("Dont read this message \n Chupacabra");
            outputStreamWriter.close();
        } catch (IOException e) {
            Toast.makeText(this, "IO " + e, Toast.LENGTH_LONG).show();
        }
    }

    private void readFromInternalStorage() {
        try {
            InputStream inputStream = this.openFileInput("readme.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                output.setText(stringBuilder.toString());
                Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_LONG).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToExternalStorage() {
        File root = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(root, "contacts.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Contact contact = new Contact("102", "Melice");
            Gson gson = new Gson();
            fileOutputStream.write(gson.toJson(contact).getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromExternalStorage() {
        File root = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(root, "contacts.txt");
        if (file.exists()) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                FileInputStream inputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receive;
                while ((receive = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receive);
                }

                Gson gson = new Gson();
                Contact contact = gson.fromJson(stringBuilder.toString(), Contact.class);
                output.setText(contact.toString());
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void InsertSP(boolean isBlackTheme){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("BLACK_THEME",isBlackTheme);
        editor.commit();
    }


    private  boolean SelectSP(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        return  sharedPref.getBoolean("BLACK_THEME",false);
    }

}

class Contact {
    String numberPhone;
    String name;

    public Contact(String numberPhone, String name) {
        this.numberPhone = numberPhone;
        this.name = name;
    }

    @Override
    public String toString() {
        return
                "numberPhone='" + numberPhone + '\'' +
                        ", name='" + name + '\''
                ;
    }
}