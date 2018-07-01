package com.example.placeitnow;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity implements OnClickListener {
	LinearLayout nearestplaces_Layout, recomendedplaces_Layout, addedplaces_Layout;
	Button adnewplace_Button;
	GPSTracker gps;
	double latitude, longitude;
	String URL, ID;
	SharedPreference mysh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy =
		       new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		mysh = new SharedPreference();
		ID = mysh.getIDValue(getApplicationContext());
		nearestplaces_Layout = (LinearLayout) findViewById(R.id.nearestplaceslayout);
		recomendedplaces_Layout = (LinearLayout) findViewById(R.id.tovisitplaceslayout);
		addedplaces_Layout = (LinearLayout) findViewById(R.id.myplaceslayout);
		adnewplace_Button = (Button) findViewById(R.id.btnaddplace);

		gps = new GPSTracker(getApplicationContext());

		// Check if GPS enabled
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			// \n is for new line
			Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
					Toast.LENGTH_LONG).show();
			adnewplace_Button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent x = new Intent(getApplicationContext(), AddPlace.class);
					x.putExtra("Lat", latitude);
					x.putExtra("Long", longitude);
					startActivity(x);
				}
			});

			 getDataFor3(nearestplaces_Layout,getResources().getString(R.string.app_server_Name)
			 + "getnearestplaces.php");
			 getDataFor(recomendedplaces_Layout,
			 getResources().getString(R.string.app_server_Name) +
			 "getrecomendedplaces.php");
			 getDataFor2(addedplaces_Layout,
			 getResources().getString(R.string.app_server_Name) +
			 "getmyaddedplaces.php");
		} else {
			startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void getDataFor(final LinearLayout ll, String URL) {
		/// asynchttp get data for url then call addtolayout
		RequestParams g = new RequestParams();
		g.put("lat", latitude);
		g.put("ID", ID);
		g.put("longi", longitude);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(20000);
		client.get(URL, g, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {

					JSONObject obj = new JSONObject(response);
					Log.d("response", response);
					if (obj.getString("status").equals("true")) {
						JSONArray co = obj.getJSONArray("names");
						JSONObject obj2;
						for (int i = 0; i < co.length(); i++) {
							obj2 = co.getJSONObject(i);

							String ID = obj2.getString("ID");
							String pname = obj2.getString("pname");
							String ptype = obj2.getString("ptype");
							
							String mphoto = obj2.getString("imagename");
							Bitmap bitmap=null;
							try {
								   bitmap = BitmapFactory.decodeStream((InputStream)new URL(getResources().getString(R.string.app_server_Name)+mphoto).getContent());
								} catch (MalformedURLException e) {
								  e.printStackTrace();
								} catch (IOException e) {
								  e.printStackTrace();
								}
						ADDToLAyout(ll, bitmap, ID, pname,ptype);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.d("at my started courses", e.getMessage());
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();

				}
			}

			@Override
			public void onFailure(int statusCode, Throwable error, String content) {

			}
		});
	}

	
	
	
	
	
	
	
	public void getDataFor2(final LinearLayout ll, String URL) {
		/// asynchttp get data for url then call addtolayout
		RequestParams g = new RequestParams();
		g.put("lat", latitude);
		g.put("ID", ID);
		g.put("longi", longitude);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(20000);
		client.get(URL, g, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {

					JSONObject obj = new JSONObject(response);
					Log.d("response", response);
					if (obj.getString("status").equals("true")) {
						JSONArray co = obj.getJSONArray("names");
						JSONObject obj2;
						for (int i = 0; i < co.length(); i++) {
							obj2 = co.getJSONObject(i);

							String ID = obj2.getString("ID");
							
							String pname = obj2.getString("pname");
							String ptype = obj2.getString("ptype");
							
							String mphoto = obj2.getString("imagename");
							Bitmap bitmap=null;
							try {
								   bitmap = BitmapFactory.decodeStream((InputStream)new URL(getResources().getString(R.string.app_server_Name)+mphoto).getContent());
								} catch (MalformedURLException e) {
								  e.printStackTrace();
								} catch (IOException e) {
								  e.printStackTrace();
								}
						ADDToLAyout(ll, bitmap, ID, pname,ptype);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.d("at my started courses", e.getMessage());
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();

				}
			}

			@Override
			public void onFailure(int statusCode, Throwable error, String content) {

			}
		});
	}
	
	
	
	
	
	
	
	
	public void getDataFor3(final LinearLayout ll, String URL) {
		/// asynchttp get data for url then call addtolayout
		RequestParams g = new RequestParams();
		g.put("lat", latitude);
		g.put("ID", ID);
		g.put("longi", longitude);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(20000);
		client.get(URL, g, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {

					JSONObject obj = new JSONObject(response);
					Log.d("response", response);
					if (obj.getString("status").equals("true")) {
						JSONArray co = obj.getJSONArray("names");
						JSONObject obj2;
						for (int i = 0; i < co.length(); i++) {
							obj2 = co.getJSONObject(i);
							Bitmap bitmap=null;
							String ID = obj2.getString("ID");
							String pname = obj2.getString("pname");
							String ptype = obj2.getString("ptype");
							String mphoto = obj2.getString("imagename");
							try {
								  bitmap = BitmapFactory.decodeStream((InputStream)new URL(getResources().getString(R.string.app_server_Name)+mphoto).getContent());
								} catch (MalformedURLException e) {
								  e.printStackTrace();
								} catch (IOException e) {
								  e.printStackTrace();
								}
							ADDToLAyout(ll, bitmap, ID, pname,ptype);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.d("at my started courses", e.getMessage());
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();

				}
			}

			@Override
			public void onFailure(int statusCode, Throwable error, String content) {

			}
		});
	}
	public void ADDToLAyout(LinearLayout ll, Bitmap bmp, final String iD, final String pname, final String ptype) {
		// add dadta to layout and set on click listner
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

		View customView = inflater.inflate(R.layout.itemsforimageslayout, null);
		ImageView image = (ImageView) customView.findViewById(R.id.imageinimages);

		image.setImageBitmap(bmp);
		TextView tv1 = (TextView) customView.findViewById(R.id.textinimages);
		tv1.setText(pname+"("+ptype+")");
		customView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent x = new Intent(getApplicationContext(), Itemcontent.class);
				x.putExtra("pID", iD);
				x.putExtra("pName", pname);
				x.putExtra("ptype", ptype);
				startActivity(x);
			}
		});
		ll.addView(customView);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
