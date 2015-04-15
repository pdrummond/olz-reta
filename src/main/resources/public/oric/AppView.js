"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');
var SockJS = require('sockjs');
var Stomp = require('stomp');

var LoopListWidget= require('./LoopListWidget');
var OlzItemCollection = require('./OlzItemCollection');

module.exports = Backbone.View.extend({
	
	el: $("body"),
	template: _.template($('#AppViewTemplate').html()),
	
	events: {
		'click #logout-link': 'onLogout',
	},
		
	initialize: function() {
		this.olzItemCollection = new OlzItemCollection();
		this.loopListWidget = new LoopListWidget({collection: this.olzItemCollection});
		this.listenTo(this.loopListWidget, 'new-loop-message', this.onNewLoopMessage);
		this.listenTo(this.loopListWidget, 'filter-message', this.onFilterMessage);
		
		var self = this;
		this.webSocketConnect(function() {
			console.log("Connected to OLZ-RETA");
			self.fetchOlzItems();
		});
	},
	
	fetchOlzItems: function() {
		this.olzItemCollection.fetch({
			reset:true,
			success:function() {
				console.log("BOOM!");
			}
		});
	},
	
	render: function() {
		this.$el.html(this.template());
		$("#loop-list-widget-container").html(this.loopListWidget.render());		
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
	
	onNewLoopMessage: function(message) {
		this.sendMessage("new_loop", message);		
	},
	
	onFilterMessage: function(message) {
		this.sendMessage("filter_message", message);		
	},
	
	sendMessage: function(messageName, messageContent) {        
        this.stompClient.send("/app/" + messageName, {}, JSON.stringify(messageContent));
    },
    
    dispatchMessage: function(message) {    	
    	console.log("MESSAGE: " + JSON.stringify(message));
    	this.loopListWidget.addLoopItem(message);
    	this.scrollBottom();
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