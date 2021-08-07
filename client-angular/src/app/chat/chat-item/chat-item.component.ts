import { Component, Input, OnInit } from '@angular/core';
import { ITwitchMessage } from './model/twitchMessage';

@Component({
  selector: 'app-chat-item',
  templateUrl: './chat-item.component.html',
  styleUrls: ['./chat-item.component.scss']
})
export class ChatItemComponent implements OnInit {

  @Input()
  public get twMessage(): ITwitchMessage {
    return this._twMessage;
  }
  public set twMessage(value: ITwitchMessage) {
    this._twMessage = value;
  }
  private _twMessage!: ITwitchMessage;
  
  ngOnInit(): void{
    
  }

}
