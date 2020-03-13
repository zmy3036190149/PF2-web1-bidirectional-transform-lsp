import { Injectable } from '@angular/core';
import { FileService } from './file.service';
import { MonacoLanguageClient, CloseAction, ErrorAction, MonacoServices, createConnection } from 'monaco-languageclient';
import { listen, MessageConnection } from 'vscode-ws-jsonrpc';
const ReconnectingWebSocket = require('reconnecting-websocket');
import {
  InitializeRequest, InitializeParams, InitializeResult,
  ShutdownRequest, ExitNotification,
  LogMessageNotification, LogMessageParams,
  ShowMessageNotification, ShowMessageParams,
  TelemetryEventNotification,
  DidChangeConfigurationNotification, DidChangeConfigurationParams,
  DidOpenTextDocumentNotification, DidOpenTextDocumentParams,
  DidChangeTextDocumentNotification, DidChangeTextDocumentParams,
  DidCloseTextDocumentNotification, DidCloseTextDocumentParams,
  DidSaveTextDocumentNotification, DidSaveTextDocumentParams,
  DidChangeWatchedFilesNotification, DidChangeWatchedFilesParams,
  PublishDiagnosticsNotification, PublishDiagnosticsParams,
} from 'vscode-languageserver-protocol/lib/main';
import { DiagramMessageFactory } from '../LSP/DiagramMessageFactory';
import { Project } from '../entity/Project';
import { ProjectService } from './project.service';

@Injectable({
  providedIn: 'root'
})
export class TextService {
  messageId
  projectAddress
  version
  ws:WebSocket;
  editor:monaco.editor.IStandaloneCodeEditor
  interval
  newValue
  noError
  connection: MessageConnection
  languageClient: MonacoLanguageClient
  filename: string
  isPfNull = true
  pf = ""
  project:Project
  hasSpace=false
  constructor(
    private projectService: ProjectService,
    private fileService: FileService) { 
    this.messageId=0    
    this.openWebSocket()
    projectService.stepEmmited$.subscribe(
      project => { 
        this.project = project;
    })
  }
  
  languageId = 'problemframe';
  hasCreatedEditor = false;
  createEditor(title,version,code){ 
    //create model    
    this.filename = title + version+".pf"
    
    let model = monaco.editor.createModel(code, "problemframe", 
      monaco.Uri.parse("file://E:/test-data/"+this.filename))
    this.editor.setModel(model)
    monaco.editor.getModels().forEach(model => {
      //console.log(model)
      //console.log(model.uri.toString())      
    })
    var that = this
    if(!this.hasCreatedEditor){
      this.hasCreatedEditor = true;
    // var rootUri = "file://E:/test-data/test.pf";
    var rootUri = "file://root/pf-language-server/test.pf";
    //console.log(this.editor)
    // install Monaco language client services    
    MonacoServices.install(this.editor,{ rootUri: rootUri });
    // create the web socket
    const url = 'ws://47.52.116.116:8030/sampleServer';
    // const url = 'ws://47.52.116.116:8099/LSP';
    const webSocket = this.createWebSocket(url);
    
    // listen when the web socket is opened
    listen({
      webSocket,
      onConnection: (connection: MessageConnection) => {
        // create and start the language client
        const languageClient = this.createLanguageClient(connection);
        const disposable = languageClient.start();
        connection.onClose(() => disposable.dispose());        
        that.connection = connection
        that.languageClient = languageClient
      }
    }); 
    }

     
    this.editor.setValue(code)  
    //listen when the editor's value changed
    this.interval = setInterval(function(){
      that.didSave()
      }
    ,1000)
  }

  //old
  public  save():void{
    let markers = monaco.editor.getModelMarkers({})
      let error = false
      if(markers.length>0)
        error = true;
      let value  =  this.editor.getValue()
      if(value!= this.newValue){
        // that.connection.sendNotification()
        // let params:DidSaveTextDocumentParams ={
        //   textDocument: {
        //     uri: "file://E:/test-data/"+filename,
        //     version: null,
        //   }
        // }
        // that.languageClient.sendNotification(
        //   DidSaveTextDocumentNotification.type,params);
        let params:DidCloseTextDocumentParams ={
          textDocument: {
            uri: "file://E:/test-data/"+this.filename
          }
        }
        this.languageClient.sendNotification(
          DidCloseTextDocumentNotification.type,params); 
        //向服务器发送最新版
        this.newValue = value
        this.send(error,value)
      }
  }

