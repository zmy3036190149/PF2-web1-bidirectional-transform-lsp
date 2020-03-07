import { Component, OnInit } from '@angular/core';
import { ComponentChoiceService } from '../service/component-choice.service';
import { DrawGraphService } from '../service/draw-graph.service';
import * as _ from 'lodash';
import { ProjectService } from '../service/project.service';
import { FileService } from '../service/file.service';
import * as backbone from 'backbone';
import { Injectable } from '@angular/core';
import { EventManager } from '@angular/platform-browser';

import * as joint from 'node_modules/jointjs/dist/joint.js';
import { Project } from '../entity/Project';
import { ActivatedRoute } from '@angular/router';
declare var $: JQueryStatic;

@Component({
  selector: 'app-drawingboard',
  templateUrl: './drawingboard.component.html',
  styleUrls: ['./drawingboard.component.css']
})

export class DrawingboardComponent implements OnInit {

  num = $('#tablabel').children.length + 1;
  // new=false;

  papers = new Array<joint.dia.Paper>();
  PhysicalPropertys = ['GivenDomain', 'DesignDomain'];
  DomainTypes = ['Causal', 'Biddable', 'Lexical'];
  phenomenonTypes = ['event', 'state', 'value'];

  step = 0;
  constructor(public component_choice_service: ComponentChoiceService,
    private route: ActivatedRoute,
    public dg_service: DrawGraphService,
    private fileService: FileService,
    private projectService: ProjectService,
    private eventManager: EventManager) {
      projectService.stepEmmited$.subscribe(
        step => {         
         if(this.step<5&&step>=5){
           //console.log('change_Menu(1)'+this.step)
          this.change_Menu(2);
         }else if(this.step>=5&&step<5){
          this.change_Menu(1);
          //console.log('change_Menu(0)'+this.step)
         }
         this.step = step;
       }
     )
     projectService.changeEmitted$.subscribe(
      project => {         
       this.project = project
      }
   )
    }
  changeInput(){
      let ele = (document.getElementById('nameint') as any)
    }
  ngOnInit() {
    this.eventManager.addGlobalEventListener('window', 'keyup.delete', () => {
      ////console.log("close");
      this.deleteCell();
      this.dg_service.deleteListen = false;
    });
    // this.eventManager.addGlobalEventListener('window', 'resize', event => {
    //   // alert("resize")
    //   // console.log(event);

    //   this.dg_service.initPapers();
    //   // this.deleteCell();
    //   // this.dg_service.deleteListen = false;
    // });
    // this.test()
  }
  onResize(event){
      // console.log(event.target.innerWidth);
      // console.log(event.target.innerHeight);
      let width = event.target.innerWidth*0.8;
      let height = event.target.innerHeight*0.8;
      this.dg_service.papers[0].setDimensions(width, height); 
      this.dg_service.papers[1].setDimensions(width, height); 
  }
  // test(){
  //   this.dg_service.initProject('123');
  //     this.dg_service.initPapers();
  // }
  projectAddress;
  varsion: string;
  project : Project;
  interval;
  //get project from backend and draw diagram
  // getProject(  ): void {
  //   //console.log("aaa");
  //   this.projectAddress = window.location.pathname.split('/')[1];
  //   var version = $("input[type='radio']:checked").val();
  //   this.fileService.getProject(this.projectAddress,version).subscribe(project => {
  //     this.project = project;
  //   });
  //   var that = this;
  //   that.interval = setInterval(function () {
  //     clearInterval(that.interval);
  //     that.projectService.sendProject(that.project);	//组件传值
  //     that.dg_service.drawDiagram(that.project);
  //     //console.log(that.project);
  //   }, 1500);
  //   // this.getContext(this.project.title);
  // }

