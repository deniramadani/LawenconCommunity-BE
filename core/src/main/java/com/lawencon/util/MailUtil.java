package com.lawencon.util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

@Component
public class MailUtil {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;
	
	@Autowired
	private SchedulerUtil schedulerUtil;

	@Value("${mail.sender-address}")
	private String mailSenderAddress;
	
	public void sendMessage(String to, String subject, String text) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(mailSenderAddress);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		schedulerUtil.startSchedulerOnce(() -> emailSender.send(message), 1, TimeUnit.SECONDS);
	}

	public void sendMessageFreeMarker(String to, String subject, 
			Map<String, Object> templateModel, String template)
			throws Exception {

		Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate(template);
		String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(mailSenderAddress);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);

		schedulerUtil.startSchedulerOnce(() -> emailSender.send(message), 1, TimeUnit.SECONDS);
	}

}
