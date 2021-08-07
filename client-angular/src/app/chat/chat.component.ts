import { Component, OnInit } from '@angular/core';
import { ITwitchMessage } from './chat-item/model/twitchMessage';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
})
export class ChatComponent implements OnInit {
  tmi: any;

  public currentMessage: string = '';
  public currentUser: string = '';
  public messageArray: ITwitchMessage[] = [];
  public currentChannel: string = 'otplol_';

  constructor() {}

  ngOnInit(): void {
    const tmi = require('tmi.js');

    const client = new tmi.Client({
      channels: [this.currentChannel],
    });

    client.connect();

    client.on('message', (channel: any, tags: any, message: any, self: any) => {
      // "Alca: Hello, World!"
      console.log(`${tags['display-name']}: ${message}`);
      this.currentMessage = message;
      this.currentUser = tags['display-name'];

      addTwitchMessageToArray(tags['display-name'], message);
    });

    const addTwitchMessageToArray = (username: string, message: string) => {
      const twmessage: ITwitchMessage = {
        user: username,
        message: message,
      };
      if (this.messageArray.length > 30) {
        this.messageArray.shift();
      }
      this.messageArray.push(twmessage);
      console.log(this.messageArray.length);
    };
  }
}