  /*deletePopBox(graphs,x,y) {
      var popBox = document.getElementById('deletePopBox');
      //console.log('deletePopBox.style.left='+y+',popBox.style.top='+x)
      y = y - 500;
      x = x - 500;
      //console.log('deletePopBox.style.left='+y+',popBox.style.top='+x)
      
      popBox.style.left = y+'px';
      popBox.style.top = x+'px';
      popBox.style.display = "block";
  }*/
  deleteCell() {
    //var popBox = document.getElementById('deletePopBox');
    //popBox.style.display = "none";
    if (this.dg_service.deleteListen == true) {
      this.dg_service.deleteElement(this.dg_service.graphs[this.dg_service.selectedGraphIndex])
    }
  }

  initPopBox() {
    //console.log('initPopBox')    
    if (this.dg_service.selectedType == 'machine') {
      this.dg_service.initMachinePopBox();
    } else if (this.dg_service.selectedType == 'problemDomain') {
      this.dg_service.initDomainPopBox();
    } else if (this.dg_service.selectedType == 'requirement') {
      this.dg_service.initRequirementPopBox();
    } else if (this.dg_service.selectedType == 'interface') {
      this.dg_service.initInterfacePopBox();
    } else if (this.dg_service.selectedType == 'reference') {
      this.dg_service.initReferencePopBox();
    } else if (this.dg_service.selectedType == 'constraint') {
      this.dg_service.initConstraintPopBox();
    }
  }

  GetObj(objName) {
    if (document.getElementById) {
      return eval('document.getElementById("' + objName + '")');
    }
  }

  change_Menu(index): void {
    this.dg_service.selectedGraphIndex = index - 1;
    for (var i = 1; i <= this.num; i++) {
      if (this.GetObj('content' + i) && this.GetObj('lm' + i)) {
        this.GetObj("content" + i).style.display = 'none';
      }
    }
    if (this.GetObj('content' + index) && this.GetObj('lm' + index)) {
      this.GetObj('content' + index).style.display = 'block';
      this.dg_service.enterTable(index);
    }
  }
  // checkChanges(){
  //   // <input type="checkbox" (change)="checkChanges()" [checked]="checkedSites[0]"/>
  //   if (eval("document.checkbox_redio.checkbox[0].checked") == true) {
  //     var selectedDiv = document.getElementById(this.dg_service.selectedType+'popBox') as any
  //     var checkbox = selectedDiv.getElementsByName("checkbox")[0];
  //     if(checkbox.checked==true){

  //     }else{

  //     }
  // }

  // close PopBox
  closeBox(event) {
    this.closePopEdit();
    //console.log("close");
  }

  // confirm PopBox
  confirm() {
    let flag = true;
    if (this.dg_service.selectedType == 'machine') {
      //console.log('this.selectedType=="machine"');
      flag = this.dg_service.changeMachineDetail();
    } else if (this.dg_service.selectedType == 'problemDomain') {
      //console.log('this.selectedType=="problemDomain"');
      flag = this.dg_service.changeProblemDomainDetail(this.dg_service.graphs[this.dg_service.selectedGraphIndex]);
    } else if (this.dg_service.selectedType == 'requirement') {
      //console.log('this.selectedType=="requirement"');
      flag = this.dg_service.changeRequirementDetail(this.dg_service.graphs[this.dg_service.selectedGraphIndex]);
    } else if (this.dg_service.selectedType == 'interface') {
      //console.log('this.selectedType=="interface"');
      this.dg_service.changeInterfaceDetail();
    } else if (this.dg_service.selectedType == 'reference') {
      //console.log('this.selectedType=="reference"');
      this.dg_service.changeReferenceDetail();
    } else if (this.dg_service.selectedType == 'constraint') {
      //console.log('this.selectedType=="constraint"');
      this.dg_service.changeConstraintDetail();
    }
    if (flag) this.closePopEdit();
    this.projectService.sendProject(this.dg_service.project);	//组件传值
    //console.log("confirm");
  }

  closePopEdit() {
    //let selectedType = this.dg_service.getElementType(this.dg_service.selectedElement);
    document.getElementById(this.dg_service.selectedType + "PopBox").style.display = "none";
    document.getElementById("popLayer").style.display = "none";
    this.dg_service.phenomenonList = [];
  }
}
