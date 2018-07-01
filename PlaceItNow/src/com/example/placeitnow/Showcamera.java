package com.example.placeitnow;

import java.io.IOException;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Showcamera extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder;
	private android.hardware.Camera theCamera;

	@SuppressWarnings("deprecation")
	public Showcamera(Context context, android.hardware.Camera camera) {
		super(context);
		theCamera = camera;
		theCamera.setDisplayOrientation(90);
	        //get the holder and set this class as the callback, so we can get camera data here
		holder = getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
	}


	 @Override
	    public void surfaceCreated(SurfaceHolder surfaceHolder) {
	        try{
	            //when the surface is created, we can set the camera to draw images in this surfaceholder
	        	theCamera.setPreviewDisplay(surfaceHolder);
	        	theCamera.startPreview();
	        } catch (IOException e) {
	            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
	        }
	    }

	    @Override
	    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
	        //before changing the application orientation, you need to stop the preview, rotate and then start it again
	        if(holder.getSurface() == null)//check if the surface is ready to receive camera data
	            return;

	        try{
	        	theCamera.stopPreview();
	        } catch (Exception e){
	            //this will happen when you are trying the camera if it's not running
	        }

	        //now, recreate the camera preview
	        try{
	        	theCamera.setPreviewDisplay(holder);
	        	theCamera.startPreview();
	        } catch (IOException e) {
			Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
	        }
	    }

	    @Override
	    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
	        //our app has only one screen, so we'll destroy the camera in the surface
	        //if you are unsing with more screens, please move this code your activity
	    	theCamera.stopPreview();
	    	theCamera.release();
	    }




}
