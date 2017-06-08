package ConnectToCSApi;

import android.content.Context;

import java.io.File;

import okhttp3.Response;

import static ConnectToCSApi.CSAPISetting.STATUS_COMPLETED;
import static ConnectToCSApi.okHttpConnect2API.getResult;
import static ConnectToCSApi.okHttpConnect2API.parseJsonWithGson;
import static ConnectToCSApi.okHttpConnect2API.postImage;

/**
 * Created by iri.jwj on 2017/6/7.
 */

public class runOKhttp {
    public static File picture;

    private static String result;

    public runOKhttp(File image) {
        picture = image;
    }

    public static String  runApi(Context mActivity) throws Exception{

        okHttpConnect2API api=new okHttpConnect2API(picture);

        Response postPesponse=postImage(mActivity);

        String token=parseJsonWithGson(postPesponse,1);

        Response resultResponse=getResult(token);

        result=parseJsonWithGson(resultResponse,2);


        return result;
    }
}
