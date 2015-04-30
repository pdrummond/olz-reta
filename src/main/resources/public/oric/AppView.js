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
var MessageDialog = require("./MessageDialog");

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
		this.messageListView = new MessageListView({appView: this, collection: this.messageCollection});
		this.quickAddInputView = new QuickAddInputView();
		this.hiddenAlertView = new HiddenAlertView();
		this.messageDetailView = new MessageDetailView();
		this.channelDropDownView = new ChannelDropDownView({collection: this.channelCollection});

		this.listenTo(this.filterInputView, 'enter-pressed', this.onFilterInputViewEnterPressed);
		this.listenTo(this.quickAddInputView, 'enter-pressed', this.onQuickAddInputViewEnterPressed);
		this.listenTo(this.hiddenAlertView, 'reveal-link-clicked', this.onRevealLinkClicked);
		this.listenTo(this.messageListView, 'message-clicked', this.onMessageClicked);
		this.listenTo(this.messageListView, 'promote-to-task-clicked', this.onPromoteToTaskClicked);
		this.listenTo(this.messageDetailView, 'message-content-updated', this.onMessageContentUpdated);
		this.listenTo(this.channelDropDownView, 'create-channel', this.onCreateChannel);
		this.listenTo(this.channelDropDownView, 'channel-clicked', this.onChannelClicked);

		var self = this;
		this.webSocketConnect(function() {
			console.log("Connected to OLZ-RETA");
			$.get("/rest/filter", function(filterQuery) {
				self.filterInputView.setFilter(filterQuery);
				self.fetchMessages();
			});
		});
		
		this.setupHeartBeat();
		this.setupCheckScroll();
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
						self.updateMessagesVisibility();						
						$("body").show();
						self.maybeFetchMoreMessages();
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
	
	onChannelClicked: function(model) {
		this.curChannelModel = model;
		this.updateMessagesVisibility();
	},

	onMessageContentUpdated: function(message) {
		this.sendMessage("message-content-updated", message);
	},
	
	onPromoteToTaskClicked: function(messageView) {
		this.sendMessage("promote-to-task", {
			messageType: "PROMOTE_TO_TASK",
			referredMessage: messageView.model.attributes
		});
	},

	onChatMessage: function(message) {
		var channel = this.curChannelModel ? this.curChannelModel.attributes : null;
		this.sendMessage("chat-message", _.extend(message, {channel: channel}));		
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
		case "UPDATE_MESSAGE_EVENT":
			var referredMessage = message.referredMessage;
			var model = this.messageCollection.get(referredMessage.id);
			model.set(referredMessage);
			break;
		default: 
			this.messageCollection.add(messageModel);
			//var view = this.messageListView.addMessageItemView(messageModel);
			//this.updateMessageVisibility(view);
			break;
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
		$("#MessageItemView a").removeClass('active');
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
	
	updateMessagesVisibility: function() {
		var self = this;
		_.each(this.messageListView.getViews(), function(view) {
			self.updateMessageVisibility(view);
		});
	},

	updateMessageVisibility: function(view) {		
		var show = true;
		var viewChannelId = view.model.get('channel') != null ? view.model.get('channel').id : 0;
		if(this.curChannelModel != null) {
			show = viewChannelId == this.curChannelModel.get('id');
		}
		view.$el.toggle(show);
	},
	
	setupHeartBeat: function() {		
		var self = this;		
		this.heartBeatInterval = setInterval(function() {
			$.post("/heartbeat", function(result) {
				//all good!
			}).fail(function(resp, textStatus, errorThrown) {
				self.stopHeartBeats();
				self.showLoggedOutDialog();
			});
		}, 10000); 
	},
	
	stopHeartBeats: function() {
		clearInterval(this.heartBeatInterval);
		delete this.heartBeatInterval;
	},
	
	showLoggedOutDialog: function() {
		if(!this.loggedOutDialogShown) {
			this.loggedOutDialogShown = true;
			var dialog = new MessageDialog({
				title: "You have been logged out", 
				okText: "Login",
				cancelText: "No thanks",
				allowCancel: true,
				msg: "Please enter your login details again to continue."
			});
			dialog.open();
			this.listenTo(dialog, 'ok', function() {
				window.location.href = "/login";
			});
			this.listenTo(dialog, 'cancel', function() {
				window.location.href = "/welcome";
			});
		}
	},
	
	setupCheckScroll: function() {
		var self = this;
		$(window).scroll(function() {			
			self.checkScroll();
		});
	},
	
	checkScroll: function () {
		var docHeight = ($(document).height() - 200);
		//console.log("window height   : " + ($(window).scrollTop() + $(window).height()));
		//console.log("document height : " + docHeight);
		var scrollTop = $(window).scrollTop();
		//console.log("scrollTop: " + scrollTop);
		if(!this.messageCollection.noMoreMessages && ( $(window).scrollTop() + $(window).height() ) >= $(document).height() - 200) {
			this.fetchMoreMessages(true);
		}		
	},
	
	fetchMoreMessages: function(callback) {
		var self = this;
		if(!this.isFetching) {
			var messages = new MessageCollection();
			messages.showMoreDate = this.messageCollection.showMoreDate;
			this.isFetching = true;			
			this.$("#show-more-busy").show();		
			messages.fetch({
				success: function(collection, resp) {
					self.isFetching = false;
					self.$("#show-more-busy").hide();
					self.messageCollection.showMoreDate = messages.showMoreDate;
					self.messageCollection.noMoreMessages = messages.noMoreMessages;
					if(collection.length > 0) {
						self.messageCollection.add(collection.models, {silent:true}); //ssh!  Add messages to list view manually below to ensure they go at the end of the list
						self.messageListView.addMessageItemViews(collection);
					}
					if(callback) {
						callback(true);
					}
				},
				error: function(collection, response) {
					self.isFetching = false;					
					self.$("#show-more-busy").hide();					
					self.showErrorDialogForResponse("Error fetching messages", response);
					if(callback) {
						callback(false);
					}
				}
			});		
		}
	},
	
	showErrorDialogForResponse: function(errorTitle, response) {
		if((response.status == 401 || response.status == 403)) {
			this.showLoggedOutDialog();
		} else {			
			//this.showMessageDialog("<i id='dialog-error-msg-icon' class='fa fa-exclamation-triangle'></i> " + errorTitle, errorMessage);
			//this.showErrorGrowl(errorTitle, this.getErrorMessageFromResponse(response));
			console.log(errorTitle + ": " + this.getErrorMessageFromResponse(response));
		}
	},
	
	getErrorMessageFromResponse: function(response) {
		var errorMessage = "Unknown error";
		if(response.responseJSON) {
			var status = response.responseJSON.status;
			errorMessage = response.responseJSON.message;
		}
		return errorMessage;
	},

	maybeFetchMoreMessages: function() {
		console.log("maybeFetchMoreMessages");
		//console.log("doc :" + $(document).height());
		//console.log("win :" + $(window).height());
		//console.log("list:" + this.$('#message-list').height());
		var self = this;
		if(this.$('#message-list').height() < $(window).height()) {
			this.$("#show-more-busy").show();
			this.fetchMoreMessages(function(ok) {
				if(ok && !self.messageCollection.noMoreMessages) {
					//maybe fetch older messages if previous fetch didn't fill screen 
					var more = self.maybeFetchMoreMessages();
					if(more == false) {
						//fetching is done
					}
				}
			});
			return true;
		} else {			
			return false;
		}
	}
});