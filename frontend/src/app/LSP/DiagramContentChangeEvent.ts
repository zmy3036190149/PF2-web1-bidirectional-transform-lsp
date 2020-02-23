import { IShape } from '../entity/IShape';
import { Phenomenon } from '../entity/Phenomenon';
import { LineInfo } from '../entity/LineInfo';
export interface DiagramContentChangeEvent{
    shapeType: string; //"mac/pro/req/int/ref/con//phe"
    changeType: string; //"add/delete/change"

    //if shapeType in "mac/pro/req/int/ref/con"
    oldShape?: IShape;
    newShape?: IShape;

    //if shapeType == "phe"
    lineInfo?: LineInfo;
    oldPhenomenon?: Phenomenon;
    newPhenomenon?: Phenomenon;
}