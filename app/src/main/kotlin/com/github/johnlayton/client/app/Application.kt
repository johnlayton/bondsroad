package com.github.johnlayton.client.app

import org.slf4j.LoggerFactory
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan(
    "com.github.johnlayton.soap.service.sal.config"
)
@ComponentScans(
    ComponentScan("com.github.johnlayton.client.app.handler"),
    ComponentScan("com.github.johnlayton.client.app.router"),
    ComponentScan("com.github.johnlayton.soap.service.sal"),
    ComponentScan("com.github.johnlayton.soap.service.api")
//    ,
//    ComponentScan("au.com.mebank.soap.service")
)
class Application {
  private val log = LoggerFactory.getLogger(Application::class.java)
}

fun main(args: Array<String>) {
  runApplication<Application>(*args) {
//    setBannerMode(Banner.Mode.OFF)
//    setBanner(Banner())/
    webApplicationType = WebApplicationType.REACTIVE
  }
}
