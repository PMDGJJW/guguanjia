package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "work_order")
public class WorkOrder {
    @Id
    private Long id;

    private String code;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "transport_user_id")
    private Long transportUserId;

    @Column(name = "recipient_user_id")
    private Long recipientUserId;

    /**
     * 0：待运输            1：运输中            2：验收
     */
    private Integer status;

    /**
     * 数据创建时间,在数据新增时设置
     */
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Hongkong")
    private Date createDate;

    /**
     * 数据修改时间,在数据新增时和修改时设置
     */
    @Column(name = "update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Hongkong")
    private Date updateDate;

    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    @Column(name = "del_flag")
    private String delFlag;

    @Column(name = "create_by")
    private String createBy;

    @Transient
    private String createUserName;

    @Transient
    private String createUserPhone;

    @Transient
    private String createOfficeName;

    @Transient
    private String transportUserName;

    @Transient
    private String transportUserPhone;

    @Transient
    private String transportOfficeName;

    @Transient
    private String recipientUserName;

    @Transient
    private String recipientUserPhone;

    @Transient
    private String recipientOfficeName;

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", createUserId=" + createUserId +
                ", transportUserId=" + transportUserId +
                ", recipientUserId=" + recipientUserId +
                ", status=" + status +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", delFlag='" + delFlag + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", createUserPhone='" + createUserPhone + '\'' +
                ", createOfficeName='" + createOfficeName + '\'' +
                ", transportUserName='" + transportUserName + '\'' +
                ", transportUserPhone='" + transportUserPhone + '\'' +
                ", transportOfficeName='" + transportOfficeName + '\'' +
                ", recipientUserName='" + recipientUserName + '\'' +
                ", recipientUserPhone='" + recipientUserPhone + '\'' +
                ", recipientOfficeName='" + recipientOfficeName + '\'' +
                '}';
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserPhone() {
        return createUserPhone;
    }

    public void setCreateUserPhone(String createUserPhone) {
        this.createUserPhone = createUserPhone;
    }

    public String getCreateOfficeName() {
        return createOfficeName;
    }

    public void setCreateOfficeName(String createOfficeName) {
        this.createOfficeName = createOfficeName;
    }

    public String getTransportUserName() {
        return transportUserName;
    }

    public void setTransportUserName(String transportUserName) {
        this.transportUserName = transportUserName;
    }

    public String getTransportUserPhone() {
        return transportUserPhone;
    }

    public void setTransportUserPhone(String transportUserPhone) {
        this.transportUserPhone = transportUserPhone;
    }

    public String getTransportOfficeName() {
        return transportOfficeName;
    }

    public void setTransportOfficeName(String transportOfficeName) {
        this.transportOfficeName = transportOfficeName;
    }

    public String getRecipientUserName() {
        return recipientUserName;
    }

    public void setRecipientUserName(String recipientUserName) {
        this.recipientUserName = recipientUserName;
    }

    public String getRecipientUserPhone() {
        return recipientUserPhone;
    }

    public void setRecipientUserPhone(String recipientUserPhone) {
        this.recipientUserPhone = recipientUserPhone;
    }

    public String getRecipientOfficeName() {
        return recipientOfficeName;
    }

    public void setRecipientOfficeName(String recipientOfficeName) {
        this.recipientOfficeName = recipientOfficeName;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
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
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return create_user_id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return transport_user_id
     */
    public Long getTransportUserId() {
        return transportUserId;
    }

    /**
     * @param transportUserId
     */
    public void setTransportUserId(Long transportUserId) {
        this.transportUserId = transportUserId;
    }

    /**
     * @return recipient_user_id
     */
    public Long getRecipientUserId() {
        return recipientUserId;
    }

    /**
     * @param recipientUserId
     */
    public void setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    /**
     * 获取0：待运输            1：运输中            2：验收
     *
     * @return status - 0：待运输            1：运输中            2：验收
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0：待运输            1：运输中            2：验收
     *
     * @param status 0：待运输            1：运输中            2：验收
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取数据创建时间,在数据新增时设置
     *
     * @return create_date - 数据创建时间,在数据新增时设置
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置数据创建时间,在数据新增时设置
     *
     * @param createDate 数据创建时间,在数据新增时设置
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取数据修改时间,在数据新增时和修改时设置
     *
     * @return update_date - 数据修改时间,在数据新增时和修改时设置
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置数据修改时间,在数据新增时和修改时设置
     *
     * @param updateDate 数据修改时间,在数据新增时和修改时设置
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取删除标记（0：正常；1：删除；2：审核；）
     *
     * @return del_flag - 删除标记（0：正常；1：删除；2：审核；）
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标记（0：正常；1：删除；2：审核；）
     *
     * @param delFlag 删除标记（0：正常；1：删除；2：审核；）
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }
}
