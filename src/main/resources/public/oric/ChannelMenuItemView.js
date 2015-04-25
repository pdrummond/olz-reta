"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id:"ChannelMenuItemView",
	
	tagName:"li",
	
	template: _.template($('#ChannelMenuItemViewTemplate').html()),
	
	events: {
		'click': 'onClicked'
	},
	
	initialize: function(options) {	
		this.model = options.model;
		this.listenTo(this.model, 'change', this.render);
	},
		
	render: function() {		
		this.$el.html(this.template(this.model.attributes));
		return this.el;
	},
	
	onClicked: function() {
		this.trigger("message-clicked", this);
	}	
});