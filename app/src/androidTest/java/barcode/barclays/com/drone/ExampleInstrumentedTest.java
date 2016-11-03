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

    public static final int SCALE = 3;

    @Test
    public void parseJpeg() throws Exception {
        final Bitmap source = BitmapFactory.decodeFile("app/fotos/IMG_20161103_145320192.jpg");
        final Bitmap blured = blur(source);
    }

    private Bitmap blur(Bitmap source) {
        final Bitmap small = Bitmap.createScaledBitmap(source, source.getWidth() / SCALE, source.getHeight() / SCALE, true);
        return Bitmap.createScaledBitmap(small, source.getWidth(), source.getHeight(), true);
    }

//    private
}
