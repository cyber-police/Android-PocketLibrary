package com.example.pocketlibray.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pocketlibray.databinding.FragmentDashboardBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    View root;
    private final ArrayList<String> authorsImagesArrayList = new ArrayList<>();

    //private ArrayList<ArrayList> allDownCoordinates = {{984, 1386}, };

    private final Handler handler = new Handler();
    private int i = 0;

    // Define the code block to be executed
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
            if (i == authorsImagesArrayList.size()) {
                i = 0;
            }
            ImageView imageView = binding.authorsImageView;
            Picasso.get().load(authorsImagesArrayList.get(i)).into(imageView);
            i++;
            // Repeat every 2 seconds
            handler.postDelayed(runnable, 2000);
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        authorsImagesArrayList.add("https://cdn.britannica.com/51/12251-050-D5F09630/Arthur-Conan-Doyle-detail-portrait-HL-Gates-1927.jpg");
        authorsImagesArrayList.add("https://upload.wikimedia.org/wikipedia/uk/3/3f/%D0%9D%D0%B5%D1%81%D1%82%D0%B0%D0%B9%D0%BA%D0%BE_%D0%92%D1%81%D0%B5%D0%B2%D0%BE%D0%BB%D0%BE%D0%B4_%D0%97%D1%96%D0%BD%D0%BE%D0%B2%D1%96%D0%B9%D0%BE%D0%B2%D0%B8%D1%87.jpg");
        authorsImagesArrayList.add("https://cdn.britannica.com/65/66765-050-63A945A7/JRR-Tolkien.jpg");

        handler.post(runnable);

        binding.documentsImageView.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), DocumentsActivity.class));
        });
        binding.authorsImageView.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AuthorsActivity.class));
        });
        binding.categoriesImageView.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), CategoriesActivity.class));
        });

        SensorManager sensorManager = (SensorManager) getContext().getSystemService(Activity.SENSOR_SERVICE);
        Sensor sensorShake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null) {
                    float xAcceleration = event.values[0];
                    float yAcceleration = event.values[1];
                    float zAcceleration = event.values[2];

                    float floatSum = Math.abs(xAcceleration) + Math.abs(yAcceleration) + Math.abs(zAcceleration);

                    if (floatSum > 14) {
                        //VIBRATE
                        throwBooks();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        sensorManager.registerListener(sensorEventListener, sensorShake, SensorManager.SENSOR_DELAY_NORMAL);

        for (int index = 1; index < (binding.constraint).getChildCount(); index++) {
            View view = (binding.constraint).getChildAt(index);

            view.setOnTouchListener((v, event) -> {
                float xDown = 0, yDown = 0;
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float movedX, movedY;
                        movedX = event.getX();
                        movedY = event.getY();

                        float distanceX = movedX - xDown;
                        float distanceY = movedY - yDown;

                        view.setX(view.getX() + distanceX);
                        view.setY(view.getY() + distanceY);

                        System.out.println(view.getX() + "YYYYYYYYYYYY" + view.getY());

                        xDown = movedX;
                        yDown = movedY;

                        break;
                }
                return true;
            });
        }
        return root;
    }

    public void throwBooks() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}