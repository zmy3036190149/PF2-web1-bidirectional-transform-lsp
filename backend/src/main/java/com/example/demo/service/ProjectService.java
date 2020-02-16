package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.bean.Constraint;
import com.example.demo.bean.ContextDiagram;
import com.example.demo.bean.Interface;
import com.example.demo.bean.Line;
import com.example.demo.bean.Machine;
import com.example.demo.bean.Node;
import com.example.demo.bean.Phenomenon;
import com.example.demo.bean.ProblemDiagram;
import com.example.demo.bean.ProblemDomain;
import com.example.demo.bean.Project;
import com.example.demo.bean.Reference;
import com.example.demo.bean.Requirement;
import com.example.demo.bean.RequirementPhenomenon;
import com.example.demo.bean.SubProblemDiagram;

import com.example.demo.bean.Error;
import com.example.demo.bean.IntInfo;

@Service
public class ProjectService {

	//获取现象列表
	public List<Phenomenon> getPhenomenon(Project project) {
		// TODO Auto-generated method stub
		List<Phenomenon> phenomenonList = new ArrayList<Phenomenon>();
		List<Interface> interfaceList = project.getContextDiagram().getInterfaceList();
		List<Reference> referenceList = project.getProblemDiagram().getReferenceList();
		List<Constraint> constraintList = project.getProblemDiagram().getConstraintList();
		for(int i = 0; i < interfaceList.size(); i ++ ) {
			Interface inte = interfaceList.get(i);
			phenomenonList.addAll(inte.getPhenomenonList());
		}
		for(int i = 0; i < referenceList.size(); i ++ ) {
			Reference reference = referenceList.get(i);
			phenomenonList.addAll(reference.getPhenomenonList());
		}
		for(int i = 0; i < constraintList.size(); i ++ ) {
			Constraint constraint = constraintList.get(i);
			phenomenonList.addAll(constraint.getPhenomenonList());
		}
		for  ( int  i  =   0 ; i  <  phenomenonList.size()  -   1 ; i ++ )  {       
		      for  ( int  j  =  phenomenonList.size()  -   1 ; j  >  i; j -- )  {       
		           if  (phenomenonList.get(j).getPhenomenon_no() == phenomenonList.get(i).getPhenomenon_no())  {       
		        	   phenomenonList.remove(j);       
		            }        
		        }        
		      }      
		for(int i = 0; i < phenomenonList.size(); i++) {
	   		 for (int j = 0; j < phenomenonList.size() -  i - 1; j++)
	   		 {
	   			 Phenomenon phe1 = phenomenonList.get(j);
	   			 Phenomenon phe2 = phenomenonList.get(j+1);
	   			 if(phe1.getPhenomenon_no() > phe2.getPhenomenon_no()) {
	   				 Phenomenon phe;
	   				 phe = phe1;
	   				 phenomenonList.set(j, phe2);
	   				 phenomenonList.set(j+1, phe);
	   			 }
	   		 }
	   	 }
		return phenomenonList;
	}
	
	//获取现象列表
	public List<Phenomenon> getPhenomenon(SubProblemDiagram spd) {
			// TODO Auto-generated method stub
			List<Phenomenon> phenomenonList = new ArrayList<Phenomenon>();
			List<Interface> interfaceList = spd.getInterfaceList();
			List<Reference> referenceList = spd.getReferenceList();
			List<Constraint> constraintList = spd.getConstraintList();
			for(int i = 0; i < interfaceList.size(); i ++ ) {
				Interface inte = interfaceList.get(i);
				phenomenonList.addAll(inte.getPhenomenonList());
			}
			for(int i = 0; i < referenceList.size(); i ++ ) {
				Reference reference = referenceList.get(i);
				phenomenonList.addAll(reference.getPhenomenonList());
			}
			for(int i = 0; i < constraintList.size(); i ++ ) {
				Constraint constraint = constraintList.get(i);
				phenomenonList.addAll(constraint.getPhenomenonList());
			}
			for  ( int  i  =   0 ; i  <  phenomenonList.size()  -   1 ; i ++ )  {       
			      for  ( int  j  =  phenomenonList.size()  -   1 ; j  >  i; j -- )  {       
			           if  (phenomenonList.get(j).getPhenomenon_no() == phenomenonList.get(i).getPhenomenon_no())  {       
			        	   phenomenonList.remove(j);       
			            }        
			        }        
			      }      
			for(int i = 0; i < phenomenonList.size(); i++) {
		   		 for (int j = 0; j < phenomenonList.size() -  i - 1; j++)
		   		 {
		   			 Phenomenon phe1 = phenomenonList.get(j);
		   			 Phenomenon phe2 = phenomenonList.get(j+1);
		   			 if(phe1.getPhenomenon_no() > phe2.getPhenomenon_no()) {
		   				 Phenomenon phe;
		   				 phe = phe1;
		   				 phenomenonList.set(j, phe2);
		   				 phenomenonList.set(j+1, phe);
		   			 }
		   		 }
		   	 }
			return phenomenonList;
		}

