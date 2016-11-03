package barcode.barclays.com.drone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final int SCALE = 3;
    public static final int THRESHHOLD = 3000;


    @Test
    public void parseJpeg() throws Exception {
        final Bitmap source = BitmapFactory.decodeFile("app/fotos/IMG_20161103_145320192.jpg");
        final Bitmap blured = blur(source);
        final Bitmap thresholded = threshhold(blured);
        thresholded.sa
    }

    private Bitmap blur(Bitmap source) {
        final Bitmap small = Bitmap.createScaledBitmap(source, source.getWidth() / SCALE, source.getHeight() / SCALE, true);
        return Bitmap.createScaledBitmap(small, source.getWidth(), source.getHeight(), true);
    }

    private Bitmap treshhold(Bitmap input){
        final Bitmap output = input.copy(input.getConfig(), false);
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

    private int getBrightness(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return  (int) Math.sqrt(r * r * .241 + g * g * .691 + b * b * .068);
    }

}
