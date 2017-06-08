package Bean;

/**
 * Created by iri.jwj on 2017/6/7.
 */

public class postImageResultBean {
    private String token;
    private String url;

    public postImageResultBean(String token, String url) {
        this.token = token;
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public String getUrl() {
        return url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