	//获取约束引用列表
	public List<RequirementPhenomenon> getRequirementPhenomenon(Project project) {
		// TODO Auto-generated method stub
		List<RequirementPhenomenon> phenomenonList = new ArrayList<RequirementPhenomenon>();
		List<Reference> referenceList = project.getProblemDiagram().getReferenceList();
		List<Constraint> constraintList = project.getProblemDiagram().getConstraintList();
		for(int i = 0; i < referenceList.size(); i ++ ) {
			Reference reference = referenceList.get(i);
			phenomenonList.addAll(reference.getPhenomenonList());
		}
		for(int i = 0; i < constraintList.size(); i ++ ) {
			Constraint constraint = constraintList.get(i);
			phenomenonList.addAll(constraint.getPhenomenonList());
		}
		for (int  i  =  0; i < phenomenonList.size() - 1; i ++ )  {
			for (int j = phenomenonList.size() - 1 ;j > i; j -- )  {       
				if(phenomenonList.get(j).getPhenomenon_no() == phenomenonList.get(i).getPhenomenon_no()
						&& phenomenonList.get(j).getPhenomenon_requirement() == phenomenonList.get(i).getPhenomenon_requirement()){       
					phenomenonList.remove(j);       
				}        
			}        
		}      
		for(int i = 0; i < phenomenonList.size(); i++) {
	   		 for (int j = 0; j < phenomenonList.size() -  i - 1; j++)
	   		 {
	   			RequirementPhenomenon phe1 = phenomenonList.get(j);
	   			RequirementPhenomenon phe2 = phenomenonList.get(j+1);
	   			 if(phe1.getPhenomenon_requirement() > phe2.getPhenomenon_requirement()) {
	   				RequirementPhenomenon phe = phe1;
	   				 phenomenonList.set(j, phe2);
	   				 phenomenonList.set(j+1, phe);
	   			 }
	   		 }
	   	 }
		return phenomenonList;
	}
	
	
	//上下文图正确性检测
	public List<Error> checkCorrectContext(Project project){
		List<Error> errorList = new ArrayList<Error>();
		ContextDiagram CD=project.getContextDiagram();
		errorList.add(check_Machine(CD));
		errorList.add(check_ProblemDomain(CD));
		errorList.add(check_Interface(CD));
		return errorList;
	}
	
	
	//问题图正确性检测
	public List<Error> checkCorrectProblem(Project project){
		List<Error> errorList = new ArrayList<Error>();
		ProblemDiagram PD=project.getProblemDiagram();
		errorList.add(check_Requirement(PD));
		errorList.add(check_Reference(PD));
		errorList.add(check_Constraint(PD));
		return errorList;
	}
	
	
	//Machine检测
	private Error check_Machine(ContextDiagram CD) {		
		Error error = new Error();
		List<String> errorList = new ArrayList<String>();
		error.setTitle(null);
		Machine CDM=CD.getMachine();
		if(CDM==null) {
			String errMsg = "Does not exist machine.";
			errorList.add(errMsg);
			error.setErrorList(errorList);
			return error;
		}
		String M_name=CDM.getName();
		if(M_name.contains("?")) {
			String errMsg = "machine's name is illegal.";
			errorList.add(errMsg);
			error.setErrorList(errorList);
			return error;
		}
		error.setErrorList(errorList);
		return error;
	}
	
