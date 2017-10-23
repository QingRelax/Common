package com.common.email;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import com.common.to.EmailSummaryTO;


public class EmailService {

	private static Logger log = Logger.getLogger(EmailService.class);

	private final static String MAIL_SMTP_HOST = "mail.smtp.host";
	private final static String MAIL_SMTP_PORT = "mail.smtp.port";
	private final static String MAIL_SMTP_USERNAME = "mail.smtp.username";
	private final static String MAIL_SMTP_PASSWORD = "mail.smtp.password";
	private final static String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private final static String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private final static String MAIL_SENDER_NAME = "mail.sender.name";
	private final static String MAIL_SENDER_ADDRESS = "mail.sender.address";
//	private final static String MAIL_SUBJECT = "mail.subject";
	private final static String MAIL_TODO_URL = "mail.todo.url";
	
    private static EmailService defaultInstance;
    
    private ExtendedProperties properties;
    
	private Map<String, ExtendedProperties> configurations;
	
	private String host;

	private String port;

	private String username;

	private String password;

	private String sender;

	private boolean auth;
	
	private boolean starttls;
	
	private String realPath;
	
	public EmailService() {
		log.debug(" -- Email Service --");
		host = EmailProperties.getProperties(MAIL_SMTP_HOST);
		port = EmailProperties.getProperties(MAIL_SMTP_PORT);
		username = EmailProperties.getProperties(MAIL_SMTP_USERNAME);
		password = EmailProperties.getProperties(MAIL_SMTP_PASSWORD);
		sender = EmailProperties.getProperties(MAIL_SENDER_ADDRESS);
		auth = Boolean.parseBoolean(EmailProperties.getProperties(MAIL_SMTP_AUTH));
		starttls = Boolean.parseBoolean(EmailProperties.getProperties(MAIL_SMTP_STARTTLS_ENABLE));
		log.debug("host=" + host + ",port=" + port + ",sender=" + sender);
		log.debug("username=" + username + ",password=" + password + " auth: " +auth);
	}

    public EmailService(ExtendedProperties properties)
    {
    	log.debug("-- EmailService Constructor Testing 001 -- " );
    	host = EmailProperties.getProperties(MAIL_SMTP_HOST);
		port = EmailProperties.getProperties(MAIL_SMTP_PORT);
		username = EmailProperties.getProperties(MAIL_SMTP_USERNAME);
		password = EmailProperties.getProperties(MAIL_SMTP_PASSWORD);
		sender = EmailProperties.getProperties(MAIL_SENDER_ADDRESS);
		auth = Boolean
				.parseBoolean(EmailProperties.getProperties(MAIL_SMTP_AUTH));
		starttls = Boolean.parseBoolean(EmailProperties.getProperties(MAIL_SMTP_STARTTLS_ENABLE));
        this.configurations = new HashMap<String, ExtendedProperties>();
        this.properties = properties;
        log.debug("-- EmailService Constructor Testing 002 -- " );
    }

	public Properties getProperties() {
		Properties p = new Properties();
		p.put(MAIL_SMTP_HOST, host);
		p.put(MAIL_SMTP_PORT, port);
		p.put(MAIL_SMTP_AUTH, auth);
		p.put(MAIL_SMTP_STARTTLS_ENABLE, starttls);
		return p;
	}
	
    public static EmailService getDefaultInstance() throws IOException
    {
    	log.debug("-- EmailService send() getDefaultInstance --: " );
        if (defaultInstance == null)
        {
        	log.debug("-- EmailService send() TESTING 001 --: " );
            ClassLoader classLoader = EmailService.class.getClassLoader();
            InputStream inputStream = classLoader
                    .getResourceAsStream("email.properties");
            ExtendedProperties extendedProperties = new ExtendedProperties();
            extendedProperties.load(inputStream);
            log.debug("-- EmailService send() TESTING 002--: " );
            defaultInstance = new EmailService(extendedProperties);
            log.debug("-- EmailService send() TESTING 003 --: " );
        }
        return defaultInstance;
    }
	
	public void send(EmailSummaryTO messageTo, String config, Context context)
            throws Exception
    {
        log.debug("-- EmailService send() start --: " + auth);
        EmailAuthenticator authenticator = null;
        
        try {
        	 ExtendedProperties configuration = getConfiguration(config);
        	 Message message ;
		 if (auth) {
			 authenticator = new EmailAuthenticator(username, password);
			 message = prepareMessage(messageTo, authenticator, configuration, context);
		 }else{
			 message = prepareMessage(messageTo, configuration, context);
		 }
		 
			 setMessageContent(message, configuration, context);

			 log.debug("-- EmailService send() Transport.send(message) Start --");
		     Transport.send(message);
		     log.debug("-- EmailService send() Transport.send(message) End --");
		        
		 }catch (Exception ex) {
			 log.debug("-- EmailService send() have exception --");
			 log.error("[Exception]: ", ex);
		  }

        log.debug("-- EmailService send() end --");
    }
	
