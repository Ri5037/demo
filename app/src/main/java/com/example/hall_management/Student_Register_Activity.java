package com.example.hall_management;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
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

public class Student_Register_Activity extends AppCompatActivity implements View.OnClickListener {


    /**declaring variables**/
    private Button signup,regbrowse;
    private EditText fullname,email,reg,password,contact,dept;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    ImageView imageView1;
    Uri filepath1;
    Bitmap bitmap1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        /**activating necessary components**/

        firebaseAuth = FirebaseAuth.getInstance();
        signup = (Button) findViewById(R.id.reg_btn);
        fullname = (EditText) findViewById(R.id.reg_name);
        email = (EditText) findViewById(R.id.reg_email);
        reg = (EditText) findViewById(R.id.reg_regno);
        password = (EditText) findViewById(R.id.reg_password);
        contact = (EditText) findViewById(R.id.reg_contact);
        progressBar = (ProgressBar) findViewById(R.id.reg_progbar);
        dept = (EditText) findViewById(R.id.reg_dept);
        imageView1 = (ImageView) findViewById(R.id.reg_img);

        /**siging up user**/
        signup.setOnClickListener(this);
        regbrowse = (Button) findViewById(R.id.reg_browse);

        /**register button is clicked**/

        regbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**taking user permission to get access to his gallery**/

                Dexter.withActivity(Student_Register_Activity.this)
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_btn:
                registerUser();
                break;
        }

    }

    private void registerUser() {

        String etemail = email.getText().toString().trim();
        String etpassword = password.getText().toString().trim();
        String etreg = reg.getText().toString().trim();
        String etcontact = contact.getText().toString().trim();
        String etname = fullname.getText().toString().trim();
        String etdept = dept.getText().toString().trim();

        /**gathering necessary info about new user before registering**/

        if (etname.isEmpty()){
            fullname.setError("Full Name Required");
            fullname.requestFocus();
            return;
        }
        if (etreg.isEmpty()){
            reg.setError("Registration Number Required");
            reg.requestFocus();
            return;
        }
        if (etemail.isEmpty()){
            email.setError("Email Required");
            email.requestFocus();
            return;
        }

        /**if email doesn't match**/

        if(!Patterns.EMAIL_ADDRESS.matcher(etemail).matches()){
            email.setError("Give Valid Email");
            email.requestFocus();
            return;
        }
        if (etpassword.isEmpty()){
            password.setError("Password Required");
            password.requestFocus();
            return;
        }
        if (etpassword.length()<6){
            password.setError("Minimum Length 6");
            password.requestFocus();
            return;
        }

        if (etcontact.isEmpty()){
            contact.setError("Contact Required");
            contact.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        /**registering user**/

        firebaseAuth.createUserWithEmailAndPassword(etemail,etpassword).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Users users = new Users(etname,etemail,etpassword,etcontact,etreg,etdept);

                            /**student info in firebase**/

                            FirebaseDatabase.getInstance().getReference("StudentsInfo")
                                    .child(reg.getText().toString()).setValue(users).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                Toast.makeText(Student_Register_Activity.this,
                                                        "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);

                                            }
                                            else{

                                                Toast.makeText(Student_Register_Activity.this,
                                                        "Failed Registration.... Try Again!!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);

                                            }
                                        }
                                    });
                        }
                        else{

                            Toast.makeText(Student_Register_Activity.this,
                                    "Already Registered", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

        /**uploading user image in firebase**/

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference uploader = storage.getReference("image1"+ new Random().nextInt(50));

        uploader.putFile(filepath1).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //progressDialog.dismiss();
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference("StudentsImage");

                                dataholder dh = new dataholder(uri.toString());

                                root.child(reg.getText().toString()).setValue(dh);

                                fullname.setText("");
                                email.setText("");
                                password.setText("");
                                contact.setText("");
                                reg.setText("");
                                dept.setText("");
                                imageView1.setImageResource(R.drawable.ic_launcher_background);
                                //Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

    }

    /**checking requestcode if permission if given**/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK){
            filepath1 = data.getData();
        }

        /**try catch block**/

        try{
            InputStream inputStream = getContentResolver().openInputStream(filepath1);
            bitmap1 = BitmapFactory.decodeStream(inputStream);
            imageView1.setImageBitmap(bitmap1);
        }
        catch (Exception ex){

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}