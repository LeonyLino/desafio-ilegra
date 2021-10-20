package br.com.ilegra.models;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Salesman implements Serializable{

	private static final long serialVersionUID = -1238252025695046603L;
	
	private String cpf;
	private String name;
	private BigDecimal salary;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getCpf() +" - "+ getName() +" - "+ getSalary();
	}
	
}
