package org.jdev2015.monitoring;

import org.jdev2015.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

@Component
public class PriceMetric implements PublicMetrics {
	@Autowired
	private MongoTemplate mongoTemplate;

	private Random random = new Random();

	@Override
	public Collection<Metric<?>> metrics() {
		Metric<Double> metricToulouse = new Metric<Double>("carburants.prix.toulouse", getPrix("toulouse"));
		Metric<Double> metricBordeaux = new Metric<Double>("carburants.prix.bordeaux", getPrix("bordeaux"));
		return Arrays.asList(metricToulouse, metricBordeaux);
	}

	private double getPrix(String ville) {
		Query q = new Query(Criteria.where("ville").is(ville));
		q.with(new Sort(Sort.Direction.DESC, "date"));
		Price price = mongoTemplate.findOne(q, Price.class, "prices");
		return price.getPrix() * (1 + random.nextGaussian() / 10);
	}
}
