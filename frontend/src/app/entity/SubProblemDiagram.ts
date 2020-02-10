import { Machine } from './Machine';
import { ProblemDomain } from './ProblemDomain';
import { Requirement } from './Requirement';
import { Interface } from './Interface';
import { Constraint } from './Constraint';
import { Reference } from './Reference';

export class SubProblemDiagram{
	title: string;	//�ļ���
	machine: Machine;	//����
	problemDomainList: ProblemDomain[];	//�����б�
	requirement: Requirement;	//����
	interfaceList: Interface[];	//�����б�
	constraintList: Constraint[];	//Լ���б�
	referenceList: Reference[];	//�����б�
}