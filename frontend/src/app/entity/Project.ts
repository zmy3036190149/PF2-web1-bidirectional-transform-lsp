import { ContextDiagram } from './ContextDiagram';
import { ProblemDiagram } from './ProblemDiagram';
import { Machine } from './Machine';
import { ProblemDomain } from './ProblemDomain';
import { Requirement } from './Requirement';
import { Interface } from './Interface';
import { Constraint } from './Constraint';
import { Reference } from './Reference';
import { Phenomenon } from './Phenomenon';

export class Project{
	title: string;
	contextDiagram: ContextDiagram;	
	problemDiagram: ProblemDiagram;	
	// scenarioGraphList: ScenarioGraph[]; 	
	// subProblemDiagramList: SubProblemDiagram[];	
	initProject(project:Project) {
		this.init(project.title)	
		this.contextDiagram.machine = Machine.copyMachine(project.contextDiagram.machine)
		this.contextDiagram = ContextDiagram.copyContextDiagram(project.contextDiagram)
		if(project.contextDiagram==null){
			this.initContexDiagram()
		}else{
			console.log('this.project.contextDiagram!=null')					
			this.contextDiagram = ContextDiagram.copyContextDiagram(project.contextDiagram)
		}
		if(project.problemDiagram==null){
			console.log('this.project.problemDiagram==null')
			this.initProblemDiagram()
		}else{
			this.problemDiagram = ProblemDiagram.copyProblemDiagram(project.problemDiagram,this.contextDiagram)
		}
		this.problemDiagram.contextDiagram = this.contextDiagram
	}
	init(title){
		this.title = title
		this.initContexDiagram()
		this.initProblemDiagram()  
	}
	initContexDiagram(){
		this.contextDiagram = new ContextDiagram();
		// project.contextDiagram.machine = new Machine();
		this.contextDiagram.title = 'contextDiagram';
		this.contextDiagram.problemDomainList = new Array<ProblemDomain>();
		this.contextDiagram.interfaceList = new Array<Interface>();
	}
	initProblemDiagram(){
		this.problemDiagram = new ProblemDiagram();
		this.problemDiagram.title = 'problemDiagram';
		this.problemDiagram.requirementList = new Array<Requirement>();
		this.problemDiagram.constraintList = new Array<Constraint>();
		this.problemDiagram.referenceList = new Array<Reference>();
		this.problemDiagram.contextDiagram = this.contextDiagram;
	}
	getTitle(){
		return this.title
	}
	setTitle(title){
		this.title = title
	}
	//Machine
	getMachine(){
		return this.contextDiagram.machine
	}
	addMachine(name, shortName, x, y,w,h){
		this.contextDiagram.machine = new Machine()
		this.contextDiagram.machine.machine_name = name
		this.contextDiagram.machine.machine_shortName = shortName
		this.contextDiagram.machine.machine_x = x
		this.contextDiagram.machine.machine_y = y		
		this.contextDiagram.machine.machine_w = w
		this.contextDiagram.machine.machine_h = h
		this.problemDiagram.contextDiagram.machine = this.contextDiagram.machine		
		return this.contextDiagram.machine
	}

	changeMachineWithNewProject(newProject:Project) {
		let name = newProject.getMachine().getName()
		let shortName = newProject.getMachine().getShortName()
		this.changeMachine(name,shortName)
		return this.contextDiagram.machine
	}
	changeMachine(name, shortName) {
		this.contextDiagram.machine.machine_name = name;
		this.contextDiagram.machine.machine_shortName = shortName;
		this.problemDiagram.contextDiagram.machine.machine_name = name;
		this.problemDiagram.contextDiagram.machine.machine_shortName = shortName
		return this.contextDiagram.machine
	}
	changeMachinePosition(name, position){
		// console.log(this.contextDiagram)
		if(!this.getMachine()){
			console.log(this.contextDiagram)
			return
		}
		if(!this.getMachine().getName()){
			console.log(this.contextDiagram)
			return
		}
		if(name == this.getMachine().getName()){
			this.contextDiagram.machine.machine_x = position.x;
			this.contextDiagram.machine.machine_y = position.y;
			this.problemDiagram.contextDiagram.machine.machine_x = position.x;
			this.problemDiagram.contextDiagram.machine.machine_y = position.y;
			return true
		}else{
			
		}
		return false
	}
	setMachine(machine){
		this.contextDiagram.machine=machine		
		this.problemDiagram.contextDiagram.machine=machine
	}

