package org.jdev2015.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "price.filter")
public class CityFilter {
	private List<String> cities = new ArrayList<>();

	public List<String> getCities() {
		return cities;
	}

	public boolean keep(String city) {
		return cities.contains(city);
	}
}
