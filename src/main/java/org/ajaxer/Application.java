package org.ajaxer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Shakir Ansari
 * @since 2025-01-10
 */
@SpringBootApplication
public class Application
{
	public static void main(String[] args) throws Exception
	{
		new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.NONE) // Ensure no web server starts
				.run(args);
	}
}
