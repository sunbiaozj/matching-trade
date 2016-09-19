import {Component, OnInit} from '@angular/core';
import {Input, trigger, state, style, transition, animate} from '@angular/core'; // Animations

import {MessengerService} from './messenger.service';

@Component({
    selector: 'messenger',
    templateUrl: 'app/common/messenger/messenger.html',
    animations: [
        trigger('messageState', [
            state('inactive', style({
                transform: 'translateY(0)',
                height: "0px"
            })),
            state('active', style({
                transform: 'translateY(0)',
                height: "30px"
            })),
            transition('inactive => active',
                animate('200ms linear', style({
                    transform: 'translateY(100%)', height: "30px"})
                )
            ),
            transition('active => inactive',
                animate('200ms linear' , style({
                    transform: 'translateY(-100%)', height: "0px"})
                )
            )
        ])
    ]
})
export class MessengerComponent implements OnInit {

    private errors: string[] = [];
    private message: MessageWrapper = new MessageWrapper();

    constructor(private messengerService: MessengerService) { };

    ngOnInit() {
        this.messengerService.getErrorObservable().subscribe(
            (e: string) => this.errors.push(e)
        );

        this.messengerService.getMessageObservable().subscribe(
            (m: string) => {
                this.message.text = m;
                this.message.state = "active";
                setTimeout(() => this.clearMessages(), 5000);
            }
        );
    }

    public clearErrors(): void {
        this.errors = [];
    }

    public clearMessages(): void {
        this.message.text = null;
        this.message.state = "inactive";
    }

}

export class MessageWrapper {
    public state: string;
    public text: string;
}