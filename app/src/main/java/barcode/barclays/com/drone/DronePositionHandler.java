package barcode.barclays.com.drone;

import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import barcode.barclays.com.drone.common.DJIBarcodeApplication;
import dji.common.error.DJIError;
import dji.common.flightcontroller.DJIVirtualStickFlightControlData;
import dji.common.util.DJICommonCallbacks;

/**
 * Created by jozef on 03/11/2016.
 */

public class DronePositionHandler {

    private final String TAG = DronePositionHandler.class.getSimpleName();

    private final float PITCH = 10;
    private final float ROLL = 10;

    private volatile int mPitchModifier = 0;
    private volatile int mRollModifier = 0;

    private ScheduledExecutorService mThreadPoolExecutor;

    private void start() {
        mThreadPoolExecutor = Executors.newSingleThreadScheduledExecutor();
        mThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Sending command to drone");
                DJIBarcodeApplication.getAircraftInstance().getFlightController().sendVirtualStickFlightControlData(
                        new DJIVirtualStickFlightControlData(PITCH * mPitchModifier, ROLL * mRollModifier, 0, 0), new DJICommonCallbacks.DJICompletionCallback() {
                            @Override
                            public void onResult(DJIError djiError) {
                                Log.e(TAG, "Error performing command: " + djiError.getDescription());
                            }
                        });
            }
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        mThreadPoolExecutor.shutdown();
    }

    public void segment1() {
        mPitchModifier = -1;
        mRollModifier = -1;
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
            start();
        }
    }

    public void segment2() {
        mPitchModifier = -1;
        mRollModifier = 1;
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
            start();
        }
    }

    public void segment3() {
        mPitchModifier = 1;
        mRollModifier = -1;
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
            start();
        }
    }

    public void segment4() {
        mPitchModifier = 1;
        mRollModifier = 1;
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
            start();
        }
    }
}
