import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Observable, observable, Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { FileService } from '../service/file.service';
import { FileUploader } from 'ng2-file-upload';
import { ProjectService } from '../service/project.service';
import { Location } from '@angular/common';
import { DrawGraphService } from '../service/draw-graph.service';
declare var $: JQueryStatic;

import { ComponentChoiceService } from '../service/component-choice.service';
import { TextService } from '../service/text.service';
// import { UploadService } from '../service/upload.service';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit {

  @Output() openEvent = new EventEmitter<string[]>();
  @Output() openOwlEvent = new EventEmitter<string[]>();
  @Output() openPOwlEvent = new EventEmitter<string[]>();
  subscription: Subscription;
  projects: string[];
  owls: string[];
  pOwls: string[]
  interval;
  interval1
  constructor(
    private route: ActivatedRoute,
    private fileService: FileService,
    private projectService: ProjectService,
    private dg_service: DrawGraphService,
    private textService:TextService,
    private location: Location,
    public component_choice_service: ComponentChoiceService) {
      projectService.stepEmmited$.subscribe(
        step => { 
          this.step = step;
      })
      this.projectService.owlAddEmitted$.subscribe(
        owlAdd=>{
          this.owlAdd = owlAdd;
      })
      this.projectService.ontNameAddEmitted$.subscribe(
        ontName=>{
          this.powlName = ontName;
      })
      this.projectService.eOntNameAddEmitted$.subscribe(
        eOntName=>{
          this.eowlName = eOntName;
      })
  }
  ngOnInit() {
  }

  projectAddress: string;
