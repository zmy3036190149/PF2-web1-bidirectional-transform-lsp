export class Requirement {
	no: number;
	name: string;	
	shortname: string;
	x: number;	
	y: number;
	h: number;
	w: number;
	static newRequirement(no,name,x,y,h,w){
		let req = new Requirement()
		req.no=no
		req.name=name
		req.shortname = getShortname(name)
		req.x=x
		req.y=y
		req.h=h
		req.w=w
		return req
	}
	static newRequirementWithOld(old:Requirement,name,shortname){
		let req = new Requirement()
		req.no = old.no
		req.name = name	
		req.shortname = shortname	
		req.x = old.x
		req.y = old.y
		req.h = old.h
		req.w = old.w
		return req
	}
	static copyRequirement(old:Requirement){
		let req = new Requirement()
		req.no = old.no
		req.name = old.name	
		req.shortname = old.shortname?old.shortname:getShortname(old.name)
		req.x = old.x
		req.y = old.y
		req.h = old.h
		req.w = old.w
		return req
	}
	getNo(){return this.no}	
	setNo(no){this.no=no}
	getName(){return this.name}	
    setName(name){this.name=name}
	getShortname(){return this.shortname}	
    setShortname(shortname){this.shortname=shortname}
	getX(){return this.x}
	setX(x){this.x=x}
    getY(){return this.y}
    setY(y){this.y=y}
    getH(){return this.h}
    setH(h){this.h=h}
	getW(){return this.w}
	setW(w){this.w=w}
}
function getShortname(name:string){
	return name.replace(/\s+/g,""); 
}