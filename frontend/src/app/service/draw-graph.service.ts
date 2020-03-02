import { Injectable, SystemJsNgModuleLoader } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ComponentChoiceService } from '../service/component-choice.service';
import * as _ from 'lodash';
import * as backbone from 'backbone';
import { Phenomenon } from '../entity/Phenomenon';
import { RequirementPhenomenon } from '../entity/RequirementPhenomenon';
import { Project } from '../entity/Project';
import { Machine } from '../entity/Machine';
import { Interface } from '../entity/Interface';
import { Requirement } from '../entity/Requirement';
import { Constraint } from '../entity/Constraint';
import { Reference } from '../entity/Reference';
import { ProblemDomain } from '../entity/ProblemDomain';
import { ProblemDiagram } from '../entity/ProblemDiagram';
//import { template, element, text } from '@angular/core/src/render3';
import * as joint from 'node_modules/jointjs/dist/joint.js';
import { ContextDiagram } from '../entity/ContextDiagram';
import { ProjectService } from './project.service';
import { FileService } from './file.service';
import { OntologyEntity } from '../entity/OntologyEntity';
import { identifierModuleUrl } from '@angular/compiler';
import { DiagramMessage} from "../LSP/DiagramMessage"
import { DiagramMessageFactory } from '../LSP/DiagramMessageFactory';
import { DiagramIdentifier } from '../LSP/DiagramIdentifier';
import { VersionedDiagramIdentifier } from '../LSP/VersionedDiagramIdentifier';
import { DiagramContentChangeEventFactory } from '../LSP/DiagramContentChangeEventFactory';
import { LineInfo } from '../entity/LineInfo';
import { Line } from '../entity/Line';
import { DidChangeDiagramParams } from '../LSP/DidChangeDiagramParams.';
import { DiagramContentChangeEvent } from '../LSP/DiagramContentChangeEvent';
import { DiagramItem } from '../LSP/DiagramItem';
import { DidOpenDiagramParams } from '../LSP/DidOpenDiagramParams'
@Injectable({
  providedIn: 'root'
})
export class DrawGraphService {
  constructor(
    public component_choice_service: ComponentChoiceService,
    private fileService: FileService,
    private projectService: ProjectService) {
    projectService.stepEmmited$.subscribe(
      step => {
       this.step = step;
       if(step==3){
        this.addinterfaceName();
       }
     })
     this.openWebSocket()
     this.project = new Project();
  }

  ws:WebSocket;
  project: Project;
  step = 1;
  papers = new Array<joint.dia.Paper>();
  clickedElement = null;
  tab1:string;
  tab2:string;
  selectPhes:Phenomenon[];
  isSaved=false;
  machine: Machine;
  problemDomain: ProblemDomain;
  interface: Interface;
  requirement: Requirement;
  reference: Reference;
  constraint: Constraint;
  selectedGraphIndex = 0;
  graphs = new Array<joint.dia.Graph>();
  phenomenonList = []
  constraintPhenomenonList = new Array<RequirementPhenomenon>();
  refPheList = new Array<Phenomenon>();
  initiatorList = new Array<String>();

  problemdomain_no = 1;
  requirement_no = 1;
  interface_no = 1;
  reference_no = 1;
  constraint_no = 1;
  phenomenon_no = 1;
  link_name_no = 1;

  rcSource = null;
  rcTarget = null;
  link_source = undefined;
  link_target = undefined;
  selectedId = undefined;
  selectedElement = undefined;
  selectedType: string;

  selectedLinkSource = undefined;
  selectedLinkTarget = undefined;

  selectedPhenomenonNo = undefined;
  selectedReferencePhenomenonNo = undefined;
  selectedConstraintPhenomenonNo = undefined;
  description = undefined;

  deleteListen = false;

  PhysicalPropertys = ['GivenDomain', 'DesignDomain']
  DomainTypes = ['Causal', 'Biddable', 'Lexical']
  phenomenonTypes = ['event', 'state', 'value']

  projectAddress: string;
  version: string;
  interval;

  initiator
  receiverList
  initiator_receiverList
  initiator_or_receiverList

  interfacePhes
  ontologyPhes
  interface_ontologyPhes
  messageId=0
  interval1
  //==============================WebSocket=========================
  openWebSocket(){
    this.ws = new WebSocket('ws://localhost:8080/DiagramLSP');
    var that = this

    this.ws.onopen = function () {
      console.log('client: ws connection is open');
    };

    this.ws.onmessage = function (e) {
      console.log("=====dg收到了消息=======") 
      var message:DiagramMessage = JSON.parse(e.data)
      if(message.method =="Diagram/didChange"){
        let params = <DidChangeDiagramParams>message.params
        console.log("----------------------\n")
        console.log(params.contentChanges[0]);
        console.log("----------------------\n")
        let diagramContentChangeEvent = <DiagramContentChangeEvent>params.contentChanges[0]
        that.wsChange(diagramContentChangeEvent)
      }
    };

    this.ws.onerror = function (e) {
      console.log('=================================error================================', e);
    };

    this.ws.onclose = function(e){
      console.log("=================================close===============================",e);
      that.openWebSocket()
    }
  }
  //-----------接收消息 old ---------

  update(pro){
    // if(pro.title!=this.projectAddress){
    //   console.log(pro.title,"!=", this.projectAddress)
    //   return
    // }
    this.wsdealId(pro.id)
    if(pro.from=="text"){
      console.log("=============updateProjectSinceTextChanged=========" )
      console.log(pro)
      this.updateProjectSinceTextChanged(pro.pro)
      return
    }
    switch(pro.type){
      case "add": 
        this.wsadd(pro.shape,pro.new)
        break
      case "delete": 
        this.wsdelete(pro.shape,pro.old)
        break
      case "change":
        this.wschange(pro.shape,pro.old,pro.new)
        break
    }
  }
  wsdealId(id){
    if(this.messageId==id){
      this.messageId +=1
      console.log("this.messageId=",this.messageId)
    }else{
      console.log("this.messageId=",this.messageId)
      console.log("id = ",id)
    }
  }
  updateProjectSinceTextChanged(pro){
    let newProject = new Project()
    newProject.initProject(pro)
    console.log("newProject")
    console.log(newProject)
    //若找到同名（简称）实体则修改,若找不到，则添加
    //删除pro中不存在的实体
    this.project.changeMachineWithNewProject(newProject)
    this.project.changeProblemDomainWithNewProject(newProject)
    this.project.changeRequirementWithNewProject(newProject)
    this.project.changeInterfaceWithNewProject(newProject)
    this.project.changeConstraintWithNewProject(newProject)
    this.project.changeReferenceWithNewProject(newProject)
    //画图
    this.drawDiagram(this.project);
  }

  wsadd(shape,new1){
    console.log("==========wsadd",shape,new1)
    switch(shape){
      case "mac":
        this.drawMachinews(new1)
        break
      case "pro":
          this.drawProblemDomainws(new1)
          break
      case "req":
        this.drawRequirementws(new1)
        break
      case "int":
        this.drawInterfacews(new1)
        break
      case "ref":
        this.drawReferencews(new1)
        break
      case "con":
        this.drawConstraintws(new1)
        break
    }
  }
  wsdelete(shape,old){
    switch(shape){
      case "mac":
        this.deleteMachinews(old)
        break
      case "pro":
          this.deleteProblemDomainws(old)
          break
      case "req":
        this.deleteRequirementws(old)
        break
      case "int":        
        console.log(old)
        this.deleteInterfacews(old)
        break
      case "ref":
        this.deleteConstraintws(old)
        break
      case "con":
        this.deleteConstraintws(old)
        break
    }
  }
  wschange(shape,old,new1){
    console.log(this.project)
    switch(shape){
      case "mac":
        this.changeMachinews(old,new1)
        break
      case "pro":
          this.changeProblemDomainws(old,new1)
          break
      case "req":
        this.changeRequirementws(old,new1)
        break
      case "int":        
        console.log(old,new1)
        this.changeInterfacews(old,new1)
        break
      case "ref":
        this.changeConstraintws(old,new1)
        break
      case "con":
        this.changeConstraintws(old,new1)
        break
    }
  }

  //----------发送消息  old -----------
  register1(title,version,pro){
    if(version==undefined)
      version = "undefined"
    console.log("-------------------diagram register-----:",title,version)
    //取消注册之前的project
    if(this.projectAddress && this.projectAddress != title){
      this.unregister(this.projectAddress,this.version)
    }

    var message = {
      "type":"register",
      "title": title,
      "version":version,
      "id" : this.messageId,
      "from": "diagram",
      // "pro":pro
    }
    this.projectAddress = title
    this.version = version
    console.log("============diagram send message=============")
    console.log(message)
    this.ws.send(JSON.stringify(message))
  }
  unregister1(title,version){
    if(version==undefined)
      version = "undefined"
    var message = {
      "type": "unregister",
      "title": title,
      "version": version,
      "id" : this.messageId      
    }
    console.log("================send message=================")
    console.log(message)
    this.ws.send(JSON.stringify(message));
  }
  change(type:string,shape:string,old,new1){
    let version = this.version
    if(version==undefined)
        version = "undefined"
    console.log("this.messageId=",this.messageId)
    var message = {
           "type": type,
           "title": this.projectAddress,
           "version": version,
           "id": this.messageId,
           "shape": shape, 
           "old": old,
           "new": new1,
           "from":"diagram",
           
    }
    console.log("============send message============")
    console.log(message)
    this.ws.send(JSON.stringify(message)) 
  }

  //----------接收消息 new ---------
  wsChange(diagramContentChangeEvent:DiagramContentChangeEvent){
    let shapeType = diagramContentChangeEvent.shapeType
    switch(shapeType){
      case "mac":
        this.wsMachine(diagramContentChangeEvent)
        break
      case "pro":
        this.wsProblemDomain(diagramContentChangeEvent)
        break
      case "req":
        this.wsRequirement(diagramContentChangeEvent)
        break
      case "int":
        this.wsInterface(diagramContentChangeEvent)
        break
      case "ref":
        this.wsReference(diagramContentChangeEvent)
        break
      case "con":
        this.wsConstraint(diagramContentChangeEvent)
        break
      case "phe":
        this.wsPhenomenon(diagramContentChangeEvent)
        break
      case "project":
        this.wsProject(diagramContentChangeEvent)
    }
  }
  wsMachine(diagramContentChangeEvent){
    let changeType = diagramContentChangeEvent.changeType
    let oldShape = diagramContentChangeEvent.oldShape
    let newShape = diagramContentChangeEvent.newShape
    switch(changeType){
      case "add":
          this.drawMachinews(newShape)
          break
      case "delete":
          this.deleteMachinews(oldShape)
          break
      case "change":
          this.changeMachinews(oldShape,newShape)
          break
    }
  }
  wsProblemDomain(diagramContentChangeEvent){
    let changeType = diagramContentChangeEvent.changeType
    let oldShape = diagramContentChangeEvent.oldShape
    let newShape = diagramContentChangeEvent.newShape
    switch(changeType){
      case "add":
          this.drawProblemDomainws(newShape)
          break
      case "delete":
          this.deleteProblemDomainws(oldShape)
          break
      case "change":
          this.changeProblemDomainws(oldShape,newShape)
          break
    }
  }
  wsRequirement(diagramContentChangeEvent){
    let changeType = diagramContentChangeEvent.changeType
    let oldShape = diagramContentChangeEvent.oldShape
    let newShape = diagramContentChangeEvent.newShape
    switch(changeType){
      case "add":
          this.drawRequirementws(newShape)
          break
      case "delete":
          this.deleteRequirementws(oldShape)
          break
      case "change":
          this.changeRequirementws(oldShape,newShape)
          break
    }
  }
  wsInterface(diagramContentChangeEvent){
    let changeType = diagramContentChangeEvent.changeType
    let oldShape = diagramContentChangeEvent.oldShape
    let newShape = diagramContentChangeEvent.newShape
    switch(changeType){
      case "add":
          this.drawInterfacews(newShape)
          break
      case "delete":
          this.deleteInterfacews(oldShape)
          break
      case "change":
          this.changeInterfacews(oldShape,newShape)
          break
    }
  }
  wsReference(diagramContentChangeEvent){
    let changeType = diagramContentChangeEvent.changeType
    let oldShape = diagramContentChangeEvent.oldShape
    let newShape = diagramContentChangeEvent.newShape
    switch(changeType){
      case "add":
          this.drawReferencews(newShape)
          break
      case "delete":
          this.deleteReferencews(oldShape)
          break
      case "change":
          this.changeReferencews(oldShape,newShape)
          break
    }
  }
  wsConstraint(diagramContentChangeEvent){
    let changeType = diagramContentChangeEvent.changeType
    let oldShape = diagramContentChangeEvent.oldShape
    let newShape = diagramContentChangeEvent.newShape
    switch(changeType){
      case "add":
          this.drawConstraintws(newShape)
          break
      case "delete":
          this.deleteConstraintws(oldShape)
          break
      case "change":
          this.changeConstraintws(oldShape,newShape)
          break
    }
  }
  wsPhenomenon(diagramContentChangeEvent:DiagramContentChangeEvent){
    let changeType = diagramContentChangeEvent.changeType
    let lineInfo = diagramContentChangeEvent.lineInfo
    let oldPhenomenon = diagramContentChangeEvent.oldPhenomenon
    let newPhenomenon = diagramContentChangeEvent.newPhenomenon
    switch(changeType){
      case "add":
          this.addPhenomenonws(lineInfo,newPhenomenon)
          break
      case "delete":
          this.deletePhenomenonws(lineInfo,oldPhenomenon)
          break
      case "change":
          this.changePhenomenonws(lineInfo,oldPhenomenon,newPhenomenon)
          break
    }
  }
  addPhenomenonws(lineInfo,newShape){

  }
  deletePhenomenonws(lineInfo,oldShape){

  }
  changePhenomenonws(lineInfo,oldShape,newShape){

  }
  wsProject(diagramContentChangeEvent:DiagramContentChangeEvent){
    let pro :Project = diagramContentChangeEvent.newProject
    let newProject = new Project()
    //若找到同名（简称）实体则修改,若找不到，则添加
    //删除pro中不存在的实体
    newProject.initProject(pro)
    this.project = newProject;
    //画图
    this.drawDiagram(this.project);
  }

