package Bean;

import java.io.File;

/**
 * Created by iri.jwj on 2017/6/7.
 */

public class imageInfoBean {
    private File imageToPost;
    private String mRemoteImageUrl;
    private String mLocale;
    private String mLanguage;
    private String mDeviceId;
    private Double mLatitude;
    private Double mLongitude;
    private Double mAltitude;
    private  Integer mTtl;
    private Integer mFocusX;
    private Integer mFocusY;

    public imageInfoBean(
            final File image,
            final String remoteImageUrl,
            final String locale,
            final String language,
            final String deviceId,
            final Double latitude,
            final Double longitude,
            final Double altitude,
            final Integer ttl,
            final Integer focusX,
            final Integer focusY
    ) {
        imageToPost = image;
        mRemoteImageUrl = remoteImageUrl;
        mLocale = locale;
        mLanguage = language;
        mDeviceId = deviceId;
        mLatitude = latitude;
        mLongitude = longitude;
        mAltitude = altitude;
        mTtl = ttl;
        mFocusX = focusX;
        mFocusY = focusY;
    }

    public File getImage() {
        return imageToPost;
    }

    public String getRemoteImageUrl() {
        return mRemoteImageUrl;
    }

    public String getLocale() {
        return mLocale;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public Double getAltitude() {
        return mAltitude;
    }

    public Integer getTtl() {
        return mTtl;
    }

    public Integer getFocusX() {
        return mFocusX;
    }

    public Integer getFocusY() {
        return mFocusY;
    }

    public void setImageToPost(File imageToPost) {
        this.imageToPost = imageToPost;
    }

    public void setmLocale(String mLocale) {
        this.mLocale = mLocale;
    }

    public void setmTtl(Integer mTtl) {
        this.mTtl = mTtl;
    }

    public void setmRemoteImageUrl(String mRemoteImageUrl) {
        this.mRemoteImageUrl = mRemoteImageUrl;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public void setmDeviceId(String mDeviceId) {
        this.mDeviceId = mDeviceId;
    }

    public void setmLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public void setmLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public void setmAltitude(Double mAltitude) {
        this.mAltitude = mAltitude;
    }

    public void setmFocusX(Integer mFocusX) {
        this.mFocusX = mFocusX;
    }

    public void setmFocusY(Integer mFocusY) {
        this.mFocusY = mFocusY;
    }
}
