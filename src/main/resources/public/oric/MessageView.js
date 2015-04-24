"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id:"MessageView",
	
	tagName:"li",
	
	template: _.template($('#MessageViewTemplate').html()),
	
	events: {
		'click': 'onClicked'
	},
	
	initialize: function(options) {
		this.message = options.message;
	},
		
	render: function() {		
		this.$el.html(this.template());
		this.$("#message-content").html(this.model.get('content'));
		//this.$("#item-image").html(this.generateUserImage());
		return this.el;
	},
	
	onClicked: function() {
		this.trigger("message-clicked", this);
	},

	select: function() {		
		this.$("a").addClass("active");
	},
	
	getMessageId: function() {
		return this.model.get('id');
	}
});