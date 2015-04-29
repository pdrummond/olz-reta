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
		'click #promote-to-task-menu-item': 'onPromoteToTaskMenuItemClicked'
	},
	
	initialize: function(options) {
		this.message = options.message;
		this.listenTo(this.model, 'change', this.render);
	},
		
	render: function() {		
		this.$el.html(this.template());
		this.$("#message-content").html(this.model.get('content'));
		var channel = this.model.get('channel');
		if(channel != null) {
			this.$("#item-channel-title").html(channel.title);
		}
		this.$("#item-channel-dropdown").toggle(channel != null);
		this.$('.dropdown-toggle').dropdown();
		switch(this.model.get('messageType')) {
			case 'TASK':
				this.$("#item-type-button i").attr('class', 'fa fa-tasks');
				this.$("#item-status-dropdown").css('display', 'inline-block');
				break;
			case 'COMMENT':
				this.$("#item-type-button i").attr('class', 'fa fa-comments');
				this.$("#item-status-dropdown").hide();
				break;
			case 'PROMOTE_TO_TASK':
				this.$('.list-group-item').attr('class', 'list-group-item activity-item');
				this.$("#item-type-button i").attr('class', 'fa fa-exchange');
				this.$("#item-status-dropdown").hide();
				this.$("#item-image").hide();
				this.$("#message-content").html("<b>@pd</b> promoted a comment to a task");
				break;
				
		}

		return this.el;
	},
	
	onViewDetailsMenuItemClicked: function() {
		this.trigger("message-clicked", this);
	},
	
	onPromoteToTaskMenuItemClicked: function() {
		this.trigger("promote-to-task-clicked", this);
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