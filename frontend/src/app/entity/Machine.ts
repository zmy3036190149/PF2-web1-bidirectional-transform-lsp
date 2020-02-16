export class Machine{
	name: string;	//��������
	shortName: string;	//������д
	x: number;	//λ����Ϣ
	y: number;
	h: number;
	w: number;
	static newMachine(name,shortName,x,y,w,h){
		let  machine =  new Machine()
		machine.name=name
		machine.shortName=shortName
		machine.x=x
		machine.y=y
		machine.h=h
		machine.w=w
		return machine
	}
	static newMachineWithOld(old, name,shortName){
		let  machine =  new Machine()
		machine.name=name
		machine.shortName=shortName
		machine.x=old.x
		machine.y=old.y
		machine.h=old.h
		machine.w=old.w
		return machine
	}
	static copyMachine(old){
		let  machine =  new Machine()
		machine.name=old.name
		machine.shortName=old.shortName
		machine.x=old.x
		machine.y=old.y
		machine.h=old.h
		machine.w=old.w
		return machine
	}
	getName(){return this.name}
	setName(name){this.name=name}

	getShortName(){return this.shortName}
	setShortName(shortName){this.shortName=shortName}
	
	getX(){return this.x}
    getY(){return this.y}
    getH(){return this.h}
	getW(){return this.w}
	
    setX(x){this.x=x}
    setY(y){this.y=y}
    setH(h){this.h=h}
	setW(w){this.w=w}
}