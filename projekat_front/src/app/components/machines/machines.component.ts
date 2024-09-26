import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, Timestamp } from 'rxjs';
import { Machine } from 'src/app/models';
import { BackService } from 'src/app/services/back.service';
import { ConfigService } from 'src/app/services/config.service';

@Component({
  selector: 'app-machines',
  templateUrl: './machines.component.html',
  styleUrls: ['./machines.component.css']
})
export class MachinesComponent implements OnInit {

  machineList: Machine[]=[];
  machineList2: Machine[]=[];
  show: boolean = false;
  start: boolean = false;
  stop: boolean = false;
  restart: boolean = false;
  search: boolean = false;
  destroy: boolean = false;
  message: string = '';
  name: string = '';
  timestamp: string = '';
  machineName: string = '';
  dateFrom: string = '';
  dateTo: string = '';
  status: string [] = [];
  machineName1: string = '';
  dateFrom1: string = '';
  dateTo1: string = '';
  status1: string [] = [];
  errorString: string[] = [];
  running: boolean = false;
  stopped: boolean = false;
  searchFlag: boolean = false;
  errorsExists: boolean = false;

  constructor(private configService: ConfigService, private backService: BackService, private router: Router) { }

  ngOnInit(): void {
    setInterval(() =>{
      if(this.searchFlag){

          this.backService.searchMachine(this.machineName1,this.status1,new Date(this.dateFrom1),new Date(this.dateTo1)).subscribe(machineList4 =>{
          this.machineList = []
          this.machineList = machineList4;
          console.log(this.searchFlag + " if")
        });;
      }else{

          this.backService.loadMachines().subscribe(machineList =>{
          this.machineList = []
          this.machineList = machineList;
          console.log(this.searchFlag+" else")
      });
      }
    }, 1000);

    this.backService.loadMachines().subscribe(machineList =>{
      this.machineList = machineList;
      this.show = true;
      console.log(this.machineList)
    },err =>{
      this.show = false;
      this.message = 'You dont have permission to see the table!'
    });
    
    var permissions = JSON.parse(localStorage.getItem('permission') || '{}') 
    this.backService.loadMachines().subscribe(machineList2 =>{
      this.machineList2 = machineList2;
    });
    for(var a in permissions){
      console.log(permissions[a])
      if(permissions[a].permissionName === 'can_search_machines'){
        this.search = true;
      }if(permissions[a].permissionName === 'can_start_machines'){
        this.start = true;
      }if(permissions[a].permissionName === 'can_stop_machines'){
        this.stop = true;
      }if(permissions[a].permissionName === 'can_restart_machines'){
        this.restart = true;
      }if(permissions[a].permissionName === 'can_destroy_machines'){
        this.destroy = true;
      }
    }
    console.log(this.search)
    console.log(permissions[0])
  }
  sleep(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  searchMachines(): void{
    this.status = []
    if(this.stopped == false && this.running == false && this.machineName =='' && this.dateFrom ==''  && this.dateTo == ''){
      this.searchFlag =false;
    }else{
      this.machineName1= this.machineName;
      this.dateFrom1 = this.dateFrom;
      this.dateTo1 = this.dateTo;
      this.status1 = this.status;
      this.searchFlag = true;
    }
    if(this.running == true){
      this.status.push('RUNNING');
    }if(this.stopped == true){
      this.status.push('STOPPED');
    }

    this.backService.searchMachine(this.machineName,this.status,new Date(this.dateFrom),new Date(this.dateTo)).subscribe(machines =>{
      console.log(machines)
    });
    
  }

  scheaduleMachine(): void{
    var d = new Date();
    this.errorString=[];
    this.errorsExists=false;
    console.log((new Date(this.timestamp).getTime() > d.getTime()))
    console.log( "\n timestamp"+new Date(this.timestamp).getTime()  +  ' date ' + d.getTime())
    if(!this.timestamp)
    {
      this.errorString.push("Morate popuniti sva polja!");
      this.errorsExists=true;
    }else if(!(new Date(this.timestamp).getTime() > d.getTime()))
    {
      this.errorString.push("Morate uneti datum veci od trenutnog!");
      this.errorsExists=true;
    }
    var actions = (document.getElementById('actions')) as HTMLSelectElement;
    var sel = actions.selectedIndex;
    var opt = actions.options[sel];
    var CurValue = (<HTMLSelectElement><unknown>opt).value;
    console.log(CurValue)

    var machine = (document.getElementById('machines')) as HTMLSelectElement;
    var selM = machine.selectedIndex;
    var optM = machine.options[selM];
    var CurValueM = (<HTMLSelectElement><unknown>optM).value;
    console.log(CurValueM)

    console.log(this.timestamp)
      if(!this.errorsExists){
      this.backService.scheduleMachine(CurValue,Number(CurValueM), new Date(this.timestamp)).subscribe(obj =>{
        console.log(CurValue + " in reuqest")
        console.log(obj)
        alert("Action scheaduled!")
      });
    }
  }
  startMachine(machineId: number): void{
    this.backService.startMachine(machineId).subscribe(machineId =>{
      console.log("Starting machine")
      this.backService.loadMachines().subscribe(machineList =>{
        this.machineList = machineList;
      });
      this.sleep(12000).then(()=>{
      });
    });
    
  }

  stopMachine(machineId: number): void{
    this.backService.stopMachine(machineId).subscribe(machineId =>{
      this.backService.loadMachines().subscribe(machineList =>{
        this.machineList = machineList;
      });
      this.sleep(12000).then(()=>{
        });
      });
  }

  restartMachine(machineId: number): void{
    var timestamp = document.querySelector('input')?.value;
    console.log(timestamp)
    
    this.backService.restartMachine(machineId).subscribe(machineId =>{  
      this.backService.loadMachines().subscribe(machineList =>{
        this.machineList = machineList;
      });
      this.sleep(12000).then(()=>{
      });
    });
  }
  
  
  destroyMachine(machineId: number): void{
    
    this.backService.destroyMachine(machineId).subscribe(machineId =>{
      this.backService.loadMachines().subscribe(machineList =>{
        this.machineList = machineList;
        this.machineList2 = machineList;
      });
    });
    
  }
}
