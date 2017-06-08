package appTools;

import android.os.Environment;

/**
 * Created by iri.jwj on 2017/6/6.
 */

public class hasSDCardMounted {
    /**
     * Check if the primary "external" storage device is available.
     *
     * @return
     */
    public static boolean SDCardMounted() {
        String state = Environment.getExternalStorageState();
        return (state != null && state.equals(Environment.MEDIA_MOUNTED));
    }
}
