package br.com.ilegra.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Customer implements Serializable{

	private static final long serialVersionUID = 4225692750879227749L;
	
	private String cnpj;
	private String name;
	private String businessArea;
	
	@Override
	public String toString() {
		return getCnpj() +" - "+ getName() +" - "+ getBusinessArea();
	}
	
	

}