	//ProblemDomain
	getProblemDomainList(){
		return this.contextDiagram.problemDomainList
	}
	addProblemDomain(no, name, shortName, type, property, x, y,w,h) {
		let problemDomain = new ProblemDomain();
		problemDomain.problemdomain_no = no;
		problemDomain.problemdomain_name = name;
		problemDomain.problemdomain_shortname = shortName;
		problemDomain.problemdomain_type = type;
		problemDomain.problemdomain_property = property;
		problemDomain.problemdomain_x = x;
		problemDomain.problemdomain_y = y;    
		problemDomain.problemdomain_w = w;
		problemDomain.problemdomain_h = h;
		problemDomain.phes = new Array<Phenomenon>();
		this.contextDiagram.problemDomainList.push(problemDomain);
		//console.log(this.project.contextDiagram.problemDomainList);
		return problemDomain;
	}
	changeProblemDomainWithNewProject(newProject:Project){
		let oldList = this.getProblemDomainList()
		let newList = newProject.getProblemDomainList()
		//change & add
		for(let newItem of newList){
			let isFind = false
			for(let item of oldList){
				if(newItem.getName()==item.getName()){
					if(newItem.getShortName()!=item.getShortName()){
						item.setShortName(newItem.getShortName())
						item.setProperty(newItem.getProperty())	
						console.log("change shortName")
					}					
					isFind = true									
				}else if(newItem.getShortName()==item.getShortName()){
					if(newItem.getName()!=item.getName()){
						item.setName(newItem.getName())
						item.setProperty(newItem.getProperty())	
						console.log("change Name")
						console.log("item=",item)
						console.log("newItem=",newItem)
						console.log(this)
					}						
					isFind = true						
				}
			}
			if(!isFind){
				oldList.push(newItem)
				console.log("add ",newItem)
			}
		}
		//delete
		let i = 0
		let len = oldList.length
		for(let i = len-1; i >= 0 ;i--){
			let item = oldList[i]
			let isFind = false
			for(let newItem of newList){
				if(newItem.getName()==item.getName()){
					isFind = true
					break									
				}
			}
			if(!isFind){
				oldList.splice(i,1)				
				console.log("delete ",item)
			}
		}
			
	}
	changeProblemDomain1(old,new1) {
		let i = 0
		for(let item of this.getProblemDomainList()){
			if(item.problemdomain_name==old.getName()){
				this.getProblemDomainList()[i] = new1
				console.log(this.problemDiagram)
				return
			}	
			i += 1 			
		}
	}
	changeProblemDomain(old,description, shortName, domainType, physicalProperty) {
		for(let item of this.getProblemDomainList()){
			if(item.getName()==old.getName()){
				item.setName(description)
				item.setShortName(shortName)
				item.setType(domainType)
				item.setProperty(physicalProperty)
				break
			}				
		}
	}
	changeProblemDomainPosition(name,position) {
		//let name = this.selectedElement.attr('root').title;
		console.log(name)
		for (let item of this.contextDiagram.problemDomainList) {
		  if (item.problemdomain_name == name) {
			item.problemdomain_x = position.x;
			item.problemdomain_y = position.y;
			console.log("changeProblemDomainPosition")
			console.log(item)
			return true
		  }
		}
		return false
	}

