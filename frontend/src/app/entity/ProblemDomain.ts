import { Phenomenon } from './Phenomenon';
import { Node } from './Node'
export class ProblemDomain extends Node{
	no: number;	
	name: string;	
	shortname: string;	
	type: string;	
	property: string;	
	x: number;	
	y: number;	
	h: number;	
	w: number;
	phes: Phenomenon[]
	static newProblemDomain(no,name, shortname, type, property,x,y,w,h){
		let pd = new ProblemDomain()
		pd.no = no
		pd.name=name
		pd.shortname=shortname
		pd.type=type
		pd.property=property
		pd.x = x
		pd.y = y
		pd.h = h
		pd.w = w
		return pd
	}
	static newProblemDomainWithOld(old:ProblemDomain, name, shortname, type, property){
		let pd = new ProblemDomain()
		pd.no = old.no
		pd.x = old.x
		pd.y = old.y
		pd.h = old.h
		pd.w = old.w

		pd.name=name
		pd.shortname=shortname
		pd.type=type
		pd.property=property
		return pd
	}
	static copyProblemDomain(old:ProblemDomain){
		let pd = new ProblemDomain()
		pd.no = old.no
		pd.x = old.x
		pd.y = old.y
		pd.h = old.h
		pd.w = old.w

		pd.name=old.name
		pd.shortname=old.shortname
		pd.type=old.type
		pd.property=old.property
		return pd
	}
	getNo(){return this.no	}
    getName(){return this.name}
    getShortname(){return this.shortname}
	getX(){return this.x}
    getY(){return this.y}
    getH(){return this.h}
	getW(){return this.w}
	getProperty(){return this.property}
	getType(){return this.type}
	setNo(no){this.no=no}
    setName(name){this.name=name}
    setShortname(shortname){this.shortname=shortname}
	setX(x){this.x=x}
    setY(y){this.y=y}
    setH(h){this.h=h}
	setW(w){this.w=w}
	setProperty(property){this.property=property}
	setType(type){this.type=type}
}
