package appTools;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irijwj.imagercognition.MainActivity;
import com.example.irijwj.imagercognition.R;

import java.io.File;
import java.io.IOException;
import java.util.List;
import android.Manifest;

import Bean.featuresBean;

import static appTools.getCurrentTime.getTime;

/**
 * Created by iri.jwj on 2017/5/22.
 */
public class featuresAdapter extends RecyclerView.Adapter<featuresAdapter.ViewHolder>  {
    private List<featuresBean> mfeaturesList;
    private static MainActivity mActivity;


    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    public static Uri imageUri;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View featureView;//列表点击事件
        ImageView featureImage;
        TextView featureText;

        private ViewHolder(View view){ //传入的view 为RecyclerView子项的最外层布局
            super(view);
            featureView=view;
            featureImage=(ImageView) view.findViewById(R.id.features_image);
            featureText=(TextView)view.findViewById(R.id.features_text);
        }
    }//内部类

    public featuresAdapter(List<featuresBean> featuresBeanList, MainActivity mainActivity){
        mfeaturesList= featuresBeanList;
        mActivity=mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){//创建ViewHolder实例
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feature_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        //列表点击事件，待修改！
        holder.featureView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                featuresBean featuresBean =mfeaturesList.get(position);

                //选择用相机中拍照时：
                if(featuresBean.getName().equals("Camera")){

                    String imageName="img2rec"+ getTime(1);
                    File outputImage=new File(mActivity.getFilesDir()+"/ImageRecognition",
                            imageName+".jpg");
                    try {
                        if(!outputImage.createNewFile()){
                            Toast.makeText(mActivity,"图片文件创建失败",Toast.LENGTH_SHORT).show();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    if(Build.VERSION.SDK_INT>=24){
                        imageUri= FileProvider.getUriForFile(mActivity,"com.ex",outputImage);
                    }else
                        imageUri=Uri.fromFile(outputImage);

                    Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    mActivity.startActivityForResult(intent,TAKE_PHOTO);
                }
                //相机end

                else if(featuresBean.getName().equals("Gallery")){
                    if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mActivity,"未获取SD卡读取权限",Toast.LENGTH_SHORT).show();
                    }else
                        openAlbum();
                }
                //相册end

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){//对于子项数据进行赋值
        featuresBean featuresBean =mfeaturesList.get(position);
        holder.featureImage.setImageResource(featuresBean.getImageId());
        holder.featureText.setText(featuresBean.getName());
    }

    @Override
    public int getItemCount(){
        return mfeaturesList.size();
    }

    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        mActivity.startActivityForResult(intent,CHOOSE_PHOTO);
    }
}
