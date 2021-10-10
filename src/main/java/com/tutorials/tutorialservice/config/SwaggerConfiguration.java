package com.tutorials.tutorialservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    public static final String AUTHORIZATION_HEADER = "Authorization";


    @Bean
    public Docket api() {
       return new Docket(DocumentationType.SWAGGER_2)
                  .securitySchemes(Collections.singletonList(apiKey()))
                   .securityContexts(Collections.singletonList(securityContext())).select()
                /*.select()*/
                .apis(RequestHandlerSelectors.basePackage("com.tutorials.tutorialservice.controllers"))
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getCustomizedResponses())
               .globalResponseMessage(RequestMethod.POST, getCustomizedResponses())
               .globalResponseMessage(RequestMethod.DELETE, getCustomizedResponses())
               .globalResponseMessage(RequestMethod.PUT, getCustomizedResponses())
               .globalResponseMessage(RequestMethod.PATCH, getCustomizedResponses())
               .globalResponseMessage(RequestMethod.OPTIONS, getCustomizedResponses());
    }
      @Bean
      ApiInfo apiInfo() {
      return new ApiInfo("Building rest api's with Spring Boot", "Securing the resources with spring boot security",
                   "v1", "", new Contact(" Dhayakar K", "https://github.com/dhayakar777", ""),
               "License of API", "API license URL", Collections.emptyList());
    }

    public List<ResponseMessage> getCustomizedResponses() {
         List<ResponseMessage> responseMessageList = new ArrayList<>();
         responseMessageList.add(new ResponseMessageBuilder().code(200).message("Resources found").build());
         responseMessageList.add(new ResponseMessageBuilder().code(201).message("New resource created").build());
         responseMessageList.add(new ResponseMessageBuilder().code(204).message("Resource removed").build());
         responseMessageList.add(new ResponseMessageBuilder().code(500).message("Internal server error while accessing the resource").build());
         responseMessageList.add(new ResponseMessageBuilder().code(401).message("You are not authorized to view the resource").build());
         responseMessageList.add(new ResponseMessageBuilder().code(403).message("Accessing the resource you were trying to reach is " +
                 "forbidden").build());
         responseMessageList.add(new ResponseMessageBuilder().code(400).message("A resource type already exists").build());
         return responseMessageList;
    }


    //These methods Configure jwt security context with a global authorization scope

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).
                forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
    }
    private List<SecurityReference> defaultAuth() {
      final AuthorizationScope authorizationScope =new AuthorizationScope("global", "accessEverything");
      final AuthorizationScope[] authorizationScopes = new AuthorizationScope[] {authorizationScope};
      return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
    }

    // This method is to define api key to include jwt as an authorization header
    private ApiKey apiKey() {
     return new ApiKey("Bearer", AUTHORIZATION_HEADER, "Header");
    }
}
