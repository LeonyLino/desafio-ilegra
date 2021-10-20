package br.com.ilegra.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Sales implements Serializable{

	private static final long serialVersionUID = -1105998576481223087L;
	
	private Long id;
	private List<Item> itens;
	private String salesman;
	private BigDecimal totalValueSaleItens;

}
