package com.ruggero.bookstorage.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

//	@Bean
//	public Docket postsApi() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("Dematic task").apiInfo(apiInfo()).select()
//				.paths(postPaths()).build();
//	}
//
//	private Predicate<String> postPaths() {
//		return or(regex("/books*"), regex("/books/.*"));
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("Dematic book storage").description("Dematic task guide")
//				.termsOfServiceUrl("none").license("This is an interview task : no license applicable")
//				.licenseUrl("ruggero.montesi1@gmail.com").version("1.0").build();
//	}
}