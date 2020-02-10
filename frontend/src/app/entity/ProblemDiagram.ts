import { ContextDiagram } from './ContextDiagram'
import { Requirement } from './Requirement'
import { Constraint } from './Constraint'
import { Reference } from './Reference'

export class ProblemDiagram {
	title: string
	contextDiagram: ContextDiagram
	requirementList: Requirement[]
	constraintList: Constraint[]
	referenceList: Reference[]
	static copyProblemDiagram(old:ProblemDiagram,contextDiagram: ContextDiagram):ProblemDiagram{
		let new1 = new ProblemDiagram()
		new1.title = old.title
		new1.contextDiagram = contextDiagram
		new1.requirementList = new Array<Requirement>()
		if(old.requirementList!=null){
			for(let oldItem of old.requirementList){
				let item = Requirement.copyRequirement(oldItem)
				new1.requirementList.push(item)
			}			
		}
		new1.constraintList = new Array<Constraint>()
		if(old.constraintList!=null){
			for(let oldItem of old.constraintList){
				let item = Constraint.copyConstraint(oldItem)
				new1.constraintList.push(item)
			}			
		}
		new1.referenceList = new Array<Reference>()
		if(old.referenceList!=null){
			for(let oldItem of old.referenceList){
				let item = Reference.copyReference(oldItem)
				new1.referenceList.push(item)
			}			
		}			
		return new1
	}
}