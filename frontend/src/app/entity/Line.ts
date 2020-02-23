import { ILine } from './ILine';
import { IShape } from './IShape';
export abstract class Line implements ILine,IShape{	
	getNo(){}
    getName(){return ""}
	getShortname() {
		throw new Error("Method not implemented.");
	}
    getDescription(){return ""}
	getFrom(){return ""}
	getTo(){return ""}
	getX1(){}
	getY1(){}
	getX2(){}
	getY2(){}
	getPhenomenonList(){}
}