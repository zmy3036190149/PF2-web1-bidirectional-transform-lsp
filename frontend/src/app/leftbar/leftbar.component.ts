import { Component, OnInit } from '@angular/core';
import { ComponentChoiceService } from '../service/component-choice.service';
import { ProjectService} from '../service/project.service';
declare var $: JQueryStatic;

import { DrawGraphService } from '../service/draw-graph.service';
@Component({
  selector: 'app-leftbar',
  templateUrl: './leftbar.component.html',
  styleUrls: ['./leftbar.component.css']
})
export class LeftbarComponent implements OnInit {
  step=1;
  constructor(
    public dg_service: DrawGraphService,
    private projectService: ProjectService,
    public component_choice_service: ComponentChoiceService) { 
    projectService.stepEmmited$.subscribe(
      step => {
       this.step = step;
     }
   )
  }
  ngOnInit() { }
  set_choice_false(): void {
    this.component_choice_service.element = false;
    this.component_choice_service.domain = false;
    this.component_choice_service.machine = false;
    this.component_choice_service.req = false;

    this.component_choice_service.link = false;
    this.component_choice_service.interface = false;
    this.component_choice_service.reference = false;
    this.component_choice_service.constraint = false;
    
    this.component_choice_service.merge = false;
  }
  //Element
  clickDomain(): void {
    this.set_choice_false();
    // console.log('click clickDomain')
    this.component_choice_service.element = true;
    this.component_choice_service.domain = true;
  }
  clickMachine(): void {
    this.set_choice_false();
    console.log('click clickMachine')
    this.component_choice_service.element = true;
    this.component_choice_service.machine = true;
    console.log('this.component_choice_service.element =',this.component_choice_service.element)
    console.log('this.component_choice_service.machine =',this.component_choice_service.machine)
  }
  clickReq(): void {
    this.set_choice_false();
    // console.log('click clickReq')
    this.component_choice_service.element = true;
    this.component_choice_service.req = true;
  }
  //Link
  clickInterface(): void {    
    this.set_choice_false();
    this.component_choice_service.link = true;
    this.component_choice_service.interface = true;
    // console.log('click interface')
    console.log(this.component_choice_service)
  }
  clickReference(): void {
    this.set_choice_false();
    this.component_choice_service.link = true;
    this.component_choice_service.reference = true;
    
    // console.log('click reference')
    console.log(this.component_choice_service)
  }
  clickConstraint(): void {
    this.set_choice_false();
    this.component_choice_service.link = true;
    this.component_choice_service.constraint = true;
    
    // console.log('click constraint')
    console.log(this.component_choice_service)
  }

  
}
