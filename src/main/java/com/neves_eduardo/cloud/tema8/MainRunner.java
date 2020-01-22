package com.neves_eduardo.cloud.tema8;

import com.neves_eduardo.cloud.tema8.calculator.Calculator;
import com.neves_eduardo.cloud.tema8.config.AppConfig;
import netflix.adminresources.resources.KaryonWebAdminModule;
import netflix.karyon.Karyon;
import netflix.karyon.KaryonBootstrapModule;
import netflix.karyon.ShutdownModule;
import netflix.karyon.archaius.ArchaiusBootstrapModule;
import netflix.karyon.health.HealthCheckHandler;
import netflix.karyon.servo.KaryonServoModule;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainRunner {
	public static void main(String[] args) {
		System.setProperty("java.awt.headless","true");
		System.setProperty("archaius.deployment.environment","dev");
		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

		HealthCheckHandler healthCheckHandler = new HealthcheckResource();
        Karyon.forRequestHandler(8888,
                new RxNettyHandler((Calculator) appContext.getBean("Calculator"),"/calculator","/healthcheck",
                        new HealthCheckEndpoint(healthCheckHandler)),
                new KaryonBootstrapModule(healthCheckHandler),
                new ArchaiusBootstrapModule("simplemath-netflix-oss"),
                Karyon.toBootstrapModule(KaryonWebAdminModule.class),
                ShutdownModule.asBootstrapModule(),
                KaryonServoModule.asBootstrapModule()
        ).startAndWaitTillShutdown();
		((AnnotationConfigApplicationContext)appContext).close();
	}
}
