"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({
	
	id: "HiddenAlertView",
	template: _.template($('#HiddenAlertViewTemplate').html()),
	
	events: {
		'click #reveal-link': 'onRevealLinkClicked'
	},
	
	initialize: function() {
		this.model = new Backbone.Model({hiddenMessageCount: 0});
		this.listenTo(this.model, 'change', this.render);
	},
	
	incHiddenMessageCount: function() {
		this.hiddenMessageCount = this.model.get('hiddenMessageCount');
		this.model.set('hiddenMessageCount', ++this.hiddenMessageCount);
	},
	
	resetHiddenMessageCount: function() {
		this.model.set('hiddenMessageCount', 0);
	},
	
	render: function() {
		this.$el.html(this.template());
		this.$("#alert-message").html('<strong><i class="fa fa-exclamation-circle"></i> ' + this.hiddenMessageCount + ' New Messages hidden by filter.</strong> <a id="reveal-link">Click here to reveal</a>.');
		this.$el.toggle(this.model.get('hiddenMessageCount') > 0);
		return this.el;		
	},
	
	onRevealLinkClicked: function() {
		this.trigger('reveal-link-clicked');
	}
});