	//ProblemDomain检测
	private Error check_ProblemDomain(ContextDiagram CD) {
		Error error = new Error();
		List<String> errorList = new ArrayList<String>();
		error.setTitle(null);
		int PDnum=0;
		List <ProblemDomain> CDPD=CD.getProblemDomainList();
		for(int i=0;i<CDPD.size();i++) {
			ProblemDomain PD_check=CDPD.get(i);
			String PD_name=PD_check.getName();
			if(PD_name.contains("?")) {
				String errMsg = "ProblemDomain's name is illegal.";
				errorList.add(errMsg);
				error.setErrorList(errorList);
				return error;
			}
			PDnum++;
		}
		if(PDnum==0) {
			String errMsg = "Does not have ProblemDomain.";
			errorList.add(errMsg);
			error.setErrorList(errorList);
			return error;
		}
		error.setErrorList(errorList);
		return error;
	}
	
	//Interface检测
	private Error check_Interface(ContextDiagram CD) {
		Error error = new Error();
		List<String> errorList = new ArrayList<String>();
		error.setTitle(null);
		int Inum=0;
		List <ProblemDomain> CDPD=CD.getProblemDomainList();
		List<Interface> CDI=CD.getInterfaceList();
		for(int i=0;i<CDPD.size();i++) {
			int hasconnect=0;
			ProblemDomain PD_check=CDPD.get(i);
			String PD_name=PD_check.getShortname();
			for(int j=0;j<CDI.size();j++) {
				Interface I_check=CDI.get(j);
				String I_name=I_check.getDescription();
				if(I_check.getPhenomenonList().size()==0) {
					String errMsg = "Exist undefined interface.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				if(I_name==null) {
					String errMsg = "Exist undefined interface.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				if(I_name.contains("?")) {
					String errMsg = "Exist undefined interface.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				Inum++;
				String I_from=I_check.getFrom();
				String I_to=I_check.getTo();
				if(I_from==null||I_to==null) {
					String errMsg = "Interface does not have from or to shape.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				if(I_from.equals(PD_name)) {
					hasconnect=1;
				}
				else if(I_to.equals(PD_name)) {
					hasconnect=1;
				}
			}
			if(hasconnect==0) {
				String errMsg = "Exist Unconnected ProblemDomain.";
				errorList.add(errMsg);
				error.setErrorList(errorList);
				return error;
			}
		}
		if(Inum==0) {
			String errMsg = "Does not have Interface.";
			errorList.add(errMsg);
			error.setErrorList(errorList);
			return error;
		}
		error.setErrorList(errorList);
		return error;
	}
	
	//Requirement检测
	private Error check_Requirement(ProblemDiagram PD) {
		Error error = new Error();
		List<String> errorList = new ArrayList<String>();
		error.setTitle(null);
		int Rnum=0;
		List <Requirement> PDR=PD.getRequirementList();
		for(int i=0;i<PDR.size();i++) {
			Requirement R_check=PDR.get(i);
			String R_name=R_check.getName();
			if(R_name.contains("?")) {
				String errMsg = "Requirement's name is illegal.";
				errorList.add(errMsg);
				error.setErrorList(errorList);
				return error;
			}
			Rnum++;
		}
		if(Rnum==0) {
			String errMsg = "Does not have Requirement.";
			errorList.add(errMsg);
			error.setErrorList(errorList);
			return error;
		}
		error.setErrorList(errorList);
		return error;
	}
	
	//Reference检测
	private Error check_Reference(ProblemDiagram PD) {
		Error error = new Error();
		List<String> errorList = new ArrayList<String>();
		int RCnum=0;
		List <Requirement> PDPD=PD.getRequirementList();
		List<Reference> PDR=PD.getReferenceList();
		List <Constraint> PDC=PD.getConstraintList();
		for(int i=0;i<PDPD.size();i++) {
			int hasconnect=0;
			Requirement R_check=PDPD.get(i);
			String R_name=R_check.getName();
			for(int j=0;j<PDR.size();j++) {
				Reference Rr_check=PDR.get(j);
				String Rr_name=Rr_check.getDescription();
				if(Rr_check.getPhenomenonList().size()==0) {
					String errMsg = "Exist undefine reference.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				if(Rr_name==null) {
					String errMsg = "Exist undefine reference.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				if(Rr_name.contains("?")) {
					String errMsg = "Reference's name is illegal.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				RCnum++;
				String Rr_from=Rr_check.getFrom();
				String Rr_to=Rr_check.getTo();
				if(Rr_from==null||Rr_to==null) {
					String errMsg = "Reference does not have from or to shape.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				if(Rr_from.equals(R_name)) {
					hasconnect=1;
				}
				else if(Rr_to.contentEquals(R_name)) {
					hasconnect=1;
				}
			}
			for(int j=0;j<PDC.size();j++) {
				Constraint C_check=PDC.get(j);
				String C_name=C_check.getDescription();
				if(C_name.contains("?")) {
					String errMsg = "Constraint's name is illegal.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				RCnum++;
				String C_from=C_check.getFrom();
				String C_to=C_check.getTo();
				if(C_from==null||C_to==null) {
					String errMsg = "Constraint does not have from or to shape.";
					errorList.add(errMsg);
					error.setErrorList(errorList);
					return error;
				}
				if(C_from.equals(R_name)) {
					hasconnect=1;
				}
				else if(C_to.equals(R_name)) {
					hasconnect=1;
				}
			}
			if(hasconnect==0) {
				String errMsg = "Exist Unconnected Reqirement.";
				errorList.add(errMsg);
				error.setErrorList(errorList);
				return error;
			}
		}
		if(RCnum==0) {
			String errMsg = "Does not have Reference or Constraint.";
			errorList.add(errMsg);
			error.setErrorList(errorList);
			return error;
		}
		error.setErrorList(errorList);
		return error;
	}
	
	//Constraint检测
	private Error check_Constraint(ProblemDiagram PD) {
		Error error = new Error();
		List<String> errorList = new ArrayList<String>();
		int RCnum=0;
		int Rnum=0;
		List <Constraint> PDC=PD.getConstraintList();
		for(int i=0;i<PDC.size();i++) {
			Constraint C_check=PDC.get(i);
			String C_name=C_check.getDescription();
			if(C_check.getPhenomenonList().size()==0) {
				String errMsg = "Exist undefine Constraint.";
				errorList.add(errMsg);
				error.setErrorList(errorList);
				return error;
			}
			if(C_name==null) {
				String errMsg = "Exist undefine Constraint.";
				errorList.add(errMsg);
				error.setErrorList(errorList);
				return error;
			}
			if(C_name.contains("?")) {
				String errMsg = "Constraint's name is illegal.";
				errorList.add(errMsg);
				error.setErrorList(errorList);
				return error;
			}
			Rnum++;
		}
		if(Rnum==0) {
			String errMsg = "Does not have Constraint";
			errorList.add(errMsg);
			error.setErrorList(errorList);
			return error;
		}
		error.setErrorList(errorList);
		return error;
	}
	
	
	
	//问题图检测
	public boolean checkPD(ProblemDiagram problemDiagram)
	   {
	     ContextDiagram contextDiagram = problemDiagram.getContextDiagram();
	     Machine machine = contextDiagram.getMachine();
	     List<ProblemDomain> problemDomainList = contextDiagram.getProblemDomainList();
	     List<Interface> interfaceList = contextDiagram.getInterfaceList();
	     List<Constraint> constraintList = problemDiagram.getConstraintList();
	     List<Reference> referenceList = problemDiagram.getReferenceList();
	     List<Requirement> requirementList = problemDiagram.getRequirementList();
	     
	     if(machine == null)
	     {
	    	 return false;
	     }
	     if(problemDomainList == null) {
	    	 return false;
	     }
	     if(interfaceList == null) {
	    	 return false;
	     }
	     if(constraintList == null) {
	    	 return false;
	     }
	     if(referenceList == null) {
	    	 return false;
	     }
	     if(requirementList == null) {
	    	 return false;
	     }
	     return true;
	   }
	
}
