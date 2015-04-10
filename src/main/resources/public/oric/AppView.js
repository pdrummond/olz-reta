"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');
var SockJS = require('sockjs');
var Stomp = require('stomp');

var LoopListWrapper = require('./LoopListWrapper');

module.exports = Backbone.View.extend({
		
	initialize: function() {
		this.loopListWrapper = new LoopListWrapper();
		this.webSocketConnect(function() {
			console.log("BOOM - it works!");
		});
	},
	
	render: function() {
		var html = _.template($('#AppViewTemplate').html())();		
		$('body').html(html);
		$("#loop-list-wrapper").html(this.loopListWrapper.render());
	},
	
	webSocketConnect: function(callback) {
		var self = this;
		var socket = new SockJS('/olz-reta');
		this.stompClient = Stomp.over(socket);
		this.stompClient.connect({}, function(frame) {
			console.log('WS Connected: ' + frame);
			self.stompClient.subscribe('/topic/messages', function(message) {
                showMessage(JSON.parse(message.body).content);
            });
			callback();
		}, function(frame) {
			console.error("web socket error: " + frame);			
			callback();
		});
	}
});