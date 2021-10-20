package br.com.ilegra.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.ilegra.models.Customer;
import br.com.ilegra.models.Sales;
import br.com.ilegra.models.Salesman;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report implements Serializable {

	private static final long serialVersionUID = -1675007225730949566L;

	private List<Customer> customers;
	private List<Salesman> salesmans;
	private List<Sales> sales;

	public Report() {
		this.customers = new ArrayList<>();
		this.salesmans = new ArrayList<>();
		this.sales = new ArrayList<>();
	}

}
