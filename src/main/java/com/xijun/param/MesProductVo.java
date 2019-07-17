package com.xijun.param;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

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
public class MesProductVo {
	
	@Min(1)
	private Integer count=1;//这个数字就算是没有值，默认是1
	
    private Integer id;

    private Integer pId;

    private String productId;

    private Integer productOrderid;
    
    private Integer productPlanid;

    private Float productTargetweight;

    private Float productRealweight;

    private Float productLeftweight;

    private Float productBakweight;

    @NotBlank(message="类别不能为空")
    private String productIrontype;
    
    private Float productIrontypeweight;

    private String productMaterialname;

    private String productImgid;

    private String productMaterialsource;

    private Integer productStatus;

    private String productRemark;

    private String productOperator;

    private Date productOperateTime;

    private String productOperateIp;

    private Integer productHeatNumber;
   
}