  //----------发送消息  new -----------
  register(title,version,pro:Project){
    if(version==undefined)
      version = "undefined"
    console.log("-------------------diagram register-----:",title,version)
    //取消注册之前的project
    if(this.projectAddress && this.projectAddress != title){
      this.unregister(this.projectAddress,this.version)
    }
    // let diagram : DiagramItem = {uri:title+version,pro:pro}
    // let cd = pro.contextDiagram
    let diagram :DiagramItem= {uri:title+version, 
      title:title, 
      project:pro, 
      contextDiagram:null,
      requirementList:null,
      constraintList: null, 
      referenceList: null }
    let params : DidOpenDiagramParams = {diagram:diagram}
    let message = new DiagramMessageFactory().getDiagramMessage("Diagram/didOpen",params)
    console.log("============diagram send message=============")
    let smessage = JSON.stringify(message)
    console.log(smessage.length)
    console.log(message)
    this.ws.send(smessage)

    this.projectAddress = title
    this.version = version
  }
  //分批发送project信息
  register2(title,version,pro:Project){
    if(version==undefined)
      version = "undefined"
    console.log("-------------------diagram register-----:",title,version)
    //取消注册之前的project
    if(this.projectAddress && this.projectAddress != title){
      this.unregister(this.projectAddress,this.version)
    }
    // let diagram : DiagramItem = {uri:title+version,pro:pro}
    let cd = pro.contextDiagram
    let diagram :DiagramItem= {uri:title+version, 
      title:title, 
      contextDiagram:cd, 
      project:null,
      requirementList:null,
      constraintList: null, 
      referenceList: null }
    let cdparams : DidOpenDiagramParams = {diagram:diagram}
    let message = new DiagramMessageFactory().getDiagramMessage("Diagram/didOpen1",cdparams)
    console.log("============diagram send message=============")    
    let smessage = JSON.stringify(message)
    console.log(smessage.length)
    console.log(message)
    this.ws.send(smessage)
    let that = this
    this.interval1 = setInterval(function () {
      clearInterval(that.interval1);       
      that.setRequirementList(title,version,pro)
      that.setConstraintList(title,version,pro)
      that.setReferenceList(title,version,pro)
    }, 100);
    this.projectAddress = title
    this.version = version
  }
  setRequirementList(title,version,pro){
    let requirementList: Requirement[] = JSON.parse(JSON.stringify(pro.problemDiagram.requirementList))
    let diagram:DiagramItem = {uri:title+version,title:title,project:null,contextDiagram:null,requirementList:requirementList,
      constraintList: null, referenceList: null }
    let params : DidOpenDiagramParams = {diagram:diagram}
    let message = new DiagramMessageFactory().getDiagramMessage("Diagram/didOpen/requirementList",params)      
    let smessage = JSON.stringify(message)
    console.log(smessage.length)
    console.log(message)
    this.ws.send(smessage)
  }
  setConstraintList(title,version,pro){
    let constraintList = pro.problemDiagram.constraintList
    let diagram:DiagramItem = {uri:title+version,title:title,project:null,contextDiagram:null,requirementList:null,
      constraintList: constraintList, referenceList: null }
    let params : DidOpenDiagramParams = {diagram:diagram}
    let message = new DiagramMessageFactory().getDiagramMessage("Diagram/didOpen/constraintList",params)      
    let smessage = JSON.stringify(message)
    console.log(smessage.length)
    console.log(message)
    this.ws.send(smessage)
  }
  setReferenceList(title,version,pro:Project){
    let referenceList = pro.problemDiagram.referenceList
    let diagram:DiagramItem = {
      uri:title+version,
      title:title,
      project:null,
      contextDiagram:null,
      requirementList:null,
      constraintList: null, 
      referenceList: referenceList
    }
    let params : DidOpenDiagramParams = {diagram:diagram}
    let message = new DiagramMessageFactory().getDiagramMessage("Diagram/didOpen/referenceList",params)      
    let smessage = JSON.stringify(message)
    console.log(smessage.length)
    console.log(message)
    this.ws.send(smessage)
  }
  unregister(title,version){
    if(version==undefined)
      version = "undefined"
    let diagram : DiagramIdentifier = {uri:title+version}
    let params= {diagram:diagram}
    let message = new DiagramMessageFactory().getDiagramMessage("Diagram/didClose",params)
    console.log("================send message=================")
    console.log(message)
    this.ws.send(JSON.stringify(message));
  }
  sendChangeShapeMessage(changeType:string,shapeType:string,oldShape,newShape){
    let version = this.version
    if(version==undefined)
        version = "undefined"
    console.log("this.messageId=",this.messageId)   
    // let params : DiagramIdentifier = {uri:this.projectAddress+version}
    let diagram: VersionedDiagramIdentifier={
      uri: this.projectAddress+version,
      version: 0      
    }
    let contentChanges = [new DiagramContentChangeEventFactory().
      getShapeChangeEvent(shapeType,changeType,oldShape,newShape)]
    let params:DidChangeDiagramParams = {
      diagram:diagram,
      contentChanges: contentChanges
    }
    let message = new DiagramMessageFactory().getDiagramMessage("Diagram/didChange",params)
    console.log("============send message============")
    console.log(message)
    this.ws.send(JSON.stringify(message)) 
  }
  //this.sendChangePhenomenonMessage("add","ref",this.reference,null,phenomenon) 
  sendChangePhenomenonMessage(changeType:string,linetype:string,line:Line,oldPhenomenon,newPhenomenon){
    let version = this.version == undefined ? "undefined" : this.version
    let diagram: VersionedDiagramIdentifier={
      uri: this.projectAddress + version,
      version: 0      
    }
    let lineInfo : LineInfo = {
      type:linetype,
      name:line.getName(),
      from:line.getFrom(),
      to:line.getTo()
    }
    let contentChanges = [new DiagramContentChangeEventFactory().
      getPhenomenonChangeEvent(changeType,lineInfo,oldPhenomenon,newPhenomenon)]
    let params = {
      diagram:diagram,
      contentChanges: contentChanges
    }
    let message = new DiagramMessageFactory().getDiagramMessage("Diagram/didChange",params)
    console.log("============send message============")
    console.log(message)
    this.ws.send(JSON.stringify(message)) 
  }
  //==========================编号==============================
  searchMaxPheNo(project) {
    let no = this.searchMaxPheNo1(0,project.contextDiagram.interfaceList)
    no = this.searchMaxPheNo1(no,project.problemDiagram.referenceList)
    no = this.searchMaxPheNo1(no,project.problemDiagram.constraintList)
    console.log('searchMaxPheNo1');
    console.log(no)
    return no
  }
  searchMaxPheNo1(no,linkList){
     for(let link of linkList){
       for( let phe of link.phenomenonList){
         if(phe.no>no){
          //  console.log(phe.no,">",no)
           no = phe.no;
         }  
       }
     }     
     return no;
  }
  searchMaxLinkNameNo(project) {
    //console.log(project);
    let name = 'a';
    if(project.problemDiagram!=undefined){
      for (let reference of project.problemDiagram.referenceList) {        
          name = this.compareLinkName(name,reference.name)    
      }
      for (let constraint of project.problemDiagram.constraintList){       
        name = this.compareLinkName(name,constraint.name)
      }
    }    
    for (let my_interface of project.contextDiagram.interfaceList) {
      name = this.compareLinkName(name,my_interface.name)
      //console.log(name+'<'+my_interface.name);
    }
    //console.log(name);
    return this.getLinkNameNo(name);
  }
  compareLinkName(n1,n2){
  for(let i of n2){
    if(i<'a'&&i>'z'){
      return n1
    }
  }
  if(n1.length>n2.length){
    return n1
  }else if(n1.length<n2.length){
    return n2
  }else if(n1<n2){
    return n2
  }else 
    return n1
  }
  getlink_name(link_name_no){
    let a = link_name_no%26;
    let b = Math.floor(link_name_no/26);
    if(a==0) {
      a=26;
      b -=1;
    }    
    let res= String.fromCharCode(96+a);
    while(b>0){
      a = b%26;
      b = Math.floor(b/26);
      if(a==0) {
        a=26;
        b-=1;
      }      
      let temp = String.fromCharCode(96+a);
      res = temp+res;
    }
    return res;
  }    
  getLinkNameNo(name:string){
        let no=0;
        for(let i=0;i<name.length;i++){
          no *=26;
          no = no + name.charCodeAt(i)-96;
        }
        return no;
  }
  getShortname(name){
    let len = name.length
    let flag = true
    let res=''
    for(let i=0;i<len;i++){      
      if(flag && (name.charAt(i) > 'a' && name.charAt(i)<'z'||name.charAt(i)>'A'&& name.charAt(i)<'Z')){
        res += name.charAt(i)
        console.log(res)
        flag = false
      }
      if(name.charAt(i)=='_' || name.charAt(i)==' '){
        console.log('flag=true')
        flag=true
      }      
    }
    console.log(name,res.toUpperCase())
    return res.toUpperCase()
  }

