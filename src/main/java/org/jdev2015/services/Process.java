package org.jdev2015.services;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class Process implements CommandLineRunner {
	private static final Logger LOG = getLogger(Process.class);

	@Value("${files.in}")
	private String inputDir;

	@Override
	public void run(String... args) throws Exception {
		LOG.debug("Fetching files from {}", inputDir);
	}
}
