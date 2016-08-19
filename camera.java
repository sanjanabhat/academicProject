package com.parse.starter;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class camera extends AppCompatActivity {

    Button btnShowLocation;
    int REQUEST_CODE = 1;
    ImageView IMG;
    Button doneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        getSupportActionBar().setTitle("");

        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
//        IMG = (ImageView) findViewById(R.id.img);
      //  doneButton = (Button) findViewById(R.id.donebutton);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, REQUEST_CODE);
                }

            }
        });
         /* doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (IMG.getDrawable() != null) {
                    Intent newpage = new Intent(getApplicationContext(), GPSLocate.class);
                    startActivity(newpage);
                }
                else {
                   /* AlertDialog.Builder builder=new AlertDialog.Builder(camera.this);
builder.setMessage("Take a Picture from a camera");
                    AlertDialog dialog=builder.create();
                    dialog.show();

                    Toast.makeText(getApplicationContext(), "Take a Picture from a camera", Toast.LENGTH_LONG).show();
                }

            }

        });
                */

    }




    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == REQUEST_CODE) {
            if (resultcode == RESULT_OK) {
                Bundle bundle = new Bundle();
                bundle = data.getExtras();
                Bitmap BMP;
                BMP = (Bitmap) bundle.get("data");
                //IMG.setImageBitmap(BMP);
                Intent i=new Intent(this,GPSLocate.class);
                ByteArrayOutputStream stream= new ByteArrayOutputStream();
                BMP.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray=stream.toByteArray();
                i.putExtra("picture",byteArray);
                startActivity(i);
/*                ParseFile file=new ParseFile("image.png",byteArray);
                ParseObject object= new ParseObject("imagestore");
                object.put("username", ParseUser.getCurrentUser().getUsername());
                object.put("image",file);
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            Log.i("database","no error");
                        }
                    }
                });

*/

            }
        }
    }


}