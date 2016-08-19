package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;
import java.util.Locale;

public class GPSLocate extends AppCompatActivity {
String type;
    String chooseLocation;
    String comments;
    String address;
    int flaga=1;
    int flag=1;
   /*    Button btnshow;
    TextView myAddress =(TextView)findViewById(R.id.myaddress);
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnshow = (Button)findViewById(R.id.showlocation);
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps=new GPSTracker(MainActivity.this);
                if(gps.canGetLocation()) {
                    double latitude=gps.getLatitude();
                    double longitude=gps.getLongitude();


                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                        if(addresses != null) {
                            Address returnedAddress = addresses.get(0);
                            /*String address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();*/
  /*                          StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                            StringBuilder strAddress = new StringBuilder("Address:\n");
                            for(int j=0; j<returnedAddress.getMaxAddressLineIndex(); j++) {
                                strReturnedAddress.append(returnedAddress.getAddressLine(j)).append("\n");
                            }
                            myAddress.setText(strReturnedAddress.toString());
                            //strAddress.append(address).append("\n").append(city).append(state).append("\n").append(country).append("\n").append(postalCode).append("\n");
                            //myAddress.setText(strAddress.toString());
                        }
                        else{
                            myAddress.setText("No Address returned!");
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        myAddress.setText("Cannot get Address!");
                    }
                }
                else
                {
                    gps.showSettingsAlert();
                }

            }
        });

    }
*/

    String complaint="Type of Complaint";
    String loc="Choose Location";
    Button btnshow;
    GPSTracker gps;
    double latitude;
    double longitude;
    TextView myAddress;
    int c=0;
    int votes=0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String statusvalue=getString(R.string.status1);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpslocate);
        getSupportActionBar().setTitle("Enter Complaint Details");

        final Spinner spinner = (Spinner) findViewById(R.id.type_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_complaint, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
       final Spinner spinner1 = (Spinner) findViewById(R.id.location_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Location, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        gps = new GPSTracker(GPSLocate.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is Lat=" + latitude + "   Long=" + longitude, Toast.LENGTH_LONG).show();
            myAddress = (TextView) findViewById(R.id.myaddress);
            myAddress.setText(getCompleteAddressString(latitude, longitude));
        }
        else {
            gps.showSettingsAlert();

        }
        address= String.valueOf(myAddress.getText());
        Log.i("address",address);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                //selectedyear = parentView.getSelectedItemPosition();
                type = spinner.getSelectedItem().toString();
                if (type == complaint){
                    Toast.makeText(getApplicationContext(), "Enter the appropriate type of complaint",Toast.LENGTH_LONG).show();
                    flag =0;

                }

                //your code here

            }

            public void onNothingSelected(AdapterView<?> parentView)
            {
                Toast.makeText(getApplicationContext(), "Enter the appropriate type of complaint",Toast.LENGTH_LONG).show();
               flag=0;
                // selectedyear = 0;
                //return;
            }
        });

       spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                //your code here
                chooseLocation = spinner1.getSelectedItem().toString();
                if (chooseLocation== loc) {
                    Toast.makeText(getApplicationContext(), "Enter the appropriate location", Toast.LENGTH_LONG).show();
                    flaga=0;
                }


                }

            public void onNothingSelected(AdapterView<?> parentView)
            {
                Toast.makeText(getApplicationContext(), "Enter the appropriate location", Toast.LENGTH_LONG).show();
               flaga=0;
                //selectedmonth = 0;
                //return;
            }
        });



