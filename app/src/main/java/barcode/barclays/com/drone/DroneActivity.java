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
    private DronePositionHandler mDronePositionHandler;

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

        mDronePositionHandler = new DronePositionHandler();

    }

    @Override
    protected void onDestroy() {
        //EventBus.getDefault().unregister(this);
        mUnbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.segment1)
    void segment1Click(View view) {
        mDronePositionHandler.segment1();
    }

    @OnClick(R.id.segment2)
    void segment2Click(View view) {
        mDronePositionHandler.segment2();
    }

    @OnClick(R.id.segment3)
    void segment3Click(View view) {
        mDronePositionHandler.segment3();

    }

    @OnClick(R.id.segment4)
    void segment4Click(View view) {
        mDronePositionHandler.segment4();
    }

    @OnClick(R.id.center)
    void centerClick(View view) {
        mDronePositionHandler.stop();
    }

}
