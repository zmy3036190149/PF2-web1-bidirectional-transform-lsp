import { Phenomenon } from './Phenomenon';
import { Line } from './Line'
export class Interface extends Line{
	no: number;	
	name: string;	
	description: string;	
	from: string;
	to: string;
	phenomenonList: Phenomenon[];	
	x1: number;	
	y1: number;
	x2: number;
	y2: number;
	static newInterface(no, name, description,from,to,phenomenonList,x1,y1,x2,y2){
		let int = new Interface()
		int.no=no
		int.name=name
		int.description=description
		int.from=from
		int.to=to
		if(phenomenonList)
		int.phenomenonList=phenomenonList
		else 
		int.phenomenonList = []
		int.x1=x1
		int.y1=y1
		int.x2=x2
		int.y2=y2
		return int
	}
	static newInterfaceWithOld(old, description){
		let int = new Interface()
		int.no=old.no
		int.name=old.name
		int.description=description
		int.from=old.from
		int.to=old.to
		if(old.phenomenonList)
		int.phenomenonList=old.phenomenonList
		else 
		int.phenomenonList = []
		int.x1=old.x1
		int.y1=old.y1
		int.x2=old.x2
		int.y2=old.y2
		return int
	}
	static copyInterface(old){
		let int = new Interface()
		int.no=old.no
		int.name=old.name
		int.description=old.description
		int.from=old.from
		int.to=old.to
		if(old.phenomenonList)
		int.phenomenonList=old.phenomenonList
		else 
		int.phenomenonList = []
		int.x1=old.x1
		int.y1=old.y1
		int.x2=old.x2
		int.y2=old.y2
		return int
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
	clearPhenomenonList(){	this.phenomenonList.length = 0	}
}