package com.example.irijwj.imagercognition;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.Contract;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Bean.featuresBean;
import ConnectToCSApi.runOKhttp;
import appTools.featuresAdapter;
import appTools.checkStorage;
import infoSQL.imgInfoDB;

import static appTools.featuresAdapter.CHOOSE_PHOTO;
import static appTools.featuresAdapter.TAKE_PHOTO;
import static appTools.featuresAdapter.imageUri;
import static appTools.getCurrentTime.getTime;
import static appTools.hasSDCardMounted.SDCardMounted;


public class MainActivity extends AppCompatActivity {

    private String imagePath=null;
    private imgInfoDB infoDataBase;
    private List<featuresBean> featuresBeanList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();

        //生成主界面

       initFeatures();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        featuresAdapter adapter=new featuresAdapter(featuresBeanList,MainActivity.this);
        recyclerView.setAdapter(adapter);
        //主界面生成end
    }

    private void initFeatures(){
            featuresBean camera=new featuresBean("Camera",R.drawable.camare_pic);
            featuresBeanList.add(camera);
            featuresBean gallery=new featuresBean("Gallery",R.drawable.gallery_pic);
            featuresBeanList.add(gallery);
    }
    private void init(){

        //SD卡权限获取
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //SD卡 end

        //创建数据库
        infoDataBase=new imgInfoDB(this,"imgInfo.db",null,1);
        infoDataBase.getWritableDatabase();
        //数据库end

        //创建程序所拍照片存储位置
        // 新建一个 File，传入文件夹目录
        File newDir = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"ImageRecognition");
        // 判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!newDir.exists()) {
            // 通过 file 的 mkdirs() 方法创建目录中包含却不存在的文件夹
            if(!newDir.mkdirs()) {
                Toast.makeText(this,"创建失败",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
            }
        }
        //文件夹创建end
    }


    //列表点击事件的反馈列表
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    File file=new File(getExternalCacheDir()+"/01.jpg");// 将要保存图片的路径
                    file=bitmapToFile(file);
                    String finalImagePath=getRealFilePath(this,imageUri);
                    if(finalImagePath==null){
                        Toast.makeText(this,"未知错误",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //File imageFile=new File(finalImagePath);
                    runOKhttp.picture=file;

                    try{
                        String result=runOKhttp.runApi(this);
                        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
                        insert2DB(file.getName(),finalImagePath,result,getTime(2));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    File file=new File(getExternalCacheDir()+"/01.jpg");
                    if(Build.VERSION.SDK_INT>=19){
                        String finalImagePath=handleImageOnKitKat(data);
                        file=bitmapToFile(file,finalImagePath);
                        runOKhttp.picture=file;

                        try{
                            String result=runOKhttp.runApi(this);
                            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
                            insert2DB(file.getName(),finalImagePath,result,getTime(2));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        String finalImagePath=handleImageBeforeKitKat(data);
                        file=bitmapToFile(file,finalImagePath);
                        runOKhttp.picture=file;

                        try{
                            String result=runOKhttp.runApi(this);
                            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
                            insert2DB(file.getName(),finalImagePath,result,getTime(2));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            default:
                break;
        }
    }

    //获取 读取SD卡权限
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    //打开相册
    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        MainActivity.this.startActivityForResult(intent,CHOOSE_PHOTO);
    }

    //从相册选择图片 start
    @TargetApi(19)
    private String  handleImageOnKitKat(Intent data){
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        return imagePath;
    }
    private String  handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        imagePath=getImagePath(uri,null);
        //添加函数位置
        return imagePath;
    }

    private  String getImagePath(Uri uri,String selection){
        String Path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                Path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return Path;
    }
    //从相册选择图片 end

    //存储数据至数据库
    private void insert2DB(String imgName,String path,String result,String time){
        SQLiteDatabase db=infoDataBase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("imgName",imgName);
        values.put("path",path);
        values.put("result",result);
        values.put("time",time);
        db.insert("imgInfo",null,values);
        values.clear();
    }
    //数据库end

    //获取真实路径
    @Contract("_, null -> null")
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    //获取真实路径end

    //bitmap2file
    private File bitmapToFile(File file){
        try{
            Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            bos.flush();
            bos.close();
        }catch (IOException ee) {
            ee.printStackTrace();
        }
        return file;
    }
    private File bitmapToFile(File file, String finalImagePath){
        if(finalImagePath!=null){
            try{
                Bitmap bitmap= BitmapFactory.decodeFile(finalImagePath);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
                bos.flush();
                bos.close();
            }catch (IOException ee){
                ee.printStackTrace();
            }
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
        return file;
    }
    //bitmapToFile end
}