  public createLanguageClient(connection: MessageConnection): MonacoLanguageClient {
    return new MonacoLanguageClient({
      name: `ProblemFrame Client`,
      clientOptions: {
        // use a language id as a document selector
        documentSelector: ["problemframe"],
        // disable the default error handler
        errorHandler: {
          error: () => ErrorAction.Continue,
          closed: () => CloseAction.DoNotRestart
        }
      },
      // create a language client connection from the JSON RPC connection on demand
      connectionProvider: {
        get: (errorHandler, closeHandler) => {
          return Promise.resolve(createConnection(<any>connection, errorHandler, closeHandler));
        }
      }
    });
  }
  public createWebSocket(url) {
    var socketOptions = {
        maxReconnectionDelay: 10000,
        minReconnectionDelay: 1000,
        reconnectionDelayGrowFactor: 1.3,
        connectionTimeout: 10000,
        maxRetries: Infinity,
        debug: false
    };
    return new ReconnectingWebSocket(url, undefined, socketOptions);
  }
  // createEditor(title,version,code){
  //   var rootUri = "file://E:/test-data";
  //   var name = rootUri + title + version + ".pf"
  //   if(this.editor==undefined){      
  //     let res = abc(code,name)
  //     this.editor = res[0]
  //     this.languageClient = res[1]
  //     var that = this
  //     // this.editor.onDidChangeModelContent(function (event) { 
  //       // //console.log("====onDidChangeModelContent=======")        
  //       // //console.log(that.languageClient)        
  //       // //console.log(that.languageClient.diagnostics)
  //     // });
  //   }
     
  //   this.editor.setValue(code)
  //   this.newValue = code;
  //   var that = this
  //   this.interval = setInterval(function () {
  //     let value  =  that.editor.getValue()
  //     that.noError = true
  //     if(value!= that.newValue || that.timeFlag>100){
  //       //向服务器发送最新版
  //       that.newValue = value
  //       that.send(value)
  //       that.timeFlag = 0
  //     }else{
  //       that.timeFlag += 1
  //     }
  //   }, 1000)    
  // }
  timeFlag = 0
  //old 
  send(error,value){
    var message = {
      "type":"change",
      "title": this.projectAddress,
      "version": this.version,
      "id" : this.messageId,
      "from": "text",
      "text": value,
      "error":error
    }
    //console.log("============text send message=============")
    //console.log(message) 
    this.ws.send(JSON.stringify(message))
  }
  getText(){
    return this.editor.getValue()
  }
  
  //打开项目时调用
  getNotNullPf(projectAddress,version){
    var that = this
    this.projectAddress = projectAddress
    if(version==undefined)
      version = "undefined"
    this.version = version
    this.isPfNull = true
    this.fileService.getNotNullPf(projectAddress,version).subscribe(
      pf => {
        that.isPfNull = false
        const r = /problem: (.*)\n/g
        let line = pf.match(r)[0].trim()
        let title = line.replace("problem: ","").trim()
        if(title.indexOf(" ")!= -1){
          if(title[0]!="#")
            title = "#"+title
          if(title[title.length-1]!="#")
            title += "#"
        }
        pf = pf.replace(/problem: .*\n/g,"problem: "+title + "\n")
        pf = pf.replace("M M ","M1 M ")
        this.pf = pf

        console.log(that.isPfNull,pf)
      })
  }

  //=============websocket============
  //old
  openWebSocket_old(){
    this.ws = new WebSocket('ws://47.52.116.116:8099/webSocket');
    var that = this
    this.ws.onopen = function () {
      //console.log('client: ws connection is open');
      // that.ws.send('hello');
    };
    this.ws.onmessage = function (e) {
      // var project = JSON.parse(e.data) 
      //console.log("=====text收到了消息=======") 
      var pro = JSON.parse(e.data)
      //console.log(pro)
      //console.log("============")
      that.update(pro)
      // that.setProject(project, project.title)
    };
    this.ws.onerror = function (e) {
      //console.log('=================================error================================', e);
    };
    this.ws.onclose = function(e){
      //console.log("=================================close===============================",e);
      that.openWebSocket()      
      that.register(that.projectAddress,that.version,null,that.getText())   
    }
  }
  //===========发送消息old===========
  register_old(title,version,text){
    if(version==undefined)
      version = "undefined"
    //console.log("-------------------register-----:",title,version)
    var message = {
      "type":"register",
      "title": title,
      "version": version,
      "id" : this.messageId,
      "from": "text",
      "text": text
    }
    this.projectAddress = title
    this.version = version
    //console.log("============send message=============")
    //console.log(message)
    this.ws.send(JSON.stringify(message))
  }

