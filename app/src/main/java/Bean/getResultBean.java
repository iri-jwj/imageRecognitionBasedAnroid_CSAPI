package Bean;

/**
 * Created by iri.jwj on 2017/6/7.
 */

public class getResultBean {
    private String status;
    private String name;
    private String flags;
    private int ttl;
    private String url;
    private String token;
    private String reason;
    public getResultBean(String status, String name, String flags, int ttl, String url, String token,String reason) {
        this.status = status;
        this.name = name;
        this.flags = flags;
        this.ttl = ttl;
        this.url = url;
        this.token = token;
        this.reason=reason;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getFlags() {
        return flags;
    }

    public int getTtl() {
        return ttl;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
