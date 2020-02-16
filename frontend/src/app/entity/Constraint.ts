import { RequirementPhenomenon } from './RequirementPhenomenon';
import { Line } from './Line'
export class Constraint extends Line{
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
	static newConstraint(no, name, description,from,to,phenomenonList,x1,y1,x2,y2){
		let con = new Constraint()
		con.no=no
		con.name=name
		con.description=description
		con.from=from
		con.to=to
		if(phenomenonList)
			con.phenomenonList=phenomenonList
		else 
			con.phenomenonList = []
		con.x1=x1
		con.y1=y1
		con.x2=x2
		con.y2=y2
		return con
	}
	static newConstraintWithOld(old, phenomenonList, description){
		let con = new Constraint()
		con.no = old.no
		con.name = old.name
		con.from = old.from
		con.to = old.to
		con.x1 = old.x1
		con.y1 = old.y1
		con.x2 = old.x2
		con.y2 = old.y2
		con.description=description
		if(phenomenonList)
			con.phenomenonList=phenomenonList
		else 
			con.phenomenonList = []
		return con
	}
	static copyConstraint(old){
		let con = new Constraint()
		con.no = old.no
		con.name = old.name
		con.from = old.from
		con.to = old.to
		con.x1 = old.x1
		con.y1 = old.y1
		con.x2 = old.x2
		con.y2 = old.y2
		con.description = old.description
		if(old.phenomenonList)
			con.phenomenonList=old.phenomenonList
		else 
			con.phenomenonList = []

		return con
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
}