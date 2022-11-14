package com.lawencon.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.lawencon.util.VerificationCodeUtil.VerificationCodes;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;

/**
 * @author lawencon05
 */

@Configuration
public class GlobalConfiguration {

	@Value("${mail.template-folder}")
	private String mailTemplateFolder;

	@Bean(name = "verificationCodes")
	public Map<String, VerificationCodes> verificationCodes() {
		return new HashMap<>();
	}

	@Bean
	public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
		freemarker.template.Configuration configuration = new freemarker.template.Configuration(
				freemarker.template.Configuration.VERSION_2_3_30);
		TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/" + mailTemplateFolder);
		configuration.setTemplateLoader(templateLoader);
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setConfiguration(configuration);
		return freeMarkerConfigurer;
	}

}
