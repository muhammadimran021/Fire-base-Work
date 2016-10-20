package com.example.muhammadimran.openpdf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final int SAVE_REQUEST_CODE = 1;
    private Button UploadButton, DownloadButton;
    private ImageView pdficon;
    private DatabaseReference mDatabas;
    private StorageReference storage;
    private Uri pathUri = null;
    private String firebaseUrl;
    private HashMap<String, String> hashMap = new HashMap<>();
    private ProgressDialog pDialog;
    private TextView filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pDialog = new ProgressDialog(this);
        mDatabas = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();
        filename = (TextView) findViewById(R.id.filename);
//////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////
        pdficon = (ImageView) findViewById(R.id.pdfImage);
        UploadButton = (Button) findViewById(R.id.pdf_Upload_Button);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("files/*");
                startActivityForResult(intent, SAVE_REQUEST_CODE);

            }
        });


        UploadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("TAG", "SADF : " + pathUri);

                StorageReference storageReference = storage.child("Files").child(pathUri.getLastPathSegment());
                storageReference.putFile(pathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        firebaseUrl = taskSnapshot.getDownloadUrl().toString();
                        Log.d("TAG", "Url : " + firebaseUrl);
                        hashMap.put("File", String.valueOf(firebaseUrl));
                        mDatabas.child("PDF-Files").push().setValue(hashMap);

                        Toast.makeText(MainActivity.this, "success!", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "URI" + firebaseUrl);

                    }
                }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Uploading failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        DownloadButton = (Button) findViewById(R.id.DownloadButton);
        DownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetriveData(MainActivity.this).execute(firebaseUrl);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SAVE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    pathUri = data.getData();
                    String FilePath = pathUri.getPath();

                    Log.d("TAg", "dsfasfsda " + FilePath);
                    // for_name_display.setText(FilePath);
                }
                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        pdficon.setVisibility(View.GONE);
        filename.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pathUri != null) {
            pdficon.setVisibility(View.VISIBLE);
            filename.setVisibility(View.VISIBLE);
            filename.setText(pathUri.getLastPathSegment().toString());
        }

    }

    public class RetriveData extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock wakeLock;

        public RetriveData(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            HttpURLConnection httpURLConnection = null;


            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {

                    return "Server Return Http " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage();
                }

                int fileLength = httpURLConnection.getContentLength();

                inputStream = httpURLConnection.getInputStream();

                String filepath = Environment.getExternalStorageDirectory() + "/imranFiles/";

                //for get a file name
                String urlExtention = String.valueOf(httpURLConnection);
                urlExtention = urlExtention.substring(urlExtention.lastIndexOf(".")).substring(0, 4);


                Log.d("TAG", "values of url : " + urlExtention);
                Log.d("TAG", "Params : " + params.toString());

                //for get a file name
                String urlExtention1 = String.valueOf(httpURLConnection);
                urlExtention1 = urlExtention1.substring(urlExtention1.lastIndexOf("%")).substring(3, urlExtention1.indexOf(".") - 2);

                File file = new File(filepath, urlExtention1 + urlExtention);
                if (!file.getParentFile().mkdirs()) {
                    file.getParentFile().mkdirs();
                }
                outputStream = new FileOutputStream(file);


                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = inputStream.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        inputStream.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    outputStream.write(data, 0, count);
                }


            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (outputStream != null)
                        outputStream.close();
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException ignored) {
                }

                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
            }
            Log.d("TAG", "values : " + httpURLConnection.getContentLength());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            wakeLock.acquire();
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // if we get here, length is known, now set indeterminate to false
            pDialog.setMessage("Downloading File");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            wakeLock.release();
            pDialog.dismiss();

            if (s != null) {
                Toast.makeText(context, "Download file error: ", Toast.LENGTH_LONG).show();
                Log.d("TAG", "File " + s);

            } else {
                Toast.makeText(context, "Download File", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }
    }

}
