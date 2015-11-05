package edu.pitt.videoapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

/**
 * Created by Christopher on 10/29/2015.
 */
public class Rig extends RelativeLayout {

    // Used to traverse this view's hierarchy
    private View mainView;

    // Used to access activity
    private Activity stageActivity;
    private Timer t;
    // Rig listeners
    private ImageButton moveLayoutButton;
    private LinearLayout centerLayout;
    private TextView camLabel;
    private TextView camera_text_label;
    private ImageButton playButton;
    private ImageButton stopButton;

    public Rig(Activity activity) {
        super(activity);
        init(activity);
        setupDrag();
        setupLongClick();
        setupPlayClick();
        setupStopClick();
    }

    // Returns the x and y of this layout
    public float[] getXY(){
        float[] xy = {mainView.getX(), mainView.getY()};
        return xy;
    }

    // Sets the x and y of this layout
    public void setXY(float x, float y){
        mainView.setX(x);
        mainView.setY(y);
    }

    public void setLabel(String s){
        camLabel.setText(s);
    }

    public String getLabel(){
        return camLabel.getText().toString();
    }

    // Initializes the rig
    private void init(Activity activity){
        RelativeLayout parent = (RelativeLayout) activity.findViewById(R.id.stageActivityLayout);
        this.stageActivity = activity;
        inflate(getContext(), R.layout.rig_layout, parent);

        // Generate a new id for each item so the next rig can access default xml ids
        this.moveLayoutButton = (ImageButton) activity.findViewById(R.id.moveButton);
        this.moveLayoutButton.setId(View.generateViewId());

        // The line below needs to be altered depending on the xml structure
        this.mainView = (View) this.moveLayoutButton.getParent().getParent().getParent().getParent().getParent();

        this.centerLayout = (LinearLayout) activity.findViewById(R.id.center_layout);
        this.centerLayout.setId(View.generateViewId());

        this.camLabel = (TextView) activity.findViewById(R.id.camera_label);
        this.camLabel.setId(View.generateViewId());

        this.camera_text_label = (TextView) activity.findViewById(R.id.camera_text_label);
        this.camera_text_label.setId(View.generateViewId());
        t=new Timer(camera_text_label);
        this.playButton = (ImageButton) activity.findViewById(R.id.playButton);
        this.playButton.setId(View.generateViewId());

        this.stopButton = (ImageButton) activity.findViewById(R.id.stopButton);
        this.stopButton.setId(View.generateViewId());
    }

    // Sets the drag listener
    private void setupDrag() {
        this.moveLayoutButton.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            public boolean onTouch(View view, MotionEvent event) {
                float[] xy = getXY();
                Log.d("Rig", "x: " + xy[0] + ", y: " + xy[1]);
                switch (event.getActionMasked()) {

                    case MotionEvent.ACTION_DOWN:

                        dX = mainView.getX() - event.getRawX();
                        dY = mainView.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        mainView.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    // Sets the long click listener
    private void setupLongClick(){
        this.centerLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("Rig", "Long click worked!!!!");

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_camera, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.add_camera_label:
                                Log.d("Rig", "add_camera_label clicked.");
                                final Dialog dialog = new Dialog(getContext());
                                activateLabelDialog();
                                break;
                            case R.id.add_camera_photo:
                                Log.d("Rig", "add_camera_photo clicked.");
                                // Start camera intent
                                //dispatchTakePictureIntent();
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
                return true;
            }


        });
    }

    public Dialog activateLabelDialog() {
        // Create a new dialog to get new camera label
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.camera_add_label_dialog);
        final Button ok_button = (Button) dialog.findViewById(R.id.dialog_ok);
        // If okay is clicked, get text from EditText and set it to the camera's label
        ok_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText dedit   = (EditText) dialog.findViewById(R.id.change_camera_label);
                camLabel.setText(dedit.getText().toString());
                dialog.hide();
            }
        });
        final Button cancel_button = (Button) dialog.findViewById(R.id.dialog_cancel);
        // If cancel is clicked, close dialog, do not change camera label
        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.hide();
            }
        });
        dialog.show();
        return dialog;
    }

    /**
     * Launch camera application to take a photo

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(stageActivity.getPackageManager()) != null) {
            stageActivity.startActivityForResult(takePictureIntent, 1);
        }
    }
    */
    private void setupPlayClick() {
        this.playButton.setOnClickListener(new View.OnClickListener() {
            //Timer t=new Timer();

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                centerLayout.setBackgroundColor(Color.parseColor("#FF0000"));
                //Timer t=new Timer();
                t.start();
            }
        });
    }

    private void setupStopClick() {
        this.stopButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                centerLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                t.cancel();
            }
        });
    }
}
