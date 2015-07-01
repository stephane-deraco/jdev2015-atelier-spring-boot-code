package org.jdev2015.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("'${process.carburant}'=='true'")
public class ProcessXMLFilesRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:///{{files.in}}?antInclude={{files.pattern}}&move={{files.done}}")
				.log("Processing file ${file:onlyname}")
				.to("direct:processXML");

		from("direct:processXML")
				.split().tokenizeXML("pdv").streaming()
				.beanRef("transform", "toPrice")
				.filter(simple("${body.isEmpty()} == false"))
				.beanRef("transform", "toDBObject")
				.to("mongodb:mongo?database=jdev&collection=prices&operation=insert");
	}
}
