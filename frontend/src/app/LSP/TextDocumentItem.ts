import { Project } from '../entity/Project';

export interface TextDocumentItem{
    uri: String,
    languageId:String,
    version: number,
    text: String,
    project:Project
}