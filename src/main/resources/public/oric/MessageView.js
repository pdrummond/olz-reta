"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id:"MessageView",
	
	tagName:"li",
	
	template: _.template($('#MessageViewTemplate').html()),
	
	initialize: function(options) {
		this.message = options.message;
	},
		
	render: function() {		
		this.$el.html(this.template());
		this.$("#message-content").html(this.model.get('content'));
		//this.$("#item-image").html(this.generateUserImage());
		return this.el;
	}
});