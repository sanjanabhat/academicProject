package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

/**
 * Created by Admin on 5/2/2016.
 */
public class complaint extends AppCompatActivity {
    Button btn;
    EditText compid;
    ImageView img;
    TextView  user1;
    TextView votes;
    TextView type;
    TextView address;
    int confirm=0;

    String complaintid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Book A Complaint ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint);
        btn=(Button)findViewById(R.id.button);
        img=(ImageView)findViewById(R.id.imageView);
        user1=(TextView)findViewById(R.id.textView);
        votes=(TextView)findViewById(R.id.votes);
        type=(TextView)findViewById(R.id.type);
        address=(TextView)findViewById(R.id.address);
        compid = (EditText) findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                complaintid = compid.getText().toString();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("complaints");
// Specify the object id
                query.getInBackground(complaintid, new GetCallback<ParseObject>() {
                    public void done(ParseObject objects, ParseException e) {
//                        if (e == null) {

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
                        if(status.equals("Rectified")){
                            RelativeLayout rl=(RelativeLayout)findViewById(R.id.complaintid);
                            RelativeLayout.LayoutParams lparams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            Button tv1=new Button(getApplicationContext());
                            tv1.setText("Confirm");
                            // tv1.setId(1);
                            rl.addView(tv1);
                            tv1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "Thank you for confirmation. Your Complaint is being deleted!", Toast.LENGTH_LONG).show();
                                    confirm=1;
                                }});
                        }
                            Date date = objects.getCreatedAt();
                            user1.setText("The Complaint initially booked by " + user + " on " + date);

                            int vote = (int) objects.get("votes");
                            votes.setText("Votes: " + vote);
                            String typeofcomplaint = (String) objects.get("typeofcomplaint");
                            type.setText("Type of complaint: " + typeofcomplaint);
                            String addr = (String) objects.get("streetaddress");
                            address.setText(addr);
             //           } else {
                            // something went wrong
                            Log.d("wrong", "wrong");

               //         }
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
        if(confirm==1){
            final ParseQuery <ParseObject> query1=ParseQuery.getQuery("complaints");
            query1.whereEqualTo("objectId",complaintid);
            query1.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    try {
                        object.delete();
                        object.saveInBackground();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                }
            });
        }
    }
}