  //==========接收消息old===========  
  update_old(jsonMessage){
    //console.log(jsonMessage)
    // switch(jsonMessage.type){
    //   case "registered":
    //     this.registered(jsonMessage);
    // }
    //console.log(jsonMessage.type)
    this.newValue = jsonMessage.text
    
    var position = this.editor.getPosition();
    
    // position.lineNumber,
    // position.column,
    this.editor.setValue(jsonMessage.text);
    this.editor.setPosition(position);
  }
  wsdealId_old(id){
    if(this.messageId==id){
      this.messageId +=1
    }else{
      //console.log("this.messageId=",this.messageId)
      //console.log("id = ",id)
    }
  }
  registered_old(jsonMessage){
    // //console.log("registered, jsonMessage.text=",jsonMessage.text)
    this.createEditor(this.projectAddress,this.version,jsonMessage.text)
    let that = this
    this.editor.addCommand(monaco.KeyMod.CtrlCmd | monaco.KeyCode.KEY_S,
      function(){that.save()},"");
  }
  //==================================== new ===========================

  //======== websocket new =========
  openWebSocket(){
    this.ws = new WebSocket('ws://47.52.116.116:8099/TextLSP');
    var that = this
    this.ws.onopen = function () {
      //console.log('client: ws connection is open');
      // that.ws.send('hello');
    };
    this.ws.onmessage = function (e) {
      // var project = JSON.parse(e.data) 
      //console.log("=====text收到了消息=======") 
      var message = JSON.parse(e.data)
      //console.log(pro)
      //console.log("============")
      that.update(message)
      // that.setProject(project, project.title)
    };
    this.ws.onerror = function (e) {
      //console.log('=================================error================================', e);
    };
    this.ws.onclose = function(e){
      console.log("=================================close===============================",e);
      that.openWebSocket()      
      that.register(that.projectAddress,that.version,that.project,that.pf)   
    }
  }

  //===========发送消息 new ===========
  register(title,version,pro:Project,text){
    version = version==undefined?"undefined":version
    let textDocument = {
      uri: title + version,
      languageId: "pf",
      version: 0,
      text: text,
      project: pro
    }
    let params:DidOpenTextDocumentParams = {
      textDocument:textDocument
    }
    let message = new DiagramMessageFactory().getDiagramMessage("TextDocument/didOpen",params)
    this.ws.send(JSON.stringify(message))
    this.projectAddress = title
    this.version = version
    this.newValue = text
    //createEditor
    this.createEditor(this.projectAddress,this.version,text)
    let that = this
    this.editor.addCommand(monaco.KeyMod.CtrlCmd | monaco.KeyCode.KEY_S,
      function(){that.didSave()},"");

  }

  //new
  public didSave():void{
    let markers = monaco.editor.getModelMarkers({})
      let error = false
      if(markers.length>0)
        error = true
      let value  =  this.editor.getValue()
      if(value!= this.newValue && ! error){
        // console.log("this.editor.getValue()\n"+value)
        // console.log("last version Value\n"+this.newValue)
        let params:DidSaveTextDocumentParams = {
          textDocument: {
            uri: this.filename.split(".")[0],
            version: null,
          },
          text:value
        }
      //向服务器发送最新版
      let message = new DiagramMessageFactory().getDiagramMessage("TextDocument/didSave",params)
      this.ws.send(JSON.stringify(message))      
      this.newValue = value
    }
  }

  //==========接收消息 new===========
  update(jsonMessage){
    console.log(" jsonMessage \n")
    console.log(jsonMessage)
    let params = jsonMessage.params
    this.newValue = params.text
    var position = this.editor.getPosition();
    this.editor.setValue(this.newValue);
    this.editor.setPosition(position);
  }
}
