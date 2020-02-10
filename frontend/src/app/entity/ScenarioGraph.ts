import { Node } from './Node';
import { CtrlNode } from './CtrlNode';
import { Line } from './Line';

export class ScenarioGraph {
	title: string;	//�ļ���
	intNodeList: Node[];	//�ڵ��б�
	ctrlNodeList: CtrlNode[];	//�ڵ��б�
	lineList: Line[];	//���б�
}