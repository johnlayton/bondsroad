package com.github.johnlayton.bondsroad.app.router

import com.github.johnlayton.bondsroad.app.handler.InputHandler
import com.github.johnlayton.bondsroad.app.model.InputResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class InputRouter(private val handler: InputHandler) {

    @Bean
    fun route() = router {
        POST("/demo", handler::sayHello)
        GET("/demo") { _ ->
            ServerResponse.ok().body(fromValue(
                InputResponse(1, "Hello world!!!")))
        }
    }

//    @Bean
//    fun router() : RouterFunction<ServerResponse> {
//
//        return RouterFunctions.route()
//            .path("/demo", { builder ->
//                builder.GET("", HandlerFunction<ServerResponse>() {
//                    ServerResponse.ok().body(fromValue(InputResponse(0, "Hello world!!!")))
//                })
//
//            })
//            .build()
//
////        return route(GET("/demo"), HandlerFunction<ServerResponse>() {
////            ServerResponse.ok().body(fromValue(InputResponse(0, "Hello world!!!")))
////        })
//
////            { req ->
////            ServerResponse.ok().body(fromValue(InputResponse(0, "Hello world!!!")))
////        })
//    }

}