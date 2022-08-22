package websocket.interceptor

import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.socket.*
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor


/**
 * 握手攔截器
 */
class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    /**
     * 握手前檢查
     * @param request the current request
     * @param response the current response
     * @param wsHandler the target WebSocket handler
     * @param attributes the attributes from the HTTP handshake to associate with the WebSocket
     * session; the provided attributes are copied, the original map is not used.
     * @return
     * @throws Exception
     */
    @Override
    boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) throws Exception {
        System.out.println("Before Handshake")

        //在握手之前将HttpSession中的用户，copy放到WebSocket Session中
        if (request instanceof ServletServerHttpRequest){

            String path = request.getURI().getPath()
            int index = path.indexOf("/websocket")
            String deviceId = path.substring(index).replace("/websocket/","")
            println "deviceId = "+deviceId
            attributes.put("deviceId",deviceId)
        }
        return super.beforeHandshake(request,response,wsHandler,attributes)
    }

    @Override
    void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception ex
    ) {
        super.afterHandshake(request, response, wsHandler, ex)
    }



}