  //==================初始化========================
  initPapers(): void {
    this.tab1 = 'Context Diagram';
    this.tab2 = 'Problem Diagram';
    for (let i = 0; i < 2; i++) {
      this.graphs[i] = new joint.dia.Graph();
      let d = $('#content' + (i + 1));
      let wid = d.width();
      let hei = d.height();      
      console.log(wid);
      console.log(hei);
      this.papers[i] = new joint.dia.Paper({
        el: $('#content' + (i + 1)),
        width: wid,
        height: hei,
        model: this.graphs[i],
        clickThreshold: 1,
        gridSize: 10,
        drawGrid: true,
        background: {
          color: 'rgb(240,255,255)'
        }
      });

      var that = this;
      this.papers[i].on('blank:mousewheel', (event, x, y, delta) => {
        const scale = this.papers[i].scale();
        this.papers[i].scale(scale.sx + (delta * 0.01), scale.sy + (delta * 0.01));
        });

      // 空白处单击事
      this.papers[i].on('blank:pointerclick', function (event, x, y) {
        //var currentElement = elementView.model;
        //that.tab1 = x + '  ' + y;
        console.log('blank:pointerclick');
        that.drawElement(that.graphs[i], event, x, y);
      });

      // 节点单击事件
      this.papers[i].on('element:pointerclick', function (elementView, evt, x, y) {
        if (that.component_choice_service.link == true) {
          // that.deleteListen = false;
          ////console.log('element:pointerclick');
          if (that.link_source == undefined) {
            that.link_source = elementView.model;
            console.log('that.link_source=',that.link_source.attr('root').title)
          } else if (that.link_target == undefined) {
            that.link_target = elementView.model;
            console.log('that.link_target=',that.link_target.attr('root').title)
            if(that.link_source==that.link_target){
              // that.link_target = undefined;
              console.log('link_source==link_target=',that.link_source.attr('root').title )
              alert('The starting point and the ending point are the same, please redraw!')
              that.link_source = undefined;
              that.link_target = undefined;
            }else{
              that.drawLink(that.link_source, that.link_target, that.graphs[i]);
              that.link_source = undefined;
              that.link_target = undefined;              
              that.component_choice_service.set_choice_false();
            }
            
          } 
        }
        if (that.component_choice_service.merge == true) {
          // that.deleteListen = false;
          ////console.log('element:pointerclick');
          if (that.link_source == undefined) {
            that.link_source = elementView.model;
          } else if (that.link_target == undefined) {
            that.link_target = elementView.model;
            that.Merge(that.link_source, that.link_target, that.graphs[i]);
            that.link_source = undefined;
            that.link_target = undefined;
            that.component_choice_service.merge = false;
          }
        }

      });

      this.papers[i].on('cell:pointerclick', function (cellView, evt, x, y) {
        //prepare to delete cell
        if (that.component_choice_service.element == false && that.component_choice_service.link == false) {
          that.selectedElement = cellView.model;
          //console.log(that.selectedElement);
          that.selectedType = that.selectedElement.attr('root').name;
          that.deleteListen = true;
        }
        // highlight
        that.resetStrokeColor();
        that.clickedElement = cellView.model;
        that.changeStrokeColor();
      });

      // 双击事件
      this.papers[i].on('cell:pointerdblclick', function (elementView, evt, x, y) {
        that.deleteListen = false;
        console.log('element:pointerdblclick');
        console.log('elementView'+elementView);
        that.selectedElement = elementView.model;
        that.selectedType = that.selectedElement.attr('root').name;
        that.selectedId = that.selectedElement.id;
        console.log('type:' + that.selectedType);
        if (that.selectedElement.isLink()) {
          //console.log('当前选中元素Id�?' + that.selectedElement.id);
          let source = that.selectedElement.source();
          let target = that.selectedElement.target();
          //that.selectedLinkSource = that.getPointName(sourceId);
          //that.selectedLinkTarget = that.getPointName(targetId);
          ////console.log('that.selectedLinkSource:' + that.selectedLinkSource);
          ////console.log('that.selectedLinkTarget:' + that.selectedLinkTarget);
        }
        //console.log('type:' + that.selectedType);
        var popBox = document.getElementById(that.selectedType + 'PopBox');
        //console.log('selectedType:' + that.selectedType);
        popBox.style.display = "block";
        var popLayer = document.getElementById("popLayer");
        popLayer.style.display = "block";
        that.initPopBox();
      });
      //initPopBox(element){}

      // 右击事件
      this.papers[i].on('cell:contextmenu', function (elementView, evt, x, y) {
        that.deleteListen = false;
        that.selectedElement = elementView.model;
        that.selectedType = that.selectedElement.attr('root').name;
        that.selectedId = that.selectedElement.id;
        //this.GetObj('delete').style.display = 'block';
        ////console.log('type:' + that.selectedType +',Id:'+ that.selectedId);
        //alert('element:contextmenu');
        //that.deleteElement(that.graphs[i]);
        //that.deletePopBox(that.graphs[i],x,y);
      });
      // change position
      this.graphs[i].on('change:position', function (element, position) {
        that.selectedElement = element;
        let name = element.attr("root").title;
        that.selectedType = that.selectedElement.attr('root').name;
        that.selectedId = that.selectedElement.id;
        that.changeElementPosition(name,position);
      });
      
    } 
    
  }
  draw_test(graph) {
    let machine = this.drawMachine(100, 400, graph);
    let pd1 = this.drawProblemDomain(400, 300, graph);
    let pd2 = this.drawProblemDomain(400, 500, graph);
    let req = this.drawRequirement(700, 400, graph);
    this.drawInterface(machine, pd1, graph);
    //console.log('draw_test');
    this.drawInterface(machine, pd2, graph);
    this.drawReference(req, pd1, graph);
    this.drawConstraint(pd2, req, graph);
  }
  initPopBox() {
    console.log('initPopBox')
    if (this.selectedType == 'machine') {
      this.initMachinePopBox();
    } else if (this.selectedType == 'problemDomain') {
      this.initDomainPopBox();
    } else if (this.selectedType == 'requirement') {
      this.initRequirementPopBox();
    } else if (this.selectedType == 'interface') {
      this.initInterfacePopBox();
    } else if (this.selectedType == 'reference') {
      this.initReferencePopBox();
    } else if (this.selectedType == 'constraint') {
      this.initConstraintPopBox();
    }
  }
  initProject(title) {    
    this.project.init(title)
    this.projectService.sendProject(this.project)
    this.link_name_no=1
    this.phenomenon_no=1
    this.problemdomain_no=1    
  }
  //============选中变色
  changeStrokeColor() {
    this.clickedElement.attr('body/stroke', 'orange');
    this.clickedElement.attr('r/stroke', 'orange');
    this.clickedElement.attr('r1/stroke', 'orange');
    this.clickedElement.attr('r2/stroke', 'orange');
    this.clickedElement.attr('line/stroke', 'orange');
  }
  resetStrokeColor() {
    if (this.clickedElement != null) {
      this.clickedElement.attr('body/stroke', 'black');
      this.clickedElement.attr('r/stroke', 'black');
      this.clickedElement.attr('r1/stroke', 'black');
      this.clickedElement.attr('r2/stroke', 'black');
      this.clickedElement.attr('line/stroke', 'black');
    }
  }
  // 引入ontology后在第二步加入interfaceName
  isLabel = true;
  addinterfaceName(){
    if(!this.isLabel){      
      //console.log(this.link_name_no)
      for(let int of this.project.contextDiagram.interfaceList){
        int.name= this.getlink_name(this.link_name_no);
        this.link_name_no += 1;
        int.description = this.project.getDescription(int.name,int.phenomenonList);
        // change interface link on graph
        let links = this.graphs[0].getLinks();
        for(let link of links){
          //此时只有interface,所以可以用no判断
          if(link.attr('root').no==int.no){
            link.attr('root').title = int.name   
            link.appendLabel({
              attrs: {
                text: {
                  text: int.name,
                  fontSize: 25,
                  textAnchor: 'middle',
                  textVerticalAnchor: 'middle',
                  background: 'none'
                }
              }});
          }
        }        
      }
    }
  }

  ontologyEntities: OntologyEntity[];
  initProjectWithOntology(title){
    //this.project.contextDiagram.problemDomainList
    this.initProject(title)  
    this.isLabel = false
    let proNum = this.ontologyEntities.length;
    this.project.contextDiagram.machine = Machine.newMachine('machine','M',100, 50*proNum, 100, 50 );
    let x = 400;
    let y = 50;
    
    //console.log(this.ontologyEntities);
    for(let ont of this.ontologyEntities){
      let pro = new ProblemDomain()
      pro.no = ont.id
      pro.name = ont.name
      pro.shortname = this.getShortname(ont.name)
      pro.type = ont.type.slice(0,-6)
      console.log("=================")
      console.log(pro)
      
      // pro.property
      pro.phes = new Array<Phenomenon>();
      for(let opt of ont.opts){
        let phe = new Phenomenon();
        phe.no = this.phenomenon_no;
        phe.type = 'event';
        phe.name = opt;
        pro.phes.push(phe);
      }
      // pro.values = new Array<Phenomenon>();
      for(let val of ont.values){
        let phe = new Phenomenon();
        phe.no = this.phenomenon_no;
        phe.type = 'value';
        phe.name = val;
        pro.phes.push(phe);
      }
      // pro.states = new Array<Phenomenon>();
      for(let opt of ont.states){
        let phe = new Phenomenon();
        phe.no = this.phenomenon_no;
        phe.type = 'state';
        phe.name = opt;
        pro.phes.push(phe);
      }
      pro.x= x;
      pro.y = y;
      y+=100;
      pro.h = 50;
      pro.w = 100;      
      console.log(pro);
      this.project.contextDiagram.problemDomainList.push(pro);

      //link
      let int = new Interface();
      int.from = 'M';
      int.to = pro.shortname;
      int.no= this.interface_no;
      this.interface_no += 1;
      int.name = '';
      this.project.contextDiagram.interfaceList.push(int);
      int.phenomenonList = new Array<Phenomenon>();
      //phe

      
    }
    this.drawDiagram(this.project);
  }

  enterTable(index) {
    ////console.log('enter tab!!!!!!!!!!!!!',index);
    switch (index) {
      case 1:
        //console.log('enter ��ta!!!!!!!!!!!!!b1');
        this.graphs[0].clear();
        this.drawContextDiagram(this.project.contextDiagram, this.graphs[0])
        break;
      case 2:
        //console.log('enter tab2!!!!!!!!!!!!');
        this.graphs[1].clear();
        //console.log(this.project);
        this.drawProblemDiagram(this.project.problemDiagram, this.graphs[1])
        
    }
  }

  //獲取Project
  getProject(projectAddress: string, version): void {
    // //console.log("aaa");
    // this.projectAddress = window.location.pathname.split('/')[1];
    var that = this
    this.projectAddress = projectAddress
    if(version==undefined)
      version = "undefined"
    this.version = version
    this.fileService.getProject(projectAddress,version).subscribe(
      project => {
        that.setProject(project)       
        that.register(projectAddress,version,project)   
      });
  }
  //upload pf 时调用
  getPfProject(projectAddress: string, version): void {
    // //console.log("aaa");
    // this.projectAddress = window.location.pathname.split('/')[1];
    var that = this
    this.projectAddress = projectAddress
    if(version==undefined)
      version = "undefined"
    this.version = version
    this.fileService.getPFProject(projectAddress).subscribe(
      project => {
        that.setProject(project)       
        that.register(projectAddress,version,project)   
      });
  }
  getLatestProject(projectAddress: string, version): void {
    // //console.log("aaa");
    // this.projectAddress = window.location.pathname.split('/')[1];
    var that = this
    this.projectAddress = projectAddress
    if(version==undefined)
      version = "undefined"
    this.version = version
    this.fileService.getLatestProject(projectAddress,version).subscribe(
      project => {
        that.setProject(project)
        
      });
  }
  
  setProject(project){
    this.project.initProject(project)
    console.log(project)
    this.link_name_no=this.searchMaxLinkNameNo(this.project)+1
    this.phenomenon_no=this.searchMaxPheNo(this.project)+1
    console.log("this.link_name_no = ",this.link_name_no)
    console.log("this.phenomenon_no = ",this.phenomenon_no)
    this.dealConstraint(this.project)
    this.drawDiagram(this.project)
    this.projectService.sendProject(this.project)
  }

  //處理 reference 和 constraint上的constraint標記
  dealConstraint(project){
    for(let temp of project.problemDiagram.referenceList){
      for(let phe of temp.phenomenonList){
        if(phe.constraint==null){
          //console.log('null-------')
          phe.constraint = false
        }
      }
    }
    for(let temp of project.problemDiagram.constraintList){
      for(let phe of temp.phenomenonList){
        if(phe.constraint==null){
          //console.log('null-------')
          phe.constraint = false
        }
      }
    }
  }
  //-----------------画图----------------------------
  //绘制图形
  drawDiagram(project: Project) {
    this.initPapers();
    this.drawContextDiagram(project.contextDiagram, this.graphs[0]);
    this.drawProblemDiagram(project.problemDiagram, this.graphs[1]);
  }

  // ContextDiagram
  drawContextDiagram(contextDiagram: ContextDiagram, graph) {
    var that = this;
    var elementList = [];
    if(contextDiagram.machine!=null)
      this.drawMachine2(contextDiagram.machine, elementList, graph);
    this.drawProblemDomains(contextDiagram.problemDomainList, elementList, graph);
    this.drawInterfaces(contextDiagram.interfaceList, elementList, graph);
  }

  //ProblemDiagram
  drawProblemDiagram(problemDiagram: ProblemDiagram, graph) {
    var that = this;
    var elementList = [];
    var reqEleList = [];
    if(problemDiagram.contextDiagram.machine!=null)
      this.drawMachine2(problemDiagram.contextDiagram.machine, elementList, graph);
    this.drawProblemDomains(problemDiagram.contextDiagram.problemDomainList, elementList, graph);
    this.drawInterfaces(problemDiagram.contextDiagram.interfaceList, elementList, graph);
    this.drawRequirements(problemDiagram.requirementList, reqEleList, graph);
    this.drawConstraints(problemDiagram.constraintList, elementList, reqEleList, graph);
    this.drawReferences(problemDiagram.referenceList, elementList, reqEleList, graph);
    /*paper.on('blank:mousewheel', (event, x, y, delta) => {
        const scale = paper.scale();
        paper.scale(scale.sx + (delta * 0.01), scale.sy + (delta * 0.01));
    });*/
  }

  //Cell
  drawElement(graph, event, x, y) {
    if (this.component_choice_service.element == true) {
      if (this.component_choice_service.domain == true) {
        this.drawProblemDomain(x, y, graph);
        this.component_choice_service.domain = false;
      } else if (this.component_choice_service.machine == true) {
        this.drawMachine(x, y, graph);
        this.component_choice_service.machine = false;
      } else if (this.component_choice_service.req == true) {
        this.drawRequirement(x, y, graph);
        this.component_choice_service.req = false;
      }else{
        console.log(this.component_choice_service)
      }
      this.component_choice_service.element = false;
    }
  }
  deleteElement(graph) {
    //console.log('deleteElement!!!');
    //console.log('selectedType' + this.selectedType);
    if (this.selectedType == 'machine') {
      this.deleteMachine(graph);
    } else if (this.selectedType == 'problemDomain') {
      this.deleteProblemDomain(graph);
    } else if (this.selectedType == 'requirement') {
      this.deleteRequirement(graph);
    } else if (this.selectedType == 'interface') {
      //console.log('deleteElement');
      this.deleteInterface(graph);
    } else if (this.selectedType == 'constraint') {
      this.deleteConstraint(graph);
    } else if (this.selectedType == 'reference') {
      this.deleteReference(graph);
    } else {
      //console.log('this.selectedType:' + this.selectedType);
    }
    //console.log(this.project);
  }
  getElementById(id){
    for (const ele of this.graphs[this.selectedGraphIndex].getElements()) {
      if(ele.id==id)
        return ele    
        //console.log('-------------',id,'!=',ele.id,'-----------')    
    }
    return null
  }
  changeElementPosition(name,position) {  
    // let name =   this.selectedElement.attr('label').title
    if(this.project.changeMachinePosition(name,position))
      return
    if(this.project.changeProblemDomainPosition(name,position))
      return   
    if(this.project.changeRequirementPosition(name,position))
      return
  }

