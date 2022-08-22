package websocket.handler

import example.ExampleService
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor

import java.util.concurrent.CopyOnWriteArraySet

@Transactional
class WebsocketDevicePointService extends TextWebSocketHandler {

    ExampleService exampleService

    //使用CopyOnWriteArraySet，保证线程安全，当一个用户
    // 退出时，这边的用户查看用户列表时不会出现安全失败
    private static CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<WebSocketSession>()


    /**
     * 連接成功時候，會觸發頁面上onopen方法
     * @param session
     * @throws Exception
     */
    @Override
    void afterConnectionEstablished(WebSocketSession session) throws Exception {
        println "afterConnectionEstablished"

        super.afterConnectionEstablished(session)
    }

    /**
     * 关闭链接时触发，当用户退出时，将用户从集合remove
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        println "afterConnectionClosed"
        sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }

    /**
     * 接受消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        JsonSlurper jsonSlurper = new JsonSlurper()

        String content = message.getPayload()
        log.debug "content = ${content}"
        try{
            JSON json = jsonSlurper.parseText(content)
            String deviceId = session.getAttributes().get("deviceId")
            println "json = "+json
            println "deviceId = "+deviceId

            exampleService.getMessage(deviceId,json)

        }catch(Exception ex){
            log.error(ex.message)
            return
        }

        super.handleTextMessage(session, message);

    }

    @Override
    void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            // session.close();
        }
        String username = (String) session.getAttributes().get(HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME);
        println("websocket connection closed......");
        sessions.remove(username);
    }

    void sendMessageToUser(String deviceId, TextMessage message) {
        for (WebSocketSession session: sessions) {
            if (session.getAttributes().get("deviceId").equals(deviceId)) {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(message)
                    }
                } catch (IOException e) {
                    e.printStackTrace()
                }
                break
            }
        }
    }
}
