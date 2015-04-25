var $ = require('jquery');
var Backbone = require('backbone');
var _ = require('underscore');

var PromptDialog = require("./PromptDialog");

module.exports = Backbone.View.extend({

	showPromptDialog: function(title, opts, callback) {
		opts = opts || {};
		var self = this;
		var modal = new PromptDialog({
			title: title,
			message: opts.message || '',
			label: opts.label || '',
			content: opts.content || '',
		});
		modal.open();
		this.listenTo(modal, 'ok', function() {
			callback(modal.getContent());
		});
	},
	
	emptyViews: function() {
		_.each(this.views, function(view) {
			view.remove();
		});		
	},
});