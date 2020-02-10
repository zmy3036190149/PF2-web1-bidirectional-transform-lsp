import { Component } from '@angular/core';
import { Project } from './entity/Project';
import { Error } from './entity/Error';
import { ProjectService } from './service/project.service';
import { FileService } from './service/file.service';
import { DrawGraphService } from './service/draw-graph.service';
import { TextService } from './service/text.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'PF2-web1';
  errors: Error[];
  res: boolean;
  strategyList: string[];
  projects: string[];
  versions: string[];
  
  owls: string[];
  owlVersions: string[];
  owl: string;

  pOwls: string[];
  pOwlVersions: string[];
  pOwl: string;

  project: string;
  projectAddress: string;
  interval;

/*  getErrors(errors: Error[]) {
    this.res = false;
    this.errors = errors;    
      //console.log(this.errors[0]);
      var popBox = document.getElementById("popBox");
      var popLayer = document.getElementById("popLayer");
      popBox.style.display = "block";
      popLayer.style.display = "block";
    
  }*/

  getErrors(errors: Error[]) {
    this.res = false;
    this.errors = errors;
    if (this.errors[0].errorList.length>0 || this.errors[1].errorList.length>0 || this.errors[2].errorList.length>0) {
      //console.log(this.errors);
      var popBox = document.getElementById("popBox");
      var popLayer = document.getElementById("popLayer");
      popBox.style.display = "block";
      popLayer.style.display = "block";
    }else{
      //console.log(errors)
    }
  }

  getProjects(projects: string[]) {
    //console.log("appopen");
    this.projects = projects;
    //console.log(projects.length);
    if (projects.length == 0) {
      alert("There is no project! You need import a new project!");
    } else {
      var popLayer = document.getElementById("popLayer");
      popLayer.style.display = "block";
      var popBox = document.getElementById('OpenProject');
      popBox.style.display = "block";
    }
  }

  getOwls(owls: string[]) {
    console.log("getOwls");
    this.owls = owls;
    //console.log(projects.length);
    if (owls.length == 0) {
      alert("There is no owl! You need upload a owls!");
    } else {
      var popLayer = document.getElementById("popLayer");
      popLayer.style.display = "block";
      var popBox = document.getElementById('OpenOwl');
      popBox.style.display = "block";
    }
  }

  getPOwls(pOwls: string[]) {
    console.log("getOwls");
    this.pOwls = pOwls;
    //console.log(projects.length);
    if (pOwls.length == 0) {
      alert("There is no owl! You need upload a owls!");
    } else {
      var popLayer = document.getElementById("popLayer");
      popLayer.style.display = "block";
      var popBox = document.getElementById('OpenPOwl');
      popBox.style.display = "block";
    }
  }
  
  search(project: string) {
    this.project = project;
    //console.log(project);
    this.fileService.searchVersion(project).subscribe(
      versions => {
        this.versions = versions;
      }
    )
  }
  searchOwlVersions(owl: string) {
    this.owl = owl;
    // console.log("searchOwlVersions");
    // this.fileService.searchOwlVersion(owl).subscribe(
    //   versions => {
    //     this.owlVersions = versions;
    //   }
    // )
  }

  searchPOwlVersions(pOwl: string) {
    this.pOwl = pOwl;
    // console.log("searchOwlVersions");
    // this.fileService.searchOwlVersion(owl).subscribe(
    //   versions => {
    //     this.owlVersions = versions;
    //   }
    // )
  }
  //chose project to open
  open() {
    // var form = document.getElementById('version');
    // //console.log(form.textContent);
    var version = $("input[id='version']:checked").val()
    if (this.project != undefined) {
      // window.location.assign('/' + this.project)
      var that = this
      that.dg_service.getProject(that.project,version) 
      that.interval = setInterval(function () {
        clearInterval(that.interval)         
        that.textService.getNotNullPf(that.project,version)       
      }, 1500)
      console.log('this.project')
      console.log(that.project)
      console.log('version')
      console.log(version)
    } 
    this.projectService.stepChange(1);
    this.close('OpenProject');
  }

  //chose owl to open
  //将后台的owl文件放到当前项目的目录下，并获取问题领域
  openOwl() {
    // var form = document.getElementById('version');
    // //console.log(form.textContent);
    console.log('open owl')
    var version = $("input[id='owlVersion']:checked").val()
    if(this.dg_service.projectAddress==null){
      alert("please new a project")
      return
    }
    if (this.owl != undefined) {
      // window.location.assign('/' + this.project)
      var that = this
      that.owlAdd = that.dg_service.projectAddress      
      this.projectService.sendOwlAdd(this.owlAdd)         
			this.projectService.sendEOntName(this.owl+".owl")
      that.fileService.moveOwl(that.dg_service.projectAddress,that.owl,that.owlVersions).subscribe(
        res =>{
          if(res){
            that.fileService.getProblemDomains(that.owlAdd,that.owl).subscribe(
              nodes=>{
                that.dg_service.ontologyEntities = nodes;
                that.dg_service.initProjectWithOntology(that.dg_service.project.title)                       
            }) 
            this.projectService.stepChange(1);    
          }else{
            alert("error!")
          }
        }
      )
    } 
    this.close('OpenOwl');
  }

  
  //chose owl to open
  //将后台的powl文件放到当前项目的目录下，并通知topbar，可以show
  openPOwl() {
    // var form = document.getElementById('version');
    // //console.log(form.textContent);
    console.log('open powl')
    var version = $("input[id='pOwlVersion']:checked").val()
    if(this.dg_service.projectAddress==null){
      alert("please new a project")
      return
    }
    if (this.pOwl != undefined) {
      // window.location.assign('/' + this.project)
      var that = this        
			this.projectService.sendEOntName(this.pOwl+".owl")
      that.fileService.movePOwl(that.dg_service.projectAddress,that.pOwl,that.pOwlVersions)
    } 
    this.close('OpenPOwl');
  }
  close(id: string) {
    //console.log("close");
    document.getElementById(id).style.display = "none"
    document.getElementById("popLayer").style.display = "none"
  }

  getStrategy(errMsg: string) {
    this.strategyList = this.projectService.getStrategy(errMsg)
    document.getElementById('Strategy').style.display = "block"
  }

  pOntologyShow:boolean;
  eOntologyShow:boolean;
  nodes:string[];
  eNodes:string[];
  subnodes:string[];
  subnodes1:string[];
  clicknode = null;
  clicknode1 = null;
	owlAdd: string;
	powlName: string;
	eowlName: string;
		searchPOntNodes(node: string){
			this.clicknode = node;
			//console.log(node);
      this.fileService.getNodes(this.owlAdd,this.powlName,node,"powl").subscribe(
				subnodes=>{
             this.subnodes = subnodes;
				}
			)
		}

		searchEOntNodes(node: string){
			this.clicknode = node;
			//console.log(node+"searchEOntNodes");
			this.fileService.getNodes(this.owlAdd,this.eowlName,node,"eowl").subscribe(
				subnodes=>{
					this.subnodes = subnodes;
				}
			)
		}

		searchEOntNodes1(node: string){
			this.clicknode1 = node;
			//console.log(node+"searchEOntNodes1");
			let subscription = this.fileService.getNodes(this.owlAdd,this.eowlName,node,"eowl").subscribe(
				subnodes1=>{
					this.subnodes1 = subnodes1;
					subscription.unsubscribe();
				}
			)
		}

  constructor(
    private projectService: ProjectService,
    private fileService: FileService,
    private dg_service: DrawGraphService,
    private textService: TextService
  ) {

    fileService.newProEmmited$.subscribe(
      res => {
        //console.log(res);
        if (res == true) {
          let popLayer = document.getElementById('popLayer');
          popLayer.style.display = "block";
          let ele = document.getElementById('projectPopBox');
          //console.log(ele);
          ele.style.display = "block";
        }

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
       }
     )
    this.pOntologyShow = false;
      this.eOntologyShow = false;
    this.projectService.pNodesToDisplayEmitted$.subscribe(
       nodes=>{
       this.nodes = nodes;
       //console.log("now in app.component");
             for(let node of this.nodes){
             //console.log(node);
           }
     }
   ) 
     this.projectService.eNodesToDisplayEmitted$.subscribe(
       nodes=>{
         this.eNodes = nodes;
       //console.log("now in app.component");
             for(let node of this.eNodes){
             //console.log(node);
           }					
       }
     )
     this.projectService.pOntShowEmitted$.subscribe(
     on =>{
       this.pOntologyShow = on;
       //console.log(this.pOntologyShow+"pOntologyShow is ok");
       if(this.pOntologyShow){
         //console.log(this.pOntologyShow+"pOntologyShow is ok1");
         var popBoxP = document.getElementById('pOntShow');
         //console.log(popBoxP);
         popBoxP.style.display = "block";	
       }
     }
   )
   this.projectService.eOntShowEmitted$.subscribe(
     on =>{
       this.eOntologyShow = on;
       //console.log(this.eOntologyShow+"eOntologyShow is ok");
       if(this.eOntologyShow){
         //console.log(this.eOntologyShow+"eOntologyShow is ok1");
         var popBoxE = document.getElementById('eOntShow');
         //console.log(popBoxE);
         popBoxE.style.display = "block";
       }
     })  
  }
 
  //new project
  confirm() {
    let selectedDiv = document.getElementById('projectPopBox');
    let description = (selectedDiv.getElementsByClassName("description")[0] as any).value;
    if (description == null) {
      alert("The project title can't be null");
    }
    else {  
      //判断是否有重名项目
      let flag=false
      this.fileService.searchProject().subscribe(
        projects => {
          for(let pro of projects){
            if(pro == description){
              alert(description+"already exist!")
              flag = true
              break
            }
          }
          if(!flag){
            //diagram
            this.dg_service.register(description,"undefined",null);
            this.dg_service.initProject(description);
            this.dg_service.initPapers();
            //text
            var that = this
            this.interval = setInterval(function () {
              clearInterval(that.interval)
              that.textService.register(description,"undefined","problem: #"+description+"#\n");     
            }, 500)            
            this.closePopEdit();
          }         
        }
      )        
    }

  }
  closeBox() {
    this.closePopEdit();
  }
  closePopEdit() {
    //let selectedType = this.dg_service.getElementType(this.dg_service.selectedElement);
    document.getElementById("projectPopBox").style.display = "none";
    document.getElementById("popLayer").style.display = "none";
  }

}
