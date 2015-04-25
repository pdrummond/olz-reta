"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id:"ChannelView",
	
	tagName:"li",
	
	template: _.template($('#ChannelViewTemplate').html()),
	
	events: {
		'click': 'onClicked'
	},
	
	initialize: function(options) {
		this.message = options.message;
		this.listenTo(this.model, 'change', this.render);
	},
		
	render: function() {		
		this.$el.html(this.template());
		this.$("#content").html(this.model.get('createdBy').tag + " created a new channel called " + this.model.get('title'));	
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