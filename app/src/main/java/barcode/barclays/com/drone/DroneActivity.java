package barcode.barclays.com.drone;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import barcode.barclays.com.drone.common.DJIBarcodeApplication;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dji.common.error.DJIError;
import dji.common.flightcontroller.DJIVirtualStickFlightControlData;
import dji.common.util.DJICommonCallbacks;
import dji.thirdparty.eventbus.EventBus;

public class DroneActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone);
        mUnbinder = ButterKnife.bind(this);
        //EventBus.getDefault().register(this);

        // When the compile and target version is higher than 22, please request the following permissions at runtime to ensure the SDK work well.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                            Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                            Manifest.permission.READ_PHONE_STATE,
                    }
                    , 1);
        }


    }

    @Override
    protected void onDestroy() {
        //EventBus.getDefault().unregister(this);
        mUnbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.stabilize_drone_btn)
    void stabilizeDroneBtnClicked(View view) {
        Toast.makeText(this, "Stabilize clicked!", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < 400; i++) {
            DJIBarcodeApplication.getAircraftInstance().getFlightController().sendVirtualStickFlightControlData(
                    new DJIVirtualStickFlightControlData(0, 10, 0, 0), null);

            try {
                Thread.sleep(10, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @OnClick(R.id.connect_drone_btn)
    void connectDroneBtnClicked(View view) {
        Toast.makeText(this, "Connect clicked!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.test_drone_btn)
    void testDroneBtnClicked(View view) {
        Toast.makeText(this, "Test clicked!", Toast.LENGTH_SHORT).show();
        DJIBarcodeApplication.getAircraftInstance().getFlightController().takeOff(new DJICommonCallbacks.DJICompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                //Toast.makeText(this, djiError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
