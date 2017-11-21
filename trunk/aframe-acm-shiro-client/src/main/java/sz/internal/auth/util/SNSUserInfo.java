package sz.internal.auth.util;

import java.util.List;

public class SNSUserInfo {

    private String openId;
    private String nickname;
    private int sex;
    private String country;
    private String province;
    private String city;
    private String headImgUrl;
    private List privilegeList;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public String getSexStr() {
        if (sex == 1) {
            return "M";
        }
        if (sex == 2) {
            return "F";
        }
        return "";
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public List getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List privilegeList) {
        this.privilegeList = privilegeList;
    }
}
