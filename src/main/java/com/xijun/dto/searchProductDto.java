package com.xijun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class searchProductDto {

	private String keyword;
	
	private String productMaterialsource;
	
	private Integer productHeatNumber;
	
	private Integer productStatus;

	private String productType;
	
	private String pidIsNull;
	
}
