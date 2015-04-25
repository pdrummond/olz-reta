"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');
var SockJS = require('sockjs');
var Stomp = require('stomp');

var ChannelCollection = require('./ChannelCollection');
var MessageCollection = require('./MessageCollection');
var FilterInputView = require('./FilterInputView');
var MessageListView = require('./MessageListView');
var QuickAddInputView = require('./QuickAddInputView');
var HiddenAlertView = require('./HiddenAlertView');
var MessageDetailView = require("./MessageDetailView");
var ChannelDropDownView = require("./ChannelDropDownView");

module.exports = Backbone.View.extend({

	el: $("body"),
	template: _.template($('#AppViewTemplate').html()),

	events: {
		'click #logout-link': 'onLogout',
	},

	initialize: function() {
		this.channelCollection = new ChannelCollection();
		this.messageCollection = new MessageCollection();
		this.filterInputView = new FilterInputView();
		this.messageListView = new MessageListView({collection: this.messageCollection});
		this.quickAddInputView = new QuickAddInputView();
		this.hiddenAlertView = new HiddenAlertView();
		this.messageDetailView = new MessageDetailView();
		this.channelDropDownView = new ChannelDropDownView({collection: this.channelCollection});

		this.listenTo(this.filterInputView, 'enter-pressed', this.onFilterInputViewEnterPressed);
		this.listenTo(this.quickAddInputView, 'enter-pressed', this.onQuickAddInputViewEnterPressed);
		this.listenTo(this.hiddenAlertView, 'reveal-link-clicked', this.onRevealLinkClicked);
		this.listenTo(this.messageListView, 'message-clicked', this.onMessageClicked);
		this.listenTo(this.messageDetailView, 'message-content-updated', this.onMessageContentUpdated);
		this.listenTo(this.channelDropDownView, 'create-channel', this.onCreateChannel);

		var self = this;
		this.webSocketConnect(function() {
			console.log("Connected to OLZ-RETA");
			$.get("/rest/filter", function(filterQuery) {
				self.filterInputView.setFilter(filterQuery);
				self.fetchMessages();
			});
		});
	},

	fetchMessages: function() {
		var self = this;
		this.messageListView.clear();
		this.channelCollection.fetch({
			reset: true,
			success:function() {
				self.messageCollection.fetch({
					reset:true,
					success:function() {				
						$("body").show();
						$('.dropdown-toggle').dropdown();
					}			
				});
			}
		});
	},

	render: function() {
		this.$el.html(this.template());
		this.$("#filter-input-view-container").html(this.filterInputView.render());		
		this.$("#message-list-view-container").html(this.messageListView.render());
		this.$("#quick-add-input-view-container").html(this.quickAddInputView.render());		
		this.$("#hidden-alert-view-container").html(this.hiddenAlertView.render());
		this.$("#message-detail-view-container").html(this.messageDetailView.render());
		this.$("#channel-dropdown-view-container").html(this.channelDropDownView.render());		
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
			self.stompClient.subscribe('/topic/hidden-messages', function(message) {
				self.onHiddenMessage(JSON.parse(message.body));
			});
			callback();
		}, function(frame) {
			console.error("web socket error: " + frame);			
			callback();
		});
	},

	onFilterInputViewEnterPressed: function(query) {
		this.setFilter(query);
	},

	setFilter: function(query) {
		var message = {content: query};
		this.onFilterMessage(message);
		if(query.length == 0) {
			this.hiddenAlertView.resetHiddenMessageCount();
		}
	},

	clearFilter: function() {
		this.setFilter("");
	},

	onQuickAddInputViewEnterPressed: function(content) {
		var message = {content: content};
		this.onChatMessage(message);
	},

	onCreateChannel: function(channelTitle) {
		this.sendMessage('channel', {title: channelTitle});
	},

	onMessageContentUpdated: function(message) {
		this.sendMessage("message-content-updated", message);
	},

	onChatMessage: function(message) {
		this.sendMessage("chat-message", message);		
	},

	onFilterMessage: function(message) {
		this.sendMessage("filter-message", message);
		this.fetchMessages();
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
		case "CHANNEL": {
			var view = this.messageListView.addMessageView(messageModel);
			this.scrollBottom();
			break;
		}
		default: console.log("MESSAGE RECIEVED: " + message.messageType); break;
		}
	},

	onHiddenMessage: function(message) {
		this.hiddenAlertView.incHiddenMessageCount();
	},

	scrollBottom: function() {
		//window.scrollTo(0,document.body.scrollHeight);
	},

	onLogout: function() {
		$.post("/logout", function() {
			window.location.href = "/welcome";
		}).fail(function(resp) {
			console.error("Error logging out: " + resp);
		});
	},

	onRevealLinkClicked: function() {
		this.clearFilter();
	},

	onMessageClicked: function(messageView) {
		$("#MessageView a").removeClass('active');
		messageView.select();

		if(this.messageDetailView.getMessageId() == messageView.getMessageId()) {
			this.slideOut("#message-detail-view-container");
		} else {		
			this.messageDetailView.setModel(messageView.model);
			this.slideIn("#message-detail-view-container");		
		}
	},

	slideIn: function(el) {
		$("#app-container").animate({left: '40%'}, 100);
		$(el).animate({left: '0%'}, 100);
	},

	slideOut: function(el) {
		$("#app-container").animate({left: '0%'}, 100);
		$(el).animate({left: '-60%'}, 100);
	},
});