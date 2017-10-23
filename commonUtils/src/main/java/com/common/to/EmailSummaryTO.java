package com.common.to;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmailSummaryTO implements Serializable {

	private static final long serialVersionUID = 2510165123852634332L;

	/** The sender address. */
	private String senderAddress;

	/** The sender name. */
	private String senderName;

	/** The recipients. */
	private List<String> recipients = new ArrayList<String>();

	/** The bcc recipients. */
	private List<String> bccRecipients = new ArrayList<String>();

	/** The cc recipients. */
	private List<String> ccRecipients = new ArrayList<String>();

	/** The subject. */
	private String subject;

	/** The content. */
	private String content;

	/** The attachment files. */
	private List<File> attachmentFiles = new ArrayList<File>();
	
	/**
	 *  1.  Request Notification
	 *  2.  To Do Notification(email)
	 */
	private String notificationMailType;
	
	private boolean notificationFlag;
	
	private String receiverName;
	
	private List<EmailMessageTO> mailConents = new ArrayList<EmailMessageTO>();

	
	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public List<String> getBccRecipients() {
		return bccRecipients;
	}

	public void setBccRecipients(List<String> bccRecipients) {
		this.bccRecipients = bccRecipients;
	}

	public List<String> getCcRecipients() {
		return ccRecipients;
	}

	public void setCcRecipients(List<String> ccRecipients) {
		this.ccRecipients = ccRecipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<File> getAttachmentFiles() {
		return attachmentFiles;
	}

	public void setAttachmentFiles(List<File> attachmentFiles) {
		this.attachmentFiles = attachmentFiles;
	}

	public String getNotificationMailType() {
		return notificationMailType;
	}

	public void setNotificationMailType(String notificationMailType) {
		this.notificationMailType = notificationMailType;
	}
	
	public boolean isNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(boolean notificationFlag) {
		this.notificationFlag = notificationFlag;
	}

	public List<EmailMessageTO> getMailConents() {
		return mailConents;
	}

	public void setMailConents(List<EmailMessageTO> mailConents) {
		this.mailConents = mailConents;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
}