  //=================================Machine=============================
  drawMachine2(machine: Machine, elementList, graph) {
    let machineElement = this.drawMachineOnGraph(machine, graph)
    elementList.push(machineElement)
  }
  //画图并修改project
  drawMachinews(wsmachine: Machine) {
    let machine = Machine.copyMachine(wsmachine)
    this.project.problemDiagram.contextDiagram.machine = machine
    this.project.contextDiagram.machine = machine
    for (let graph of this.graphs){
      this.drawMachineOnGraph(machine, graph)      
    }    
  }
  //鼠标点击画图，画图并添加machine
  drawMachine(x, y, graph) {
    console.log("drawMachine")
    if (this.project.contextDiagram.machine == undefined) {
      // let machine = this.project.addMachine('machine', 'M', x, y,100,50)
      // let element = this.drawMachineOnGraph(machine, graph);
      // this.projectService.sendProject(this.project);
      let machine = Machine.newMachine('machine', 'M1', x, y,100,50)
      this.sendChangeShapeMessage("add","mac",null,machine)
      // return element;
    } else {
      alert('machine already exist!');
    }

  }
  //在特定画板画图
  drawMachineOnGraph(machine: Machine, graph) {
    var MachineElement = joint.dia.Element.define('examples.CustomTextElement', {
      attrs: {
        label: {
          textAnchor: 'middle',	//???????
          textVerticalAnchor: 'middle',
          fontSize: 25,
        },
        r: {          
          strokeWidth: 1,	//????
          stroke: '#000000',	//???
          fill:'rgb(240,255,255)',
        },
        r1: {
          strokeWidth: 1,
          stroke: '#000000',
          fill:'rgb(240,255,255)',
          
        },
        r2: {
          strokeWidth: 1,
          stroke: '#000000',
          fill:'rgb(240,255,255)',      
        },
      }
    }, {
        markup: [{
          tagName: 'rect',
          selector: 'r'
        }, {
          tagName: 'rect',
          selector: 'r1'
        }, {
          tagName: 'rect',
          selector: 'r2'
        },{
          tagName: 'text',
          selector: 'label'
        }]
      });

    var machineElement = new MachineElement();
    machineElement.attr(
      {
        label: {
          text: machine.name + '\n(' + machine.shortname + ')',
          // x: machine.x,
          // y: machine.y,
        },
        r: {
          ref: 'label',
          text:'rrrrrrrrrrrrrrrrrrrrr',
          refX: -35,	//???????
          refY: 0,
          x: 0,	//???��??
          y: 0,
          
          refWidth: 45,	//??��?��
          refHeight: '120%',
        },
        r1: {
          ref: 'label',
          text: '111111111111',
          refX: -20,
          refY: 0,
          x: 0,
          y: 0,
          refWidth: 30,          
          refHeight: '120%',
        },
        r2: {
          text: '2222222222',
          ref: 'label',
          refX: -5,
          refY: 0,
          x: 0,
          y: 0,
          refWidth: 15,
          refHeight: '120%',
        },
        root: {
          name: 'machine',
          title: machine.name,
          shortname:  machine.shortname,
        }
      });
    machineElement.position(machine.x, machine.y);
    machineElement.addTo(graph);
    return machineElement;
  }
  initMachinePopBox() {

    this.machine = this.project.contextDiagram.machine;
    let selectedDiv = document.getElementById('machinePopBox');
    (selectedDiv.getElementsByClassName("description")[0] as any).value = this.machine.name;
    (selectedDiv.getElementsByClassName("shortName")[0] as any).value = this.machine.shortname;
    
    selectedDiv.style.display = "block";
  }
  //修改Machine信息，仅向服务器发送消息
  changeMachineDetail() {
    let selectedDiv = document.getElementById('machinePopBox');
    let description = (selectedDiv.getElementsByClassName("description")[0] as any).value;
    let shortname = (selectedDiv.getElementsByClassName("shortName")[0] as any).value; 
    let old = this.project.contextDiagram.machine
    let machine =  Machine.newMachineWithOld(old,description,shortname)
    // this.projectService.sendProject(this.project)    
    this.sendChangeShapeMessage("change","mac",old,machine)
    return true;
  }
  changeMachinews(old:Machine,new1:Machine) {
    this.changeRelatedLink(old.shortname,new1.shortname)
    for(let graph of this.graphs)
      for(let ele of graph.getElements()){
        if(ele.attr("root").title == old.name){
          console.log(ele.attr("root").title ,"== ",old.name)
          this.changeMachineOnGraph(ele, new1.name, new1.shortname) 
        }             
      }
   this.project.changeMachine(new1.name, new1.shortname)    
    this.projectService.sendProject(this.project)
    return true;
  }
  changeMachineOnGraph(element, name, shortname) {
    element.attr({
      root: { 
        title: name,
        name:"machine",
        shortname:shortname 
      },
      label: { text: name + '\n(' + shortname + ')' }
    });
  }

  deleteMachinews(old:Machine) {
    let shortname = old.getShortname();
    this.project.deleteRelatedLink(shortname)    
    this.project.contextDiagram.machine = undefined;
    this.project.problemDiagram.contextDiagram.machine = undefined;    
    this.projectService.sendProject(this.project)    

    for(let graph of this.graphs){      
      for(let element of graph.getCells() ){
        if (old.shortname == element.attr('root').shortname)
          graph.removeCells([element])
      }
    }
  }
  deleteMachine(graph) {
    console.log("==========deleteRelatedLink========")
    console.log(this.project)
    let old = this.project.contextDiagram.machine    
    this.sendChangeShapeMessage("delete","mac",old,null)
  }

  //============================================Domain===============================================
  drawProblemDomains(problemDomainList: ProblemDomain[], elementList, graph): void {
    for (let i = 0; i < problemDomainList.length; i++) {
      var element;
      let problemDomain = problemDomainList[i];
      if (problemDomain.property === 'DesignDomain') {
        element = this.drawDesignDomain(problemDomain, graph);
      } else {
        element = this.drawGivenDomain(problemDomain, graph);
      }
      elementList.push(element);
    }
  }
  drawProblemDomainws(problemDomain:ProblemDomain){
    problemDomain = ProblemDomain.copyProblemDomain(problemDomain);
    this.project.contextDiagram.problemDomainList.push(problemDomain);
    for(let graph of this.graphs){
      if (problemDomain.property === 'DesignDomain') {
        this.drawDesignDomain(problemDomain, graph);
     } else {
       this.drawGivenDomain(problemDomain, graph);
     }
    }
  }
  drawProblemDomain(x, y, graph) {
    let no,name,shortname
    while(true){
      no = this.problemdomain_no;
      this.problemdomain_no += 1;
      name = 'problemDomain' + no;
      shortname = 'PD' + no;
      let conflicting_name = false;
      for(let pdi of this.project.contextDiagram.problemDomainList){
        if (pdi.name==name || pdi.shortname==shortname){
          conflicting_name = true
        }
      }
      if(!conflicting_name){
        break;
      }
    }
    let pd = ProblemDomain.newProblemDomain(no, name, shortname, 'Causal', 'GivenDomain', x, y,100,50)  
    this.sendChangeShapeMessage("add", "pro",null,pd)
    // return element;
  }
  drawDesignDomain(designDomain: ProblemDomain, graph) {
    let element = this.drawGivenDomain(designDomain, graph);
    element.attr('r/refX', '-15');
    element.attr('r/refWidth', '20');
    return element;

  }
  drawGivenDomain(givenDomain: ProblemDomain, graph) {
    var GivenElement = joint.dia.Element.define('DesignDomain', {
      attrs: {
        label: {
          textAnchor: 'middle',	//???????
          textVerticalAnchor: 'middle',
          fontSize: 25,
        },
        r: {
          strokeWidth: 1,	//????
          stroke: '#000000',	//???
          fill:'rgb(240,255,255)',
        },
        r1: {
          strokeWidth: 1,
          stroke: '#000000',
          fill:'rgb(240,255,255)',
        },
      }
    }, {
        markup: [{
          tagName: 'rect',
          selector: 'r'
        }, {
          tagName: 'rect',
          selector: 'r1'
        },{
          tagName: 'text',
          selector: 'label'
        }]
      });

    var givenElement = new GivenElement();
    givenElement.attr({
      label: {
        text: givenDomain.name + '\n(' + givenDomain.shortname + ')',
      },
      r: {
        ref: 'label',
        refX: -5,	//???????
        refY: -5,
        x: 0,	//???��??
        y: 0,        
        refWidth: 10,	//??��?��
        refHeight: 10,
      },
      r1: {
        ref: 'label',
        refX: -5,
        refY: -5,
        x: 0,
        y: 0,        
        refWidth: 10,
        refHeight: 10,
      },
      root: {
        name: 'problemDomain',
        title: givenDomain.name,
        shortname:  givenDomain.shortname,
      }
    });
    givenElement.position(givenDomain.x, givenDomain.y);
    givenElement.addTo(graph);
    return givenElement;
  }

  initDomainPopBox() {
    console.log('initDomainPopBox:');
    for (let item of this.project.contextDiagram.problemDomainList) {     
      let name = this.selectedElement.attr('root').title;
      if (item.name == name) {
        console.log(item.name+'=='+name);
        this.problemDomain = item;
        break;
      }
      console.log(item.name+'---!='+name+'---');        
    }
    console.log(this.problemDomain);
    let selectedDiv = document.getElementById('problemDomainPopBox');
    (selectedDiv.getElementsByClassName("description")[0] as any).value = this.problemDomain.name;
    (selectedDiv.getElementsByClassName("shortName")[0] as any).value = this.problemDomain.shortname;
    for (let i = 0; i < 2; i++) {
      let property = this.PhysicalPropertys[i];
      //console.log('��ǰproperty:');
      //console.log(this.problemDomain.property);
      //console.log('��i��property:');
      //console.log(property);
      if (property == this.problemDomain.property) {

        (selectedDiv.getElementsByClassName("physicalProperty")[0] as any).selectedIndex = i;
      }
    }
    for (let i = 0; i < 3; i++) {
      let domainType = this.DomainTypes[i];
      if (domainType == this.problemDomain.type) {
        (selectedDiv.getElementsByClassName("domainType")[0] as any).selectedIndex = i;
      }
    }
  }
  changeProblemDomainDetail(graph) {
    //console.log('changeProblemDomainDetail:');
    let selectedDiv = document.getElementById('problemDomainPopBox');
    //description
    let description = (selectedDiv.getElementsByClassName("description")[0] as any).value;
    for (let existedProblemDomain of this.project.contextDiagram.problemDomainList) {
      if (existedProblemDomain.name == description &&
        existedProblemDomain.name != this.problemDomain.name) {
        alert(description + 'already exist!');
        return false;
      }
    }
    //shortname
    let shortname = (selectedDiv.getElementsByClassName("shortName")[0] as any).value;
    //domainType
    let selectElement = selectedDiv.getElementsByClassName("domainType")[0];
    let selectedIndex = (selectElement as any).selectedIndex;
    let domainType = this.DomainTypes[selectedIndex];

    //physical Property
    selectElement = selectedDiv.getElementsByClassName("physicalProperty")[0];
    selectedIndex = (selectElement as any).selectedIndex;
    let physicalProperty = this.PhysicalPropertys[selectedIndex];

    let old = this.problemDomain
    let pd = ProblemDomain.newProblemDomainWithOld(old, description, shortname, domainType, physicalProperty)
    // this.changeProblemDomainOnGraph(this.problemDomain, this.selectedElement)
    
    // this.projectService.sendProject(this.project)    
    this.sendChangeShapeMessage("change", "pro", old, pd)
    return true;
  }
  changeProblemDomainws(old:ProblemDomain,new1:ProblemDomain){
    old = ProblemDomain.copyProblemDomain(old)
    new1 = ProblemDomain.copyProblemDomain(new1)
    this.changeRelatedLink(old.shortname,new1.shortname)
    this.project.changeProblemDomain1(old, new1)
    this.projectService.sendProject(this.project)    
    this.changeProblemDomainOnGraph1(old, new1)
    return true
  }
  changeProblemDomainOnGraph1(old:ProblemDomain, new1:ProblemDomain){
    for(let graph of this.graphs){
      for(let element of graph.getElements()){
        if(element.attr('root').title==old.name){
          this.changeProblemDomainOnGraph(new1,element)
          break
        }
      }
    }
  }
  changeProblemDomainOnGraph(domainEntity:ProblemDomain, domainElement) {
    console.log("=======changeProblemDomainOnGraph=========")
    console.log(domainEntity,domainEntity.shortname)
    domainElement.attr({
      label: {
        text: domainEntity.name + '\n(' + domainEntity.shortname + ')',
      },
      root: {
        name: 'problemDomain',
        title: domainEntity.name,
        shortname: domainEntity.shortname,
      }
    });
    if (domainEntity.property === 'GivenDomain') {
      this.change2GivenDomain(domainElement);
    } else {
      this.change2DesignDomain(domainElement);
    }
  }
  change2DesignDomain(element) {
    element.attr('r/refX', '-15');
    element.attr('r/refWidth', '20');
  }
  change2GivenDomain(element) {
    element.attr('r/refX', '-5');
    element.attr('r/refWidth', '10');
  }

