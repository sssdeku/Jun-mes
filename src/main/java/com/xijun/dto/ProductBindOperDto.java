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
public class ProductBindOperDto {

	private String id;
	
	private String targetweight;
	
	private Integer faId;
	
	private Integer numberid;
	
	private String productId;
}
