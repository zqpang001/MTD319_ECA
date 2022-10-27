package com.example.mtd319_tma02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddNewHostActivity_Task extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView imgMain;

    Spinner categorySpinner;
    ImageView addNewPhoto;
    EditText listingTitleField;
    EditText priceTextField;
    EditText quantityAvailableTextField;
    CheckBox deliveryCheckBox;
    EditText locationTextField;
    Button confirmButton;
    Bitmap photo;
    Bitmap bitmap;
    String encodeImageString;
    private final int GALLERY_REQ_CODE=1000;

    private static final int CAMERA_REQUEST=1888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_host_task);


        categorySpinner = findViewById(R.id.categorySpinnerField);
        addNewPhoto = findViewById(R.id.addNewPhoto);
        listingTitleField = findViewById(R.id.listingTitleField);
        priceTextField = findViewById(R.id.priceTextField);
        quantityAvailableTextField = findViewById(R.id.quantityAvailableTextField);
        deliveryCheckBox = findViewById(R.id.deliveryCheckBox);
        locationTextField = findViewById(R.id.locationTextField);
        confirmButton = findViewById(R.id.confirmButton);

        imgMain=findViewById(R.id.testImage);
//
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.category, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(this);

//            addNewPhoto.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Dexter.withActivity(AddNewHostActivity_Task.this)
//                            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                            .withListener(new PermissionListener() {
//                                @Override
//                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                                    Intent intent = new Intent(Intent.ACTION_PICK);
//                                    intent.setType("images/*");
//                                    startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
//                                }
//
//                                @Override
//                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                                }
//
//                                @Override
//                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//
//                                }
//                            }).check();
//                }
//            });
            addNewPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iGallery = new Intent(Intent.ACTION_PICK);
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery,GALLERY_REQ_CODE);
                }
            });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getItemAtPosition(i).equals("Select Category")){
            // do nothing
        }else{

            String choice = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(getApplicationContext(),choice,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    public void addNewPhoto(View view) {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent,CAMERA_REQUEST);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_REQ_CODE && resultCode==RESULT_OK){
            imgMain.setImageURI(data.getData());
            Uri filepath=data.getData();
//            try {
//                InputStream inputStream=getContentResolver().openInputStream(filepath);
//                bitmap= BitmapFactory.decodeStream(inputStream);
////                imgMain.setImageBitmap(bitmap);
//                encodeBitmapImage(bitmap);
//            }catch (Exception ex){
//
//            }
        }
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);

    }
}