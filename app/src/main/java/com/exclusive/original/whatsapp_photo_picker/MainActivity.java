package com.exclusive.original.whatsapp_photo_picker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.camerakit.CameraKitView;
import com.exclusive.original.whatsapp_photo_picker.Adapters.HomeAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {

    private BottomSheetBehavior sheetBehavior;
    private RelativeLayout bottom_sheet;
    RecyclerView peekRecyclerView , MainRecyclerView ;
    CameraKitView cameraKitView;
    RelativeLayout peekView, collapsedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraKitView = findViewById(R.id.camera);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        peekRecyclerView = findViewById(R.id.peekRecyclerView);
        peekView = findViewById(R.id.peekView);
        collapsedView = findViewById(R.id.collapsedView);
        MainRecyclerView = findViewById(R.id.MainRecyclerView);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);

        peekRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
        peekRecyclerView.setAdapter(new HomeAdapter(MainActivity.this));

        MainRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        MainRecyclerView.setAdapter(new HomeAdapter(MainActivity.this));
        MainRecyclerView.scrollToPosition(0);


        MainRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!MainRecyclerView.canScrollVertically(-1)){
                    if(sheetBehavior.isDraggable()== false){
                        sheetBehavior.setDraggable(true);
                    }
                }else{
                  if(sheetBehavior.isDraggable()==true){
                      sheetBehavior.setDraggable(false);
                  }
                }
            }
        });



        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.e( "onStateChanged: ","hidden" );
                        //sheetBehavior.setDraggable(true);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        Log.e("onStateChanged: ","expanded" );
                        MainRecyclerView.suppressLayout(false);
                        sheetBehavior.setDraggable(false);
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        Log.e( "onStateChanged: ","collapse" );
                        sheetBehavior.setDraggable(true);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        MainRecyclerView.suppressLayout(true);
                        Log.e( "onStateChanged: ", "dragging" );
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:

                        Log.e( "onStateChanged: ",sheetBehavior.isDraggable()+" settled " );

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                peekView.setAlpha(1.0f-v);
                collapsedView.setAlpha(v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
