package example

import grails.converters.JSON
import groovy.json.JsonSlurper
import org.springframework.http.HttpStatus

import java.net.http.HttpClient

class ExampleController {

    ExampleService exampleService


    def index() { }

    JSON sentToHelloDiv2(){
        LinkedHashMap result = [:]
        String deviceId = '9870987'
        String messageS = '{"test":"from server"}'
        JsonSlurper jsonSlurper = new JsonSlurper()
        JSON message = jsonSlurper.parseText(messageS)
        exampleService.sentMessage(deviceId,message)

        render status: HttpStatus.OK, text: result as JSON,contentType: 'application/json'
    }
}
