import { DiagramMessage } from "./DiagramMessage";
export class DiagramMessageFactory {
    static id = 0
    public  getDiagramMessage(method:string,params:object):DiagramMessage{
        if(method == null){
            return null;
        }
        let message = new DiagramMessage()
        message.jsonrpc = "2.0"
        message.id = DiagramMessageFactory.id
        message.method = method
        message.params = params
        return message
    }

 }