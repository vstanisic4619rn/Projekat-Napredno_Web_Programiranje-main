import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { LoginComponent } from './components/login/login.component';
import { CreateComponent } from './components/create/create.component';
import { UpdateComponent } from './components/update/update.component';
import { HomeComponent } from './components/home/home.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ErrorsComponent } from './components/errors/errors.component';
import { CreateMachineComponent } from './components/create-machine/create-machine.component';
import { MachinesComponent } from './components/machines/machines.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CreateComponent,
    UpdateComponent,
    HomeComponent,
    ErrorsComponent,
    CreateMachineComponent,
    MachinesComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
