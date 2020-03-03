package com.samu.imagefromtablerow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class ViewImage extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextid;
    private Button button;
    private ImageView imageView;
    private RequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        editTextid = (EditText)findViewById(R.id.editText);
        button =(Button) findViewById(R.id.getImage);
        imageView =(ImageView)findViewById(R.id.imageView);
        requestHandler = new RequestHandler();

        button.setOnClickListener(this);

    }
    private void getImage(){
        String id = editTextid.getText().toString().trim();

        class GetImage extends AsyncTask<String,Void, Bitmap> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewImage.this, "Downloading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                imageView.setImageBitmap(b);

            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "http://10.0.2.2/LifePro/Image_1st_Project/getImage.php?id="+id;
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                }
                catch (MalformedURLException e){
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                return image;
        }

    }

        GetImage getImage = new GetImage();
        getImage.execute(id);

        }






    @Override
    public void onClick(View v) {
        getImage();

    }
}
