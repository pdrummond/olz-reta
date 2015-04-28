"use strict";

//var crypto = require('crypto');
var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id:"MessageView",
	
	tagName:"li",
	
	template: _.template($('#MessageViewTemplate').html()),
	
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
		$('.dropdown-toggle').dropdown();
		if(this.model.get('messageType') == 'TASK') {
			this.$('.list-group-item').attr('class', 'list-group-item list-group-item-success');
			this.$("#item-type-button i").attr('class', 'fa fa-tasks');
			this.$("#item-status-dropdown").css('display', 'inline-block');
		} else {
			this.$("#item-type-button i").attr('class', 'fa fa-comments');
			this.$("#item-status-dropdown").hide();
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