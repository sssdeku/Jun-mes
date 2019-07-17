package com.xijun.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SearchPlanDto {

    private String keyword;

    private Date fromTime;//yyyy-MM-dd HH:mm:ss

    private Date toTime;
    
    private Integer search_status;
    
    private String search_msource;
	
}