  changeProblemDomainEntity(name, shortname, type, property) {
    this.problemDomain.name = name;
    this.problemDomain.shortname = shortname;
    this.problemDomain.type = type;
    this.problemDomain.property = property;
    return this.problemDomain
    //console.log(this.project.contextDiagram.problemDomainList);
  }
  deleteProblemDomain(graph) {
    let name = this.selectedElement.attr('root').title;
    let shortname = this.selectedElement.attr('root').shortname;
    let pdList = this.project.contextDiagram.problemDomainList

    //delete problem domain
    let i = 0;
    let oldPd
    for (let item of pdList) {
      if (item.name == name) {
        oldPd = item
        break;
      }
      i++;
    }
    this.sendChangeShapeMessage( "delete", "pro", oldPd, null)
  }
  deleteProblemDomainws(pd1:ProblemDomain) {
    let pd = ProblemDomain.copyProblemDomain(pd1)
    let name = pd.name
    let shortname = pd.shortname
    let pdList = this.project.contextDiagram.problemDomainList

    //delete related links
    this.project.deleteRelatedLink(shortname)
    //delete problemdomain 
    let i = 0;
    for (let item of pdList) {
      if (item.name == name) {
        pdList.splice(i, 1);
        break;
      }
      i++;
    }
    this.projectService.sendProject(this.project);   
  }

  //=====================================Requirement==========================
  drawRequirements(requirementList: Requirement[], reqEleList, graph: joint.dia.Graph) {
    //console.log('problemDiagram.requirementList:');
    for (var i = 0; i < requirementList.length; i++) {
      var requirement = requirementList[i];
      var requirementElement = this.drawRequirement1(requirement, graph);
      reqEleList.push(requirementElement);
    }
  }
  drawRequirementws(req1) {
    let req = Requirement.copyRequirement(req1)
    this.project.problemDiagram.requirementList.push(req)
    this.drawRequirement1(req, this.graphs[1]); 

  }
  //手动画，只发送消息
  drawRequirement(x, y, graph) {
    let no
    let name 
    while(true){
      no = this.requirement_no;
      this.requirement_no += 1;
      name = 'requirement' + no;
      let conflicting_name = false;
      for(let reqi of this.project.problemDiagram.requirementList){
        if (reqi.name==name){
          conflicting_name = true
        }
      }
      if(!conflicting_name){
        break;
      }
    }    
    // let req = this.project.addRequirement(no, name, x, y,100,50)
    let req = Requirement.newRequirement(no, name, x, y,100,50)
    // let element = this.drawRequirement1(req, graph);    
    // this.projectService.sendProject(this.project);
    this.sendChangeShapeMessage( "add", "req", null, req)
    // return element;
  }
  drawRequirement1(requirement: Requirement, graph) {
    let requirementElement = new joint.shapes.standard.Ellipse();
    requirementElement.attr({
      root: { 
        name: 'requirement', 
        title: requirement.name,
        shortname:  requirement.name,
       },
      label: {
        text: requirement.name,
        fontSize: 25,
        textAnchor: 'middle',	//???????
        textVerticalAnchor: 'middle',
      },
      body: {
        ref: 'label',
        refX: 0,
        refY: 0,
        refRx: '60%',
        refRy: '70%',
        refCx: '50%',
        refCy: '50%',
        fill: 'rgb(240,255,255)',
        strokeWidth: 1,
        strokeDasharray: '8,4'
      }
    });
    requirementElement.position(requirement.x, requirement.y);
    requirementElement.addTo(graph);
    return requirementElement;
  }

  initRequirementPopBox() {
    for (let item of this.project.problemDiagram.requirementList) {
      if (item.name == this.selectedElement.attr('label').text) {
        this.requirement = item;
      }
    }
    let selectedDiv = document.getElementById('requirementPopBox');
    (selectedDiv.getElementsByClassName("description")[0] as any).value = this.requirement.name;
    (selectedDiv.getElementsByClassName("shortName")[0] as any).value = this.requirement.shortname;
  }
  //手动修改，只发送消息
  changeRequirementDetail(graph) {
    let selectedDiv = document.getElementById('requirementPopBox');
    let description = (selectedDiv.getElementsByClassName("description")[0] as any).value;
    let shortname = (selectedDiv.getElementsByClassName("shortName")[0] as any).value;
    for (let existedRq of this.project.problemDiagram.requirementList) {
      if ((existedRq as any).name == description &&
        existedRq.name != this.requirement.name) {
        alert(description + 'already exist!');
        return false;
      }
      if ((existedRq as any).shortname == shortname &&
      existedRq.shortname != this.requirement.shortname) {
      alert(shortname + 'already exist!');
      return false;
    }
    }
    //console.log(description);
    // this.changeRequirementOnGraph(this.selectedElement, description);
    let old = this.requirement
    // let newR = this.project.changeRequirement(old,description)    
    // this.projectService.sendProject(this.project)    
    let newR = Requirement.newRequirementWithOld(old,description,shortname)
    this.sendChangeShapeMessage("change", "req", old, newR)
    return true;
  }
  changeRequirementws(old1:Requirement,new2:Requirement) {
    let old = Requirement.copyRequirement(old1)
    let new1 = Requirement.copyRequirement(new2)
    this.changeRelatedLink(old.name,new1.name)    
    this.project.changeRequirement1(old, new1)    
    this.projectService.sendProject(this.project)
    this.changeRequirementOnGraph1(old, new1)
    return true;
  }
  changeRequirementOnGraph1(old:Requirement, new1:Requirement) {
    for(let graph of this.graphs){
      for(let element of graph.getElements()){
        if(element.attr('root').title==old.getName()){
          this.changeRequirementOnGraph(element,new1.getName())
          break
        }
      }
    }
  }
  changeRequirementOnGraph(element, name) {
    element.attr({
      root: { 
        title: name,
        shortname: name
      },
      label: { text: name }
    });
  }
  //向服务器发生删除信息
  deleteRequirement(graph) {
    let name = this.selectedElement.attr('root').title;
    let list = this.project.problemDiagram.requirementList;
    // this.project.deleteRelatedLink(name);
    //delete requirement
    let i = list.length - 1;
    let old
    for (; i >= 0; i--) {
      let item = list[i];
      if (item.name == name) {
        old = item
        // list.splice(i, 1);
        break;
      }
    }
    // graph.removeCells([this.selectedElement]);
    // this.projectService.sendProject(this.project)    
    this.sendChangeShapeMessage("delete", "req", old, null)
  }
  deleteRequirementws(requirement:Requirement) {
    let req = Requirement.copyRequirement(requirement)
    this.project.deleteRequirement(req)
    this.project.deleteRelatedLink(req.getName())    
    this.projectService.sendProject(this.project)
    this.deleteRequirementOnGraph(req.getName())    
  }
  deleteRequirementOnGraph(reqName:String){
    for(let graph of this.graphs){    
      for(let element of graph.getCells() ){
        if (reqName == element.attr('root').title){
          graph.removeCells([element])  
          console.log("delete req ",reqName," ",element) 
        }       
      }      
    }
  }

