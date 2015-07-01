package org.jdev2015.rest;

import org.jdev2015.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/prices")
public class PriceController {
	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value = "/near", method = GET)
	public GeoResults<Price> getNear(@RequestParam double lon, @RequestParam double lat, @RequestParam int max) {
		NearQuery q = NearQuery.near(lon, lat).maxDistance(new Distance(max, Metrics.KILOMETERS));
		GeoResults<Price> geoResults = mongoTemplate.geoNear(q, Price.class, "prices");
		return geoResults;
	}
}
