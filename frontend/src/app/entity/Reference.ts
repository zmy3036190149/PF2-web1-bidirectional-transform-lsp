import { RequirementPhenomenon } from './RequirementPhenomenon';
import { Line } from './Line'
export class Reference extends Line{
	reference_no: number;	
	reference_name: string;
	reference_description: string;	
	reference_from: string;	
	reference_to: string;
	reference_x1: number;	
	reference_y1: number;
	reference_x2: number;
	reference_y2: number;
	phenomenonList: RequirementPhenomenon[];	

	static newReference(no, name, description,from,to,phenomenonList,x1,y1,x2,y2){
		let ref = new Reference()
		ref.reference_no=no
		ref.reference_name=name
		ref.reference_description=description
		ref.reference_from=from
		ref.reference_to=to
		if(phenomenonList)
			ref.phenomenonList=phenomenonList
		else 
			ref.phenomenonList = []
		ref.reference_x1=x1
		ref.reference_y1=y1
		ref.reference_x2=x2
		ref.reference_y2=y2
		return ref
	}
	static newReferenceWithOld(old, phenomenonList, description){
		let ref = new Reference()
		ref.reference_no=old.reference_no
		ref.reference_name=old.reference_name
		ref.reference_description=description
		ref.reference_from=old.reference_from
		ref.reference_to=old.reference_to
		if(phenomenonList)
			ref.phenomenonList=phenomenonList
		else 
			ref.phenomenonList = []
		ref.reference_x1=old.reference_x1
		ref.reference_y1=old.reference_y1
		ref.reference_x2=old.reference_x2
		ref.reference_y2=old.reference_y2
		return ref
	}
	static copyReference(old){
		let ref = new Reference()
		ref.reference_no=old.reference_no
		ref.reference_name=old.reference_name
		ref.reference_description=old.reference_description
		ref.reference_from=old.reference_from
		ref.reference_to=old.reference_to
		if(ref.phenomenonList)
			ref.phenomenonList=ref.phenomenonList
		else 
			ref.phenomenonList = []
		ref.reference_x1=old.reference_x1
		ref.reference_y1=old.reference_y1
		ref.reference_x2=old.reference_x2
		ref.reference_y2=old.reference_y2
		return ref
	}
	getNo(){return this.reference_no}	
	setNo(no){this.reference_no=no}

	getName(){return this.reference_name}	
	setName(name){this.reference_name=name}

	getDescription(){ return this.reference_description}
	setDescription(description){ this.reference_description=description}

	getFrom(){return this.reference_from}
	setFrom(from){this.reference_from=from}

	getTo(){return this.reference_to}
	setTo(to){ this.reference_to=to}

	getX1(){return this.reference_x1}
	setX1(x1){ this.reference_x1=x1}

	getY1(){return this.reference_y1}
	setY1(y1){ this.reference_y1=y1}

	getX2(){return this.reference_x2}
	setX2(x2){ this.reference_x2=x2}

	getY2(){return this.reference_y2}
	setY2(y2){ this.reference_y2=y2}
	getPhenomenonList(){return this.phenomenonList}
	setPhenomenonList(phenomenonList){ this.phenomenonList=phenomenonList}
}