/*  version: string;*/
  uploadFile(): void {    
    document.getElementById("xmlFile").click();
  }
  uploader: FileUploader = new FileUploader({
    method: "POST",
    itemAlias: "xmlFile",
    autoUpload: false,
  });
  // project;

  selectedFileOnChanged(event: any) {
    //console.log(event.target.files);
    var path = event.target.files[0].webkitRelativePath;
    this.projectAddress = path.split('/')[0];
    //console.log('即将调用upload!');
    this.upload();
    // this.addNewItem(this.projectAddress);
  }

  openProject() {
    //console.log("open");
    this.fileService.searchProject().subscribe(
      projects => {
        this.projects = projects
        // console.log(this.projects)
        this.openEvent.emit(projects)
      }
    )
  }

  openEOwl() {
    //console.log("open");
    this.fileService.searchOwl("eowl").subscribe(
      owls => {
        this.owls = owls
        console.log(this.owls)
        this.openOwlEvent.emit(owls)
      }
    )
  }

  openPOwl() {
    //console.log("open");
    this.fileService.searchOwl("powl").subscribe(
      pOwls => {
        this.pOwls = pOwls
        console.log(pOwls)
        this.openPOwlEvent.emit(pOwls)
      }
    )
  }
  newFile(): void {
    this.fileService.newProject(true);
    //this.dg_service.initPapers();
  }
  step=1;
  merge(){   
      //console.log(this.dg_service.project)
      if(this.step<3){
        this.component_choice_service.set_choice_false();
        this.component_choice_service.merge = true;
      }
  }
  upload() {
    /*this.version = new Date().getTime().toString();*/
    //console.log('this.fileService.setProject(this.projectAddress)')
    this.fileService.setProject(this.projectAddress).subscribe(
      res => {
        //console.log(res);
        if (res === true) {
          alert('The project was imported successfully!');
          this.fileService.uploadFile(this.uploader);          
          var that = this;
          that.interval = setInterval(function () {
            clearInterval(that.interval);
            that.dg_service.getProject(that.projectAddress,undefined);
          }, 1500);
        } else {
          alert('The project already exists!');
        }
      }
    );

  }	
  saveProject():Boolean {
    var project = this.dg_service.project;
    var result: boolean;
    this.projectAddress = this.dg_service.projectAddress;
    this.dg_service.changeLinkPosition(project);
    if(project.contextDiagram.machine==null && project.contextDiagram.problemDomainList.length == 0
      && project.problemDiagram.requirementList.length == 0){
        alert("项目为空")
        return
      }
    this.fileService.saveProject(this.projectAddress,project).subscribe(
      res => {
        result = res;
        if(res){
          this.dg_service.isSaved = true;
        }
        return res
      }
    );
  }
  saveProjectAndPf() {
    var project = this.dg_service.project;
    var pf = this.textService.getText();
    var result: boolean;
    this.projectAddress = this.dg_service.projectAddress;
    this.dg_service.changeLinkPosition(project);
    if(project.contextDiagram.machine==null && project.contextDiagram.problemDomainList.length == 0
      && project.problemDiagram.requirementList.length == 0){
        alert("项目为空")
        return
      }
    this.fileService.saveProject(this.projectAddress,project).subscribe(
      res => {
        result = res;
        if(res){          
          this.fileService.savePf(this.projectAddress,pf).subscribe(
            res => {
              this.dg_service.isSaved = true;
              alert("save succeed!")
            }
          );
        }
  
      }
    );    
  }

  // saveProjectAndPf() {
  //   var project = this.dg_service.project;
  //   var pf = this.textService.getText();
  //   var result: boolean;
  //   this.projectAddress = this.dg_service.projectAddress;
  //   this.dg_service.changeLinkPosition(project);
  //   if(project.contextDiagram.machine==null && project.contextDiagram.problemDomainList.length == 0
  //     && project.problemDiagram.requirementList.length == 0){
  //       alert("项目为空")
  //       return
  //   }
  //   this.fileService.savePf(this.projectAddress,pf).subscribe(
  //     res => {
  //       if(res){
  //         this.saveProject()
  //       }
  //     }
  //   );
  // }

  //导出模板下载
  download() {
    //后台方法、文件类型、文件名
    //this.projectAddress = window.location.pathname.split('/')[1];
    // if (this.version === undefined){
   // this.version = window.location.pathname.split('/')[2];
    // }
    var project = this.dg_service.project;
    var url = 'http://localhost:8080/file/download/' + project.title + '/' ;
    //console.log(url);
    var form = document.createElement('form');
    document.body.appendChild(form);
    form.style.display = "none";
    form.action = url;
    form.id = 'excel';
    form.method = 'post';

    var newElement = document.createElement("input");
    newElement.setAttribute("type", "hidden");
    form.appendChild(newElement);

    form.submit();
  }
  owlAdd: string;
	powlName: string;
	eowlName: string;
  type: string;
  // uploadOwl(address: string){    
	// 	//this.fileService.setProject(address).subscribe();
	// 	this.fileService.uploadOwlFile(this.uploader,address);
  // }

  format() {
    var project = this.dg_service.project;
    var result: boolean;
    this.projectAddress = project.title;
    this.dg_service.changeLinkPosition(project);
    this.fileService.format(this.projectAddress, project).subscribe(
      res => {
        result = res;
        if (res) {
          alert('save succeed!')
          this.format_download();
        }
      }
    );
  }

  format_download() {
    //后台方法、文件类型、文件名
    //this.projectAddress = window.location.pathname.split('/')[1];
    // if (this.version === undefined){
    // this.version = window.location.pathname.split('/')[2];
    // }
    var project = this.dg_service.project;
    var url = 'http://localhost:8080/file/formatdownload/' + project.title + '/';
    //console.log(url);
    var form = document.createElement('form');
    document.body.appendChild(form);
    form.style.display = "none";
    form.action = url;
    form.id = 'excel';
    form.method = 'post';

    var newElement = document.createElement("input");
    newElement.setAttribute("type", "hidden");
    form.appendChild(newElement);

    form.submit();
  }

  uploadPOwl():void{
		this.uploader.clearQueue();
		this.type = 'powl';
		if(this.dg_service.project.title === undefined){
      alert('Please new a project!');
      return;
    }
      // this.owlAdd = new Date().getTime().toString();
      this.owlAdd = this.dg_service.project.title;
			this.projectService.sendOwlAdd(this.owlAdd);
		
		document.getElementById("ontologyFile").click();
	}
	uploadEOwl():void{
    // if(!this.dg_service.isSaved){
    //   alert("Please save the project first!")
    //   return;
    // }
		this.uploader.clearQueue();
		this.type = 'eowl';
		if(this.owlAdd == undefined){
			this.owlAdd = this.dg_service.project.title;
			this.projectService.sendOwlAdd(this.owlAdd);
		}
    document.getElementById("ontologyFile").click();
  }
  selectedOWLOnChanged(event:any) {
    //console.log("this.type="+this.type);
		if(this.type === 'powl'){
			this.powlName = this.uploader.queue[0].file.name;
			this.projectService.sendOntName(this.powlName);
			//console.log("this is pontology name: "+this.powlName);
		}
		else if(this.type === 'eowl'){
			this.eowlName = this.uploader.queue[0].file.name;
			this.projectService.sendEOntName(this.eowlName);
			//console.log("this is eontology name: "+this.eowlName);
    }
    //console.log("this.uploadOwl("+this.owlAdd+");");
    
    var that = this
    
    //若未新建项目,则创建项目
    if(that.dg_service.projectAddress==undefined){    
      let title = that.eowlName.substring(0,that.eowlName.length-4)     
      that.fileService.searchProject().subscribe(
        projects => {
          //判断是否有重名项目
          console.log(projects)
          for(let pro of projects){
            if(pro == title){
              alert(title + " already exist! please new a project with other name")
              return
            }
          }
          that.dg_service.projectAddress=title
          that.dg_service.project.title=title
          that.dg_service.register(title,"undefined",null)                        
        }) 
    }    
    this.fileService.uploadOwlFile(this.uploader,this.type, this.owlAdd);
    if(this.type=="eowl")
      setTimeout(
        function(){  
                that.fileService.getProblemDomains(that.owlAdd,that.eowlName).subscribe(
                  nodes=>{
                    that.dg_service.ontologyEntities = nodes;
                    that.dg_service.initProjectWithOntology(that.dg_service.project.title)                       
                })
              }
        ,1500)
  }
 
  nodes:string[];
  pOntShow():void{
      // var newPowlName = "/"+this.powlName;
      // var address = this.owlAdd+newPowlName;
      this.fileService.getNodes(this.owlAdd,this.powlName,"Thing","powl").subscribe(
        nodes => {
          this.nodes = nodes;
          this.projectService.sendPNodes(this.nodes);
        });
      
      
      this.projectService.pOntShow(true);
  }
  
  eOntShow():void{
      // var address = this.owlAdd.concat("/"+this.eowlName);
      // //console.log(address);
      this.fileService.getNodes(this.owlAdd,this.eowlName,"Thing","eowl").subscribe(
        nodes=>{
          this.nodes = nodes;
          this.projectService.sendENodes(this.nodes);
        });
        this.projectService.eOntShow(true);
  }
  uploadPF(): void {    
    document.getElementById("pfFile").click();
  }
  selectedPFOnChanged(event: any) {
    //console.log(event.target.files);    
    this.projectAddress = null;
    //console.log('即将调用upload!');
    this.fileService.uploadpfFile(this.uploader);
    var pfNameWithSuffix = this.uploader.queue[0].file.name;
    this.projectAddress = pfNameWithSuffix.substring(0,pfNameWithSuffix.length-3)
    console.log(pfNameWithSuffix);
    var that = this;
    this.interval = setInterval(function () {
      clearInterval(that.interval);
      that.dg_service.getPfProject(that.projectAddress,"undefined")
    }, 1000);
    that.interval1 = setInterval(function () {
      clearInterval(that.interval1);
      that.textService.getNotNullPf(that.projectAddress,"undefined");
    }, 2000);
    // this.addNewItem(this.projectAddress);
  }
}
