import { Line } from '../entity/Line';
import { DiagramContentChangeEvent } from './DiagramContentChangeEvent';

export class DiagramContentChangeEventFactory{
 public getShapeChangeEvent(shapeType,changeType,oldShape,newShape):DiagramContentChangeEvent{
     let diagramContentChangeEvent : DiagramContentChangeEvent={
        newProject:null,
        shapeType : shapeType,
        changeType:changeType,
        oldShape:oldShape,
        newShape:newShape,
        lineInfo:null,
       oldPhenomenon:null,
       newPhenomenon:null
     }
    return diagramContentChangeEvent
 }

 public getPhenomenonChangeEvent(changeType,line,oldPhenomenon,newPhenomenon):DiagramContentChangeEvent{
    let diagramContentChangeEvent : DiagramContentChangeEvent={
       newProject:null,
       shapeType : "phe",
       changeType: changeType,
       oldShape:null,
       newShape:null,
       lineInfo:line,
       oldPhenomenon:oldPhenomenon,
       newPhenomenon:newPhenomenon
    }
   return diagramContentChangeEvent
}
}