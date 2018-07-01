package com.example.placeitnow;

import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {

	public static final String PREFS_NAME = "DATA";
	public static final String LOg_VALID = "valid";
	public static final String _NAME = "name";
	public static final String _PHONE = "phone";
	public static final String _PASSWORD = "pass";
	public static final String _EMAIL = "email";
	public static final String _lang = "lang";
	public static final String _cost = "cost";
	public static final String _numberofitemes = "number";

	public static final String _ID = "ID";
	public static final String _Logtimes = "log";
	public static int used = 0;

	public SharedPreference() {
		super();

	}

	String get_Cost(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		return settings.getString(_cost, null);
	}

	String get_numberofitemes(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		return settings.getString(_numberofitemes, null);
	}

	float get_used_times(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		return settings.getFloat(_Logtimes, 0);
	}

	@SuppressWarnings("null")
	public void save(Context context, String name, String phone, String email, String pass, String v, String ID) {
		SharedPreferences settings;
		Editor editor;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); // 1
		editor = settings.edit(); // 2

		editor.putString(_NAME, name); // 3
		editor.putString(_EMAIL, email);
		editor.putString(_PHONE, phone);
		editor.putString(_PASSWORD, pass);
		editor.putString(LOg_VALID, v);
		editor.putString(_ID, ID);
		used = (int) settings.getFloat(_Logtimes, 0);
		editor.putFloat(_Logtimes, used + 1);
		editor.commit(); // 4
	}

	public void setcarddeatails(Context context, String cost, String numofitms) {
		SharedPreferences settings;
		Editor editor;
		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); // 1
		editor = settings.edit(); // 2
		editor.putString(_cost, cost); // 3
		editor.putString(_numberofitemes, numofitms); // 3

		editor.commit(); // 4

	}

	public void setlangValue(Context context, String lang) {
		SharedPreferences settings;
		Editor editor;
		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); // 1
		editor = settings.edit(); // 2
		editor.putString(_lang, lang); // 3

		editor.commit(); // 4

	}

	public String getnameValue(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		text = settings.getString(_NAME, null);
		return text;
	}

	public String getpassValue(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		text = settings.getString(_PASSWORD, null);
		return text;
	}

	public String getlangValue(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		text = settings.getString(_lang, null);
		return text;
	}

	public String getIDValue(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		text = settings.getString(_ID, null);
		return text;
	}

	public String getphoneValue(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		text = settings.getString(_PHONE, null);
		return text;
	}

	public String getemailValue(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		text = settings.getString(_EMAIL, null);
		return text;
	}

	public String getvalidValue(Context context) {
		SharedPreferences settings;
		String text;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		text = settings.getString(LOg_VALID, null);
		return text;
	}

	public void clearSharedPreference(Context context) {
		SharedPreferences settings;
		Editor editor;

		// settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = settings.edit();

		editor.clear();
		editor.commit();
	}

	public void removeValid(Context context) {
		SharedPreferences settings;
		Editor editor;

		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = settings.edit();

		editor.remove(LOg_VALID);
		editor.putString(LOg_VALID, "0");
		editor.commit();
	}
}