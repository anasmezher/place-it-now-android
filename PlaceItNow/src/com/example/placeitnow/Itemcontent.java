package com.example.placeitnow;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Itemcontent extends Activity implements OnClickListener {
	TextView placename, placedescription;
	LinearLayout ImagesLayout, CommentsLayout;
	EditText commentedttext;
	ImageView sendcommentButton, addimagebutton;
	String ID, PlaceID, PlaceName,personname;
	SharedPreference mysh;

	private PopupWindow mPopupWindow;
	private String Ptype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itemcontent);


		mysh = new SharedPreference();
		Bundle extras = getIntent().getExtras();
		PlaceID = extras.getString("pID");
		PlaceName = extras.getString("pName");
		Ptype = extras.getString("ptype");
		Toast.makeText(getApplicationContext(), Ptype, Toast.LENGTH_LONG).show();
		ID = mysh.getIDValue(getApplicationContext());
		personname=mysh.getnameValue(getApplicationContext());
		placedescription = (TextView) findViewById(R.id.description);
		placename = (TextView) findViewById(R.id.name);
		ImagesLayout = (LinearLayout) findViewById(R.id.imageslayout);
		addimagebutton = (ImageView) findViewById(R.id.addimagebutton);
		CommentsLayout = (LinearLayout) findViewById(R.id.commentsLayout);
		commentedttext = (EditText) findViewById(R.id.commentedittext);
		sendcommentButton = (ImageView) findViewById(R.id.sendcommentbutton);
		placename.setText(PlaceName);
		placedescription.setText(Ptype);
		GetImages(ImagesLayout, PlaceID);
		GetComments(CommentsLayout);
		
		
		
		addimagebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// open addimage activity
				Intent x = new Intent(getApplicationContext(), AddImage.class);
				String a=placename.getText().toString();
				String b=placedescription.getText().toString();
				x.putExtra("PlaceID", PlaceID);
				x.putExtra("ID", ID);
				x.putExtra("pName", a);
				x.putExtra("ptype", b);
				startActivity(x);

			}
		});
		sendcommentButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String comment = commentedttext.getText().toString();
				sendcommentButton.setImageResource(android.R.drawable.ic_menu_send);
				commentedttext.setText("");
				SendComment(ID, comment);
			}
		});
		commentedttext.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				sendcommentButton.setImageResource(android.R.drawable.ic_media_next);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void GetImages(LinearLayout ll, String placeID) {

		RequestParams g = new RequestParams();
		g.put("pID", placeID);
		Toast.makeText(getApplicationContext(), placeID, Toast.LENGTH_LONG).show();
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(20000);
		client.get(getResources().getString(R.string.app_server_Name) + "getplaceimages.php", g, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {

					JSONObject obj = new JSONObject(response);
					response = null;
					if (obj.getString("status").equals("true")) {
						JSONArray co = obj.getJSONArray("names");
						JSONObject obj2;
						for (int i = 0; i < co.length(); i++) {
							obj2 = co.getJSONObject(i);

							String mphoto = obj2.getString("imagename");

							Toast.makeText(getApplicationContext(), mphoto, Toast.LENGTH_LONG).show();
							Bitmap bitmap=null;
							try {
								   bitmap = BitmapFactory.decodeStream((InputStream)new URL(getResources().getString(R.string.app_server_Name)+mphoto).getContent());
								} catch (MalformedURLException e) {
								  e.printStackTrace();
								} catch (IOException e) {
								  e.printStackTrace();
								}
							SetImagesToLayout(bitmap, ID, placename.getText().toString());
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

	public void SetImagesToLayout(Bitmap bmp, final String ID, String placename) {
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

		View customView = inflater.inflate(R.layout.itemsforimageslayout, null);

		ImageView image = (ImageView) customView.findViewById(R.id.imageinimages);

		image.setImageBitmap(bmp);

		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//openpopup(v, ID);
			}
		});
		ImagesLayout.addView(customView);
	}

	protected void openpopup(View v, String iD2) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

		// Inflate the custom layout/view
		View customView = inflater.inflate(R.layout.popupimages, null);

		mPopupWindow = new PopupWindow(customView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		// Get a reference for the custom view close button
		ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
		final ImageView im = (ImageView) customView.findViewById(R.id.popupimage);

		RequestParams g = new RequestParams();
		g.put("ID", iD2);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(20000);
		client.get(getResources().getString(R.string.app_server_Name)+"getimage.php", g, new AsyncHttpResponseHandler() {
			@SuppressWarnings("null")
			@Override
			public void onSuccess(String response) {
				try {

					// JSON Object
					JSONObject obj = new JSONObject(response);
					if (obj.getString("status").equals("true")) {
						String mphoto = obj.getString("imagename");
						try {
							  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(getResources().getString(R.string.app_server_Name)+mphoto).getContent());
							  im.setImageBitmap(bitmap);
							} catch (MalformedURLException e) {
							  e.printStackTrace();
							} catch (IOException e) {
							  e.printStackTrace();
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

		// Set a click listener for the popup window close button
		closeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Dismiss the popup window
				mPopupWindow.dismiss();
			}
		});

		mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

	}

	public void GetComments(LinearLayout ll) {
	
		RequestParams g = new RequestParams();
		g.put("ID", PlaceID);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(20000);
		client.get(getResources().getString(R.string.app_server_Name)+"getcomments.php", g, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {

					JSONObject obj = new JSONObject(response);
					response = null;
					if (obj.getString("status").equals("true")) {
						JSONArray co = obj.getJSONArray("names");
						JSONObject obj2;
						for (int i = 0; i < co.length(); i++) {
							obj2 = co.getJSONObject(i);

							String time = obj2.getString("datee");
							String comment = obj2.getString("comment");
							String pname = obj2.getString("pname");
							SetCommentsToLayout(time, comment, pname);
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

	protected void SetCommentsToLayout(String time, String comment, String pname) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

		// Inflate the custom layout/view
		View customView = inflater.inflate(R.layout.itemsforcommentslayout, null);
		TextView commentername = (TextView) customView.findViewById(R.id.txtname);
		TextView commentdate = (TextView) customView.findViewById(R.id.txtdate);
		TextView commentcontent = (TextView) customView.findViewById(R.id.txtcoment);
		commentername.setText(pname);
		commentdate.setText(time);
		commentcontent.setText(comment);
		CommentsLayout.addView(customView);
	}

	protected void SendComment(String iD2, final String comment) {
		// TODO Auto-generated method stub
		RequestParams rr = new RequestParams();
		rr.put("personID", iD2);
		rr.put("personname", personname);
		rr.put("placeID", PlaceID);
		rr.put("comment", comment);
		AsyncHttpClient cl = new AsyncHttpClient();
		cl.setTimeout(30000);
		cl.get(getResources().getString(R.string.app_server_Name)+"addcomment.php", rr, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {

					JSONObject obj = new JSONObject(response);
					response = null;
					if (obj.getString("status").equals("true")) {
						Toast.makeText(getApplicationContext(), "comment added sucessfully", Toast.LENGTH_LONG).show();
						SetCommentsToLayout("now", comment, "ME") ;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.itemcontent, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
