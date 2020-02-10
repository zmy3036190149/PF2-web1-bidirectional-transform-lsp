 import { Component, OnInit, Input,Output, EventEmitter } from '@angular/core';
 import { ProjectService} from '../service/project.service';
 import { FileService} from '../service/file.service';
import { Project } from '../entity/Project';
import { Phenomenon } from '../entity/Phenomenon';
import { RequirementPhenomenon } from '../entity/RequirementPhenomenon';
import { Button } from 'protractor';
import { DrawGraphService } from '../service/draw-graph.service';

@Component({
  selector: 'app-rightbar',
  templateUrl: './rightbar.component.html',
  styleUrls: ['./rightbar.component.css']
})
export class RightbarComponent implements OnInit {
	projectAddress: string;
	project: Project;
  pheList: Phenomenon[];

	reqPheList: RequirementPhenomenon[];
	step = 1;
	// problemDiagram;
	@Output() errorsEvent = new EventEmitter<Error[]>(); 
	// res: boolean;

	open1 = false;
	open2 = false;
	open3 = false;
	open4 = false;
	open5 = false;


	setOpen1():void{
		this.open1 = !this.open1;
	}
	setOpen2():void{
		this.open2 = !this.open2;
	}
	setOpen3():void{
		this.open3 = !this.open3;
	}
	setOpen4():void{
		this.open4 = !this.open4;
	}
	setOpen5():void{
		this.open5 = !this.open5;
	}

	sendErrors(errors: Error[]){	//发送错误信息
		this.errorsEvent.emit(errors);
	}

  back(){
    switch(this.step){
      case 2:
        this.step = 1;
        this.projectService.stepChange(this.step);
        break;
      case 3:
        this.step = 2;
        this.projectService.stepChange(this.step);
        break;
      case 4:
        this.step = 3;
        this.projectService.stepChange(this.step);
        break;
      case 5:
        this.step = 4;
        this.projectService.stepChange(this.step);
        break;
      case 6:
        this.step = 5;
        this.projectService.stepChange(this.step);
        break;
      case 7:
        this.step = 6;
        this.projectService.stepChange(this.step);
        break;
      case 8:
        this.step = 7;
        this.projectService.stepChange(this.step);
    }
  }
  check() {
    //console.log(this.step);
    switch (this.step) {
      case 1:
        // var next = confirm('Have you finished drawing Machine?');
        // if (next) {
        //   this.step = 2;
        // }
        this.step = 2;
        this.projectService.stepChange(this.step);
        break;
      case 2:
        // var next = confirm('Have you finished drawing Problem Domain?');
        // if (next) {
        //   this.step = 3;
        // }
        this.step = 3;
        this.projectService.stepChange(this.step);
        break;

      case 3:
        // var next = confirm('Have you finished drawing Interface?');
        // if (next) {
        //   this.step = 4;
        // }
        this.step = 4;
        this.projectService.stepChange(this.step);
        break;

      case 4:
        var errorList;
        var project_to = this.dg_service.project;
        this.projectService.checkCorrectContext(project_to).subscribe(
          errors => {
            errorList = errors;
            this.sendErrors(errorList);
            if (this.projectService.getRes(errorList)) {
              alert('The diagram is correct.')
              this.step = 5;
            } else {
              console.log(this.project)
              this.step = 1;
            }
            this.projectService.stepChange(this.step);
          }
        );
        break;

      case 5:
        // var next = confirm('Have you finished drawing Requirement?');
        // if (next) {
        //   this.step = 6;
        // }
        this.step = 6;
        this.projectService.stepChange(this.step);
        break;

      case 6:
        // var next = confirm('Have you finished drawing Reference?');
        // if (next) {
        //   this.step = 7;
        // }
        this.step = 7;
        this.projectService.stepChange(this.step);
        break;

      case 7:
        var errorList;
        var project_to = this.dg_service.project;
        this.projectService.checkCorrectProblem(project_to).subscribe(
          errors => {
            errorList = errors;
            this.sendErrors(errorList);
            if (this.projectService.getRes(errorList)) {
              alert('The diagram is correct.')
              this.step = 8;
            } else {

              this.step = 5;
            }
            this.projectService.stepChange(this.step);
          }
        );
        break;

      case 8:
        const button = document.getElementById('next');
        this.projectService.getSubProblemDiagram(this.project).subscribe(
          project => {
            this.project = project;
           // this.projectService.sendProject(this.project);
          }
        )
        button.innerHTML = 'Finish';
        this.step = 1;
        this.projectService.stepChange(this.step);
        break;
    }
  }

	constructor(
		private projectService: ProjectService,
      private fileService: FileService,
      private dg_service: DrawGraphService,
	) { 
    projectService.stepEmmited$.subscribe(
      step => {
       this.step = step;
     }
    )
    projectService.changeEmitted$.subscribe(
      project => {
        this.project = project;
        //this.step = 1;
        this.pheList = this.projectService.getPhenomenon(project);
        this.reqPheList = this.projectService.getReference(project);
      });

    fileService.newProEmmited$.subscribe(
      res => {
        //console.log(res);
        if (res == true) {
          this.step = 1;
          this.projectService.stepChange(this.step);
        }
      }
    )

		// projectService.pdEmmited$.subscribe(
		// 	problemDiagram => {
		// 		this.problemDiagram = problemDiagram;
		// 	}
		// )
	}

	ngOnInit() {
	}
}
