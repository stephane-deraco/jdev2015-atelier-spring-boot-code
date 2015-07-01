package org.jdev2015.rest;

import org.jdev2015.domain.MeanPrice;
import org.jdev2015.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
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

	@RequestMapping(value = "/moyenne/{ville}/{type}", method = GET)
	public List<double[]> getMoyenne(@PathVariable String ville, @PathVariable String type) {
		Aggregation aggregation = newAggregation(
				match(Criteria.where("ville").is(ville).and("type").is(type)),
				project("prix").and("date").extractYear().as("year"),
				group("year").avg("prix").as("moyenne"),
				sort(ASC, "_id")
		);

		AggregationResults<MeanPrice> moyenne = mongoTemplate.aggregate(aggregation, "prices", MeanPrice.class);

		return moyenne.getMappedResults().stream()
				.map(item -> new double[]{item.annee, item.moyenne})
				.collect(Collectors.toList());
	}
}
