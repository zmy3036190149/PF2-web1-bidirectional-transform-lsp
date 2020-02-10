import { Phenomenon } from './Phenomenon';
import { Line } from './Line'
export class Interface extends Line{
	interface_no: number;	//�������
	interface_name: string;	//��������
	interface_description: string;	//��������
	interface_from: string;
	interface_to: string;
	phenomenonList: Phenomenon[];	//�����б�
	interface_x1: number;	//λ����Ϣ
	interface_y1: number;
	interface_x2: number;
	interface_y2: number;
	static newInterface(no, name, description,from,to,phenomenonList,x1,y1,x2,y2){
		let int = new Interface()
		int.interface_no=no
		int.interface_name=name
		int.interface_description=description
		int.interface_from=from
		int.interface_to=to
		if(phenomenonList)
		int.phenomenonList=phenomenonList
		else 
		int.phenomenonList = []
		int.interface_x1=x1
		int.interface_y1=y1
		int.interface_x2=x2
		int.interface_y2=y2
		return int
	}
	static newInterfaceWithOld(old, phenomenonList, description){
		let int = new Interface()
		int.interface_no=old.interface_no
		int.interface_name=old.interface_name
		int.interface_description=description
		int.interface_from=old.interface_from
		int.interface_to=old.interface_to
		if(old.phenomenonList)
		int.phenomenonList=old.phenomenonList
		else 
		int.phenomenonList = []
		int.interface_x1=old.interface_x1
		int.interface_y1=old.interface_y1
		int.interface_x2=old.interface_x2
		int.interface_y2=old.interface_y2
		return int
	}
	static copyInterface(old){
		let int = new Interface()
		int.interface_no=old.interface_no
		int.interface_name=old.interface_name
		int.interface_description=old.interface_description
		int.interface_from=old.interface_from
		int.interface_to=old.interface_to
		if(old.phenomenonList)
		int.phenomenonList=old.phenomenonList
		else 
		int.phenomenonList = []
		int.interface_x1=old.interface_x1
		int.interface_y1=old.interface_y1
		int.interface_x2=old.interface_x2
		int.interface_y2=old.interface_y2
		return int
	}
	getNo(){return this.interface_no}	
	setNo(no){this.interface_no=no}

	getName(){return this.interface_name}	
	setName(name){this.interface_name=name}

	getDescription(){ return this.interface_description}
	setDescription(description){ this.interface_description=description}

	getFrom(){return this.interface_from}
	setFrom(from){this.interface_from=from}

	getTo(){return this.interface_to}
	setTo(to){ this.interface_to=to}

	getX1(){return this.interface_x1}
	setX1(x1){ this.interface_x1=x1}

	getY1(){return this.interface_y1}
	setY1(y1){ this.interface_y1=y1}

	getX2(){return this.interface_x2}
	setX2(x2){ this.interface_x2=x2}

	getY2(){return this.interface_y2}
	setY2(y2){ this.interface_y2=y2}
	getPhenomenonList(){return this.phenomenonList}
	setPhenomenonList(phenomenonList){ this.phenomenonList=phenomenonList}
}