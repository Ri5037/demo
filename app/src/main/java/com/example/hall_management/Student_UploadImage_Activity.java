package com.example.hall_management;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class Student_UploadImage_Activity extends AppCompatActivity {

    EditText etreg,etname,etdept,etcontact,etemail,etpassword;
    Button signup,browse,upload;
    ImageView imageView;
    Uri filepath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_uploadimage);

        imageView = (ImageView) findViewById(R.id.img);
        upload = (Button) findViewById(R.id.STD_Upload);
        browse = (Button) findViewById(R.id.browse);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(Student_UploadImage_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtofirebase();
            }
        });
    }


    private void uploadtofirebase() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registering User");
        progressDialog.show();


        etname = (EditText) findViewById(R.id.STD_NAME);
        etdept = (EditText) findViewById(R.id.STD_DEPT);
        etcontact = (EditText) findViewById(R.id.STD_CONTACT);
        etemail = (EditText) findViewById(R.id.STD_EMAIL);
        etreg = (EditText) findViewById(R.id.STD_REG_NO);
        etpassword = (EditText) findViewById(R.id.STD_Password);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference uploader = storage.getReference("image1"+ new Random().nextInt(50));

        uploader.putFile(filepath).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //progressDialog.dismiss();
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                progressDialog.dismiss();
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference("Students");

                                //dataholder dh = new dataholder(uri.toString());

                                //root.child(etreg.getText().toString()).setValue(dh);

                                etname.setText("");
                                etemail.setText("");
                                etpassword.setText("");
                                etcontact.setText("");
                                etreg.setText("");
                                etdept.setText("");
                                imageView.setImageResource(R.drawable.ic_launcher_background);
                                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull  UploadTask.TaskSnapshot snapshot) {
                float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded :"+(int)percent+" %");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            filepath = data.getData();
        }
        try{
            InputStream inputStream = getContentResolver().openInputStream(filepath);
            bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
        }catch (Exception ex){

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}