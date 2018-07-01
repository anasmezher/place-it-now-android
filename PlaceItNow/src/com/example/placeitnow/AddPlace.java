package com.example.placeitnow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
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


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddPlace extends Activity implements OnClickListener {
	public Camera cameraObject;
	private Showcamera showCam;
	GPSTracker gps;
	double latitude, longitude;
	FrameLayout preview2;
	private String[] arayspin;
	String pname, pcountry, pcity, ptype, provider, alati, alongi, Rate, platitude, plongitude;
	public Camera cameraObject2;
	int phcount;
	public Showcamera showCamToSave;
	int pid, counter2, counterr;
	TextView place_latitude_view, place_longitude_view, ConnectedTxtView;
	EditText place_name, place_country, place_city, place_Details;
	String mlocProvider, PDet;
	Button btnSave;
	SharedPreference mysh;
	float RateValue;
	Spinner place_type;
	double currentLatitude, currentLongitude;
	private ImageButton take;
	private ImageButton save;
	private ImageButton restart;
	private ProgressDialog pd;

	ImageView imageView;
	
	
	private String city2;
	private String country;
	private String langi;
	private String lati;
	private String details;
	private String type;
	private String myID;
	
	
	
	
	
	  Button btpic, btnup;
	    private Uri fileUri;
	    String picturePath;
	    Uri selectedImage;
	    Bitmap photo;
	    String ba1;
	    public static String URL ;
	    
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_place);
		URL = getResources().getString(R.string.app_server_Name) + "addnewplace.php";
		mysh=new SharedPreference();
		 
        pd = new ProgressDialog(getApplicationContext());
        pd.setTitle("Please Wait");
        pd.setMessage("Uploading Image In Progress");
        pd.setIndeterminate(false);
        pd.setCancelable(true);
  
		this.arayspin = new String[] { "Hospital", "Home", "Hotel", "Motel", "Resturant", "University", "School",
				"Cafe", "Net Cafe", "Library", "Mol", "SuperMarket", "Bar", "Carage", "AirPort", "Port", "Garden" };
		place_type = (Spinner) findViewById(R.id.Place_Type_Spinner);
		ArrayAdapter<String> adapter_spin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				arayspin);
		place_type.setAdapter(adapter_spin);
		place_Details = (EditText) findViewById(R.id.Place_details_edittext);
		place_name = (EditText) findViewById(R.id.Place_name_edit_txt);
		place_country = (EditText) findViewById(R.id.Place_country_edit_txt);
		place_city = (EditText) findViewById(R.id.Place_city_edit_txt);
		place_latitude_view = (TextView) findViewById(R.id.Place_Lati_edit_txtView);
		place_longitude_view = (TextView) findViewById(R.id.Place_Long_edit_txtView);
		gps = new GPSTracker(getApplicationContext());

		// Check if GPS enabled
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> addresses;
			try {
				addresses = geocoder.getFromLocation(latitude, longitude, 1);

				String cityName = addresses.get(0).getAddressLine(1);
				String stateName = addresses.get(0).getAddressLine(1);
				String countryName = addresses.get(0).getAddressLine(2);
				String lat = String.valueOf(latitude);

				String longi = String.valueOf(longitude);
				place_latitude_view.setText(lat);
				place_longitude_view.setText(longi);
				place_city.setText(cityName);
				place_country.setText(countryName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
		take = (ImageButton) findViewById(R.id.btntakeimage1);
		save = (ImageButton) findViewById(R.id.btnsaveimag1);
		restart = (ImageButton) findViewById(R.id.btnretakeimag1);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    city2 = place_city.getText().toString();
				 country = place_country.getText().toString();
				langi = place_longitude_view.getText().toString();
				lati = place_latitude_view.getText().toString();
				 details = place_Details.getText().toString();
				type = place_type.getSelectedItem().toString();
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

//	public void start_my_camera() {
//
//		cameraObject = iscameraAvilable();
//		showCam = new Showcamera(this, cameraObject);
//		preview2 = (FrameLayout) findViewById(R.id.camerashoww);
//		preview2.addView(showCam);
//	}
//
//	public static Camera iscameraAvilable() {
//		Camera object = null;
//		try {
//			object = Camera.open();
//		} catch (Exception e) {
//			Log.d("ERROR", "Failed to get camera: " + e.getMessage());
//		}
//		return object;
//	}
//
//	public void take(FrameLayout frameLayout) {
//		frameLayout.setDrawingCacheEnabled(true);
//		frameLayout.buildDrawingCache();
//		
//
//	}
//	byte[] imageBytes = null;
//	public void saveFrameLayout(final FrameLayout frameLayout, final Context c, final String city2, final String country, final String langi, final String lati, final String details, final String type) {/////// then
//		save.setVisibility(View.GONE);
//		take.setVisibility(View.GONE);															/////// save
//																			/////// place
//		final String myID=mysh.getIDValue(c);
//		
//		PictureCallback mPicture = new PictureCallback() {
//
//		    @Override
//		    public void onPictureTaken(byte[] data, Camera camera) {
//				Bitmap m_bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);
//				ByteArrayOutputStream stream = new ByteArrayOutputStream();
//				m_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//				byte[] byteArray = stream.toByteArray();
//                String encodedImage = Base64.encodeToString(byteArray,Base64.DEFAULT);
//				try {
//					
//
//		            RequestParams params = new RequestParams();
//		            
//		            params.put("city", city2);
//		            params.put("country", country);
//		            params.put("langi", langi);
//		            params.put("lati", lati);
//		            params.put("details", details);
//		            params.put("type", type);
//		            params.put("ID", myID);
//		            params.put("image", encodedImage);
//		            AsyncHttpClient client = new AsyncHttpClient();
//		            client.post(getResources().getString(R.string.app_server_Name)
//		       			 + "addnewplace.php", params, new TextHttpResponseHandler() {
//		 
//		            
//		                @Override
//		                public void onStart() {
//		                	 Toast.makeText(getApplicationContext(),"saving...", Toast.LENGTH_SHORT).show();
//		                }
//		 
//		                @Override
//		                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//		 
//		                    Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_SHORT).show();
//		                }
//		 
//		                @Override
//		                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//		 
//		                    Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_SHORT).show();
//		                }
//		                 
//		 
//		                @Override
//		                public void onFinish() {
//		                    pd.dismiss();
//		                }
//		            });
//		 
//
//		     
//
//		            
//		            
//			
//				} catch (Exception e) {
//					// TODO: handle exception
//				} finally {
//					frameLayout.destroyDrawingCache();
//				}
//		       
//		    }
//		};
//		cameraObject.takePicture(null, null, mPicture);	
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//
//	}
//
//	
//	
//	
//	
	
	

	  private void upload() {
	        // Image location URL
	        Log.e("path", "----------------" + picturePath);
	 
	        // Image
	        Bitmap bm = BitmapFactory.decodeFile(picturePath);
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
	 
	            selectedImage = data.getData();
	            photo = (Bitmap) data.getExtras().get("data");
	 
	            // Cursor to get image uri to display
	 
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};
	            Cursor cursor = getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            picturePath = cursor.getString(columnIndex);
	            cursor.close();
	 
	            Bitmap photo = (Bitmap) data.getExtras().get("data");
	            imageView = (ImageView) findViewById(R.id.camerashoww);
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
	            nameValuePairs.add(new BasicNameValuePair("city", city2));
	            nameValuePairs.add(new BasicNameValuePair("country", country));
	            nameValuePairs.add(new BasicNameValuePair("langi", langi));
	            nameValuePairs.add(new BasicNameValuePair("lati", lati));
	            nameValuePairs.add(new BasicNameValuePair("details", details));
	            nameValuePairs.add(new BasicNameValuePair("type", type));
	            nameValuePairs.add(new BasicNameValuePair("ID", myID));
	            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
	            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));

	            try {
	                HttpClient httpclient = new DefaultHttpClient();
	                HttpPost httppost = new HttpPost(URL);
	                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	                HttpResponse response = httpclient.execute(httppost);
	                String st = EntityUtils.toString(response.getEntity());
	               
	      
	              
	                
	            } catch (Exception e) {
	                Log.v("log_tag", "Error in http connection " + e.toString());
	            }
	         	Intent x = new Intent(getApplicationContext(), Home.class);
				startActivity(x);

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
