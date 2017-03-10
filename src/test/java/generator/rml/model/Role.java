package generator.rml.model;

import java.util.Date;
import javax.persistence.*;

public class Role {
    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * code
     */
    private String code;

    /**
     * name
     */
    private String name;

    /**
     * power
     */
    private Integer power;

    /**
     * create_date
     */
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "expire_time")
    private Date expireTime;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取code
     *
     * @return code - code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code
     *
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取name
     *
     * @return name - name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取power
     *
     * @return power - power
     */
    public Integer getPower() {
        return power;
    }

    /**
     * 设置power
     *
     * @param power power
     */
    public void setPower(Integer power) {
        this.power = power;
    }

    /**
     * 获取create_date
     *
     * @return create_date - create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置create_date
     *
     * @param createDate create_date
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return expire_time
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * @param expireTime
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}