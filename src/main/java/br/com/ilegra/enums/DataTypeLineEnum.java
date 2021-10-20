package br.com.ilegra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataTypeLineEnum {
	
	SALESMAN("001"), CUSTOMER("002"), SALES("003");
	
	private String id; 
	
	public static DataTypeLineEnum getPorId(String id) {
		for (DataTypeLineEnum dtle : DataTypeLineEnum.values()) {
			if (dtle.getId().equals(id)) {
				return dtle;
			}
		}
		return null;
	}

}
