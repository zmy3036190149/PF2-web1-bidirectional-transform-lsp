import { Node } from './Node';

export class CtrlNode extends Node {
	node_fromList: Node[];	
	node_toList: Node[];
	node_text: string;	//判断
	node_consition1: string;	//分支条件
	node_consition2: string;
}