package de.dokukaefer;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration>{

	public static void main(String[] args) throws Exception {
		new HelloWorldApplication().run(args);
	}
	
	@Override
	public String getName() {
		return "hello this is the hello world";
	}
	
	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		//nothing to do jet
	}
	
	@Override
	public void run(HelloWorldConfiguration conf, Environment env) throws Exception {
		final HelloWorldResource rs = new HelloWorldResource(conf.getTemplate(), conf.getDefaultName());
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(conf.getTemplate());
		env.healthChecks().register("template", healthCheck);
		env.jersey().register(rs);
	}
	
}