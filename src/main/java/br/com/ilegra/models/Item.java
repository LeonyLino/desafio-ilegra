package br.com.ilegra.models;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal total;
	

}
