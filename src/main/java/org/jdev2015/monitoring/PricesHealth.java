package org.jdev2015.monitoring;

import org.jdev2015.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class PricesHealth implements HealthIndicator {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Value("${prix.seuil:1.40}")
	private double threshold;

	@Override
	public Health health() {
		Query q = new Query(Criteria.where("ville").is("toulouse"));
		q.with(new Sort(Sort.Direction.DESC, "date"));
		Price price = mongoTemplate.findOne(q, Price.class, "prices");

		if (price.getPrix() > threshold) {
			return Health.down().withDetail("price", price.getPrix()).build();
		} else {
			return Health.up()
					.withDetail("price", price.getPrix())
					.withDetail("msg", "Il faut faire le plein !").build();
		}
	}
}
