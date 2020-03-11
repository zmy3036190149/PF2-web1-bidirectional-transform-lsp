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
			console.log(this.contextDiagram.interfaceList.length)
			console.log(this.contextDiagram.problemDomainList.length)
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
	addMachine(name, shortname, x, y,w,h){
		this.contextDiagram.machine = new Machine()
		this.contextDiagram.machine.name = name
		this.contextDiagram.machine.shortname = shortname
		this.contextDiagram.machine.x = x
		this.contextDiagram.machine.y = y		
		this.contextDiagram.machine.w = w
		this.contextDiagram.machine.h = h
		this.problemDiagram.contextDiagram.machine = this.contextDiagram.machine		
		return this.contextDiagram.machine
	}

	changeMachineWithNewProject(newProject:Project) {
		let name = newProject.getMachine().getName()
		let shortname = newProject.getMachine().getShortname()
		this.changeMachine(name,shortname)
		return this.contextDiagram.machine
	}
	changeMachine(name, shortname) {
		this.contextDiagram.machine.name = name;
		this.contextDiagram.machine.shortname = shortname;
		this.problemDiagram.contextDiagram.machine.name = name;
		this.problemDiagram.contextDiagram.machine.shortname = shortname
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
			this.contextDiagram.machine.x = position.x;
			this.contextDiagram.machine.y = position.y;
			this.problemDiagram.contextDiagram.machine.x = position.x;
			this.problemDiagram.contextDiagram.machine.y = position.y;
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
	addProblemDomain(no, name, shortname, type, property, x, y,w,h) {
		let problemDomain = new ProblemDomain();
		problemDomain.no = no;
		problemDomain.name = name;
		problemDomain.shortname = shortname;
		problemDomain.type = type;
		problemDomain.property = property;
		problemDomain.x = x;
		problemDomain.y = y;    
		problemDomain.w = w;
		problemDomain.h = h;
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
					if(newItem.getShortname()!=item.getShortname()){
						item.setShortname(newItem.getShortname())
						item.setProperty(newItem.getProperty())	
						console.log("change shortname")
					}					
					isFind = true									
				}else if(newItem.getShortname()==item.getShortname()){
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
			if(item.name==old.getName()){
				this.getProblemDomainList()[i] = new1
				// console.log(this.problemDiagram)
				return
			}	
			i += 1 			
		}
	}
	changeProblemDomain(old,description, shortname, domainType, physicalProperty) {
		for(let item of this.getProblemDomainList()){
			if(item.getName()==old.getName()){
				item.setName(description)
				item.setShortname(shortname)
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
		  if (item.name == name) {
			item.x = position.x;
			item.y = position.y;
			// console.log("changeProblemDomainPosition")
			// console.log(item)
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
		requirement.no = no;
		requirement.name = context;
		requirement.x = x;
		requirement.y = y;
		requirement.w = w;
		requirement.h = h;
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
				item.setShortname(description)
				break
			}				
		}
	}
	changeRequirementPosition(name,position) {
		for (let item of this.getRequirementList()) {
		  if (item.name == name) {
			item.x = position.x;
			item.y = position.y;
			return true
		  }
		}
		return false
	}
	deleteRequirement(requirement:Requirement){
		let name = requirement.name;
    	let list = this.problemDiagram.requirementList;
		let i = list.length - 1;
		for (; i >= 0; i--) {
		let item = list[i];
		if (item.name == name) {

			list.splice(i, 1);
			break;
		}
		}
	}

	//link
	// deleteRelatedLink(shortname) {
	// 	console.log('deleteRelatedLink,shortname=' + shortname);
	// 	let i = this.getReferenceList().length - 1;
	// 	for (; i >= 0; i--) {
	// 	  let reference = this.problemDiagram.referenceList[i];
	// 	  if (reference.from == shortname || reference.to == shortname) {
	// 		console.log(reference)
	// 		let name = reference.name;
	// 		this.problemDiagram.referenceList.splice(i, 1);
	// 	  }
	// 	}
	// 	i = this.problemDiagram.constraintList.length - 1;
	// 	for (; i >= 0; i--) {
	// 	  let constraint = this.problemDiagram.constraintList[i];
	// 	  if (constraint.from == shortname || constraint.to == shortname) {
	// 		console.log(constraint)
	// 		this.problemDiagram.constraintList.splice(i, 1);
	// 	  }
	// 	}
	// 	i = this.contextDiagram.interfaceList.length - 1;
	// 	for (; i >= 0; i--) {
	// 	  let my_interface = this.contextDiagram.interfaceList[i];
	// 	  if (my_interface.from == shortname || my_interface.to == shortname) {
	// 		console.log(my_interface)
	// 		this.contextDiagram.interfaceList.splice(i, 1);
	// 	  }
	// 	}
	// }
	
	deleteRelatedLink(shortname) {
		let interfaceList = this.contextDiagram.interfaceList
		let referenceList = this.problemDiagram.referenceList
		let constraintList = this.problemDiagram.constraintList
		let deleteInterfaceList = new Array<Interface>();
		let deleteReferenceList = new Array<Reference>();
		let deleteConstraintList = new Array<Constraint>();

		//delete interface
		let i = 0;
		for(let item of interfaceList){
			if(item.from === shortname || item.to === shortname){
				interfaceList[i].clearPhenomenonList();
				deleteInterfaceList.push(interfaceList[i]);
			}
			i++
		}
		interfaceList = interfaceList.filter(item => !deleteInterfaceList.includes(item))

		//delete references
		i = 0;
		for(let item of referenceList){
			if(item.from === shortname || item.to === shortname){
				referenceList[i].clearPhenomenonList();
				deleteReferenceList.push(referenceList[i]);
			}
			i++
		}
		referenceList = referenceList.filter(item => !deleteReferenceList.includes(item))

		//delete constraints
		i = 0;
		for(let item of constraintList){
			if(item.from === shortname || item.to === shortname){
				constraintList[i].clearPhenomenonList();
				deleteConstraintList.push(constraintList[i]);
			}
			i++
		}
		constraintList = constraintList.filter(item => !deleteConstraintList.includes(item))
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
				if(newItem.name==item.name){
					item.type = newItem.type
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
				if(newItem.name==item.name){
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
		let no = int.no;
		let list = this.contextDiagram.interfaceList;
		let i = 0
		for (let item of list) {
		if (item.no == no) {
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
	addConstraint(con){
		this.problemDiagram.constraintList.push(con)
	}
	changeConstraintWithNewProject(newProject:Project){
		//change & add
		let newList = newProject.getConstraintList()
		let oldList = this.getConstraintList()
		this.changeLineWithNewProject(oldList,newList)
	}

	changeConstraint(old:Constraint,new1){
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
		let no = con.no
		let list = this.problemDiagram.constraintList;
		let i = 0
		for (let item of list) {
		  if (item.no == no) {
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
	setDescription(line) {
		let name = line.getName()
		let pheList = line.getPhenomenonList()
		//a:M!{on},P!{off}
		let s = "";
		s = s + name + ":";
		let s1 = "";
		let s2 = "";
		let desList = [];
		for (let phe of pheList) {
		  let flag = false;
		  for (let des of desList) {
			if (phe.from == des[0]) {
			  des.push(phe.name);
			  flag = true;
			  break;
			}
		  }
		  if (!flag) {
			desList.push([phe.from, phe.name]);
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
		line.setDescription(s);
		return s;
	}
	deleteReference(ref:Reference){
		let no = ref.no
		let list = this.problemDiagram.referenceList;
		let i = 0
		for (let item of list) {
		  if (item.no == no) {
			list.splice(i, 1);
			break;
		  }
		  i++;
		}
	}
}