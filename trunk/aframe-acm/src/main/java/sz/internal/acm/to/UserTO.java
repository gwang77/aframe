package sz.internal.acm.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.component.code.to.CodeTO;
import sz.internal.common.util.DateUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class UserTO extends BaseTO {
    private String username;
    private String password;
    private String confirm_password;
    @JsonIgnore
    private String salt;
    private String user_type;
    private CodeTO user_type_code;
    private String realname;
    private String staff_id;
    private String id_number;
    private String sex;
    private String birth_date_s;
    private String tel;
    private String email;
    private String locked;
    private String register_date_s;
    private CodeTO locked_code;
    private String reg_id_s;

    //default role when create user
    private String default_role;

    @JsonIgnore
    private String username_like;

    private List<RoleTO> roleTOList;

    private String app_id;

    public UserTO() {
    }

    public UserTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt() {
        String saltTmp = salt == null ? "" : salt;
        return username + saltTmp;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public CodeTO getUser_type_code() {
        return user_type_code;
    }

    public void setUser_type_code(CodeTO user_type_code) {
        this.user_type_code = user_type_code;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @JsonIgnore
    public Date getBirth_date() {
        if (birth_date_s == null || "".equals(birth_date_s)) {
            return null;
        }
        return DateUtil.parseDate(birth_date_s);
    }

    public void setBirth_date(Date birth_date) {
        if (birth_date == null) {
            birth_date_s = null;
            return;
        }
        birth_date_s = DateUtil.formatDate(birth_date);
    }

    public String getBirth_date_s() {
        return birth_date_s;
    }

    public void setBirth_date_s(String birth_date_s) {
        this.birth_date_s = birth_date_s;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public CodeTO getLocked_code() {
        return locked_code;
    }

    public void setLocked_code(CodeTO locked_code) {
        this.locked_code = locked_code;
    }

    public String getDefault_role() {
        return default_role;
    }

    public void setDefault_role(String default_role) {
        this.default_role = default_role;
    }

    @JsonIgnore
    public Date getRegister_date() {
        if (register_date_s == null || "".equals(register_date_s)) {
            return null;
        }
        return DateUtil.parseDate(register_date_s, DateUtil.DEFAULT_FORMAT_DATETIME);
    }

    public void setRegister_date(Date register_date) {
        if (register_date == null) {
            register_date_s = null;
            return;
        }
        register_date_s = DateUtil.formatDate(register_date, DateUtil.DEFAULT_FORMAT_DATETIME);
    }

    public String getRegister_date_s() {
        return register_date_s;
    }

    public void setRegister_date_s(String register_date_s) {
        this.register_date_s = register_date_s;
    }

    @JsonIgnore
    public Integer getReg_id() {
        if (reg_id_s == null || "".equals(reg_id_s)) {
            return null;
        }
        return new Integer(reg_id_s);
    }

    public void setReg_id(Integer reg_id) {
        if (reg_id == null) {
            reg_id_s = null;
            return;
        }
        reg_id_s = String.valueOf(reg_id);
    }

    public String getReg_id_s() {
        return reg_id_s;
    }

    public void setReg_id_s(String reg_id_s) {
        this.reg_id_s = reg_id_s;
    }

    public String getUsername_like() {
        return username_like;
    }

    public void setUsername_like(String username_like) {
        this.username_like = username_like;
    }

    public List<RoleTO> getRoleTOList() {
        return roleTOList;
    }

    public void setRoleTOList(List<RoleTO> roleTOList) {
        this.roleTOList = roleTOList;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
}


