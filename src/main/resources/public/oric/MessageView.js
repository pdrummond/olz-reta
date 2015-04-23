"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id:"MessageView",
	
	tagName:"li",
	
	template: _.template($('#MessageViewTemplate').html()),
	
	events: {
		'click': 'onItemClicked',
		'click #show-detail-button': 'onShowDetailButtonClicked'
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
	
	onItemClicked: function() {
		$("#MessageView a").removeClass('active');
		this.$("a").addClass("active");		
	},
	
	onShowDetailButtonClicked: function() {
		if($("#app-container").css('left') == '0px') {
			$("#app-container").animate({left: '50%'}, 100);
			$("#left-sidebar").animate({left: '0%'}, 100);
		} else {
			$("#app-container").animate({left: '0%'}, 100);
			$("#left-sidebar").animate({left: '-60%'}, 100);
		}
	}
});