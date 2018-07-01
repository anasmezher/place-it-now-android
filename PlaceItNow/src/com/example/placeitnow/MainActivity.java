package com.example.placeitnow;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	SharedPreference mysh;
	EditText SignUPemail, SignUPname, SignUPUserName, SignUPPassword, SignUPPhone, SignUPAdress, Loginemail,
			Loginpassword;
	Button Loginbtn, SignUPbtn, setDate;
	CheckBox remembreckbx;
	Spinner Student_type;
	String birthDate;
	OnDateSetListener selectedDate;
	private int year;
	private int monthOfYear;
	private int dayOfMonth;
	private Date current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		mysh = new SharedPreference();
		current = new Date();
		year = current.getYear() + 1900;
		monthOfYear = current.getMonth() + 1;
		dayOfMonth = current.getDay() - 1;
		String[] arayspin = new String[] { "Male", "Female" };
		Student_type = (Spinner) findViewById(R.id.gender_Spinner);
		ArrayAdapter<String> adapter_spin = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, arayspin);
		Student_type.setAdapter(adapter_spin);
		setDate = (Button) findViewById(R.id.btnsetDate);
		SignUPAdress = (EditText) findViewById(R.id.input_adress);
		SignUPemail = (EditText) findViewById(R.id.input_emails);
		SignUPname = (EditText) findViewById(R.id.input_names);
		SignUPPassword = (EditText) findViewById(R.id.input_passwords);
		SignUPPhone = (EditText) findViewById(R.id.input_phones);
		SignUPUserName = (EditText) findViewById(R.id.input_Unames);
		SignUPbtn = (Button) findViewById(R.id.btn_signup);
		Loginpassword = (EditText) findViewById(R.id.logpassword);
		Loginemail = (EditText) findViewById(R.id.logemail);
		Loginbtn = (Button) findViewById(R.id.btn_login);
		remembreckbx = (CheckBox) findViewById(R.id.logremembre);
		selectedDate = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker p, int myear, int month, int day) {
				// TODO Auto-generated method stub
				String y = String.valueOf(myear);
				String m = String.valueOf(month + 1);
				String d = String.valueOf(day);

				birthDate = y + "-" + m + "-" + d;
				Toast.makeText(getApplicationContext(), birthDate, Toast.LENGTH_LONG).show();

			}
		};
		setDate.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				showDialog(4);

			}
		});
		

		TabHost host = (TabHost) findViewById(R.id.tabhost);
		host.setup();

		// Tab 1
		TabHost.TabSpec spec = host.newTabSpec("Sign In");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Sign In");
		host.addTab(spec);

		// Tab 2
		spec = host.newTabSpec("Sign UP");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Sign UP");
		host.addTab(spec);
		if (mysh.get_used_times(getApplicationContext()) == 0) {
			Toast.makeText(getApplicationContext(), "Welcome To first Running Time", Toast.LENGTH_LONG).show();
		} else {
			if (mysh.getvalidValue(getApplicationContext()).equals("1")) {
				remembreckbx.setChecked(true);
				Loginemail.setText(mysh.getemailValue(getApplicationContext()));
				Loginpassword.setText(mysh.getpassValue(getApplicationContext()));
				Login(mysh.getemailValue(getApplicationContext()), mysh.getpassValue(getApplicationContext()));

			}
			if (mysh.getvalidValue(getApplicationContext()).equals("0")) {
				remembreckbx.setChecked(false);
			}

		}
		Loginbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Login(Loginemail.getText().toString(), Loginpassword.getText().toString());
			}
		});
		SignUPbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SignUp(SignUPAdress.getText().toString(), SignUPemail.getText().toString(),
						SignUPname.getText().toString(), SignUPPassword.getText().toString(),
						SignUPPhone.getText().toString(), SignUPUserName.getText().toString());
				;
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog d = null;
		switch (id) {

		case 4:
			// code to create date picker dialog
			DatePickerDialog p = new DatePickerDialog(this, selectedDate, year, monthOfYear, dayOfMonth);
			d = p;
		}

		return d;
	}

	protected void SignUp(String adrs, final String email, final String name, final String pass, final String phone,
			String username) {
		Toast.makeText(getApplicationContext(), "signing....", Toast.LENGTH_LONG).show();
		// TODO Auto-generated method stub
		String gender = Student_type.getSelectedItem().toString();
		RequestParams params = new RequestParams();
		params.put("email", email);
		params.put("pass", pass);
		params.put("adress", adrs);
		params.put("phone", phone);
		params.put("name", name);
		params.put("username", username);
		params.put("gender", gender);
		params.put("birthdate", birthDate);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(70000);
		
		client.get(getResources().getString(R.string.app_server_Name) + "signup.php", params,
				new AsyncHttpResponseHandler() {

					ArrayList<String> NameList = new ArrayList<String>();

					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
						// Hide Progress Dialog
						try {

							// JSON Object
							JSONObject obj = new JSONObject(response);
							// When the JSON response has status boolean value
							// assigned with true

							if (obj.getString("status").equals("true")) {

								mysh.save(getApplicationContext(), name, phone, email, pass, "0", obj.getString("ID"));
								Intent x = new Intent(getApplicationContext(), Home.class);
								startActivity(x);
							} else {
								Toast.makeText(getApplicationContext(), "ksjdksjd.", Toast.LENGTH_LONG).show();

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block

							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "else."+response, Toast.LENGTH_LONG).show();

						}
					}

					// When the response returned by REST has Http response code
					// other than '200'
					@Override
					public void onFailure(int statusCode, Throwable error, String content) {
						Toast.makeText(getApplicationContext(), "fail." + content + error.getMessage().toString(),
								Toast.LENGTH_LONG).show();

					}
				});
	}

	protected void Login(String email, String pass) {
		// TODO Auto-generated method stub
		final RequestParams params = new RequestParams();
		params.put("email", email);
		params.put("pass", pass);
		Toast.makeText(getApplicationContext(), "Logeging in.....", Toast.LENGTH_LONG).show();

		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(70000);
		Toast.makeText(getApplicationContext(), params+"", Toast.LENGTH_LONG).show();
		client.get(getResources().getString(R.string.app_server_Name) + "Login.php", params,
				new AsyncHttpResponseHandler() {
					// When the response returned by REST has Http response code
					// '200'
					@Override
					public void onSuccess(String response) {
						// Hide Progress Dialog
						try {

							// JSON Object
							JSONObject obj = new JSONObject(response);
							// When the JSON response has status boolean value
							// assigned with true

							if (obj.getString("status").equals("true")) {
								String ckd = "0";

								if (remembreckbx.isChecked()) {
									ckd = "1";
								}
                      			mysh.save(getApplicationContext(), obj.getString("name"), obj.getString("phone"),
										obj.getString("email"), obj.getString("pass"), ckd, obj.getString("ID"));

								Intent x = new Intent(getApplicationContext(), Home.class);
								startActivity(x);
								finish();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block

							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "ksjdksjd.", Toast.LENGTH_LONG).show();

						}
					}

					// When the response returned by REST has Http response code
					// other than '200'
					@Override
					public void onFailure(int statusCode, Throwable error, String content) {

						Toast.makeText(getApplicationContext(),getResources().getString(R.string.app_server_Name) + "Login.php"+params, Toast.LENGTH_LONG).show();

					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
