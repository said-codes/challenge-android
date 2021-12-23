package com.example.challengetwo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.challengetwo.databinding.ActivityFormMapsBinding;
import com.example.challengetwo.data.DBHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final int REQUEST_CODE_GALLERY = 999;
    private Button btnInsert, btnConsult, btnDelete, btnUpdate, btnChoose;
    private EditText id, name, description;
    private ImageView imgSelectedOffice;
    private TextView location;

    private DBHelper dbHelper;
    private GoogleMap mMap;
    private ActivityFormMapsBinding binding;
    String nameInsert;
    String descriptionInsert;
    String locationInsert;
    byte [] imageInsertOffice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFormMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnInsert = (Button) findViewById(R.id.btnInsertOffice);
        btnDelete = (Button) findViewById(R.id.btnDeleteOffice);
        btnConsult = (Button) findViewById(R.id.btnConsultOffice);
        btnUpdate = (Button) findViewById(R.id.btnUpdateOffice);
        btnChoose = (Button) findViewById(R.id.btnChooseOffice);

        imgSelectedOffice = (ImageView) findViewById(R.id.imgSelectedOffice);
        id = (EditText) findViewById(R.id.edtIdOffice);
        name = (EditText) findViewById(R.id.edtNameOffice);
        description = (EditText) findViewById(R.id.edtDescriptionOffice);
        location = (TextView) findViewById(R.id.tvLocationOffice);

        dbHelper = new DBHelper(getApplicationContext());

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        FormMapsActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String idInsert = id.getText().toString();
                    String nameInsert = name.getText().toString();
                    String descriptionInsert = description.getText().toString();
                    String locationInsert = location.getText().toString();
                    byte[] imageInsert = imageViewToByte(imgSelectedOffice);
                    dbHelper.insertData(nameInsert, descriptionInsert, locationInsert, imageInsert, "Offices");
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
                try {
                    Cursor cursor =  dbHelper.getDataById("Offices",id.getText().toString().trim());
                    while(cursor.moveToNext()){
                        name.setText(cursor.getString(1));
                        description.setText(cursor.getString(2));
                        location.setText(cursor.getString(3));
                        byte[] img = cursor.getBlob(4);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
                        imgSelectedOffice.setImageBitmap(bitmap);
                        Toast.makeText(getApplicationContext(), "Consult success", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbHelper.deleteById("Offices",id.getText().toString().trim());
                    cleanFields();
                    Toast.makeText(getApplicationContext(), "Delete success", Toast.LENGTH_SHORT).show();
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
                        dbHelper.updateOfficeById(
                                "Offices",
                                id.getText().toString(),
                                nameInsert,
                                descriptionInsert,
                                locationInsert,
                                imageInsertOffice
                        );
                        cleanFields();
                        Toast.makeText(getApplicationContext(), "Update success", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        int zoom = 5;
        LatLng bogota = new LatLng(3.52, -72);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota,zoom));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                location.setText(latLng.latitude+","+latLng.longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                googleMap.clear();
                googleMap.addMarker(markerOptions);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(), "Sin Permisos", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSelectedOffice.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public void fillFields(){
        nameInsert = name.getText().toString().trim();
        descriptionInsert = description.getText().toString().trim();
        descriptionInsert = location.getText().toString().trim();
        imageInsertOffice = imageViewToByte(imgSelectedOffice);
    }
    public void cleanFields(){
        name.setText("");
        description.setText("");
        location.setText("");
        imgSelectedOffice.setImageResource(R.mipmap.ic_launcher);
    }
}