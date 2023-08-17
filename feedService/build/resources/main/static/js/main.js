'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var privateMessageForm = document.querySelector('#privateMessageForm');
var messageInput = document.querySelector('#message');
var privateMessageInput = document.querySelector('#privateMessage');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var password=null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();
    password = document.querySelector('#password').value.trim();

    if(username && password) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/feed/utsa');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.subscribe('/user/topic/private-messages', onMessageReceived2);

    // Tell your username to the server
    stompClient.send("/app/chat.register",
        {},
        JSON.stringify({name: username, password:password })
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function send(event) {
    console.log(username," ", password);
    event.preventDefault();
}
function sendPrivateMessage(event) {
    console.log(username," ", password);
        event.preventDefault();

    }


function onMessageReceived2(payload){
var user = {
            name:username,
            password: password

        };
      var message=JSON.parse(payload.body);
stompClient.send("/app/private-message2", {}, JSON.stringify(user));
onMessageReceived(payload);
}

function onMessageReceived(payload) {
console.log("In onMessageReceived "+payload.body )
    var message = JSON.parse(payload.body);
    console.log("In onMessageReceived message "+message );

    }


usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', send, true)
privateMessageForm.addEventListener('submit', sendPrivateMessage, true)
