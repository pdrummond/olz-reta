'use strict';

var $ = require('jquery');
var Backbone = require('backbone');
Backbone.$ = $;

var BaseDialog = require('./BaseDialog');

var DialogView;

module.exports = BaseDialog.extend({

	initialize: function(options) {
		BaseDialog.prototype.initialize.apply(this, [{
			title: options.title,
			animate: true,
			focusOk: false,
			allowCancel: options.allowCancel || false,			
			okText: options.okText || "Ok",
			content: new DialogView(options)
		}]);            
	},
});

var DialogView = Backbone.View.extend({

	initialize: function(options) {
		this.options = options;
	},

	render: function() {
		this.$el.html(this.options.msg);
		return this;
	},      

	error: function(errorMsg) {
		this.model.set({error: true, errorMsg: errorMsg});
		this.render();
		this.postDomRender();
	}
});


