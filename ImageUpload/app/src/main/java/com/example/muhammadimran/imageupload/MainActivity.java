package com.example.muhammadimran.imageupload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Document;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST = 1;
    private ImageView imageView, DownloadView;
    private Uri mImageUri = null;
    private Button uploadimage, DownloadButton;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    public HashMap<String, String> hashMap = new HashMap<>();
    private Bitmap bitmap;
    private ProgressDialog pDialog;
    private String url;
    private FileOutputStream fop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        imageView = (ImageView) findViewById(R.id.imageview);
        uploadimage = (Button) findViewById(R.id.uploadImage);
        DownloadView = (ImageView) findViewById(R.id.imagedownload);
        SelectImage();
        UploadImageOnFireBAse();
        downloadImage();
    }

    private void downloadImage() {
        DownloadButton = (Button) findViewById(R.id.DowmloadButton);
        DownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Bitmap bitmap1;
                //OutputStream output1;

                new DownloadImage().execute(url);
                Log.d("TAG", "Image Url : " + url);
//                pDialog = new ProgressDialog(MainActivity.this);
//                pDialog.setMessage("Downloading Image");
//                pDialog.show();
//                Picasso.with(MainActivity.this).load(String.valueOf(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        String root = Environment.getExternalStorageState().toString();
//                        File file = new File(root + "/ImageFIRE/");
//                        if (!file.mkdirs()) {
//                            file.mkdirs();
//                        }
//                        String name = new Date().toString();
//                        file = new File(file, name);
//                        try {
//                            FileOutputStream fop = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fop);
//                            fop.flush();
//                            fop.close();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                })).into(DownloadView);
//                pDialog.dismiss();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SelectImage() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("*/*");
                startActivityForResult(intent, REQUEST);

            }
        });
    }


    private void UploadImageOnFireBAse() {
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StorageReference storage = mStorage.child("Images").child(mImageUri.toString());
                storage.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        url = taskSnapshot.getDownloadUrl().toString();
                        hashMap.put("Picture Url : ", url);
                        mDatabase.child("Images").push().setValue(hashMap);
                        Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri = result.getUri();

                Log.d("TAG", "val : " + mImageUri);
                imageView.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Downloading Image");
            pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String ImageUrl = params[0];

            try {

                InputStream inputStream = new URL(ImageUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);


            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmapimage) {
            if (bitmapimage != null) {


                String PATH = Environment.getExternalStorageDirectory() + "/" + "Firebase Images" + "" + "/";
                File imagefile = new File(PATH, bitmapimage + ".jpg");
                if (!imagefile.getParentFile().mkdirs()) {
                    imagefile.getParentFile().mkdirs();
                }

                String path = imagefile.toString();

                String image = bitmapimage.toString();
                try {
                    fop = new FileOutputStream(path);
                    bitmapimage.compress(Bitmap.CompressFormat.JPEG, 100, fop);
                    fop.write(image.getBytes());
                    fop.close();

                } catch (FileNotFoundException e) {
                    Toast.makeText(MainActivity.this, "sorry can not store", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "Value : " + imagefile.getAbsolutePath());

                DownloadView.setImageBitmap(bitmapimage);
                Log.d("TAG", "IMAGE " + bitmapimage);
                pDialog.dismiss();
            } else {

                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Sorry Image Can Not Download", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