	//Requirement
	getRequirementList(){
		return this.problemDiagram.requirementList
	}
	addRequirement(no, context, x, y,w,h) {
		let requirement = new Requirement();
		requirement.requirement_no = no;
		requirement.requirement_context = context;
		requirement.requirement_x = x;
		requirement.requirement_y = y;
		requirement.requirement_w = w;
		requirement.requirement_h = h;
		this.problemDiagram.requirementList.push(requirement);
		return requirement;
	}
	changeRequirementWithNewProject(newProject:Project){
		// add
		let newList = newProject.getRequirementList()
		let oldList = this.getRequirementList()
		for(let newPro of newList){
			let isFind = false
			for(let pro of oldList){
				if(newPro.getName()==pro.getName()){					
					isFind = true									
				}
			}
			if(!isFind){
				oldList.push(newPro)
			}
		}
		//delete
		let i = 0
		let len = oldList.length
		for(let i = len-1; i >= 0 ;i--){
			let pro = oldList[i]
			let isFind = false
			for(let newPro of newList){
				if(newPro.getName()==pro.getName()){
					isFind = true
					break									
				}
			}
			if(!isFind){
				oldList.splice(i,1)
			}
		}
			
	}
	changeRequirement1(old,new1) {
		let i = 0
		for(let item of this.getRequirementList()){
			if(item.getName()==old.getName()){
				this.getRequirementList()[i] = new1
				return
			}				
		}
	}
	changeRequirement(old,description) {
		for(let item of this.getRequirementList()){
			if(item.getName()==old.getName()){
				item.setName(description)
				item.setShortName(description)
				break
			}				
		}
	}
	changeRequirementPosition(name,position) {
		for (let item of this.getRequirementList()) {
		  if (item.requirement_context == name) {
			item.requirement_x = position.x;
			item.requirement_y = position.y;
			return true
		  }
		}
		return false
	}
	deleteRequirement(requirement:Requirement){
		let name = requirement.requirement_context;
    	let list = this.problemDiagram.requirementList;
		let i = list.length - 1;
		for (; i >= 0; i--) {
		let item = list[i];
		if (item.requirement_context == name) {

			list.splice(i, 1);
			break;
		}
		//console.log(item.requirement_context + '!=' + name);
		}
	}

	//link
	deleteRelatedLink(shortName) {
		console.log('deleteRelatedLink,shortName=' + shortName);
		let i = this.getReferenceList().length - 1;
		for (; i >= 0; i--) {
		  let reference = this.problemDiagram.referenceList[i];
		  if (reference.reference_from == shortName || reference.reference_to == shortName) {
			console.log(reference)
			let name = reference.reference_name;
			this.problemDiagram.referenceList.splice(i, 1);
		  }
		}
		i = this.problemDiagram.constraintList.length - 1;
		for (; i >= 0; i--) {
		  let constraint = this.problemDiagram.constraintList[i];
		  if (constraint.constraint_from == shortName || constraint.constraint_to == shortName) {
			console.log(constraint)
			this.problemDiagram.constraintList.splice(i, 1);
		  }
		}
		i = this.contextDiagram.interfaceList.length - 1;
		for (; i >= 0; i--) {
		  let my_interface = this.contextDiagram.interfaceList[i];
		  if (my_interface.interface_from == shortName || my_interface.interface_to == shortName) {
			console.log(my_interface)
			this.contextDiagram.interfaceList.splice(i, 1);
		  }
		}
	}

	//Interface
	getInterfaceList(){
		return this.contextDiagram.interfaceList
	}
	addInterface(int:Interface){
		this.contextDiagram.interfaceList.push(int) 
	}
	changeInterfaceWithNewProject(newProject:Project){
		//change & add
		let newList = newProject.getInterfaceList()
		let oldList = this.getInterfaceList()
		this.changeLineWithNewProject(oldList,newList)
	}
	changePhenomenon(oldList:Phenomenon[], newList:Phenomenon[]){
		//change & add
		for(let newItem of newList){
			let isFind = false
			for(let item of oldList){
				if(newItem.phenomenon_name==item.phenomenon_name){
					item.phenomenon_type = newItem.phenomenon_type
					isFind = true									
				}
			}
			if(!isFind){
				oldList.push(newItem)
			}
		}
		//delete
		let i = 0
		let len = oldList.length
		for(let i = len-1; i >= 0 ;i--){
			let item = oldList[i]
			let isFind = false
			for(let newItem of newList){
				if(newItem.phenomenon_name==item.phenomenon_name){
					isFind = true
					break									
				}
			}
			if(!isFind){
				oldList.splice(i,1)
			}
		}
	}

	changeInterface(old,new1){
		let i = 0
		for (let item of this.getInterfaceList()) {
			if (item.getName() == old.getName()) {
			  this.getInterfaceList()[i] = new1
			  return
			}
			i += 1
		  }
	}
	deleteInterface(int:Interface){
		let no = int.interface_no;
		let list = this.contextDiagram.interfaceList;
		let i = 0
		for (let item of list) {
		if (item.interface_no == no) {
			list.splice(i, 1);
			break;
		}
		i++;
		}
	}

