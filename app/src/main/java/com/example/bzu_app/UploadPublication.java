package com.example.bzu_app;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UploadPublication extends AppCompatActivity {
    private FirebaseStorage storge;
    private StorageReference storageReference;
    private Button uploadButton;
    private ImageView uploadImage;
    EditText uploadCaption;
    ProgressBar progressBar;
    private Uri imageUri;
    private Button button;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");

    //    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_publication);
        uploadButton = findViewById(R.id.button2);
        uploadCaption = findViewById(R.id.uploadCaption);
        uploadImage = findViewById(R.id.uploadImage);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        storge = FirebaseStorage.getInstance();




        storageReference = storge.getReference();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            uploadImage.setImageURI(imageUri);
                        } else {
                            Toast.makeText(UploadPublication.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES. TIRAMISU) {
//
//            if (ContextCompat.checkSelfPermission( UploadNews.this, android.Manifest.permission.POST_NOTIFICATIONS) !=
//                    PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(UploadNews.this,new String[]{Manifest.permission.POST_NOTIFICATIONS),  101);
//                }}
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caption = uploadCaption.getText().toString();
                if (imageUri != null || !caption.isEmpty()) { // Check if imageUri is not null or caption is not empty
                    uploadToFirebase(imageUri, caption);
                } else {
                    Toast.makeText(UploadPublication.this, "Please select image or write caption", Toast.LENGTH_SHORT).show();
                }
            macknotficatio();


            }

        });
    }

    private void macknotficatio() {
        String chanelID = "CHANNEL_ID_NOTIFICATION";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), chanelID);

        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("Notification Title")
                .setContentText("Some text for notification here")
                .setAutoCancel(true)
                .setPriority (NotificationCompat. PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), UploadPublication.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("data",  "Some value to be passed here");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0, intent, PendingIntent.FLAG_MUTABLE);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {

            NotificationChannel notificationChannel = notificationManager.getNotificationChannel (chanelID);

            if (notificationChannel == null) {

                int importance = NotificationManager.IMPORTANCE_HIGH;

                notificationChannel = new NotificationChannel(chanelID, "Some description", importance);


                notificationChannel.setLightColor(Color.GREEN);

                notificationChannel.enableVibration(true);

                notificationManager.createNotificationChannel (notificationChannel);

            }

        }
        notificationManager.notify( 8, builder.build());

    }

    private void uploadToFirebase(Uri uri, String caption) {
        StorageReference imageReference;
        if (uri != null) {
            imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        } else {
            imageReference = null;
        }

        if (imageReference != null) {
            imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DataClass dataClass = new DataClass(uri.toString(), caption);
                            String key = databaseReference.push().getKey();
                            databaseReference.child(key).setValue(dataClass);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(UploadPublication.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UploadPublication.this, UploadNews.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(UploadPublication.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            DataClass dataClass = new DataClass(null, caption); // Create DataClass without image URI
            String key = databaseReference.push().getKey();
            databaseReference.child(key).setValue(dataClass);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(UploadPublication.this, "Uploaded", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UploadPublication.this, UploadNews.class);
            startActivity(intent);
            finish();
        }
    }

    private String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            imageUri = data.getData();

            uploadImage.setImageURI(imageUri);
            uploadPicture();
        }

    }
    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");

        pd.show();

        final String randomkey = UUID.randomUUID().toString();

        StorageReference riversRef = storageReference.child("images/" + randomkey);

        riversRef.putFile(imageUri)

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override

                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();

                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();

                    }

                })

                .addOnFailureListener(new OnFailureListener() {

                    @Override

                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();

                        Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();

                    }

                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override

                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                        pd.setMessage("Percentage:" + (int) progressPercent + 1);

                    }

                });

    }
}
