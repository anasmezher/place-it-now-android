package com.example.placeitnow;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.placeitnow.AddPlace.uploadToServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AddImage extends Activity implements OnClickListener {
	public Camera cameraObject;
	private Showcamera showCam;
	FrameLayout preview2;
	static int i = 0;
	ImageButton take, save, restart;
	
	  Button btpic, btnup;
	    private Uri fileUri;
	    String picturePath;
	    Uri selectedImage;
	    Bitmap photo;
	    String ba1;
	    private String myID;
		private String PlaceID;
		private String ID;
		private ImageView imageView;
		private SharedPreference mysh;
		private String PlaceName;
		private String Ptype;
	    public static String URL ;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_image);
		mysh=new SharedPreference();
		URL = getResources().getString(R.string.app_server_Name) + "addimagetoplace.php";
		take = (ImageButton) findViewById(R.id.btntakeimage);
		save = (ImageButton) findViewById(R.id.btnsave);
		restart = (ImageButton) findViewById(R.id.btnretake);
		Bundle extras = getIntent().getExtras();
		PlaceID = extras.getString("PlaceID");
		ID = extras.getString("ID");
		PlaceName = extras.getString("pName");
		Ptype = extras.getString("ptype");
		save.setOnClickListener(new OnClickListener() {

			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myID=mysh.getIDValue(getApplicationContext());
				upload();
				///saveFrameLayout(preview2, getApplicationContext(),city,country,langi,lati,details,type);

			}
		});
		restart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				///cameraObject.startPreview();
				
				save.setVisibility(v.GONE);
				restart.setVisibility(v.GONE);
				take.setVisibility(View.VISIBLE);	

			}
		});
		take.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			///	take(preview2);
				clickpic();
			}
		});


	}


	  private void upload() {
	        // Image location URL
		
	        imageView.buildDrawingCache();
	        Bitmap bmap = imageView.getDrawingCache();
	        ByteArrayOutputStream bao = new ByteArrayOutputStream();
	        bmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
	        byte[] ba = bao.toByteArray();
	        ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);
	 
	        Log.e("base64", "-----" + ba1);
	 
	        // Upload image to server
	        new uploadToServer().execute();
	 
	    }
	 
	    private void clickpic() {
	        // Check Camera
	        if (getApplicationContext().getPackageManager().hasSystemFeature(
	                PackageManager.FEATURE_CAMERA)) {
	            // Open default camera
	            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	 
	            // start the image capture Intent
	            startActivityForResult(intent, 100);
	 
	        } else {
	            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
	        }
	    }
	 
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == 100 && resultCode == RESULT_OK) {
	 
	
	 
	            Bitmap photo = (Bitmap) data.getExtras().get("data");
	            imageView = (ImageView) findViewById(R.id.camerapreview);
	            imageView.setImageBitmap(photo);

				save.setVisibility(View.VISIBLE);
				restart.setVisibility(View.VISIBLE);
	        }
	    }
	 
	    public class uploadToServer extends AsyncTask<Void, Void, String> {
	 
	 
	        protected void onPreExecute() {
	            super.onPreExecute();

	        }
	 
	        @Override
	        protected String doInBackground(Void... params) {
//	            params.put("city", city2);
//	            params.put("country", country);
//	            params.put("langi", langi);
//	            params.put("lati", lati);
//	            params.put("details", details);
//	            params.put("type", type);
//	            params.put("ID", myID);
//	            params.put("image", encodedImage);
	            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
	            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
	            nameValuePairs.add(new BasicNameValuePair("ID", myID));
	            nameValuePairs.add(new BasicNameValuePair("placeID", PlaceID));
              
	            try {
	                HttpClient httpclient = new DefaultHttpClient();
	                HttpPost httppost = new HttpPost(URL);
	                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	                HttpResponse response = httpclient.execute(httppost);
	                String st = EntityUtils.toString(response.getEntity());
	           
	 
	            } catch (Exception e) {
	                Log.v("log_tag", "Error in http connection " + e.toString());
	            }
	        	Intent x = new Intent(getApplicationContext(), Itemcontent.class);
				x.putExtra("pID", PlaceID);
				x.putExtra("pName", PlaceName);
				x.putExtra("ptype", Ptype);
				startActivity(x);
	            finish();
	            return "Success";
	 
	        }
	 
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);

	        }
	    }





	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
