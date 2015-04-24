"use strict";

var _ = require('underscore');
var $ = require('jquery');
var Backbone = require('backbone');

module.exports = Backbone.View.extend({

	id:"MessageDetailView",	
	template: _.template($('#MessageDetailViewTemplate').html()),
	
	initialize: function() {
	},
	
	render: function() {
		if(this.model != null) {
			this.$el.html(this.template(this.model.attributes));
		}
		return this.el;
	},

	setModel: function(model) {
		this.model = model;
		this.listenTo(this.model, 'reset', this.render);
		this.render();
	},
	
	getMessageId: function() {
		if(this.model == null) {
			return 0;
		} else {
			return this.model.get('id');
		}
	}
});