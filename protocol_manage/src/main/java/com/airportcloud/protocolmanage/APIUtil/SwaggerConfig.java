package com.airportcloud.protocolmanage.APIUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @ClassName: SwaggerConfig
 * @Description: 接口文档管理 自定义文件
 * @author wzt
 * @date 2016-12-26 13:17:52
 * @version V1.0
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.airportcloud.protocolmanage"))
				.paths(PathSelectors.any())
				.build();
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("贵宾云后台管理系统")
				.description("贵宾云后台管理系统-描述")
				.termsOfServiceUrl("http://www.baidu.com/")
				.contact("wzt")
				.version("1.0")
				.build();
	}

	/*@Bean
	public Docket addUserDocket() {
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("贵宾云 接口文档管理")
				.description("如果没有特殊说明，所有接口返回的数据格式为JSON格式，JSON模板为："
						+ "{ \"status\":200/500/... ,\"msg\":\"操作成功/操作失败/...\", \"data\":{数据} }")
				.version("1.0")
				.build();
		
		*//**
		 * 匹配api路径 新增一个新接口就在这个地方加入对应的路径正则表达式
		 * 
		 * 例如：^/interim/[a-zA-Z]+.do$
		 *//*
		StringBuffer sb = new StringBuffer();
		//sb.append("^/interim/[a-zA-Z]+.do$");
		sb.append("|^/cities/[a-zA-Z]+.do$");
		*//*sb.append("|^/bidSect/ajaxFindBidSectsByPrjId.do$");
		sb.append("|^/bidSect/ajaxFindBidSectsByProjectId.do$");
		sb.append("|^/systemDDL/getLibraryByCategoryName.do$");
		sb.append("|^/contractManageController/[a-zA-Z]+.do$");
		sb.append("|^/projectController/[a-zA-Z]+.do$");
		sb.append("|^/projectContact/ajaxFindContactNameById.do$");
		sb.append("|^/projectContact/ajaxFindNamesByDetailIdAndProjectId.do$");
		sb.append("|^/contractManageController/[a-zA-Z]+.do$");
		sb.append("|^/contractAptitudeController/[a-zA-Z]+.do$");
		sb.append("|^/contractReviewController/[a-zA-Z]+.do$");
		sb.append("|^/contractRemindController/[a-zA-Z]+.do$");
		sb.append("|^/contractBorrowController/[a-zA-Z]+.do$");
		sb.append("|^/contractProgressController/[a-zA-Z]+.do$");
		sb.append("|^/contractMoneyManagerController/[a-zA-Z]+.do$");
		sb.append("|^/contractPaymentsController/[a-zA-Z]+.do$");
		sb.append("|^/contractPerormanceBondsController/[a-zA-Z]+.do$");
		sb.append("|^/contractAttachmentController/[a-zA-Z]+.do$");
		sb.append("|^/treeController/[a-zA-Z]+.do$");
		sb.append("|^/BidBasicController/[a-zA-Z]+.do$");*//*
		
		Predicate<String> pathRegex = PathSelectors.regex(sb.toString());
		//String regex = "^/interim/[a-zA-Z]+.do$|^/newsDataController/[a-zA-Z]+.do$";
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                .paths(pathRegex)//需要展示的api(使用正则表达式过滤请求路径，显示最新接口)
                .build();
		docket.apiInfo(apiInfo);
		return docket;
	}*/
}
