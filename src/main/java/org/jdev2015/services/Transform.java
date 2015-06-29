package org.jdev2015.services;

import com.google.common.base.Strings;
import org.apache.camel.language.XPath;
import org.jdev2015.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Transform {
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private CityFilter cityFilter;

	public List<Price> toPrice(@XPath("/pdv/@latitude") String latitude,
							   @XPath("/pdv/@longitude") String longitude,
							   @XPath("/pdv/ville/text()") String city,
							   @XPath("/pdv/prix") List<Element> prices) {

		if (Strings.isNullOrEmpty(latitude)
				|| Strings.isNullOrEmpty(longitude)
				|| Strings.isNullOrEmpty(city)
				|| prices == null || prices.isEmpty()) {
			return Collections.emptyList();
		}

		String nfdCity = Normalizer.normalize(city, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
		if (!cityFilter.keep(nfdCity)) {
			return Collections.emptyList();
		}

		List<Price> collect = prices.stream()
				.map(elem -> createPrice(longitude, latitude, nfdCity, elem))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
		return collect;
	}

	private Optional<Price> createPrice(String longitude, String latitude, String city, Element element) {
		String type = element.getAttribute("nom");
		String maj = element.getAttribute("maj");
		String priceValue = element.getAttribute("valeur");
		if (Strings.isNullOrEmpty(type) || Strings.isNullOrEmpty(maj) || Strings.isNullOrEmpty(priceValue)) {
			return Optional.empty();
		}

		try {
			Date date = df.parse(maj);
			Price price = new Price(longitude, latitude, city, type, date, priceValue);
			return Optional.of(price);
		} catch (ParseException e) {
			return Optional.empty();
		}
	}
}
