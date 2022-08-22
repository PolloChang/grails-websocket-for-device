package websocket.config

import grails.gorm.transactions.Transactional
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor
import websocket.handler.WebsocketDevicePointService
import websocket.interceptor.HandshakeInterceptor

@Configuration
@Transactional
@EnableWebSocket
class DeviceWebSocketConfigService implements WebSocketConfigurer {

    WebsocketDevicePointService websocketDevicePointService

    @Override
    void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(websocketDevicePointService, "/websocket/{token}")
                .addInterceptors(new HandshakeInterceptor()) //这个方法是向spring容器注册一个handler地址，我把他理解成requestMapping。
                .setAllowedOrigins("*") //允许指定的域名或IP(含端口号)建立长连接，
    }

    /**
     * 拦截器，当建立websocket连接的时候，我们可以通过继承spring的HttpSessionHandshakeInterceptor来搞事情。
     * @return
     */
    @Bean
    HttpSessionHandshakeInterceptor myHandlerInterceptor(){
        return new HandshakeInterceptor()
    }
}
