"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id:"MessageView",
	
	tagName:"li",
	
	template: _.template($('#MessageViewTemplate').html()),
	
	events: {
		'click #show-detail-button': 'onShowDetailButtonClicked'
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
		$('.dropdown-toggle').dropdown();
		return this.el;
	},
	
	onShowDetailButtonClicked: function() {
		this.trigger("message-clicked", this);
	},

	select: function() {		
		this.$("a").addClass("active");
	},
	
	getMessageId: function() {
		return this.model.get('id');
	}
});