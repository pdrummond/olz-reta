"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');
var SockJS = require('sockjs');
var Stomp = require('stomp');

var MessageCollection = require('./MessageCollection');
var FilterInputView = require('./FilterInputView');
var MessageListView = require('./MessageListView');
var QuickAddInputView = require('./QuickAddInputView');


module.exports = Backbone.View.extend({
	
	el: $("body"),
	template: _.template($('#AppViewTemplate').html()),
	
	events: {
		'click #logout-link': 'onLogout',
	},
		
	initialize: function() {
		this.messageCollection = new MessageCollection();
		this.filterInputView = new FilterInputView();
		this.messageListView = new MessageListView({collection: this.messageCollection});
		this.quickAddInputView = new QuickAddInputView();
		
		this.listenTo(this.filterInputView, 'enter-pressed', this.onFilterInputViewEnterPressed);
		this.listenTo(this.quickAddInputView, 'enter-pressed', this.onQuickAddInputViewEnterPressed);
		
		var self = this;
		this.webSocketConnect(function() {
			console.log("Connected to OLZ-RETA");
			self.fetchOlzItems();
		});
	},
	
	fetchOlzItems: function() {
		this.messageCollection.fetch({
			reset:true,
			success:function() {
				console.log("BOOM!");
			}
		});
	},
	
	render: function() {
		this.$el.html(this.template());
		this.$("#filter-input-view-container").html(this.filterInputView.render());		
		this.$("#message-list-view-container").html(this.messageListView.render());
		this.$("#quick-add-input-view-container").html(this.quickAddInputView.render());		
	},
	
	webSocketConnect: function(callback) {
		var self = this;
		var socket = new SockJS('/olz-reta');
		this.stompClient = Stomp.over(socket);
		this.stompClient.connect({}, function(frame) {
			console.log('WebSocket Connected: ' + frame);
			self.stompClient.subscribe('/topic/messages', function(message) {
                self.dispatchMessage(JSON.parse(message.body));
            });
			callback();
		}, function(frame) {
			console.error("web socket error: " + frame);			
			callback();
		});
	},
	
	onFilterInputViewEnterPressed: function(query) {
		var message = {content: query};
		this.onFilterMessage(message);
	},
	
	onQuickAddInputViewEnterPressed: function(content) {
		var message = {content: content};
		this.onChatMessage(message);
	},
	
	onChatMessage: function(message) {
		this.sendMessage("chat-message", message);		
	},
	
	onFilterMessage: function(message) {
		this.sendMessage("filter-message", message);		
	},
	
	sendMessage: function(messageName, messageContent) {        
        this.stompClient.send("/app/" + messageName, {}, JSON.stringify(messageContent));
    },
    
    dispatchMessage: function(message) {
    	console.log("MESSAGE: " + JSON.stringify(message));
    	var messageModel = new Backbone.Model(message);
    	switch(message.messageType) {
    	case "CHAT_MESSAGE": {
    		var view = this.messageListView.addMessageView(messageModel);
        	this.scrollBottom();
    		break;
    	}
    	default: console.log("MESSAGE RECIEVED: " + message.messageType); break;
    	}
    },
    
    scrollBottom: function() {
    	window.scrollTo(0,document.body.scrollHeight);
    },
    
    onLogout: function() {
		$.post("/logout", function() {
			window.location.href = "/welcome";
		}).fail(function(resp) {
			console.error("Error logging out: " + resp);
		});
	},
});