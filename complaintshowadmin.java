package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

public class complaintshowadmin extends AppCompatActivity {
    Button btn;
    EditText compid;
    int isAdmin;
    ImageView img;
    TextView  user1;
    TextView votes;
    TextView type;
    TextView address;
    int vote;

    String complaintid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaintshowadmin);
        getSupportActionBar().setTitle("Complaint Details");

        btn=(Button)findViewById(R.id.button);
        img=(ImageView)findViewById(R.id.imageView);
        user1=(TextView)findViewById(R.id.textView);
        votes=(TextView)findViewById(R.id.votes);
        type=(TextView)findViewById(R.id.type);
        address=(TextView)findViewById(R.id.address);
        //compid = (EditText) findViewById(R.id.editText);

        Bundle extras = getIntent().getExtras();

        complaintid = extras.getString("complaintid");
        isAdmin=extras.getInt("admin");

        Log.i("Complaint",complaintid);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("complaints");
// Specify the object id
        query.getInBackground(complaintid, new GetCallback<ParseObject>() {
            public void done(ParseObject objects, ParseException e) {
                if (e == null) {
                    Log.i("NO",complaintid);
                    ParseFile file = (ParseFile) objects.get("image");
                    file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
                                img.setImageBitmap(image);
                            }
                        }
                    });
                    String user = (String) objects.get("username");
                    String status=(String) objects.get("status");


                    Date date = objects.getCreatedAt();
                    user1.setText("The Complaint initially booked by " + user + " on " + date+"and the status is "+status);

                    vote = (int) objects.get("votes");
                    votes.setText("Votes: " + vote);
                    String typeofcomplaint = (String) objects.get("typeofcomplaint");
                    type.setText("Type of complaint: " + typeofcomplaint);
                    String addr = (String) objects.get("streetaddress");
                    address.setText(addr);
                } else {
                    // something went wrong
                    Log.d("wrong", "wrong");

                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("complaints");
// Specify the object id
                query.getInBackground(complaintid, new GetCallback<ParseObject>() {
                    public void done(ParseObject objects, ParseException e) {
                        if (e == null) {



                            objects.put("status", "Work in Progress");
                            objects.saveInBackground();
                        }
                    }
                });
            }
        });
                /*ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("complaints");
                query.whereEqualTo("objectId", complaintid);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null){
                            if(objects.size()>0){
                                for(ParseObject object:objects){
                                    ParseFile file=(ParseFile)object.get("image");
                                    file.getDataInBackground(new GetDataCallback() {
                                        @Override
                                        public void done(byte[] data, ParseException e) {
                                            if (e == null) {
                                                Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                img.setImageBitmap(image);
                                            }
                                        }
                                    });
                                    String user=(String)object.get("username");
                                    Date date=(Date)object.get("createdAt");
                                    user1.setText("The Complait initially booked by "+user+" on "+date);

                                    int vote=(int)object.get("votes");
                                    votes.setText("Votes: "+vote);
                                    String typeofcomplaint=(String)object.get("typeofcomplaint");
                                    type.setText("Type of complaint: " +typeofcomplaint);
                                    String addr=(String)object.get("streetaddress");
                                    address.setText(addr);
                                }
                            }
                        }
                    }
                });

            }
        });*/

        Button logbtn=(Button)findViewById(R.id.logbtn);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("complaints");
// Specify the object id
                query.getInBackground(complaintid, new GetCallback<ParseObject>() {
                    public void done(ParseObject objects, ParseException e) {
                        if (e == null) {



                            objects.put("status", "Rectified");
                            objects.saveInBackground();
                        }
                    }
                });
            }
        });

    }
    }

