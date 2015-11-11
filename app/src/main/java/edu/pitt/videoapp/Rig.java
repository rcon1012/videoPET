package edu.pitt.videoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.TextView;

/**
 * Created by Christopher on 10/29/2015.
 */
public class Rig extends RelativeLayout {

    // Rig types
    final static public int CAMERA = 0;
    final static public int STAGE = 1;

    private int rigType;

    // Used to traverse this view's hierarchy
    private View mainView;

    // Used to access activity
    private StageActivity stageActivity;
    private Timer t;

    // Rig listeners
    private ImageButton moveLayoutButton;
    private LinearLayout centerLayout;
    private TextView camLabel;
    private TextView camera_text_label;
    private ImageButton playButton;
    private ImageButton stopButton;

    // misc
    private boolean lock;
    private boolean deleted;

    public Rig(StageActivity activity) {
        super(activity);
        init(activity);
        setupDrag();
        setupLongClick();
        setupPlayClick();
        setupStopClick();
    }

    public Rig(StageActivity activity, int type){
        super(activity);
        this.rigType = type;
        if (type == STAGE){
            initStage(activity);
            // put stage only things here
            setupDrag();
            setupLongClick();
        }
        else{
            // default to a camera rig
            init(activity);
            setupDrag();
            setupLongClick();
            setupPlayClick();
            setupStopClick();
        }
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

    public boolean getLock(){
        return this.lock;
    }

    public void setLock(boolean lock){
        this.lock = lock;
        if (this.lock){
            //playButton.setVisibility(View.VISIBLE);
            //stopButton.setVisibility(View.VISIBLE);
            moveLayoutButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            //playButton.setVisibility(View.INVISIBLE);
            //stopButton.setVisibility(View.INVISIBLE);
            moveLayoutButton.setVisibility(View.VISIBLE);
        }
    }

    // Check to see if this rig was deleted
    public boolean wasDeleted(){
        return this.deleted;
    }

    // the main view has an unique id - use this to get it.
    public int getId () {
        return this.mainView.getId();
    }

    // Initializes the rig
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init(StageActivity activity){
        RelativeLayout parent = (RelativeLayout) activity.findViewById(R.id.stageActivityLayout);
        this.stageActivity = activity;
        inflate(getContext(), R.layout.rig_layout, parent);

        // For every view
        // Generate a new id so the next rig can access default xml ids

        // Implements move button
        this.moveLayoutButton = (ImageButton) activity.findViewById(R.id.moveButton);
        this.moveLayoutButton.setId(View.generateViewId());

        // The line below needs to be altered depending on the xml structure
        this.mainView = (View) this.moveLayoutButton.getParent().getParent().getParent().getParent().getParent();

        // Implemenets the center layout / for the menu that shows up on a long click
        this.centerLayout = (LinearLayout) activity.findViewById(R.id.center_layout);
        this.centerLayout.setId(View.generateViewId());

        // Implements the camera label
        this.camLabel = (TextView) activity.findViewById(R.id.camera_label);
        this.camLabel.setId(View.generateViewId());

        // Implements Timer
        this.camera_text_label = (TextView) activity.findViewById(R.id.camera_text_label);
        this.camera_text_label.setId(View.generateViewId());
        t=new Timer(camera_text_label,centerLayout);

        this.playButton = (ImageButton) activity.findViewById(R.id.playButton);
        this.playButton.setId(View.generateViewId());

        this.stopButton = (ImageButton) activity.findViewById(R.id.stopButton);
        this.stopButton.setId(View.generateViewId());

        // Sets the starting lock to UNLOCKED
        this.lock = false;

        // Used to check if the camera has been removed
        this.deleted = false;
    }

    // Sets the drag listener
    private void setupDrag() {
        this.moveLayoutButton.setOnTouchListener(new View.OnTouchListener() {
            // Change in x and y
            float dX, dY;

            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {

                    case MotionEvent.ACTION_DOWN:

                        dX = mainView.getX() - event.getRawX();
                        dY = mainView.getY() - event.getRawY();
                        break;

                    // Animate the change in x and y
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
                Log.d("Rig", "Long Clicked worked");

                // Creating the instance of PopupMenu
                final PopupMenu popup = new PopupMenu(getContext(), view);

                // Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_camera, popup.getMenu());

                // Toggle the title of the lock/unlock option
                if (lock) {
                    popup.getMenu().findItem(R.id.cam_lock).setTitle("Unlock");
                } else {
                    popup.getMenu().findItem(R.id.cam_lock).setTitle("Lock");
                }
                popup.show();

                // Set menu item click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {

                            // Displays the label dialog
                            case R.id.add_camera_label:
                                activateLabelDialog();
                                break;

                            // Displays the description dialog
                            case R.id.add_camera_desc:
                                //activateDescDialog();
                                break;

                            // Changes to the camera to take a photo
                            case R.id.cam_angle:
                                activateStagePickerDialog();
                                break;

                            // Locks/Unclocks the camera
                            case R.id.cam_lock:
                                setLock(!lock);
                                break;

                            // Deletes the camera
                            case R.id.cam_delete:
                                // Ask before delete
                                mainView.setVisibility(View.GONE);
                                deleted = true;
                                break;

                            // Closes the popup
                            case R.id.close_cam_menu:
                                break;
                        }
                        return true;
                    }
                });
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

    public Dialog activateStagePickerDialog() {

        // Create a new dialog to get stage picker
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.angle_stage_chooser);
        final Button close_button = (Button) dialog.findViewById(R.id.close_stage_list);
        // If cancel is clicked, close dialog, do not change camera label
        close_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.hide();
            }
        });
        dialog.show();
        return dialog;
    }

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

    // Below are stage specific methods
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initStage(StageActivity activity){
        RelativeLayout parent = (RelativeLayout) activity.findViewById(R.id.stageActivityLayout);
        this.stageActivity = activity;
        inflate(getContext(), R.layout.stage_layout, parent);

        // For every view
        // Generate a new id so the next rig can access default xml ids

        // Implements move button
        this.moveLayoutButton = (ImageButton) activity.findViewById(R.id.moveStageButton);
        this.moveLayoutButton.setId(View.generateViewId());

        // The line below needs to be altered depending on the xml structure
        this.mainView = (View) this.moveLayoutButton.getParent().getParent().getParent();

        // Implements the stage label
        this.camLabel = (TextView) activity.findViewById(R.id.stage_label);
        this.camLabel.setId(View.generateViewId());

        // Implemenets the center layout / for the menu that shows up on a long click
        this.centerLayout = (LinearLayout) activity.findViewById(R.id.stage_center_layout);
        this.centerLayout.setId(View.generateViewId());

        // Sets the starting lock to UNLOCKED
        this.lock = false;

        // Used to check if the stage has been removed
        this.deleted = false;
    }
}
