package org.jdev2015.domain;

import org.springframework.data.annotation.Id;

public class MeanPrice {
	@Id
	public long annee;
	public double moyenne;
}