	//Constraint
	getConstraintList(){
		return this.problemDiagram.constraintList
	}
	// addConstraint(no, name, description, from, to, phe, x1, y1, x2, y2) {
	// 	let constraint = new Constraint();
	// 	constraint.constraint_no = no;
	// 	constraint.constraint_name = name;
	// 	constraint.constraint_description = description;
	// 	constraint.constraint_from = from;
	// 	constraint.constraint_to = to;
	// 	constraint.phenomenonList = phe;
	// 	constraint.constraint_x1 = x1;
	// 	constraint.constraint_y1 = y1;
	// 	constraint.constraint_x2 = x2;
	// 	constraint.constraint_y2 = y2;
	// 	this.problemDiagram.constraintList.push(constraint);
	// 	//console.log('this.constraintList');
	// 	//console.log(this.project.problemDiagram.constraintList);
	// 	return constraint;
	// }
	addConstraint(con){
		this.problemDiagram.constraintList.push(con)
	}
	changeConstraintWithNewProject(newProject:Project){
		//change & add
		let newList = newProject.getConstraintList()
		let oldList = this.getConstraintList()
		this.changeLineWithNewProject(oldList,newList)
	}

	changeConstraint(old,new1){
		let i = 0
		for (let item of this.getConstraintList()) {
			if (item.getName() == old.getName()) {
				this.getConstraintList()[i] = new1
			  return
			}
			i += 1
		  }
	}
	deleteConstraint(con){
		let no = con.constraint_no
		let list = this.problemDiagram.constraintList;
		let i = 0
		for (let item of list) {
		  if (item.constraint_no == no) {
			list.splice(i, 1);
			break;
		  }
		  i++;
		}
	}


	//Reference
	getReferenceList(){
		return this.problemDiagram.referenceList
	}
	addReference(ref){
		this.problemDiagram.referenceList.push(ref)
	}	
	changeReferenceWithNewProject(newProject:Project){
		//change & add
		let newList = newProject.getReferenceList()
		let oldList = this.getReferenceList()
		this.changeLineWithNewProject(oldList,newList)
	}
	changeLineWithNewProject(oldList,newList){
		//change & add
		for(let newItem of newList){
			let isFind = false
			for(let item of oldList){
				if(newItem.getFrom()==item.getFrom() && newItem.getTo()==item.getTo()){
					if(newItem.getName()!=null)
						item.setName(newItem.getName())
					//deal with phe
					this.changePhenomenon(item.getPhenomenonList(),newItem.getPhenomenonList())
					isFind = true									
				}
			}
			if(!isFind){
				oldList.push(newItem)
			}
		}
		//delete
		for(let i = oldList.length-1; i >= 0 ;i--){
			let item = oldList[i]
			let isFind = false
			for(let newItem of newList){
				if(newItem.getFrom()==item.getFrom() && newItem.getTo()==item.getTo()){
					isFind = true
					break									
				}
			}
			if(!isFind){
				oldList.splice(i,1)
			}
		}
	}
	changeReference(old,new1){
		let i = 0
		for (let item of this.getReferenceList()) {
			if (item.getName() == old.getName()) {
				this.getReferenceList()[i]=new1
			  return
			}
		  }
	}
	getDescription(name, pheList) {
		//a:M!{on},P!{off}
		let s = "";
		s = s + name + ":";
		let s1 = "";
		let s2 = "";
		let desList = [];
		for (let phe of pheList) {
		  let flag = false;
		  for (let des of desList) {
			if (phe.phenomenon_from == des[0]) {
			  des.push(phe.phenomenon_name);
			  flag = true;
			  break;
			}
		  }
		  if (!flag) {
			desList.push([phe.phenomenon_from, phe.phenomenon_name]);
		  }
		}
		//console.log(desList);
		for (let des of desList) {
		  s += des[0] + '!{';
		  for (let item of des.slice(1)) {
			s += item + ',';
		  }
		  s = s.slice(0, -1);      
		  s += '},'
		}
		s = s.slice(0, -1);
		console.log(s);
		return s;
	}
	deleteReference(ref:Reference){
		let no = ref.reference_no
		let list = this.problemDiagram.referenceList;
		let i = 0
		for (let item of list) {
		  if (item.reference_no == no) {
			list.splice(i, 1);
			break;
		  }
		  i++;
		}
	}
}