  //==================================Link=============================================
  changeRelatedLink(oldName,newName) {
    let i = this.project.problemDiagram.referenceList.length - 1;
    for (let reference of this.project.problemDiagram.referenceList) {
      if (reference.from == oldName) {        
        reference.from = newName;        
      }else if(reference.to == oldName){
        reference.to = newName;
      }
    }
    for (let constraint of this.project.problemDiagram.constraintList) {
      if (constraint.from == oldName) {
        constraint.from = newName;
      }else if(constraint.to == oldName){
        constraint.to = newName;
      }
    }
    for (let my_interface of this.project.contextDiagram.interfaceList) {
      if (my_interface.from == oldName) {
        my_interface.from = newName;
      }else if(my_interface.to == oldName){
        my_interface.to = newName;
      }
    }
  }
  Merge(source, target, graph) {
    let list0 = this.project.contextDiagram.interfaceList;
    let i0 = list0.length - 1;
    for (; i0 >= 0; i0--) {
      let item = list0[i0]; 
      if (item.from == target.attr('root').shortname) {
          list0.splice(i0, 1);
      }
      else if (item.to == target.attr('root').shortname) {
        list0.splice(i0, 1);
      }
    }

    let list1 = this.project.problemDiagram.constraintList;
    let i1 = list1.length - 1;
    for (; i1 >= 0; i1--) {
      let item = list1[i1];
      if (item.from == target.attr('root').shortname) {
        list1.splice(i1, 1);
      }
      else if (item.to == target.attr('root').shortname) {
        list1.splice(i1, 1);
      }
    }

    let list2 = this.project.problemDiagram.referenceList;
    let i2 = list2.length - 1;
    for (; i2 >= 0; i2--) {
      let item = list2[i2];
      if (item.from == target.attr('root').shortname) {
        list2.splice(i2, 1);
      }
      else if (item.to == target.attr('root').shortname) {
        list2.splice(i2, 1);
      }
    }

    for (let item of this.project.contextDiagram.interfaceList) {
      if (item.from == source.attr('root').shortname) {
        item.from = source.attr('root').shortname + target.attr('root').shortname
      }
      else if (item.to == source.attr('root').shortname) {
        item.to = source.attr('root').shortname + target.attr('root').shortname
      }
    }
    for (let item of this.project.problemDiagram.constraintList) {
      if (item.from == source.attr('root').shortname) {
        item.from = source.attr('root').shortname + target.attr('root').shortname
      }
      else if (item.to == source.attr('root').shortname) {
        item.to = source.attr('root').shortname + target.attr('root').shortname
      }
    }
    for (let item of this.project.problemDiagram.referenceList) {
      if (item.from == source.attr('root').shortname) {
        item.from = source.attr('root').shortname + target.attr('root').shortname
      }
      else if (item.to == source.attr('root').shortname) {
        item.to = source.attr('root').shortname + target.attr('root').shortname
      }
    }
    let list = this.project.contextDiagram.problemDomainList;
    for (let item of this.project.contextDiagram.problemDomainList) {
      if (item.name == source.attr('root').title) {
        let j = 0;
        for (let item2 of this.project.contextDiagram.problemDomainList) {
          if (item2.name == target.attr('root').title) {
            item.name = item.name + "&" + item2.name;
            item.shortname = item.shortname + item2.shortname;
            for (var i = 0; i < item2.phes.length; i++) {
              item.phes.push(item2.phes[i]);
            }
            graph.removeCells(target);
            source.attr('root').title = item.name;
            source.attr('label').text = item.name + '\n(' + item.shortname + ')';
            list.splice(j, 1);
            this.drawDiagram(this.project);
          }
          j++;
        }

      }
    }
  }
  drawLink(source, target, graph) {
    console.log('drawLink')    
    if (this.component_choice_service.interface == true) {
      console.log('this.component_choice_service.interface == true')
      this.drawInterface(source, target, graph);
      this.component_choice_service.interface = false;
    } else if (this.component_choice_service.reference == true) {
      console.log('this.component_choice_service.reference == true')
      this.drawReference(source, target, graph);
      this.component_choice_service.reference = false;
    } else if (this.component_choice_service.constraint == true) {
      console.log('this.component_choice_service.constraint == true')
      this.drawConstraint(source, target, graph);
      this.component_choice_service.constraint = false;
    }
    this.projectService.sendProject(this.project);
    
  }
  changeLinkPosition(project){
    for (let reference of project.problemDiagram.referenceList ) {
      let pos = this.changeLinkPosition1(reference.from,reference.to,project);
      reference.x1 = pos[0];
      reference.x2 = pos[1];
      reference.y1 = pos[2];
      reference.y2 = pos[3];
    }
    for (let constraint of project.problemDiagram.constraintList) {
      let pos = this.changeLinkPosition1(constraint.from,constraint.to,project);
      constraint.x1 = pos[0];
      constraint.x2 = pos[1];
      constraint.y1 = pos[2];
      constraint.y2 = pos[3];
    }
   for (let my_interface of project.contextDiagram.interfaceList) {
      let pos = this.changeLinkPosition1(my_interface.from,my_interface.to,project);
      my_interface.x1 = pos[0];
      my_interface.x2 = pos[1];
      my_interface.y1 = pos[2];
      my_interface.y2 = pos[3];
    }
  }
  changeLinkPosition1(from,to,project){
    let x1,x2,y1,y2;
      if(from==project.contextDiagram.machine.shortname){
        x1 = this.project.contextDiagram.machine.x;
        y1 = this.project.contextDiagram.machine.y;
      }else if(to==this.project.contextDiagram.machine.shortname){
        x2 = this.project.contextDiagram.machine.x;
        y2 = this.project.contextDiagram.machine.y;
      }

    for(let ele of project.contextDiagram.problemDomainList){
        if(ele.shortname==from){
          x1 = ele.x;
          y1 = ele.y;          
        }else if(ele.shortname==to){
          x2 = ele.x;
          y2 = ele.y;          
        }
      }

    for(let ele of project.problemDiagram.requirementList){
        if(ele.name==from){
          x1 = ele.x;
          y1 = ele.y;          
        }else if(ele.name==to){
          x2 = ele.x;
          y2 = ele.y;          
        }
      }
      return [x1,x2,y1,y2];
    
  }
  deleteRelatedLinkOnGraph(name, graph) {
    //console.log('deleteRelatedLinkOnGraph');
    let links = graph.getLinks();
    for (let link of links) {
      if (link.attr('root').title == name) {
        graph.removeCells([link]);
      }
    }
  }
  //====================================Interface=======================================
  drawInterfaces(interfaceList: Interface[], elementList, graph: joint.dia.Graph) {
    var elefrom: joint.dia.Element;
    var eleto: joint.dia.Element;
    for (var i = 0; i < interfaceList.length; i++) {
      const int = interfaceList[i];
      const from = int.from;
      const to = int.to;
      for (var j = 0; j < elementList.length; j++) {
        if (from == elementList[j].attr('root').shortname) {
          elefrom = elementList[j];
          // console.log('elefrom');
          // console.log(elefrom);
          // console.log(from);
        }
        else if (to === elementList[j].attr('root').shortname) {
          eleto = elementList[j];
          // console.log('eleto');
          // console.log(eleto);
          // console.log(to);
        }
      }
      this.drawInterface1(int, elefrom, eleto, graph);
    } 
  }
  drawInterface(source, target, graph) {
    //console.log('drawInterface');
    //console.log(source);
    //console.log(target);    
    let no
    while(true){
      no = this.interface_no;
      this.interface_no += 1;
      let conflicting_no = false;
      for(let inti of this.project.contextDiagram.interfaceList){
        if (inti.no==no){
          conflicting_no = true
        }
      }
      if(!conflicting_no){
        break;
      }
    }  
    let name = this.getname(); 
    let from = source.attr('root').shortname;
    let to = target.attr('root').shortname;
    // let myinterface = this.addInterfaceEntity(no, name, name + '?', from, to, [], 0, 0, 0, 0);
    // let element = this.drawInterface1(myinterface, source, target, graph)
    let myinterface = Interface.newInterface(no, name, name + '?', from, to, [], 0, 0, 0, 0);    
    this.sendChangeShapeMessage("add", "int", null, myinterface)
    return myinterface
  }
  drawInterfacews(int1:Interface) {
    let int = Interface.copyInterface(int1)
    this.project.addInterface(int)  
    this.drawInterfaceOnGraph(int)
  }
  drawInterfaceOnGraph(int){
    let from = int.from
    let to = int.to
    for(let graph of this.graphs){
      var elefrom: joint.dia.Element
      var eleto: joint.dia.Element
      let elementList = graph.getElements() 
      for (var j = 0; j < elementList.length; j++) {        
        if (from == elementList[j].attr('root').shortname) {
          elefrom = elementList[j];
          console.log('elefrom');
          console.log(elefrom);
          console.log(from);
        }
        else if (to === elementList[j].attr('root').shortname) {
          eleto = elementList[j];
          console.log('eleto');
          console.log(eleto);
          console.log(to);
        }
      }
      this.drawInterface1(int, elefrom, eleto, graph)
    }
  }
  addInterfaceEntity(no, name, description, from, to, phe, x1, y1, x2, y2) {
    let myinterface = new Interface();
    myinterface.no = no;
    myinterface.name = name;
    myinterface.from = from;
    myinterface.to = to;
    myinterface.x1 = x1;
    myinterface.y1 = y1;
    myinterface.x2 = x2;
    myinterface.y2 = y2;
    myinterface.phenomenonList = phe;
    myinterface.description = description;
    this.project.contextDiagram.interfaceList.push(myinterface);
    //console.log('addInterfaceEntity:');
    //console.log(this.project.contextDiagram.interfaceList);
    return myinterface;
  }
  //在图上画接口
  drawInterface1(int, source, target, graph) {
    var link = new joint.shapes.standard.Link({
      source: { id: source.id },
      target: { id: target.id }
    });
    link.attr({
      root: { 
        name: 'interface', 
        title: int.name,
        no:int.no
    },
      line: {
        strokeWidth: 1,
        targetMarker: {
          'fill': 'none',
          'stroke': 'none',
        }
      },
    });
    link.appendLabel({
      attrs: {
        text: {
          text: int.name,
          fontSize: 25,
          textAnchor: 'middle',	//???????
          textVerticalAnchor: 'middle',
          background: 'none'
        }
      }
    });
    link.addTo(graph);
    
    //console.log('drawInterface');
    return link;
  }
  initInterfacePopBox() {
    //console.log(this.selectedElement);
    for (let item of this.project.contextDiagram.interfaceList) {
      if (item.no == this.selectedElement.attr('root').no) {
        this.interface = item;
        this.phenomenonList = item.phenomenonList;
        this.selectedLinkSource = item.from;
        this.selectedLinkTarget = item.to;
        break;
      }
    }
    let selectedDiv = document.getElementById('interfacePopBox');
    (selectedDiv.getElementsByClassName("name")[0] as any).value='';
    let source = this.getProblemEntityByShortName(this.selectedLinkSource)
    let target = this.getProblemEntityByShortName(this.selectedLinkTarget)
    this.selectPhes = []
    if(source != null && source.phes!=undefined){
      for(let phe of source.phes)
        this.selectPhes.push(phe)
    }
    if(target!=null && target.phes!=undefined){
      for(let phe of target.phes)
        this.selectPhes.push(phe)
    } 
    
  }
  getProblemEntityByShortName(shortname){    
    for(let pro of this.project.contextDiagram.problemDomainList){
      if(pro.shortname==shortname)
        return pro; 
    }
    console.log(shortname,this.project.contextDiagram.problemDomainList)
    return null;
  }
  changeInterfaceDetail() {
    let int = Interface.newInterfaceWithOld(this.interface, this.phenomenonList, this.project.getDescription(this.interface.name, this.phenomenonList))
    this.sendChangeShapeMessage("change", "int", this.interface, int)
    //console.log('changeInterfaceDetail');
    //console.log(this.project.contextDiagram.interfaceList);
  }
  changeInterfacews(old:Interface,new1:Interface) {        
    old = Interface.copyInterface(old)  
    new1 = Interface.copyInterface(new1)
    this.project.changeInterface(old, new1)    
    this.projectService.sendProject(this.project)
    return true;
  }
  //向服务器发送删除接口的信息
  deleteInterface(graph) {
    //console.log('deleteInterface');
    let no = this.selectedElement.attr('root').no;
    let list = this.project.contextDiagram.interfaceList;
    let i = 0
    let inter
    for (let item of list) {
      if (item.no == no) {
        inter = item
        // list.splice(i, 1);
        break;
      }
      i++;
    }

    // graph.removeCells([this.selectedElement])
    this.sendChangeShapeMessage( "delete", "int", inter,null)
    // this.projectService.sendProject(this.project);
  }
  deleteInterfacews(int:Interface) {
    int = Interface.copyInterface(int)
    this.project.deleteInterface(int)
    this.projectService.sendProject(this.project)
    this.deleteInterfaceOnGraph(int.getName())
  }
  deleteInterfaceOnGraph(name){
    for(let graph of this.graphs){
      for(let link of graph.getLinks()){
        if(link.attr('root').title==name){
          graph.removeCells([link])
          break
        }
      }
    }
  }
  //=====================================Reference=================================
  drawReferences(referenceList: Reference[], elementList, reqEleList, graph: joint.dia.Graph) {
    var link;
    var elefrom: joint.dia.Element;
    var eleto: joint.dia.Element;
    for (var i = 0; i < referenceList.length; i++) {
      const reference = referenceList[i];
      const from = reference.from;
      const to = reference.to;
      for (var j = 0; j < elementList.length; j++) {
        if (from === elementList[j].attr('root').shortname) {
          elefrom = elementList[j];
        }
        else if (to === elementList[j].attr('root').shortname) {
          eleto = elementList[j];
        }
      }
      for (var j = 0; j < reqEleList.length; j++) {
        if (from === reqEleList[j].attr('label').text) {
          elefrom = reqEleList[j];
        }
        else if (to === reqEleList[j].attr('label').text) {
          eleto = reqEleList[j];
        }
      }
      this.drawReference1(reference,elefrom,eleto,graph);
    }
  }
  drawReferencews(ref) {
    ref = Reference.copyReference(ref)
    this.project.addReference(ref)
    this.projectService.sendProject(this.project)
    this.drawReferenceOnGraph(ref)     
  }
  drawReferenceOnGraph(ref){
    const from = ref.from
    const to = ref.to    
    var elefrom: joint.dia.Element
    var eleto: joint.dia.Element
    let elementList = this.graphs[1].getElements()
    
    console.log("elementList.length=",elementList.length)
    console.log("elementList.length=",elementList.length)
    for (var j = 0; j < elementList.length; j++) {
      if (from == elementList[j].attr('root').shortname) {
        elefrom = elementList[j];
        console.log('elefrom');
        console.log(elefrom);
        console.log(from);
      }
      else if (to === elementList[j].attr('root').shortname) {
        eleto = elementList[j];
        console.log('eleto');
        console.log(eleto);
        console.log(to);
      }
      console.log(elementList[j].attr('root').shortname)
    }
    this.drawReference1(ref, elefrom, eleto, this.graphs[1])

  }
  drawReference(source, target, graph) {
    let no
    while(true){
      no = this.reference_no;
      this.reference_no += 1;
      let conflicting_no = false;
      for(let refi of this.project.problemDiagram.referenceList){
        if (refi.name==no){
          conflicting_no = true
        }
      }
      if(!conflicting_no){
        break;
      }
    }    
    let name = this.getname();  
    let from = source.attr('root').shortname;
    let to = target.attr('root').shortname;
    let ref = Reference.newReference(this.reference_no, name, '', from, to, [], 0, 0, 0, 0)
    this.sendChangeShapeMessage( "add", "ref", null, ref)
    // return element;

  }
  getReqNo(req_context){
    for (const req of this.project.problemDiagram.requirementList) {
      if (req.name==req_context) {
        return req.no;        
      }      
    }
    return -1;
  }
  addReferenceEntity(no, name, description, from, to, phe, x1, y1, x2, y2) {
    //console.log('addReferenceEntity');
    let reference = new Reference();
    reference.no = no;
    reference.name = name;
    reference.description = description;
    reference.from = from;
    reference.to = to;
    reference.phenomenonList = phe;
    reference.x1 = x1;
    reference.y1 = y1;
    reference.x2 = x2;
    reference.y2 = y2;
    this.project.problemDiagram.referenceList.push(reference);
    return reference;
  }
  drawReference1(reference, source, target, graph) {
    var link = new joint.shapes.standard.Link({
      source: { id: source.id },
      target: { id: target.id }
    });

    link.attr({
      root: {
        name: 'reference',
        title: reference.name,
        no: reference.no
      },
      line: {
        strokeWidth: 1,
        targetMarker: {
          'fill': 'none',
          'stroke': 'none',
        },
        strokeDasharray: '8,4'
      },

    });
    link.appendLabel({
      attrs: {
        text: {
          text: reference.name,
          fontSize: 25,
          textAnchor: 'middle',	//???????
          textVerticalAnchor: 'middle',
          background: 'none'
        }
      }
    });
    link.addTo(graph);
    //console.log('drawReference');
    return link;
  }
  initReferencePopBox() {
    console.log(11111)
    for (let item of this.project.problemDiagram.referenceList) {
      if (item.name == this.selectedElement.attr('root').title) {
        this.reference = item;
        this.phenomenonList = item.phenomenonList;   
        console.log(item)     
        break;
      }
    }
 

    //initiator and receiverList
    this.receiverList = []  //link to initiator
    this.initiator_receiverList =[] //phe initiator list
    this.initiator_or_receiverList = [] //receiverList change according to initiator

    //get initiator
    for (let temp of this.project.contextDiagram.problemDomainList) {
      if(temp.shortname==this.reference.from
        || temp.shortname==this.reference.to){
              //get initiator
              this.initiator = temp.shortname
              this.initiator_receiverList.push(temp.shortname)
        }      
    }

    //get receiver list
    for (let temp_int of this.project.contextDiagram.interfaceList) {
      if (temp_int.from == this.initiator) {
        this.receiverList.push(temp_int.to)
        this.initiator_receiverList.push(temp_int.to)
        this.initiator_or_receiverList.push(temp_int.to)
        //console.log('--------------')
        //console.log('this.receiverList')
        //console.log(this.receiverList)
        //console.log('this.initiator_receiverList')
        //console.log(this.initiator_receiverList)
        //console.log('this.initiator_or_receiverList')
        //console.log(this.initiator_or_receiverList)
      }
      else if (temp_int.to == this.initiator) {
        this.receiverList.push(temp_int.from)
        this.initiator_receiverList.push(temp_int.from)          
        this.initiator_or_receiverList.push(temp_int.from)  
        //console.log('--------------')
        //console.log('this.receiverList')
        //console.log(this.receiverList)
        //console.log('this.initiator_receiverList')
        //console.log(this.initiator_receiverList)
        //console.log('this.initiator_or_receiverList')
        //console.log(this.initiator_or_receiverList)        
      }      
    }

    //from interface and ontology,if a phe exist in both interface and ontology,it will appear only once
    this.interface_ontologyPhes = [] 
    //form interface
    this.interfacePhes = []  
    //from ontology
    this.ontologyPhes = []

    //get reference phenomenon list according problemdomain short name
    this.interfacePhes = this.getRefPheList(this.initiator)   

    //get reference phenomenon list according to ontology      
    let pro = this.getProblemEntityByShortName(this.initiator)
    this.ontologyPhes = pro.phes;
    if(this.ontologyPhes==undefined)
      this.ontologyPhes=[]
    //console.log(this.ontologyPhes)

    //get reference phenomenon list from both interface & ontology
    //if a phe exist in both interface and ontology,it will appear only once
    this.interface_ontologyPhes  = this.getRefPheList(this.initiator) 
    for (let temp_envphe of this.ontologyPhes) {
      if(!this.exist(temp_envphe,this.interface_ontologyPhes)){
        this.interface_ontologyPhes.push(temp_envphe);
      }          
    }    
    //phe name and initiator
    let selectedDiv = document.getElementById('referencePopBox') as any
    (selectedDiv.getElementsByClassName("name")[0] as any).value='';
    console.log(this.phenomenonList);
   
    setTimeout(
      function(){    
        ($("#referencePopBox .initiator").get(0) as any).selectedIndex=  0       
      },
      15)
    
    // $("#referencePopBox .initiator").find("option:contains('xx')").prop("selected",true);
    
  }
  exist(phe,pheList){
    for(let temp of pheList){
      if (phe.name==temp.name){
        return true
      }
    }
    return false
  }
  getRelatedPhes(proName,phes,unRelatedPhes){
    for (let item of this.project.contextDiagram.interfaceList) {      
        this.getRelatedPhes1(proName,item.phenomenonList,phes,unRelatedPhes)  
    }
    for (let item of this.project.problemDiagram.constraintList) {
      this.getRelatedPhes1(proName,item.phenomenonList,phes,unRelatedPhes)
    }
    for (let item of this.project.problemDiagram.referenceList) {
      this.getRelatedPhes1(proName,item.phenomenonList,phes,unRelatedPhes)
    }
  }
  getRelatedPhes1(name,pheList,phes,unRelatedPhes){
    for(let item of pheList){
      if(name == item.from || name == item.to){
        phes.push(item)
      }else{
        unRelatedPhes.push(item)
      }
    }
  }
  changereceiver(){
    let selectedDiv = document.getElementById(this.selectedType + 'PopBox');
    let selectElement = selectedDiv.getElementsByClassName("initiator")[0];
    let selectedIndex = (selectElement as any).selectedIndex;
    let initiator = this.initiator_receiverList[selectedIndex]
    //console.log(initiator)
    this.initiator_or_receiverList=[]
    if(this.initiator==initiator){
      for(let rec of this.receiverList){
        this.initiator_or_receiverList.push(rec)
      }
    }else{      
      this.initiator_or_receiverList.push(this.initiator)      
    }
    //console.log(this.initiator_or_receiverList)
  }
  changeReferenceDetail() {
    let ref = Reference.newReferenceWithOld(this.reference, this.phenomenonList, this.project.getDescription(this.reference.name, this.phenomenonList))
    this.sendChangeShapeMessage("change", "ref", this.reference, ref)
  }
  changeReferencews(old:Reference,new1:Reference) {
    old = Reference.copyReference(old)
    new1 = Reference.copyReference(new1)
    this.project.changeReference(old, new1)    
    this.projectService.sendProject(this.project)
    return true;
  }
  deleteReference(graph) {
    let name = this.selectedElement.attr('root').title;
    let list = this.project.problemDiagram.referenceList;
    let i = 0
    let ref
    for (let item of list) {
      if (item.name == name) {
        ref = item
        // list.splice(i, 1);
        break;
      }
      i++;
    }
    // graph.removeCells([this.selectedElement])

    this.sendChangeShapeMessage("delete", "ref", ref, null)
    // this.projectService.sendProject(this.project);
  }
  deleteReferencews(ref:Reference) {
    ref = Reference.copyReference(ref)
    this.project.deleteReference(ref)  
    this.projectService.sendProject(this.project)
    this.deleteReferenceOnGraph(ref.getName())  
  }
  deleteReferenceOnGraph(refName:string){
    for(let graph of this.graphs){
      for(let link of graph.getLinks()){
        if(link.attr('root').title==refName)
        graph.removeCells([link])
      }
    }
  }
  getname():string{  
    while(true){
      let name = this.getlink_name((this.link_name_no));      
      this.link_name_no += 1;
      let conflicting_name = false;
      for(let inti of this.project.contextDiagram.interfaceList){
        if (inti.name==name){
          conflicting_name = true
        }
      }
      for(let refi of this.project.problemDiagram.referenceList){
        if (refi.name==name){
          conflicting_name = true
        }
      }
      for(let coni of this.project.problemDiagram.constraintList){
        if (coni.name==name){
          conflicting_name = true
        }
      }
      if(!conflicting_name){
        return name
      }
    }    
}
  //===========================================Constraint==========================================
  drawConstraints(constraintList: Constraint[], elementList, reqEleList, graph: joint.dia.Graph) {
    var link;
    var elefrom: joint.dia.Element;
    var eleto: joint.dia.Element;
    for (var i = 0; i < constraintList.length; i++) {
      const constraint = constraintList[i];
      const from = constraint.from;
      const to = constraint.to;
      for (var j = 0; j < elementList.length; j++) {
        if (from === elementList[j].attr('root').shortname) {
          elefrom = elementList[j];
        }
        else if (to === elementList[j].attr('root').shortname) {
          eleto = elementList[j];
        }
      }
      for (var j = 0; j < reqEleList.length; j++) {
        if (from === reqEleList[j].attr('label').text) {
          elefrom = reqEleList[j];
        }
        else if (to === reqEleList[j].attr('label').text) {
          eleto = reqEleList[j];
        }
      }
      if (elefrom.attr('root').name != 'requirement') {
        let temp = elefrom;
        elefrom = eleto;
        eleto = temp;         
      }
      this.drawConstraint1(constraint,elefrom,eleto,graph);
    }
  }
  drawConstraint(source, target, graph) {    
    let name = this.getname();
    let no
    while(true){
      no = this.constraint_no;
      this.constraint_no += 1;
      let conflicting_no = false;
      for(let coni of this.project.problemDiagram.constraintList){
        if (coni.no==no){
          conflicting_no = true
        }
      }
      if(!conflicting_no){
        break;
      }
    }   
    let req:string;
    //确保箭头（target）放在domain这一侧
    if (source.attr('root').name != 'requirement') {
      let temp = source;
      source = target;
      target = temp;     
    }
    let to = target.attr('root').shortname;
    let from = source.attr('root').shortname
    let con = Constraint.newConstraint(no, name, '', from, to, [], 0, 0, 0, 0)
    this.sendChangeShapeMessage("add", "con", null, con)

  }
  drawConstraintws(con) {
    console.log(con)
    con = Constraint.copyConstraint(con)
    this.project.addConstraint(con)
    this.projectService.sendProject(this.project)
    this.drawConstraintOnGraph(con)
  }
  drawConstraintOnGraph(constraint){
    const from = constraint.from;
    const to = constraint.to;
    var elefrom: joint.dia.Element
    var eleto: joint.dia.Element
    let elementList = this.graphs[1].getElements() 
    console.log(elementList.length)
    for (var j = 0; j < elementList.length; j++) {
      if (from == elementList[j].attr('root').shortname) {
        elefrom = elementList[j];
        console.log('elefrom');
        console.log(elefrom);
        console.log(from);
      }
      else if (to === elementList[j].attr('root').shortname) {
        eleto = elementList[j];
        console.log('eleto');
        console.log(eleto);
        console.log(to);
      }else{
        console.log(from,"  ",to,"  ",elementList[j].attr('root').shortname)
      }
    }
    this.drawConstraint1(constraint, elefrom, eleto, this.graphs[1])
     
  }
  drawConstraint1(con, source, target, graph) {
    var link = new joint.shapes.standard.Link({
      source: { id: source.id },
      target: { id: target.id }
    });
    link.attr({
      root: { 
        name: 'constraint', 
        title: con.name,
        no: con.no

      },
      line: { strokeWidth: 1, strokeDasharray: '8,4' }
    });
    link.appendLabel({
      attrs: {
        text: {
          text: con.name,
          fontSize: 25,
          textAnchor: 'middle',	//???????
          textVerticalAnchor: 'middle',
          background: 'none'
        }
      }
    });
    link.addTo(graph);
    //console.log('drawConstraint');
    return link;
  }
  initConstraintPopBox() {
    for (let item of this.project.problemDiagram.constraintList) {
      if (item.name == this.selectedElement.attr('root').title) {
        this.constraint = item;
        this.phenomenonList = item.phenomenonList;        
        break;
      }
    }
 
    //phe name 
    let selectedDiv = document.getElementById('constraintPopBox');
    (selectedDiv.getElementsByClassName("name")[0] as any).value='';

    //initiator and receiverList
    this.receiverList = []  //link to initiator
    this.initiator_receiverList =[] //phe initiator list
    this.initiator_or_receiverList = [] //receiverList change according to initiator

    //get initiator
    for (let temp of this.project.contextDiagram.problemDomainList) {
      if(temp.shortname==this.constraint.from
        || temp.shortname==this.constraint.to){
              //get initiator
              this.initiator = temp.shortname
              this.initiator_receiverList.push(temp.shortname)
        }      
    }

    //get receiver list
    for (let temp_int of this.project.contextDiagram.interfaceList) {
      if (temp_int.from == this.initiator) {
        this.receiverList.push(temp_int.to)
        this.initiator_receiverList.push(temp_int.to)
        this.initiator_or_receiverList.push(temp_int.to)
        //console.log('--------------')
        //console.log('this.receiverList')
        //console.log(this.receiverList)
        //console.log('this.initiator_receiverList')
        //console.log(this.initiator_receiverList)
        //console.log('this.initiator_or_receiverList')
        //console.log(this.initiator_or_receiverList)
      }
      else if (temp_int.to == this.initiator) {
        this.receiverList.push(temp_int.from)
        this.initiator_receiverList.push(temp_int.from)          
        this.initiator_or_receiverList.push(temp_int.from)  
        //console.log('--------------')
        //console.log('this.receiverList')
        //console.log(this.receiverList)
        //console.log('this.initiator_receiverList')
        //console.log(this.initiator_receiverList)
        //console.log('this.initiator_or_receiverList')
        //console.log(this.initiator_or_receiverList)        
      }      
    }

    //from interface and ontology,if a phe exist in both interface and ontology,it will appear only once
    this.interface_ontologyPhes = [] 
    //form interface
    this.interfacePhes = []  
    //from ontology
    this.ontologyPhes = []

    //get reference phenomenon list according problemdomain short name
    this.interfacePhes = this.getRefPheList(this.initiator)   

    //get reference phenomenon list according to ontology      
    let pro = this.getProblemEntityByShortName(this.initiator)
    this.ontologyPhes = pro.phes;

    //get reference phenomenon list from both interface & ontology
    //if a phe exist in both interface and ontology,it will appear only once
    this.interface_ontologyPhes  = this.getRefPheList(this.initiator) 
    if(this.ontologyPhes!=null){
      for (let temp_envphe of this.ontologyPhes) {
        if(!this.exist(temp_envphe,this.interface_ontologyPhes)){
          this.interface_ontologyPhes.push(temp_envphe);
        }          
      }
    }

    //initiator
    setTimeout(
      function(){    
        // console.log('设置initiator选中项为0');
        ($("#constraintPopBox .initiator").get(0) as any).selectedIndex=  0  
        // console.log('获取initiator选中项')
        // console.log($("#referencePopBox .initiator").val())         
      },
      15)
        
  }
  changeConstraintDetail() {
    let con = Constraint.newConstraintWithOld(this.constraint, this.phenomenonList, this.project.getDescription(this.constraint.name, this.phenomenonList))
    console.log(con)
    this.sendChangeShapeMessage("change", "con", this.constraint, con)
  }
  changeConstraintws(old:Constraint,new1:Constraint) {
    this.project.changeConstraint(old, new1)    
    this.projectService.sendProject(this.project)
    return true;
  }

