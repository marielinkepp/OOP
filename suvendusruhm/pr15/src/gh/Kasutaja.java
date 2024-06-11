package gh;

import java.net.URL;
import java.util.Date;
import com.google.gson.annotations.SerializedName;

public class Kasutaja {

    @SerializedName("login")
    private String kasutajanimi;

    @SerializedName("html_url")
    private URL profiiliUrl;

    @SerializedName("public_repos")
    private int avalikudRepod;

    @SerializedName("created_at")
    private String profiiliLoomiseKuupaev;


    public String getKasutajanimi() {
        return kasutajanimi;
    }

    public void setKasutajanimi(String kasutajanimi) {
        this.kasutajanimi = kasutajanimi;
    }

    public URL getProfiiliUrl() {
        return profiiliUrl;
    }

    public void setProfiiliUrl(URL profiiliUrl) {
        this.profiiliUrl = profiiliUrl;
    }

    public int getAvalikudRepod() {
        return avalikudRepod;
    }

    public void setAvalikudRepod(int avalikudRepod) {
        this.avalikudRepod = avalikudRepod;
    }

    public String getProfiiliLoomiseKuupaev() {
        return profiiliLoomiseKuupaev;
    }

    public void setProfiiliLoomiseKuupaev(String profiiliLoomiseKuupaev) {
        this.profiiliLoomiseKuupaev = profiiliLoomiseKuupaev;
    }

    public String toString() {
        return "Kasutaja{" +
                "kasutajanimi='" + this.getKasutajanimi() + '\'' +
                ", profiiliUrl=" + this.getProfiiliUrl() +
                ", avalikudRepod=" + this.getAvalikudRepod() +
                ", profiiliLoomiseKuupaev=" + this.getProfiiliLoomiseKuupaev() +
                '}';
    }
}