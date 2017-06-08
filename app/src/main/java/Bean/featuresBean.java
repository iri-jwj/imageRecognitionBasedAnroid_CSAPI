package Bean;

/**
 * Created by iri.jwj on 2017/5/22.
 */
public class featuresBean {
    private String Name;
    private int imageId;

    public featuresBean(String Name, int imageId){
        this.imageId=imageId;
        this.Name=Name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return Name;
    }
}
