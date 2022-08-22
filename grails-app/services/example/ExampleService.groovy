package example

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.springframework.web.socket.TextMessage
import websocket.handler.WebsocketDevicePointService

@Transactional
class ExampleService {

    WebsocketDevicePointService websocketDevicePointService

    def sentMessage(
            String deviceId,
            JSON message
    ){
        TextMessage textMessage = new TextMessage(message.toString())
        websocketDevicePointService.sendMessageToDevice(deviceId,textMessage)
    }
}
