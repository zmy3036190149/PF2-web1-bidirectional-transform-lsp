import { RequirementPhenomenon } from './RequirementPhenomenon';
import { Line } from './Line'
export class Reference extends Line{
	no: number;	
	name: string;
	description: string;	
	from: string;	
	to: string;
	x1: number;	
	y1: number;
	x2: number;
	y2: number;
	phenomenonList: RequirementPhenomenon[];	

	static newReference(no, name, description,from,to,phenomenonList,x1,y1,x2,y2){
		let ref = new Reference()
		ref.no=no
		ref.name=name
		ref.description=description
		ref.from=from
		ref.to=to
		if(phenomenonList)
			ref.phenomenonList=phenomenonList
		else 
			ref.phenomenonList = []
		ref.x1=x1
		ref.y1=y1
		ref.x2=x2
		ref.y2=y2
		return ref
	}
	static newReferenceWithOld(old, phenomenonList, description){
		let ref = new Reference()
		ref.no=old.no
		ref.name=old.name
		ref.description=description
		ref.from=old.from
		ref.to=old.to
		if(old.phenomenonList) ref.phenomenonList=old.phenomenonList
		else ref.phenomenonList = []
		ref.x1=old.x1
		ref.y1=old.y1
		ref.x2=old.x2
		ref.y2=old.y2
		return ref
	}
	static copyReference(old:Reference){
		let ref = new Reference()
		ref.no=old.no
		ref.name=old.name
		ref.description=old.description
		ref.from=old.from
		ref.to=old.to
		if(old.phenomenonList)
			ref.phenomenonList=old.phenomenonList
		else 
			ref.phenomenonList = []
		ref.x1=old.x1
		ref.y1=old.y1
		ref.x2=old.x2
		ref.y2=old.y2
		return ref
	}
	getNo(){return this.no}	
	setNo(no){this.no=no}

	getName(){return this.name}	
	setName(name){this.name=name}

	getDescription(){ return this.description}
	setDescription(description){ this.description=description}

	getFrom(){return this.from}
	setFrom(from){this.from=from}

	getTo(){return this.to}
	setTo(to){ this.to=to}

	getX1(){return this.x1}
	setX1(x1){ this.x1=x1}

	getY1(){return this.y1}
	setY1(y1){ this.y1=y1}

	getX2(){return this.x2}
	setX2(x2){ this.x2=x2}

	getY2(){return this.y2}
	setY2(y2){ this.y2=y2}
	getPhenomenonList(){return this.phenomenonList}
	setPhenomenonList(phenomenonList){ this.phenomenonList=phenomenonList}
	clearPhenomenonList(){
		this.phenomenonList.length = 0;
	}
}