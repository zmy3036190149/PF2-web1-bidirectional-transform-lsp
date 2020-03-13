import { Injectable } from '@angular/core';
import { Observable, Subject} from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FileUploader} from 'ng2-file-upload';
import { Project } from '../entity/Project';
import { DrawGraphService } from './draw-graph.service';

import { OntologyEntity } from '../entity/OntologyEntity';
@Injectable({
  providedIn: 'root'
})
export class FileService{
	constructor(
      private http: HttpClient,
	) {}
  projectAddress; string;
	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
	// textHttpOptions: FileService["httpOptions"]= {
  //   headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  //     responseType: 'json'
  // };

  private newProEmit = new Subject<any>();
  newProEmmited$ = this.newProEmit.asObservable();
  newProject(isnew: boolean) {
    this.newProEmit.next(isnew);
  }

  private projectNameEmit = new Subject<any>();
  projectNameEmmited$ = this.projectNameEmit.asObservable();
  editProName(proName: string) {
    this.projectNameEmit.next(proName);
  }

  setProject(projectAddress: string): Observable<any> {
    this.projectAddress = projectAddress;
    const url = `http://47.52.116.116:8099/file/setProject/${projectAddress}`;
    //console.log(url);
    var res = this.http.post<any>(url, this.httpOptions);
    return res;
  }

  //上传文件
  uploadFile(uploader: FileUploader) {
    // 开始上传
    let url = `http://47.52.116.116:8099/file/upload/${this.projectAddress}`
    uploader.setOptions({ url: url });
    uploader.uploadAll();
    //console.log(url);
  }
    //上传Owl文件
  uploadOwlFile(uploader: FileUploader,type,projectAddress) {
      // 开始上传
      let url = `http://47.52.116.116:8099/file/uploadOwl/${projectAddress}/${type}`
      uploader.setOptions({ url: url });
      uploader.uploadAll();
      console.log("uploadOwlFile",url);
  }

  //上传pf文件
  uploadpfFile(uploader: FileUploader) {
    // 开始上传
    let url = `http://47.52.116.116:8099/file/uploadpf/${this.projectAddress}`
    uploader.setOptions({ url: url });
    uploader.uploadAll();
    //console.log(url);
  }
  //将后端owl目录下owl文件移动到Project目录下
  moveOwl(projectAddress: string,owl:string,version){
    this.projectAddress = projectAddress;
    const url = `http://47.52.116.116:8099/file/moveOwl/${projectAddress}/${owl}/${version}`;
    //console.log(url);
    var res = this.http.post<any>(url, this.httpOptions);
    return res;
  }
  movePOwl(projectAddress: string,pOwl:string,version){
    this.projectAddress = projectAddress;
    const url = `http://47.52.116.116:8099/file/movePOwl/${projectAddress}/${pOwl}/${version}`;
    //console.log(url);
    var res = this.http.post<any>(url, this.httpOptions);
    return res;
  }

  //读取项目信息
  getProject(projectAddress: string,ver): Observable<Project> {
    const url = `http://47.52.116.116:8099/file/getProject/${projectAddress}/${ver}`;
    let res = this.http.get<Project>(url);
    //console.log("begin");
    //console.log(url);
    //console.log("testttt");
    return res;
  }
  // //读取pf，若文件不存在，则返回空串
  // getPf(projectAddress: string,ver):Observable<string> {
  //     const url = `http://47.52.116.116:8099/file/getPf/${projectAddress}/${ver}`;
  //     let res = this.http.get(url, {responseType: 'text'});
  //     //console.log("begin");
  //     //console.log(url);
  //     //console.log("testttt");
  //     return res;
  // }
  //读取pf,若文件不存在，则将项目转为pf文件，再将内容返给客户端
  getNotNullPf(projectAddress: string,ver):Observable<string> {
      const url = `http://47.52.116.116:8099/file/getNotNullPf/${projectAddress}/${ver}`;
      let res = this.http.get(url, {responseType: 'text'});
      //console.log("begin");
      //console.log(url);
      //console.log("testttt");
      return res;
  }
  //将projectAddress对应的项目转为pf文件
  // ProjectToPf(projectAddress: string, project: Project): Observable<boolean> {
  //   const url = `http://47.52.116.116:8099/file/ProjectToPf/${projectAddress}`;
  //   var res = this.http.post<boolean>(url, project, this.httpOptions);
  //   return res;
  // }
  //读取项目信息
  getLatestProject(projectAddress: string,ver): Observable<Project> {
      const url = `http://47.52.116.116:8099/file/getLatestProject/${projectAddress}/${ver}`;
      let res = this.http.get<Project>(url);
      //console.log("begin");
      //console.log(url);
      //console.log("testttt");
      return res;
  }

