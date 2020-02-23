export class DiagramMessage{
  jsonrpc:String //2.0
  id
  method: string //"Diagram/didChange"
  params?: object
  
}