    protected void setMessageContent(Message message,
            ExtendedProperties configuration, Context context) throws Exception
    {
    	log.debug("--- setMessageContent start--- " );
        ExtendedProperties content = configuration.subset("message");
        @SuppressWarnings("unchecked")
		Enumeration<String> keys = content.keys();

        Multipart multipart = new MimeMultipart();

        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            String templateName = content.getString(key);
            //String fileDir = EmailService.class.getResource("/template").getPath();
            String fileDir = this.getRealPath() + "/Template";
            Properties properties = new Properties();
            log.debug("---setMessageContent realPath: " + fileDir);
            properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, fileDir);
            Velocity.init(properties);   
            log.debug("---templateName: " + templateName);
            Template template = Velocity.getTemplate(templateName);
            log.debug("--- setMessageContent Testing 001--- " );
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            writer.close();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(writer.toString(), key);
            multipart.addBodyPart(bodyPart);
            log.debug("--- setMessageContent Testing 002 --- " );
        }

        message.setContent(multipart);
        log.debug("--- setMessageContent end--- " );
    }
    
    protected MimeMessage prepareMessage(EmailSummaryTO messageTo,EmailAuthenticator authenticator, ExtendedProperties configuration,
            Context context) throws Exception
    {
    	 Session sendMailSession = Session.getDefaultInstance(
    			 this.getProperties(), authenticator);
    	 
        MimeMessage result = new MimeMessage(sendMailSession);

        result.setSubject(messageTo.getSubject());

        String fromAddr = messageTo.getSenderAddress();
        String fromName = messageTo.getSenderName();
        InternetAddress fromAddress = new InternetAddress(fromAddr, fromName);
        result.setFrom(fromAddress);
        
        int i=0;
		InternetAddress[] address = new InternetAddress[messageTo.getRecipients().size()];
		for (String recipient : messageTo.getRecipients()) {
				address[i++] = new InternetAddress(recipient);
       		 }

        result.setRecipients(Message.RecipientType.TO, address);

        return result;
    }
    
    protected MimeMessage prepareMessage(EmailSummaryTO messageTo, ExtendedProperties configuration,
            Context context) throws Exception
    {
    	log.debug("-- EmailService prepareMessage Start --");
    	Session session = Session.getInstance(getProperties());

        MimeMessage result = new MimeMessage(session);

        result.setSubject(messageTo.getSubject());

        String fromAddr = messageTo.getSenderAddress();
        String fromName = messageTo.getSenderName();
        InternetAddress fromAddress = new InternetAddress(fromAddr, fromName);
        result.setFrom(fromAddress);
        
        int i=0;
		InternetAddress[] address = new InternetAddress[messageTo.getRecipients().size()];
		for (String recipient : messageTo.getRecipients()) {
				address[i++] = new InternetAddress(recipient);
       		 }

        result.setRecipients(Message.RecipientType.TO, address);
        log.debug("-- EmailService prepareMessage End --");
        
        return result;
    }


    protected String evaluate(ExtendedProperties configuration,
            String property, Context context) throws Exception
    {
        Velocity.init();

        String subjectTemplate = configuration.getString(property);
        StringWriter stringWriter = new StringWriter();
        Velocity.evaluate(context, stringWriter, "emailLog", subjectTemplate);
        stringWriter.close();
        return stringWriter.toString();
    }

    protected ExtendedProperties getConfiguration(String name)
    {
        ExtendedProperties config = (ExtendedProperties) configurations
                .get(name);
        if (config == null)
        {
            config = properties.subset(name);
            configurations.put(name, config);
        }
        return config;
    }
	
	public void sendMail(List<EmailSummaryTO> notiList, String realPath) throws Exception {

		log.debug("-- Job sendMail start --"+ realPath);
		this.setRealPath(realPath);
		try {
			EmailService es = EmailService.getDefaultInstance();
			for(EmailSummaryTO mail : notiList){
				log.debug("-- start email service send --");
				
				VelocityContext context = new VelocityContext();  
				log.debug("-- MAIL TESTING 001 --");

				mail.setSenderName(EmailProperties.getProperties(MAIL_SENDER_NAME));
				mail.setSenderAddress(EmailProperties.getProperties(MAIL_SENDER_ADDRESS));
				context.put("mail", mail);
				context.put("objList", mail.getMailConents());
				context.put("toDoUrl", EmailProperties.getProperties(MAIL_TODO_URL));
				
				es.send(mail, "mail", context);
				log.debug("-- start email service end --");
			}
			
		} catch (Exception e) {
			log.error("[Exception]: ", e);
			log.error("-- Job sendMail have exception --");
		}
		
		log.debug("-- Job sendMail end --");		 
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
}
