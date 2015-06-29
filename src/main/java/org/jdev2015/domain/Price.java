package org.jdev2015.domain;

import com.google.common.base.MoreObjects;

import java.util.Date;

public class Price {
	private double[] loc;
	private String ville;
	private String type;
	private Date date;
	private double prix;

	public Price() {
	}

	public Price(String longitude, String latitude, String ville, String type, Date date, String priceValue) {
		double x = Double.parseDouble(longitude) / 100_000;
		double y = Double.parseDouble(latitude) / 100_000;
		setLoc(new double[]{x, y});

		setVille(ville);
		setType(type);
		setDate(date);
		setPrix(Double.parseDouble(priceValue) / 1_000);
	}

	public double[] getLoc() {
		return loc;
	}

	public void setLoc(double[] loc) {
		this.loc = loc;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("loc", loc)
				.add("ville", ville)
				.add("type", type)
				.add("date", date)
				.add("prix", prix)
				.toString();
	}
}
