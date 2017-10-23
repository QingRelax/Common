package com.common.to;

import java.io.Serializable;
import java.util.Date;

public class EmailMessageTO implements Serializable {

	private static final long serialVersionUID = 2510165816202634333L;

	
	private Date dueDate;

	private String jobSection;

	private String organizationName;

	private String requestType;

	private long requestTypeID;

	private String status;

	private String requestNo;
	
	private String createdName;
	
	private String followupAction;

	private long requestID;

	private long organizationID;

	private long inventoryItemID;

	private String inventoryItemCode;

	private String remark;

	private Date lastUpdatedDate;

	private String followupActionCode;

	private boolean read;
	
	private String receiveDate;

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getJobSection() {
		return jobSection;
	}

	public void setJobSection(String jobSection) {
		this.jobSection = jobSection;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public long getRequestTypeID() {
		return requestTypeID;
	}

	public void setRequestTypeID(long requestTypeID) {
		this.requestTypeID = requestTypeID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	public String getFollowupAction() {
		return followupAction;
	}

	public void setFollowupAction(String followupAction) {
		this.followupAction = followupAction;
	}

	public long getRequestID() {
		return requestID;
	}

	public void setRequestID(long requestID) {
		this.requestID = requestID;
	}

	public long getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(long organizationID) {
		this.organizationID = organizationID;
	}

	public long getInventoryItemID() {
		return inventoryItemID;
	}

	public void setInventoryItemID(long inventoryItemID) {
		this.inventoryItemID = inventoryItemID;
	}

	public String getInventoryItemCode() {
		return inventoryItemCode;
	}

	public void setInventoryItemCode(String inventoryItemCode) {
		this.inventoryItemCode = inventoryItemCode;
	}
	
	public String getContent() {
		String content = this.requestType;
		if (this.jobSection != null && !jobSection.equals("")) {
			content = jobSection + " - " + content;
		}
		if (this.inventoryItemCode != null && !inventoryItemCode.equals("")) {
			content = content + " (" + inventoryItemCode + ") ";
		}
		return content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getFollowupActionCode() {
		return followupActionCode;
	}

	public void setFollowupActionCode(String followupActionCode) {
		this.followupActionCode = followupActionCode;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
}
