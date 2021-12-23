package com.example.challengetwo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.challengetwo.data.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormActivity extends AppCompatActivity {

    private final int REQUEST_CODE_GALLERY = 999;
    private TextView tvTitle;
    private EditText field1, field2, field3, editId;
    private ImageView imgSelected;
    private DBHelper dbHelper;
    Button btnChoose, btnInsert , btnConsult, btnDelete, btnUpdate;
    String name = "";
    String field1Insert;
    String field2Insert;
    String field3Insert;
    byte [] imageInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        field1 = (EditText) findViewById(R.id.editTextField1);
        field2 = (EditText) findViewById(R.id.editTextField2);
        field3 = (EditText) findViewById(R.id.editTextField3);
        editId = (EditText) findViewById(R.id.editIdItem);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnConsult = (Button) findViewById(R.id.btnConsult);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        imgSelected = (ImageView) findViewById(R.id.imgSelected);
        dbHelper = new DBHelper(getApplicationContext());

        Intent intent  = getIntent();
        name = intent.getStringExtra("name");
        tvTitle.setText(name);
        if(name.equals("Products")){
            field1.setHint("Name");
            field2.setHint("Description");
            field3.setHint("Price");
        }else if(name.equals("Services")){
            field1.setHint("Name");
            field2.setHint("Description");
            field3.setHint("Price");
        }else if(name.equals("Offices")){
            field1.setHint("Name");
            field2.setHint("Description");
            field3.setHint("Location");
            field3.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        FormActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );

            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fillFields();
                    dbHelper.insertData(field1Insert,field2Insert,field3Insert,imageInsert,name);
                    cleanFields();
                    Toast.makeText(getApplicationContext(), "Insert success", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutForm);
                try {
                    Cursor cursor =  dbHelper.getDataById(name,editId.getText().toString().trim());
                    while(cursor.moveToNext()){
                        field1.setText(cursor.getString(1));
                        field2.setText(cursor.getString(2));
                        field3.setText(cursor.getString(3));
                        byte[] img = cursor.getBlob(4);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
                        imgSelected.setImageBitmap(bitmap);
                        Snackbar.make(view,"Consult success",Snackbar.LENGTH_LONG);
                        //Toast.makeText(getApplicationContext(), "Consult success", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutForm);
                try {
                    dbHelper.deleteById(name,editId.getText().toString().trim());
                    cleanFields();
                    Snackbar.make(view,"Deleted",Snackbar.LENGTH_LONG);
                    //Toast.makeText(getApplicationContext(), "Delete success", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillFields();
                try {
                    if(name.equals("Offices")){
                        dbHelper.updateOfficeById(
                                name,
                                editId.getText().toString(),
                                field1Insert,
                                field2Insert,
                                field3Insert,
                                imageInsert
                        );
                        cleanFields();
                        Toast.makeText(getApplicationContext(), "Update success", Toast.LENGTH_SHORT).show();
                    }else {
                        dbHelper.updateTableById(
                                name,
                                editId.getText().toString(),
                                field1Insert,
                                field2Insert,
                                field3Insert,
                                imageInsert
                        );
                    }
                    cleanFields();
                    Toast.makeText(getApplicationContext(), "Update success", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });



    }
    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)  imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void fillFields(){
        field1Insert = field1.getText().toString().trim();
        field2Insert = field2.getText().toString().trim();
        field3Insert = field3.getText().toString().trim();
        imageInsert = imageViewToByte(imgSelected);
    }
    public void cleanFields(){
        field1.setText("");
        field2.setText("");
        field3.setText("");
        imgSelected.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(), "Without Permissions", Toast.LENGTH_SHORT).show();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data!= null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSelected.setImageBitmap(bitmap);

            }catch (FileNotFoundException e){
                e.printStackTrace();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}