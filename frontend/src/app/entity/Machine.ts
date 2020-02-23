export class Machine{
	name: string;	
	shortname: string;	
	x: number;	
	y: number;
	h: number;
	w: number;
	static newMachine(name,shortname,x,y,w,h){
		let  machine =  new Machine()
		machine.name=name
		machine.shortname=shortname
		machine.x=x
		machine.y=y
		machine.h=h
		machine.w=w
		return machine
	}
	static newMachineWithOld(old, name,shortname){
		let  machine =  new Machine()
		machine.name=name
		machine.shortname=shortname
		machine.x=old.x
		machine.y=old.y
		machine.h=old.h
		machine.w=old.w
		return machine
	}
	static copyMachine(old){
		let  machine =  new Machine()
		machine.name=old.name
		machine.shortname=old.shortname
		machine.x=old.x
		machine.y=old.y
		machine.h=old.h
		machine.w=old.w
		return machine
	}
	getName(){return this.name}
	setName(name){this.name=name}

	getShortname(){return this.shortname}
	setShortname(shortname){this.shortname=shortname}
	
	getX(){return this.x}
    getY(){return this.y}
    getH(){return this.h}
	getW(){return this.w}
	
    setX(x){this.x=x}
    setY(y){this.y=y}
    setH(h){this.h=h}
	setW(w){this.w=w}
}