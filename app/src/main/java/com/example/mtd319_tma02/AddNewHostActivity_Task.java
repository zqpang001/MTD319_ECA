package com.example.mtd319_tma02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddNewHostActivity_Task extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView imgMain;

    Spinner categorySpinner;
    String spinnerSelected;
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
    Intent intent;
    String isDeliveryAvailable;
    Uri filepath;
    String imageUrl;
    private final int GALLERY_REQ_CODE=1000;


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
//        initConfig();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.category, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);

        getSupportActionBar().setTitle("Host");

        //Config cloudinary connection


        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigationView);
        //Set Host Selected
        bottomNavigationView.setSelectedItemId(R.id.host);
        //Bottom navigation selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeActivity.class));
                        overridePendingTransition(0,0);
                        SignInActivity.callListingItem();
                        return true;
                    case R.id.host:
                        startActivity(new Intent(getApplicationContext()
                                ,AddNewHostActivity_Task.class));
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.bag:
//                        startActivity(new Intent(getApplicationContext()
//                                ,AddNewHostActivity_Task.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.profile:
//                        startActivity(new Intent(getApplicationContext()
//                                ,AddNewHostActivity_Task.class));
//                        overridePendingTransition(0,0);
//                        return true;

                }
                return false;
            }
        });




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

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getItemAtPosition(i).equals("Select Category")){
            // do nothing
        }else{
            spinnerSelected = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(getApplicationContext(),spinnerSelected,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addNewPhoto(View view) {
        Intent iGallery = new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iGallery,GALLERY_REQ_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_REQ_CODE && resultCode==RESULT_OK){
//            imgMain.setImageURI(data.getData());
            filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                addNewPhoto.setImageBitmap(bitmap);
//                encodeBitmapImage(bitmap);
            }catch (Exception ex){

            }
        }
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesOfImages=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesOfImages, Base64.DEFAULT);
    }

    public void confirmUploadListingItem(View view) {
        MediaManager.get().upload(filepath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d("cloudinary: ","onStart");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d("cloudinary: ","onProgress");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Log.d("cloudinary: ","onSuccess"+resultData.get("url").toString());
                imageUrl=resultData.get("url").toString();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d("cloudinary: ","onError");
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.d("cloudinary: ","onReschedule");
            }
        }).dispatch();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mtd319-ed05.restdb.io/rest/host?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                response -> Toast.makeText(this,"Upload listing item success",Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this,"Error upload listing item",Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                checkDeliveryAvailability();
                ListingItem listingItem = new ListingItem(spinnerSelected,listingTitleField.getText().toString(),priceTextField.getText().toString(),quantityAvailableTextField.getText().toString(),locationTextField.getText().toString(),isDeliveryAvailable,imageUrl);
                Gson gson = new Gson();
                String jsonString = gson.toJson(listingItem);
                Map map = gson.fromJson(jsonString, Map.class);
                Log.d("getMap: ",map.toString());


                return map;
            }



        };
        queue.add(stringRequest);
//        intent = new Intent(this,AddNewHostActivity_Task.class);
//        startActivity(intent);
    }

    public void checkDeliveryAvailability(){
        if (deliveryCheckBox.isChecked()){
            isDeliveryAvailable="true";
        }else{
            isDeliveryAvailable="false";
        }
    }

    private void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "djyg6gc6k");
        config.put("api_key", "599563296253257");
        config.put("api_secret", "jOdaE_9KHP6BMATSsbW2zLqWka8");
        try {
            MediaManager.init(this,config);
        }catch(Exception e) {
            Log.d("Media Manager","Media Manager went wrong");
        }

    }

}