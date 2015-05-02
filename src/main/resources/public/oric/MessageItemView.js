"use strict";

//var crypto = require('crypto');
var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id:"MessageItemView",
	
	tagName:"li",
	
	template: _.template($('#MessageItemViewTemplate').html()),
	
	events: {		
		'click #view-details-menu-item': 'onViewDetailsMenuItemClicked',
		'click #promote-to-task-menu-item': 'onPromoteToTaskMenuItemClicked',
		'click #archive-menu-item': 'onArchiveMenuItemClicked',
		'click #status-open-menu-item': 'onStatusOpenMenuItemClicked',
		'click #status-in-progress-menu-item': 'onStatusInProgressMenuItemClicked',
		'click #status-blocked-menu-item': 'onStatusBlockedMenuItemClicked',
		'click #status-in-test-menu-item': 'onStatusInTestMenuItemClicked',
		'click #status-done-menu-item': 'onStatusDoneMenuItemClicked',
		'click #move-chanel-menu-item': 'onMoveChannelMenuItemClicked'
	},
	
	initialize: function(options) {
		this.appView = options.appView;
		this.message = options.message;
		this.listenTo(this.model, 'change', this.render);
	},
		
	render: function() {
		var self = this;
		this.$el.html(this.template());
		this.$("#message-content").html(this.model.get('content'));
		this.$el.addClass("item-type-" + this.model.get('messageType').toLowerCase());
		var channel = this.model.get('channel');
		if(channel != null) {
			this.$("#item-channel-title").html(channel.title);
		} else {
			this.$("#item-channel-title").html("HOME");
		}
		this.$("#item-channel-dropdown").toggle(channel != null);
		this.appView.channelCollection.each(function(channel) {
			self.$("#item-channel-dropdown-list").append("<li id='move-chanel-menu-item' data-channel-id='" + channel.get('id') + "'><a href='#'>" + channel.get('title') + "</a></li>");
		});
		this.$('.dropdown-toggle').dropdown();
		switch(this.model.get('messageType')) {
			case 'TASK':
				this.$("#item-type-button i").attr('class', 'fa fa-tasks');
				this.$("#item-status-dropdown").css('display', 'inline-block');
				this.$("#item-channel-dropdown").show();
				break;
			case 'COMMENT':
				this.$("#item-type-button i").attr('class', 'fa fa-comments');
				this.$("#item-status-dropdown").hide();
				this.$("#item-channel-dropdown").show();
				break;
			case 'PROMOTE_TO_TASK':
				this.renderActivityView("<b>@pd</b> promoted a comment to a task");
				break;
			case 'ARCHIVE_MESSAGE':
				this.renderActivityView("<b>@pd</b> archived a message");
				break;
			case 'RESTORE_MESSAGE':
				this.renderActivityView("<b>@pd</b> restored a message");
				break;
			case 'UPDATE_STATUS':
				this.renderActivityView("<b>@pd</b> changed the status of a message");
				break;
			case 'UPDATE_CHANNEL':
				this.renderActivityView("<b>@pd</b> moved a message to another channel");
				break;
			case 'CHANNEL':
				this.$('.list-group-item').attr('class', 'list-group-item list-group-item-success');
				this.$("#item-type-button i").attr('class', 'fa fa-desktop');
				this.$("#item-status-dropdown").hide();
				this.$("#item-image").hide();
				this.$("#message-content").html(this.model.get('createdBy').tag + " created a <b>new channel</b> called " + this.model.get('title'));
				break;
		}
		var isArchived = this.model.get('archived');
		this.$el.toggle(isArchived === false);
		this.$el.toggleClass('archived-message', isArchived);
		
		this.$("#archive-menu-item a").text(isArchived?"Restore":"Archive");
		
		switch(this.model.get('status')) {
		case 100: this.$("#current-status").text("Open"); this.$("#current-status-button").attr('class', 'btn btn-default btn-xs dropdown-toggle'); break;
		case 101: this.$("#current-status").text("In Progress"); this.$("#current-status-button").attr('class', 'btn btn-success btn-xs dropdown-toggle'); break;
		case 102: this.$("#current-status").text("Blocked"); this.$("#current-status-button").attr('class', 'btn btn-danger btn-xs dropdown-toggle'); break;
		case 103: this.$("#current-status").text("In Test"); this.$("#current-status-button").attr('class', 'btn btn-warning btn-xs dropdown-toggle'); break;
		case 104: this.$("#current-status").text("Done"); this.$("#current-status-button").attr('class', 'btn btn-primary btn-xs dropdown-toggle'); break;
		}
		

		return this.el;
	},
	
	renderActivityView: function(msg) {
		this.$('.list-group-item').attr('class', 'list-group-item activity-item');
		this.$("#item-type-button i").attr('class', 'fa fa-exchange');
		this.$("#item-status-dropdown").hide();
		this.$("#item-image").hide();
		this.$("#message-content").html(msg);
	},
	
	onViewDetailsMenuItemClicked: function() {
		this.trigger("message-clicked", this);
	},
	
	onPromoteToTaskMenuItemClicked: function() {
		this.trigger("promote-to-task-clicked", this);
	},
	
	onArchiveMenuItemClicked: function() {
		var messageName = this.model.get('archived')?"restore-message":"archive-message";
		this.appView.sendMessage(messageName, {
			referredMessage: this.model.attributes
		});
	},
	
	onStatusOpenMenuItemClicked: function() {
		this.sendStatusMessage(100);
	},

	onStatusInProgressMenuItemClicked: function() {
		this.sendStatusMessage(101);
	},

	onStatusBlockedMenuItemClicked: function() {
		this.sendStatusMessage(102);
	},
	
	onStatusInTestMenuItemClicked: function() {
		this.sendStatusMessage(103);
	},
	
	onStatusDoneMenuItemClicked: function() {
		this.sendStatusMessage(104);
	},
	
	sendStatusMessage: function(statusValue) {
		this.model.set('status', statusValue);
		this.appView.sendMessage('update-status', {
			referredMessage: this.model.attributes
		});
	},
	
	onMoveChannelMenuItemClicked: function(ev) {
		 ev.stopPropagation();
		 ev.preventDefault();
		 var channelId = $(ev.target).parent().attr('data-channel-id');
		 var channelTitle = $(ev.target).text();
		 this.model.set('channel', {id: channelId, title: channelTitle});
			this.appView.sendMessage('update-channel', {
				referredMessage: this.model.attributes
			});
	},

	select: function() {		
		this.$("a").addClass("active");
	},
	
	getMessageId: function() {
		return this.model.get('id');
	},
	
	/*md5: function(str) {
		str = str.toLowerCase().trim();
		var hash = crypto.createHash("md5");
		hash.update(str);
		return hash.digest("hex");
	},*/
});