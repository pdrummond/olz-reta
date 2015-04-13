var Backbone = require('backbone');
var _ = require('underscore');

var LoopItemModel = require('./LoopItemModel');

module.exports = Backbone.Collection.extend({
	model: LoopItemModel,

	comparator : function(loopItem) {
		return loopItem.get('createdAt');
	},

	url: function() {
		return "/rest/olz-items";
	}
});
