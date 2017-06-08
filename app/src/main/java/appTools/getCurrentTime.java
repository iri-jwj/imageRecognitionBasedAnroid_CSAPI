package appTools;

import java.util.Calendar;

/**
 * Created by iri.jwj on 2017/6/5.
 */

public class getCurrentTime {
    public static String getTime(int type){
        Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        long time=System.currentTimeMillis();
        time=time%800;
        if(type==1)
            return "-"+month+"-"+day+"-"+time;
        else
            return year+"-"+month+"-"+day;
    }
}
