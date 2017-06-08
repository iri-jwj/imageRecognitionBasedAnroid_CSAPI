package ConnectToCSApi;

/**
 * Created by iri.jwj on 2017/6/7.
 */

public class CSAPISetting {
    private static final String BASE_URL = "https://api.cloudsightapi.com";
    public static final String IMAGE_REQUESTS_URL = BASE_URL+ "/image_requests";
    public static final String IMAGE_RESPONSES_URL = BASE_URL+ "/image_responses/%s";
    public static final String CONTENT_TYPE = "multipart/form-data";
    public static final String AUTHORIZATION_FORMAT = "Authorization: CloudSight %s";
    public static final String URL_CONCATENATION_FORMAT = "%s/%s";
    public static final String mAuthorizationKey = "keyhere";
    // Recognition has not yet been completed for this image. Continue polling until
    //response has been marked completed.
    public static final String STATUS_NOT_COMPLETED = "not completed";
    // Recognition has been completed. Annotation can be found in Name and Categories
    //field of Job structure.
    public static final String STATUS_COMPLETED = "completed";
    //Token supplied on URL does not match an image.
    public static final String STATUS_NOT_FOUND = "not found";
    //Image couldn't be recognized because of a specific reason. Check the
    //`reason` field.
    public static final String STATUS_SKIPPED = "skipped";
    //Recognition process exceeded the allowed TTL setting.
    public static final String STATUS_TIMEOUT = "timeout";
    //The API may choose not to return any response for given image. Below constants
    // include possible reasons for such behavior.
    //Offensive image content.
    public static final String REASON_OFFENSIVE = "offensive";
    //Too blurry to identify.
    public static final String REASON_BLURRY = "blurry";
    //Too close to identify.
    public String  REASON_CLOSE = "close";
    //Too dark to identify.
    public String REASON_DARK = "dark";
    //Too bright to identify.
    public String REASON_BRIGHT = "bright";
    //Content could not be identified.
    public String REASON_UNSURE = "unsure";

    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String FORM_DATA_FORMAT = "form-data; name=\"%s\"";
    public static final String FORM_DATA_FORMAT_FILE = "form-data; name=\"%s\"; filename=\"%s\"";
    public static final String IMAGE_REQUEST_FORM_KEY = "image_request[image]";
    public static final String REMOTE_URL_REQUEST_FORM_KEY = "image_request[remote_image_url]";
    public static final String LOCALE_FORM_KEY = "image_request[locale]";
    public static final String LANGUAGE_FORM_KEY = "image_request[language]";
    public static final String DEVICE_ID_FORM_KEY = "image_request[device_id]";
    public static final String LATITUDE_FORM_KEY = "image_request[latitude]";
    public static final String LONGITUDE_FORM_KEY = "image_request[longitude]";
    public static final String ALTITUDE_FORM_KEY = "image_request[altitude]";
    public static final String TTL_FORM_KEY = "image_request[ttl]";
    public static final String FOCUS_X_FORM_KEY = "focus[X]";
    public static final String FOCUS_Y_FORM_KEY = "focus[Y]";
}
