import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FileUploadModule } from 'ng2-file-upload';
import { AppRoutingModule }     from './app-routing.module';

import { AppComponent } from './app.component';
import { MonacoEditorModule } from 'ngx-monaco-editor';
import { NgxEditorComponent } from './ngx-editor/ngx-editor.component';
import { MonacoConfig } from './ngx-editor/monaco-config';
import { TopbarComponent } from './topbar/topbar.component';
import { LeftbarComponent } from './leftbar/leftbar.component';
import { RightbarComponent } from './rightbar/rightbar.component';
import { DrawingboardComponent } from './drawingboard/drawingboard.component';
@NgModule({
  declarations: [
    AppComponent,
    TopbarComponent,
    LeftbarComponent,
    RightbarComponent,
    DrawingboardComponent,
    NgxEditorComponent
  ],
  imports: [
	BrowserModule,
	HttpClientModule,
	FormsModule,
	CommonModule,
	FileUploadModule,
  AppRoutingModule,
  MonacoEditorModule.forRoot(MonacoConfig) // use forRoot() in main app module only.

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
