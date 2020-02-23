package com.example.demo.bean;
import javax.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.service.FileService;
public class LSPDiagramSubject extends LSPSubject{	

	@Autowired	//自动装配
	FileService fileService;
	private Project project = null;
	public LSPDiagramSubject(String uri, LSPObserver observer) {
		super(uri,"diagram",observer);
		System.out.println("new LSPDiagramSubject,title = "+ uri);
	}
	public LSPDiagramSubject(String uri, Project project,LSPObserver observer) {
		super(uri,"diagram",observer);
		this.project = project;
		System.out.println("new LSPDiagramSubject,title = "+ uri);
	}

	public void detach(Session session) {
		super.detach(session);
		System.out.println("removeSession");
		System.out.println(this.observerSet);
	}
	@Override
	public void setValue(String message) {
		notifyObserver(message);
	}
	public void notifyObserver(String message){
		System.out.println("observer数目：		"+getObserverSet().size());
		for(LSPObserver observer: getObserverSet()) {
			observer.update(message);
		}
	}	
}
