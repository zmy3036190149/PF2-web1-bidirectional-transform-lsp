import { RequirementPhenomenon } from './RequirementPhenomenon';
import { Line } from './Line'
export class Constraint extends Line{
	constraint_no: number;	
	constraint_name: string;	
	constraint_description: string;	
	constraint_from: string;	
	constraint_to: string;
	constraint_x1: number;	
	constraint_y1: number;
	constraint_x2: number;
	constraint_y2: number;
	phenomenonList: RequirementPhenomenon[];
	static newConstraint(no, name, description,from,to,phenomenonList,x1,y1,x2,y2){
		let con = new Constraint()
		con.constraint_no=no
		con.constraint_name=name
		con.constraint_description=description
		con.constraint_from=from
		con.constraint_to=to
		if(phenomenonList)
			con.phenomenonList=phenomenonList
		else 
			con.phenomenonList = []
		con.constraint_x1=x1
		con.constraint_y1=y1
		con.constraint_x2=x2
		con.constraint_y2=y2
		return con
	}
	static newConstraintWithOld(old, phenomenonList, description){
		let con = new Constraint()
		con.constraint_no = old.constraint_no
		con.constraint_name = old.constraint_name
		con.constraint_from = old.constraint_from
		con.constraint_to = old.constraint_to
		con.constraint_x1 = old.constraint_x1
		con.constraint_y1 = old.constraint_y1
		con.constraint_x2 = old.constraint_x2
		con.constraint_y2 = old.constraint_y2
		con.constraint_description=description
		if(phenomenonList)
			con.phenomenonList=phenomenonList
		else 
			con.phenomenonList = []
		return con
	}
	static copyConstraint(old){
		let con = new Constraint()
		con.constraint_no = old.constraint_no
		con.constraint_name = old.constraint_name
		con.constraint_from = old.constraint_from
		con.constraint_to = old.constraint_to
		con.constraint_x1 = old.constraint_x1
		con.constraint_y1 = old.constraint_y1
		con.constraint_x2 = old.constraint_x2
		con.constraint_y2 = old.constraint_y2
		con.constraint_description = old.constraint_description
		if(old.phenomenonList)
			con.phenomenonList=old.phenomenonList
		else 
			con.phenomenonList = []

		return con
	}
	getNo(){return this.constraint_no}	
	setNo(no){this.constraint_no=no}

	getName(){return this.constraint_name}	
	setName(name){this.constraint_name=name}

	getDescription(){ return this.constraint_description}
	setDescription(description){ this.constraint_description=description}

	getFrom(){return this.constraint_from}
	setFrom(from){this.constraint_from=from}

	getTo(){return this.constraint_to}
	setTo(to){ this.constraint_to=to}

	getX1(){return this.constraint_x1}
	setX1(x1){ this.constraint_x1=x1}

	getY1(){return this.constraint_y1}
	setY1(y1){ this.constraint_y1=y1}

	getX2(){return this.constraint_x2}
	setX2(x2){ this.constraint_x2=x2}

	getY2(){return this.constraint_y2}
	setY2(y2){ this.constraint_y2=y2}
	getPhenomenonList(){return this.phenomenonList}
	setPhenomenonList(phenomenonList){ this.phenomenonList=phenomenonList}
}