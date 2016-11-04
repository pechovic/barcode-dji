package barcode.barclays.com.drone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final int SCALE = 10;
    public static final int THRESHHOLD = 250;
    private static final String TAG = ExampleInstrumentedTest.class.getName();
    private static final int X = 0 / SCALE;
    private static final int Y = 43 / SCALE;
    private static final int DELTA = 50;

    @Test
    public void parseJpeg() throws Exception {
        long start = System.currentTimeMillis();
        Context appContext = InstrumentationRegistry.getTargetContext();
        Resources res = appContext.getResources();
        int id = R.drawable.testimg2;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        final Bitmap source = BitmapFactory.decodeResource(res, id, opts);
        final Bitmap blured = blur(source);
        final Bitmap thresholded = threshhold(blured);
        final Coordinates coords = calcWhiteMassCenter(thresholded);
        Log.i(TAG, "x: " + coords.x + "; y:" + coords.y);
        Log.i(TAG, "Time delta: " + (System.currentTimeMillis() - start));
        save(blured);
        save(thresholded);
        assertTrue("x: " + coords.x, X - DELTA < coords.x && coords.x <  X + DELTA);
        assertTrue("y:" + coords.y, Y - DELTA < coords.y && coords.y <  Y + DELTA);
    }

    private Bitmap blur(Bitmap source) {
        return Bitmap.createScaledBitmap(source, source.getWidth() / SCALE, source.getHeight() / SCALE, true);
    }

    private Bitmap threshhold(Bitmap input){
        final Bitmap output = input.copy(input.getConfig(), true);
        for (int x=0; x < input.getWidth(); x++) {
            for (int y=0; y < input.getHeight(); y++) {
                if(getBrightness(input.getPixel(x,y)) > THRESHHOLD) {
                    output.setPixel(x, y, Color.WHITE);
                } else {
                    output.setPixel(x, y, Color.BLACK);
                }
            }
        }
        return output;
    }

    private Coordinates calcWhiteMassCenter(Bitmap bitmap) {
        int partialX = 0;
        int partialY = 0;
        int whiteCount = 0;
        for (int x=0; x < bitmap.getWidth(); x++) {
            for (int y=0; y < bitmap.getHeight(); y++) {
                if(bitmap.getPixel(x,y) == Color.WHITE) {
                    partialX = partialX + (x - bitmap.getWidth() / 2);
                    partialY = partialY + (y - bitmap.getHeight() / 2);
                    whiteCount++;
                }
            }
        }
        Coordinates cords = new Coordinates();
        if (whiteCount != 0) {
            cords.x = partialX / whiteCount;
            cords.y = partialY / whiteCount;
        }
        return cords;
    }

    private static class Coordinates {
        public int x;
        public int y;
    }

    private int getBrightness(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return  (int) Math.sqrt(r * r * .241 + g * g * .691 + b * b * .068);
    }

    private void save(Bitmap input) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/req_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        Log.i(TAG, "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            input.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
