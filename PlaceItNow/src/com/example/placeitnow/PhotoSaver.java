package com.example.placeitnow;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.widget.Toast;

public class PhotoSaver implements PictureCallback {

	static Context contt;
	byte[] imageBytes= null;
	public PhotoSaver(Context context) {
		PhotoSaver.contt = context;
		

	}


	public void onPictureTaken(byte[] data, Camera camera) {


		try {
			 imageBytes =data;
		} catch (Exception error) {
			Toast.makeText(contt, "Image could not be saved.",
					Toast.LENGTH_LONG).show();
		}

	}
public byte[] returnimagebytes() {
	return imageBytes;
}

}
