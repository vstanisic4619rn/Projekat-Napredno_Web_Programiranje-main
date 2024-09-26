import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Error } from 'src/app/models';
import { BackService } from 'src/app/services/back.service';
import { ConfigService } from 'src/app/services/config.service';

@Component({
  selector: 'app-errors',
  templateUrl: './errors.component.html',
  styleUrls: ['./errors.component.css']
})
export class ErrorsComponent implements OnInit {

  errorList: Error [] = [];
  message: string= '';
  show: boolean = false;

  constructor(private configService: ConfigService, private backService: BackService, private router: Router) { 
  }

  ngOnInit(): void {
    this.backService.errorMessages().subscribe(errors =>{
      this.errorList = errors;
    });
    if(this.errorList.length === 0){
      this.message = "Error messages are empty";
      this.show = true;
    }
  }

}
