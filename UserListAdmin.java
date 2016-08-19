
package com.parse.starter;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TabHost;

        import com.google.android.gms.common.api.GoogleApiClient;
        import com.parse.FindCallback;
        import com.parse.GetDataCallback;
        import com.parse.ParseException;
        import com.parse.ParseFile;
        import com.parse.ParseObject;
        import com.parse.ParseQuery;

        import java.util.ArrayList;
        import java.util.List;

public class UserListAdmin extends AppCompatActivity {

    Button btn;
    Button search;
    Button signup;
    ListView list,list1;


    ArrayList<String> things1= new ArrayList<String>();
    ArrayList<String> things= new ArrayList<String>();
    ArrayList<Bitmap> images= new ArrayList<Bitmap>();
    ArrayList<Bitmap> images1= new ArrayList<Bitmap>();
    ArrayList<String> complaintidlatest=new ArrayList<String>();
    ArrayList<String> complaintidpopular=new ArrayList<String>();

    String[] web = {
            /*"Google Plus",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Drupal"*/
    };
    Integer[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7

    };
    String[] web1 = {
            "Google Plus",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Drupal"
    };
    Integer[] imageId1 = {
            R.drawable.image1,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image2,

    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_admin);
        getSupportActionBar().setTitle("Admin Page");


      /*  search=(Button)findViewById(R.id.searchbtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ntent = new Intent(getApplicationContext(), complaint.class);
                startActivity(ntent);
            }
        });*/
        signup=(Button)findViewById(R.id.signupbtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ntent = new Intent(getApplicationContext(), signupadmin.class);
                startActivity(ntent);
            }
        });
       // btn= (Button) findViewById(R.id.button1);
        //btn.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {

//                Intent ntent = new Intent(getApplicationContext(), camera.class);
  //              startActivity(ntent);
    //        }
      //  });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("complaints");
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for (ParseObject object : objects) {
                        things.add((String) object.get("generallocation"));
                        complaintidlatest.add(object.getObjectId());
                        ParseFile file = (ParseFile) object.get("image");

                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    images.add(image);
                                }
                            }
                        });

                    }
                }
            }
        });

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("complaints");
        query1.addDescendingOrder("votes");
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for (ParseObject object : objects) {
                        things1.add("votes:"+object.get("votes"));
                        //complaintidlatest.add((String)object.get("objectId"));
                        complaintidpopular.add(object.getObjectId());
                        ParseFile file = (ParseFile) object.get("image");

                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    images1.add(image);
                                }
                            }
                        });

                    }
                }
            }
        });




        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();
        //Tab 1

        final TabHost.TabSpec spec = host.newTabSpec("Latest");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Latest");
        host.addTab(spec);

        //Tab 2

        final TabHost.TabSpec spec1 = host.newTabSpec("Popular");
        spec1.setContent(R.id.tab2);
        spec1.setIndicator("Popular");
        host.addTab(spec1);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            String com;
            @Override
            public void onTabChanged(String tabId) {
                Log.i("Going inside if 1 ",tabId);
                if(tabId.equals("Latest")) {
                    Log.i("Going inside if 1 ","yes");
                    //Change first image
                    CustomList adapter = new CustomList(UserListAdmin.this, things, images);
                    list = (ListView) findViewById(R.id.list);
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            //Toast.makeText(UserList.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
                            com=complaintidlatest.get(position);
                            Intent newact=new Intent(UserListAdmin.this,complaintshow.class);
                            newact.putExtra("complaintid",com);
                            Log.i("Complaint-Id",com);
                            startActivity(newact);


                        }
                    });


                }
                if(tabId.equals("Popular")) {

                    CustomList adapter = new
                            CustomList(UserListAdmin.this,things1, images1);
                    list1 = (ListView) findViewById(R.id.list1);
                    list1.setAdapter(adapter);
                    list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            //Toast.makeText(UserList.this, "You Clicked at " + web1[+position], Toast.LENGTH_SHORT).show();
                            com=complaintidpopular.get(position);
                            Intent newact=new Intent(UserListAdmin.this,complaintshowadmin.class);
                            newact.putExtra("complaintid",com);
                            newact.putExtra("admin",1);
                            Log.i("Complaint-id",com);
                            startActivity(newact);
                        }
                    });//change second image ...so on*//**//*
                }
            }});

    }


}

