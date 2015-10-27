package edu.pitt.videoapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

/**
 * Created by jake on 10/11/15.
 */
public class CameraView extends ImageView implements PopupMenu.OnMenuItemClickListener{
    private static final String TAG = CameraView.class.getSimpleName();
    private Camera cam;

    public CameraView(final Activity activity, Camera cam) {
       super(activity);

        this.cam = cam;
        Drawable cameraDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraDrawable = getResources().getDrawable(R.drawable.camera, activity.getTheme());
        } else {
            cameraDrawable = getResources().getDrawable(R.drawable.camera);
        }

        int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();

        setOnTouchListener(new CameraTouchListener(this, screenHeight, screenWidth));
        setOnLongClickListener(new CameraLongClickListener(this, activity));

        super.setImageDrawable(cameraDrawable);
    }

    private class CameraTouchListener implements View.OnTouchListener {
        private int windowX;
        private int windowY;
        private CameraView cameraView;

        public CameraTouchListener(CameraView cameraView, int screenHeight, int screenWidth) {
            this.cameraView = cameraView;
            windowX = screenWidth;
            windowY = screenHeight;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cameraView.getLayoutParams();

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    int xCoord = (int) event.getRawX() - 100;
                    int yCoord = (int) event.getRawY() - 150;
                    if (xCoord > windowX) {
                        xCoord = windowX;
                    } else if (xCoord < 0) {
                        xCoord = 0;
                    }
                    if (yCoord > windowY) {
                        yCoord = windowY;
                    } else if (yCoord < 0) {
                        yCoord = 0;
                    }

                    params.leftMargin = xCoord;
                    params.topMargin = yCoord;
                    Log.d(TAG, "onTouch x = " + xCoord + "; y = " + yCoord);

                    cam.setCoordinates(xCoord, yCoord);

                    cameraView.setLayoutParams(params);
                    break;
                case MotionEvent.ACTION_DOWN:
                    break;
            }
            return false;
        }
    }

    private class CameraLongClickListener implements View.OnLongClickListener{
        private CameraView cameraView;
        private Activity activity;

        CameraLongClickListener(CameraView cameraView, Activity activity) {
            this.cameraView = cameraView;
            this.activity = activity;
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "Long click worked!!!!");

            PopupMenu popup = new PopupMenu(activity, v);
            popup.setOnMenuItemClickListener(cam.getCameraView());

            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_camera, popup.getMenu());
            popup.show();

            return false;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.add_camera_label:
                final Dialog dialog = new Dialog(cam.stage_activity);
                dialog.setContentView(R.layout.camera_add_label_dialog);
                final Button ok_button = (Button) dialog.findViewById(R.id.dialog_ok);
                ok_button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        EditText dedit   = (EditText) dialog.findViewById(R.id.change_camera_label);
                        String new_camera_label = dedit.getText().toString();
                        cam.setCamLabel(new_camera_label);
                        dialog.hide();
                    }
                });
                final Button cancel_button = (Button) dialog.findViewById(R.id.dialog_cancel);
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });

                dialog.show();
                break;
            case R.id.add_camera_photo:
                Log.d(TAG, "add_camera_photo clicked.");
                dispatchTakePictureIntent();
                break;
        }
        return true;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(cam.stage_activity.getPackageManager()) != null) {
            cam.stage_activity.startActivityForResult(takePictureIntent, 1);
        }
    }

    /* TODO another sprint
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
        }
    }*/
}
