package jason.model;

/**
 * Created by Jason on 2016/9/14.
 */
public class University {

    private int id;         //大学id编号
    private String name;    //大学名称
    private String mailUrl; //大学主页
    private String region;  //大学所在地区
    private String type;    //大学教学类型，本科，高职，民办，独立学院等
    private String is211;   //是否为211
    private String is985;   //是否为985

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailUrl() {
        return mailUrl;
    }

    public void setMailUrl(String mailUrl) {
        this.mailUrl = mailUrl;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIs211() {
        return is211;
    }

    public void setIs211(String is211) {
        this.is211 = is211;
    }

    public String getIs985() {
        return is985;
    }

    public void setIs985(String is985) {
        this.is985 = is985;
    }
}
