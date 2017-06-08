package ConnectToCSApi;



import android.content.Context;
import android.widget.Toast;


import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;

import Bean.getResultBean;
import Bean.postImageResultBean;
import okhttp3.Authenticator;
import okhttp3.Call;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;


import static ConnectToCSApi.CSAPISetting.AUTHORIZATION_FORMAT;
import static ConnectToCSApi.CSAPISetting.CONTENT_DISPOSITION;
import static ConnectToCSApi.CSAPISetting.FORM_DATA_FORMAT;
import static ConnectToCSApi.CSAPISetting.IMAGE_REQUESTS_URL;
import static ConnectToCSApi.CSAPISetting.IMAGE_RESPONSES_URL;
import static ConnectToCSApi.CSAPISetting.LOCALE_FORM_KEY;
import static ConnectToCSApi.CSAPISetting.STATUS_COMPLETED;
import static ConnectToCSApi.CSAPISetting.STATUS_NOT_COMPLETED;
import static ConnectToCSApi.CSAPISetting.STATUS_NOT_FOUND;
import static ConnectToCSApi.CSAPISetting.STATUS_SKIPPED;
import static ConnectToCSApi.CSAPISetting.STATUS_TIMEOUT;
import static ConnectToCSApi.CSAPISetting.mAuthorizationKey;

/**
 * Created by iri.jwj on 2017/6/6.
 */

public class okHttpConnect2API {
    private static File mImage;
    public static final String TYPE = "application/octet-stream";

    private static okHttpConnect2API mInstance;
    private static OkHttpClient mOkHttpClient;
    private static Gson mGson;
    private static Response postResponse;
    private static Response getResponse;
    private static String result;

    private static final String TAG = "OkHttpClientManager";

    public okHttpConnect2API(File iImage)
    {
        mOkHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator(){
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                System.out.println("Authenticating for response: " + response);
                System.out.println("Challenges: " + response.challenges());
                return response.request().newBuilder()
                        .header("Authorization", String.format(AUTHORIZATION_FORMAT,mAuthorizationKey))
                        .build();
            }
        }).build();
        mGson = new Gson();
        mImage=iImage;
    }


    //获取结果
    private Response _getAsyn(String url) throws IOException
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }



    public static Response postImage(Context mMainActivity){
        if(!mImage.exists()){
            Toast.makeText(mMainActivity, "文件不存在", Toast.LENGTH_SHORT).show();
        }else{
            RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), mImage);
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("filename",mImage.getName(), fileBody)
                    .addPart(Headers.of(
                            CONTENT_DISPOSITION,
                            String.format(FORM_DATA_FORMAT,LOCALE_FORM_KEY)),
                            RequestBody.create(null, "en-US"))
                    .build();

            Request requestPostFile = new Request.Builder()
                    .url(IMAGE_REQUESTS_URL)
                    .post(requestBody)
                    .build();

            try{
                postResponse = mOkHttpClient.newCall(requestPostFile).execute();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        return  postResponse;
    }

    public static String parseJsonWithGson(Response response,int checkPoint) throws Exception{
        if(checkPoint==1){
            String responseData=response.body().string();
            postImageResultBean postResultBeen= mGson.fromJson(responseData,new TypeToken<postImageResultBean>(){}.getType());
            return postResultBeen.getToken();
        }
        else {
            String responseData=response.body().string();
            getResultBean getResultBean=mGson.fromJson(responseData,new TypeToken<getResultBean>(){}.getType());
            switch (getResultBean.getStatus()){
                case STATUS_COMPLETED:
                    result=getResultBean.getStatus()+"#"+getResultBean.getName();
                    break;
                case STATUS_NOT_COMPLETED:
                    result=STATUS_NOT_COMPLETED;
                    break;
                case STATUS_TIMEOUT:
                    result=STATUS_TIMEOUT;
                    break;
                case STATUS_NOT_FOUND:
                    result=STATUS_NOT_FOUND;
                    break;
                case STATUS_SKIPPED:
                    result=getResultBean.getReason();
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public static Response getResult(String token){
        final Request request = new Request.Builder()
                .get()
                .url(String.format(IMAGE_RESPONSES_URL,token) )
                .build();
        try{
            getResponse=mOkHttpClient.newCall(request).execute();
        }catch (IOException e){
            e.printStackTrace();
        }
        return getResponse;
    }

}