  //保存项目
  saveProject(projectAddress: string,project: Project): Observable<boolean> {
    const url = `http://47.52.116.116:8099/file/saveProject/${projectAddress}`;
    var res = this.http.post<boolean>(url, project, this.httpOptions);
    return res;
  }
  //保存pf文件
  savePf(projectAddress: string,pf: string): Observable<boolean> {
    const url = `http://47.52.116.116:8099/file/savePf/${projectAddress}`;
    var res = this.http.post<boolean>(url, pf, this.httpOptions);
    return res;
  }

  format(projectAddress: string, project: Project): Observable<boolean> {
    const url = `http://47.52.116.116:8099/file/format/${projectAddress}`;
    var res = this.http.post<boolean>(url, project, this.httpOptions);
    return res;
  }

  /*downloadProject(projectAddress: string) {
    var
      fs = require('fs'),
      url = require('url'),
      path = require('path'),
      http = require('http');

    // 从命令行参数获取root目录，默认是当前目录:
    var root = projectAddress + "\\Project.xml";


    // 创建服务器:
    var server = http.createServer(function (request, response) {
      // 获得URL的path，类似 '/css/bootstrap.css':
      var pathname = url.parse(request.url).pathname;
      // 获得对应的本地文件路径，类似 '/srv/www/css/bootstrap.css':
      var filepath = path.join(root, pathname);
      // 获取文件状态:
      fs.stat(filepath, function (err, stats) {
        if (!err && stats.isFile()) {
          // 没有出错并且文件存在:
          //console.log('200 ' + request.url);
          // 发送200响应:
          response.writeHead(200);
          // 将文件流导向response:
          fs.createReadStream(filepath).pipe(response);
        } else {
          // 出错了或者文件不存在:
          //console.log('404 ' + request.url);
          // 发送404响应:
          response.writeHead(404);
          response.end('404 Not Found');
        }
      });
    });

  }*/

  searchProject(): Observable<string[]> {
    const url = 'http://47.52.116.116:8099/file/searchProject';
    return this.http.get<string[]>(url);
  }

  searchVersion(project: string): Observable<string[]> {
    const url = `http://47.52.116.116:8099/file/searchVersion/${project}`;
    return this.http.get<string[]>(url);
  }
  searchOwlVersion(owl: string): Observable<string[]> {
    const url = `http://47.52.116.116:8099/file/searchOwlVersion/${owl}`;
    return this.http.get<string[]>(url);
  }

  getNodes(owlAdd,powlName,nodeName,type):Observable<string[]>{
		const url = `http://47.52.116.116:8099/file/getNodes/${owlAdd}/${powlName}/${nodeName}/${type}`;
		return this.http.get<string[]>(url);
  }
  searchOwl(type): Observable<string[]> {
    const url = 'http://47.52.116.116:8099/file/searchOwl/${type}';
    return this.http.get<string[]>(url);
  }
  getProblemDomains(owlAdd,owlName):Observable<OntologyEntity[]>{
		const url = `http://47.52.116.116:8099/file/getProblemDomains/${owlAdd}/${owlName}`;
		return this.http.get<OntologyEntity[]>(url);
  }
  //上传pf文件后，获取project
  getPFProject(projectAddress: string): Observable<Project> {
    const url = `http://47.52.116.116:8099/file/xtextToXmi`;
    let res = this.http.post<Project>(url, projectAddress, this.httpOptions);
    //console.log("begin");
    //console.log(url);
    //console.log("testttt");
    return res;
  }
}