  deleteConstraint(graph) {
    let name = this.selectedElement.attr('root').title;
    let list = this.project.problemDiagram.constraintList;
    let i = 0;
    let con
    for (let item of list) {
      if (item.name == name) {
        con = item
        // list.splice(i, 1);
        break;
      }
      i++;
    }
    // graph.removeCells([this.selectedElement])    
    this.sendChangeShapeMessage( "delete", "con", con, null)
    // this.projectService.sendProject(this.project)
  }
  deleteConstraintws(con:Constraint) {
    con = Constraint.copyConstraint(con)
    this.project.deleteConstraint(con)    
    this.projectService.sendProject(this.project)
    this.deleteConstraintOnGraph(con.getName())
  }
  deleteConstraintOnGraph(conName){
    for(let graph of this.graphs){
      for(let link of graph.getLinks()){
        if(link.attr('root').title==conName)
        graph.removeCells([link])
      }
    }
  }
  
  //====================================phenomenon=====================================
  //get phenomenon list of interface
  getPhenomenonList(shortname) {
    for (let int of this.project.contextDiagram.interfaceList) {
      if (int.from == shortname || int.to == shortname) {
        return int.phenomenonList;
      }
    }
    return null;
  }
  //get phenomenon list of link according to problem shortname
  getRefPheList(shortname) {
    let res =[];
    for (let int of this.project.contextDiagram.interfaceList) {
      if (int.from == shortname || int.to == shortname) {
        for (var i=0; i < int.phenomenonList.length; i++) {
          res.push(int.phenomenonList[i]);
        }
      }
    }
    for (let item of this.project.problemDiagram.referenceList) {
      if (item.from == shortname || item.to == shortname) {
        for (var i=0; i < item.phenomenonList.length; i++) {
          res.push(item.phenomenonList[i]);
        }
      }
    }
    for (let item of this.project.problemDiagram.constraintList) {
      if (item.from == shortname || item.to == shortname) {
        for (var i=0; i < item.phenomenonList.length; i++) {
          res.push(item.phenomenonList[i]);
        }
      }
    }
    for  ( let  i  =   0 ; i  <  res.length; i ++ )  {       
			for  ( let  j  =  res.length  -   1 ; j  >  i; j -- )  {       
				if  (res[i].no == res[j].no)  {       
				res.splice(j,1);       
				}        
			}        
		}
    //console.log(res);
    return res;
  }

