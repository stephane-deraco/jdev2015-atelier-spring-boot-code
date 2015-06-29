package org.jdev2015.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProcessXMLFilesRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:///{{files.in}}?antInclude={{files.pattern}}&move={{files.done}}")
				.log("Processing file ${file:onlyname}");
	}
}
