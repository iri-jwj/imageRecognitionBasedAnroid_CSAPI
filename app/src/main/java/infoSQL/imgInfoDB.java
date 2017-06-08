package infoSQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by iri.jwj on 2017/6/4.
 */

public class imgInfoDB extends SQLiteOpenHelper {
    private static final String CREATE_INFO="create table ImgInfo ("
            + "imgName text primary key,"
            + "path text,"
            +"result text,"
            +"time text)";
    private Context mContext;

    public imgInfoDB(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_INFO);
        Toast.makeText(mContext,"succeed",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
