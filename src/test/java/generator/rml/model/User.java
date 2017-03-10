package generator.rml.model;

import java.util.Date;
import javax.persistence.*;

public class User {
    @Id
    private Integer id;

    private String code;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "password_md5")
    private String passwordMd5;

    @Column(name = "reg_timestamp")
    private Date regTimestamp;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return login_name
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return password_md5
     */
    public String getPasswordMd5() {
        return passwordMd5;
    }

    /**
     * @param passwordMd5
     */
    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }

    /**
     * @return reg_timestamp
     */
    public Date getRegTimestamp() {
        return regTimestamp;
    }

    /**
     * @param regTimestamp
     */
    public void setRegTimestamp(Date regTimestamp) {
        this.regTimestamp = regTimestamp;
    }
}