  //addPhenomenon
  addPhenomenon() {
    if (this.selectedType == 'interface') {
      this.addInterfacePhenomenon();
    } else if (this.selectedType == 'reference') {
      this.addReferencePhenomenon();
    } else if (this.selectedType == 'constraint') {
      this.addConstraintPhenomenon();
    }
    this.projectService.sendProject(this.project);
    //console.log(this.project)
  }
  addInterfacePhenomenon() {
    let phenomenon = new Phenomenon();
    this.changePhenomenon(phenomenon);
    this.sendChangePhenomenonMessage("add","int",this.interface,null,phenomenon)  
  }
  addReferencePhenomenon() {
    let phenomenon = new RequirementPhenomenon();

    //phenomenon_requirement
    let source = this.getElementById(this.selectedElement.source().id);
    let target = this.getElementById(this.selectedElement.target().id);
    if(source != null) this.rcSource = source;
    else source = this.rcSource;
    if(target != null) this.rcTarget = target;
    else target = this.rcTarget;
 
    let reqno;
    if (source.attr('root').name == 'requirement') {      
      reqno=this.getReqNo(source.attr('root').title);
    } else {
      reqno=this.getReqNo(target.attr('root').title);
    }   
    phenomenon.requirement = reqno;
    this.changePhenomenon1(phenomenon);
    this.sendChangePhenomenonMessage("add","ref",this.reference,null,phenomenon) 
    // const that = this;
    //  setTimeout(function () {
    //   that.changePhenomenon1(phenomenon);
    //  }, 100);
  }
  addConstraintPhenomenon() {
    let phenomenon = new RequirementPhenomenon();
    
      //phenomenon_requirement
      let source = this.getElementById(this.selectedElement.source().id);
      let target = this.getElementById(this.selectedElement.target().id);
      if(source != null) this.rcSource = source;
      else source = this.rcSource;
      if(target != null) this.rcTarget = target;
      else target = this.rcTarget;
      
      let reqno;
      if (source.attr('root').name == 'requirement') {      
        reqno=this.getReqNo(source.attr('root').title);
      } else {
        reqno=this.getReqNo(target.attr('root').title);
      }   
      phenomenon.requirement = reqno;  
    this.description = this.project.getDescription(this.selectedElement.attr('root').title, this.constraintPhenomenonList)
    this.changePhenomenon1(phenomenon);
    this.sendChangePhenomenonMessage("add","phe",this.constraint,null,phenomenon) 
   
    // const that = this;
    //  setTimeout(function () {
    //   that.changePhenomenon1(phenomenon);
    //  }, 100);
  }
  changePhenomenon(phenomenon) {
    let selectedDiv = document.getElementById(this.selectedType + 'PopBox');
    //name
    let phenomenonName = (selectedDiv.getElementsByClassName("name")[0] as any).value;    
    for (let existPhenomenon of this.phenomenonList) {
      if (existPhenomenon.name == phenomenonName) {
        alert(phenomenonName + 'already exists');
        return;
      }
    }
    phenomenon.name = phenomenonName;
    let flag = false;
    if(this.ontologyPhes != undefined){
      for(let phe of this.ontologyPhes){
        if(phe.name==phenomenonName){
          //type        
          phenomenon.type = phe.type
          flag = true;
          break;
        }
      } 
    }
    
    if(!flag){           
      //type
      let selectElement = selectedDiv.getElementsByClassName("phenomenonType")[0];
      let selectedIndex = (selectElement as any).selectedIndex;
      let phenomenonType = (selectElement as any).options[selectedIndex].text;
      phenomenon.type = phenomenonType;
    }
    //no
    phenomenon.no = this.phenomenon_no
    this.phenomenon_no += 1

    //from & to
    let selectElement = selectedDiv.getElementsByClassName("initiator")[0];
    let selectedIndex = (selectElement as any).selectedIndex;
    let initiator = (selectElement as any).options[selectedIndex].text;
    let receiver = (selectElement as any).options[1 - selectedIndex].text;
    phenomenon.to = receiver;    
    phenomenon.from = initiator;
    this.phenomenonList.push(phenomenon);

  }
  changePhenomenon1(phenomenon) {
    //get message from popBox
    let selectedDiv = document.getElementById(this.selectedType + 'PopBox');
    //name
    let phenomenonName = (selectedDiv.getElementsByClassName("name")[0] as any).value;  
    //type
    let selectElement = selectedDiv.getElementsByClassName("phenomenonType")[0];
    let selectedIndex = (selectElement as any).selectedIndex;
    let phenomenonType = (selectElement as any).options[selectedIndex].text;
    //from
    selectElement = selectedDiv.getElementsByClassName("initiator")[0];
    selectedIndex = (selectElement as any).selectedIndex;
    let initiator = (selectElement as any).options[selectedIndex].text;
    //to
    selectElement = selectedDiv.getElementsByClassName("receiver")[0];
    selectedIndex = (selectElement as any).selectedIndex;
    let receiver = (selectElement as any).options[selectedIndex].text;
    //constraint
    let checkbox = selectedDiv.getElementsByClassName("checkbox")[0] as any
    let constraint = checkbox.checked
    
    
    let relatedPhes =[]
    let unRelatedPhes = []
    this.getRelatedPhes(this.initiator,relatedPhes,unRelatedPhes)
    for (let existPhenomenon of this.phenomenonList) {
      if (existPhenomenon.name == phenomenonName) {
        alert(phenomenonName + 'already exists');
        return;
      }
    }
         

    //phe exist in related link
    let flag = false;
    for(let phe of relatedPhes){
      if(phe.name==phenomenonName){
        phenomenon.no = phe.no
        phenomenon.name = phe.name   
        phenomenon.from = phe.from     
        phenomenon.to = phe.to    
        phenomenon.type = phe.type
        phenomenon.constraint = phe.constraint
        flag = true
      }
    }

    //phe doesn't exist in related link
    if(!flag){
      phenomenon.no = this.phenomenon_no
      this.phenomenon_no += 1  
      phenomenon.name = phenomenonName
      phenomenon.from = initiator
      phenomenon.to = receiver    
      
      //type 
      flag = false;
      if(this.ontologyPhes != undefined){
        for(let phe of this.ontologyPhes){
          if(phe.name==phenomenonName){               
            phenomenon.type = phe.type
            flag = true;
            break;
          }
        } 
      }    
      if(!flag){        
        phenomenon.type = phenomenonType;
      }
    } 
    console.log('addphe: constraint=',constraint)     
    phenomenon.constraint = constraint
    this.phenomenonList.push(phenomenon)  
  }  

  //select phenomenon from phenomenon list
  pre = null;
  selectPhenomenon(sObject, no) {    
    $(sObject).attr("style", "BACKGROUND-COLOR: #e6f0fc");
    this.selectedPhenomenonNo=no
  }

  //从interface,reference,和constraint的现象列表中找
  getPheByName(pheName) {
    for (let int of this.project.contextDiagram.interfaceList) {      
        for (let phe of int.phenomenonList) {
          if(pheName==phe.name)
            return phe
        }      
    }
    for (let ref of this.project.problemDiagram.referenceList) {      
      for (let phe of ref.phenomenonList) {
        if(pheName==phe.name)
          return phe
      }    
    }
    for (let ref of this.project.problemDiagram.constraintList) {      
      for (let phe of ref.phenomenonList) {
        if(pheName==phe.name)
          return phe
      }    
    }
    return null       
  }

  //从备选列表选择
  selectPhe() {   
    let index
    if(this.selectedType=='reference')
      index = ($("#referencePopBox .phes").get(0) as any).selectedIndex
    else if(this.selectedType=="constraint")    
      index = ($("#referencePopBox .phes").get(0) as any).selectedIndex
    if(index==0) 
      return
    let phe = this.interface_ontologyPhes[index-1]    
    console.log(this.interface_ontologyPhes)
    console.log(index)
    console.log(phe)
    let phe_name = phe.name as any

    //name
    (document.getElementById('nameref') as any).value = phe_name
    
    //initiator
    let initiatorIndex=0
    for(let ini of this.initiator_receiverList){
      if(phe.from==ini){
        console.log("initiator i=",initiatorIndex)
        break
      }
      initiatorIndex++
    }    
    if(this.selectedType=='reference'){
      ($("#referencePopBox .initiator").get(0) as any).selectedIndex=  initiatorIndex;
    }else if(this.selectedType=='constraint'){
      ($("#constraintPopBox .initiator").get(0) as any).selectedIndex=  initiatorIndex;
    }

    //receiver
    this.changereceiver()
    let receiverIndex=0
    for(let rev of this.initiator_or_receiverList){
      if(phe.to==rev){
        console.log("receiver i=",receiverIndex)
        break
      }
      receiverIndex++
    }
    if(this.selectedType=='reference'){
      ($("#referencePopBox .receiver").get(0) as any).selectedIndex=  receiverIndex;
    }else if(this.selectedType=='constraint'){
      ($("#constraintPopBox .receiver").get(0) as any).selectedIndex=  receiverIndex;
    }


    //type
    let typeIndex=0
    for(let type of this.phenomenonTypes){
      if(type==phe.type){
        console.log('type i=',typeIndex)
        break
      }
      typeIndex++
    }

    if(this.selectedType=='reference'){   
      
      $("#referencePopBox .name").val(phe.name); 
      ($("#referencePopBox .phenomenonType").get(0) as any).selectedIndex=typeIndex  
    }else if(this.selectedType=='constraint'){ 
      
      $("#constraintPopBox .name").val(phe.name); 
      ($("#constraintPopBox .phenomenonType").get(0) as any).selectedIndex=typeIndex  
    }     
  }

  //deletePhenomenon
  deletePhenomenon() {
    if (this.selectedType == 'interface') {
      let phenomenon = this.getPhenomenonByNo(this.selectedPhenomenonNo);
      this.sendChangePhenomenonMessage("delete","int",this.interface,phenomenon,null) 
      const that = this;
      setTimeout(function () {
        let phenomenon = that.deletePhenomenon1();
      }, 100);
    }
    else if (this.selectedType == 'reference') {
      let phenomenon = this.getPhenomenonByNo(this.selectedPhenomenonNo);
      this.sendChangePhenomenonMessage("delete","ref",this.reference,phenomenon,null) 
      const that = this;
      setTimeout(function () {
        let phenomenon = that.deletePhenomenon1();
      }, 100);
    } 
    else if (this.selectedType == 'constraint') {
      let phenomenon = this.getPhenomenonByNo(this.selectedPhenomenonNo);
      this.sendChangePhenomenonMessage("delete","con",this.constraint,phenomenon,null) 
      const that = this;
      setTimeout(function () {
        let phenomenon = that.deletePhenomenon1();
      }, 100);
    }
  }
  deletePhenomenon1():Phenomenon {
    let phenomenon = this.deletePhenomenonByNo(this.selectedPhenomenonNo, this.phenomenonList)    
    this.projectService.sendProject(this.project);
    return phenomenon
  }
  deletePhenomenonByNo(phenomenonNo, phenomenonList):Phenomenon {
    let i = 0;
    for (let phenomenon of phenomenonList) {
      if (phenomenon.no == phenomenonNo) {
        phenomenonList.splice(i, 1);
        return phenomenon
      }
      i += 1;
    }
  }
  getPhenomenonByNo(phenomenonNo):Phenomenon{
    for(let phenomenon of this.phenomenonList){
      if(phenomenon.no === phenomenonNo) return phenomenon;
    }
  }

  getAllPhenomenon(){
    let res = new Array<Phenomenon>();
    for(let interfacee of this.project.contextDiagram.interfaceList){
      for(let phenomenon of interfacee.phenomenonList){
        res.push(phenomenon);
      }
    }
    for(let reference of this.project.problemDiagram.referenceList){
      for(let phenomenon of reference.phenomenonList){
        res.push(phenomenon);
      }
    }
    for(let constraint of this.project.problemDiagram.constraintList){
      for(let phenomenon of constraint.phenomenonList){
        res.push(phenomenon);
      }
    }
    for  ( let  i  =   0 ; i  <  res.length; i ++ )  {       
			for  ( let  j  =  res.length  -   1 ; j  >  i; j -- )  {       
				if  (res[i].no == res[j].no)  {       
				res.splice(j,1);       
				}        
			}        
		}
    return res;
  }

  getAllRequirementPhenomenon(){
    let res = new Array<Phenomenon>();
    for(let reference of this.project.problemDiagram.referenceList){
      for(let phenomenon of reference.phenomenonList){
        res.push(phenomenon);
      }
    }
    for(let constraint of this.project.problemDiagram.constraintList){
      for(let phenomenon of constraint.phenomenonList){
        res.push(phenomenon);
      }
    }
    for  ( let  i  =   0 ; i  <  res.length; i ++ )  {       
			for  ( let  j  =  res.length  -   1 ; j  >  i; j -- )  {       
				if  (res[i].no == res[j].no)  {       
				res.splice(j,1);       
				}        
			}        
		}
    return res;
  }
}