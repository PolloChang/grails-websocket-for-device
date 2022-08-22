package websocket.server

import grails.converters.JSON
import grails.gorm.transactions.Transactional

@Transactional
class WebsocketServerService {

    def getMessage(
            String deviceId,
            JSON message
    ){
        println "ExampleService"
        println "deviceId = "+deviceId
        println "message = "+message
    }
}
