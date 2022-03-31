import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { NumbersListComponent } from './numbers-list/numbers-list.component';
import { HttpClientModule } from '@angular/common/http';
import {MaterialExampleModule} from '../material.module';

@NgModule({
  declarations: [
    AppComponent,
    NumbersListComponent
  ],
  imports: [
    BrowserModule,HttpClientModule,MaterialExampleModule,BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
