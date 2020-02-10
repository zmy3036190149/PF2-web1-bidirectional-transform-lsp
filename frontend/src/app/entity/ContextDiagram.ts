import { Machine } from './Machine'
import { ProblemDomain } from './ProblemDomain'
import { Interface } from './Interface'
import { OptionalDecorator } from '@angular/core';

export class ContextDiagram {
	title: string
	machine: Machine
	problemDomainList: ProblemDomain[]
	interfaceList: Interface[]
	static copyContextDiagram(old:ContextDiagram):ContextDiagram{
		let new1 = new ContextDiagram()
		new1.title = old.title
		new1.machine = Machine.copyMachine(old.machine)
		new1.problemDomainList = new Array<ProblemDomain>()
		if(old.problemDomainList!=null){
			for(let oldPd of old.problemDomainList){
				let pd = ProblemDomain.copyProblemDomain(oldPd)
				new1.problemDomainList.push(pd)
			}			
		}
		new1.interfaceList = new Array<Interface>()
		if(old.interfaceList!=null){
			for(let oldInt of old.interfaceList){
				let int = Interface.copyInterface(oldInt)
				new1.interfaceList.push(int)
			}			
		}		
		return new1
	}
}