/*
        gps = new GPSTracker(GPSLocate.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is Lat=" + latitude + "   Long=" + longitude, Toast.LENGTH_LONG).show();
            myAddress = (TextView) findViewById(R.id.myaddress);
            myAddress.setText(getCompleteAddressString(latitude, longitude));
        }
        else {
            gps.showSettingsAlert();

        }
        address= String.valueOf(myAddress.getText());
        Log.i("address",address);
*/

        ImageView img = (ImageView) findViewById(R.id.img);
        Bundle extras = getIntent().getExtras();
        final byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        img.setImageBitmap(bmp);
        btnshow = (Button) findViewById(R.id.showlocation);
       // Boolean res=false;



            btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                gps = new GPSTracker(GPSLocate.this);
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Toast.makeText(getApplicationContext(), "Your Location is Lat=" + latitude + "   Long=" + longitude, Toast.LENGTH_LONG).show();
                    myAddress.setText(getCompleteAddressString(latitude, longitude));
                    ImageView img = (ImageView) findViewById(R.id.img);
                    Bundle extras = getIntent().getExtras();
                    byte[] byteArray = extras.getByteArray("picture");
                    Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    img.setImageBitmap(bmp);

                    */
                boolean res=false;
                if (res=(!((type.equals("Type of Complaint") && chooseLocation.equals("Choose Location")) && address != null)))
                {
                    Log.i("True of False", String.valueOf(res));
                    final ParseGeoPoint geoPoint = new ParseGeoPoint(latitude, longitude);
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("complaints");
                    query.whereWithinKilometers("location", geoPoint, 1);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (flag == 1 && flaga == 1) {
                                if (e == null) {
                                    if (objects.size() != 0) {
                                        for (final ParseObject object : objects) {
                                            if (type.equals( String.valueOf(object.get("typeofcomplaint")))) {
                                                  final ParseObject newObject= object;
                                                 votes = (int)object.get("votes");
                                                c=1;

                                                Toast.makeText(getApplicationContext(), "this complaint has already been registered. The complaint will get a vote up from your end", Toast.LENGTH_LONG).show();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(GPSLocate.this);
                                                builder.setTitle("Complaint exists. Do you want vote for this complaint?");

                                                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        showalert();
                                                        Intent backActivity = new Intent(getApplicationContext(),Finalpage.class);
                                                        startActivity(backActivity);
                                                    }
                                                });
                                                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                 votes+=1;
                                                        Log.i("Votes=", String.valueOf(votes));
                                                 newObject.put("votes",votes);
                                                        newObject.saveInBackground();
                                                        showalert();


                                                    }
                                                });
                                                AlertDialog dialog = builder.create();
                                                dialog.show();


                                                break;

                                            }
                                            }
                                        if(c==0) {
                                            ParseFile file = new ParseFile("image.png", byteArray);
                                            ParseGeoPoint geoPoint = new ParseGeoPoint(latitude, longitude);
                                            ParseObject object = new ParseObject("complaints");
                                            object.put("username", ParseUser.getCurrentUser().getUsername());
                                            object.put("image", file);

                                            object.put("location", geoPoint);

                                            EditText comm = (EditText) findViewById(R.id.comment);
                                            comments = String.valueOf(comm.getText());
                                            object.put("comment", comments);
                                            object.put("streetaddress", address);
                                            object.put("typeofcomplaint", type);
                                            object.put("generallocation", chooseLocation);

                                            object.put("status",statusvalue);
                                            object.put("confirmation",false);
                                            object.put("votes", 1);
                                            object.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e == null) {
                                                        Log.i("database", "no error");
                                                        Toast.makeText(getApplicationContext(), "COMPLAINT SUCCESSFULLY LODGED", Toast.LENGTH_LONG).show();
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(GPSLocate.this);
                                                        builder.setTitle("Do you want register another complaint?");

                                                        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {


                                                                Intent backActivity = new Intent(getApplicationContext(),Finalpage.class);
                                                                startActivity(backActivity);
                                                            }
                                                        });
                                                        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent backActivity = new Intent(getApplicationContext(), UserList.class);
                                                                startActivity(backActivity);


                                                            }
                                                        });
                                                        AlertDialog dialog = builder.create();
                                                        dialog.show();

                                                    }
                                                }
                                            });
                                    }
                                    }
                                    else { //If no complaint exist in that location
                                        ParseFile file = new ParseFile("image.png", byteArray);
                                        ParseGeoPoint geoPoint = new ParseGeoPoint(latitude, longitude);
                                        ParseObject object = new ParseObject("complaints");
                                        object.put("username", ParseUser.getCurrentUser().getUsername());
                                        object.put("image", file);

                                        object.put("location", geoPoint);
                                        EditText comm = (EditText) findViewById(R.id.comment);
                                        comments = String.valueOf(comm.getText());
                                        object.put("comment", comments);
                                        object.put("streetaddress", address);
                                        object.put("typeofcomplaint", type);
                                        object.put("status",statusvalue);
                                        object.put("confirmation",false);
                                        object.put("generallocation", chooseLocation);
                                        object.put("votes", 1);
                                        object.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    Log.i("database", "no error");
                                                    Toast.makeText(getApplicationContext(), "COMPLAINT SUCCESSFULLY LODGED", Toast.LENGTH_LONG).show();
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(GPSLocate.this);
                                                    builder.setTitle("Do you want register another complaint?");

                                                    builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent backActivity = new Intent(getApplicationContext(),Finalpage.class);
                                                            startActivity(backActivity);

                                                        }
                                                    });
                                                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent backActivity = new Intent(getApplicationContext(), UserList.class);
                                                            startActivity(backActivity);


                                                        }
                                                    });
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();


                                                }
                                            }
                                        });


                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "ENTER COMPLETE DETAILS", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
                else {
                                Toast.makeText(getApplicationContext(), "Incomplete Details Provided or location not recorded1", Toast.LENGTH_LONG).show();
                            }
                    /*
                else {
                    gps.showSettingsAlert();

                }

            }
*/
                        }
                    });


                    // ATTENTION: This was auto-generated to implement the App Indexing API.
                    // See https://g.co/AppIndexing/AndroidStudio for more information.
                    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
                }
public void showalert()
{
    AlertDialog.Builder builder = new AlertDialog.Builder(GPSLocate.this);
    builder.setTitle("Do you want register another complaint?");

    builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });
    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent backActivity = new Intent(getApplicationContext(), UserList.class);
            startActivity(backActivity);


        }
    });
    AlertDialog dialog = builder.create();
    dialog.show();

}

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
String  strAdd="";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current Address", "No Address returned!");
            }
        } catch (Exception e) {


            e.printStackTrace();
            Log.w("My Current address", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GPSLocate Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.parse.starter/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GPSLocate Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.parse.starter/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
