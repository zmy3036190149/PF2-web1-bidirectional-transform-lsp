package com.example.demo.bean;

import java.util.List;

public abstract class ProblemDiagramLine extends Line {
	public abstract List<RequirementPhenomenon> getPhenomenonList() ;
	public abstract void setPhenomenonList(List<RequirementPhenomenon> phenomenonList);
}
