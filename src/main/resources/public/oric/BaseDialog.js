/**
 * BaseDialog.js - Adapted from backbone.bootstrap-modal.js; a Bootstrap Modal wrapper for use with Backbone.
 *
 * Takes care of instantiation, manages multiple modals,
 * adds several options and removes the element from the DOM when closed
 *
 * @author Charles Davison <charlie@powmedia.co.uk>
 *
 * Events:
 * shown.bs.modal: Fired when the modal has finished animating in
 * hidden.bs.modal: Fired when the modal has finished animating out
 * cancel: The user dismissed the modal
 * ok: The user clicked OK
 */
var $ = jQuery = require('jquery');
var _ = require('underscore');
var Backbone = require('backbone');
var bootstrap = require('bootstrap');
Backbone.$ = $;


//Set custom template settings
var _interpolateBackup = _.templateSettings;
_.templateSettings = {
		interpolate: /\{\{(.+?)\}\}/g,
		evaluate: /<%([\s\S]+?)%>/g
};

var template = _.template('\
		<div class="modal-dialog">\
		<div class="modal-content">\
		<% if (customHeader) { %>\
		{{customHeader}}\
		<% } else if (title) { %>\
		<div class="modal-header">\
		<% if (allowCancel) { %>\
		<a class="close">&times;</a>\
		<% } %>\
		<h4>{{title}}</h4>\
		</div>\
		<% } %>\
		<div id="base-dialog-alert" style="display:none" class="alert alert-info" role="alert">\
		<span id="base-dialog-alert-msg"></span>\
		</div>\
		<% if(removeModalBodyPadding) { %>\
		<div class="modal-body" style="padding:0">{{content}}</div>\
		<% } else { %>\
		<div class="modal-body">{{content}}</div>\
		<% } %>\
		<% if (allowFooter) { %>\
		<div class="modal-footer">\
		<button id="third-button" class="pull-left btn {{thirdButtonType}}">{{thirdButtonText}}</button>\
		<% if (allowCancel) { %>\
		<button class="cancel btn btn-default">{{cancelText}}</button>\
		<% } %>\
		<% if (allowOk) { %>\
		<button class="ok btn btn-primary">{{okText}}</button>\
		<% } %>\
		</div>\
		<% } %>\
		</div></div>\
');

//Reset to users' template settings
_.templateSettings = _interpolateBackup;


var Modal = Backbone.View.extend({

	className: 'modal',

	events: {
		'click #third-button': function(event) {
			event.preventDefault();
			this.trigger(this.options.thirdButtonEvent);
			this.close();
		},
		'click .close': function(event) {
			event.preventDefault();

			this.trigger('cancel');

			if (this.options.content && this.options.content.trigger) {
				this.options.content.trigger('cancel', this);
			}
		},
		'click .cancel': function(event) {
			event.preventDefault();

			this.trigger('cancel');

			if (this.options.content && this.options.content.trigger) {
				this.options.content.trigger('cancel', this);
			}
		},
		'click .ok': function(event) {
			event.preventDefault();

			var ok = true;
			if(this.options.validate) {
				ok = this.doValidationFirst();
			} 

			if(ok == true) {
				this.trigger('ok');
				if (this.options.content && this.options.content.trigger) {
					this.options.content.trigger('ok', this);
				}

				if (this.options.okCloses) {
					this.close();
				}
			}
		},
		'keypress': function(event) {
			$("#base-dialog-alert").slideUp();
			if (this.options.preventDefaultForEnterKey && event.which == 13) {
				event.preventDefault();

				if(this.options.enterTriggersOk) { 

					this.trigger('ok');

					if (this.options.content && this.options.content.trigger) {
						this.options.content.trigger('ok', this);
					}

					if (this.options.okCloses) {
						this.close();
					}
				}
			}
		}
	},
	
	doValidationFirst: function() {
		var result = this.options.validate();
		if(!result.ok && result.errorMessage != null) {
			$("#base-dialog-alert-msg").html(result.errorMessage);
			$("#base-dialog-alert").slideDown();
		}
		return result.ok;
	},

	/**
	 * Creates an instance of a Bootstrap Modal
	 *
	 * @see http://twitter.github.com/bootstrap/javascript.html#modals
	 *
	 * @param {Object} options
	 * @param {String|View} [options.content] Modal content. Default: none
	 * @param {String} [options.title]        Title. Default: none
	 * @param {String} [options.okText]       Text for the OK button. Default: 'OK'
	 * @param {String} [options.cancelText]   Text for the cancel button. Default: 'Cancel'. If passed a falsey value, the button will be removed
	 * @param {Boolean} [options.allowCancel  Whether the modal can be closed, other than by pressing OK. Default: true
	 * @param {Boolean} [options.escape]      Whether the 'esc' key can dismiss the modal. Default: true, but false if options.cancellable is true
	 * @param {Boolean} [options.animate]     Whether to animate in/out. Default: false
	 * @param {Function} [options.tmpl]   	  Compiled underscore template to override the default one
	 */
	initialize: function(options) {
		this.options = _.extend({
			title: null,
			okText: 'OK',
			focusOk: true,
			okCloses: true,
			cancelText: 'Cancel',
			allowCancel: true,
			allowOk: true,
			allowFooter: true,
			escape: true,
			animate: false,
			customHeader: false,
			removeModalBodyPadding: false,
			tmpl: template,
			thirdButtonText: '',
			thirdButtonType: 'btn-default'
		}, options);
	},

	/**
	 * Creates the DOM element
	 *
	 * @api private
	 */
	render: function() {
		var $el = this.$el,
		options = this.options,
		content = options.content;

		//Create the modal container
		$el.html(options.tmpl(options));
		
		var $content = this.$content = $el.find('.modal-body');

		//Insert the main content if it's a view
		if (content.$el) {
			content.render();
			$el.find('.modal-body').html(content.$el);
			$el.find('#third-button').toggle(options.thirdButtonText != null && options.thirdButtonText.length > 0);
			if(content.postDomRender) {
				content.postDomRender();
			}
		}

		if (options.animate) $el.addClass('fade');

		this.isRendered = true;

		return this;
	},

	/**
	 * Renders and shows the modal
	 *
	 * @param {Function} [cb]     Optional callback that runs only when OK is pressed.
	 */
	open: function(cb) {
		if (!this.isRendered) this.render();

		var self = this,
		$el = this.$el;

		//Create it
		$el.modal(_.extend({
			keyboard: this.options.allowCancel,
			backdrop: this.options.backdrop || 'static'
		}, this.options.modalOptions));

		//Focus OK button
		$el.one('shown.bs.modal', function() {
			if (self.options.focusOk) {
				$el.find('.btn.ok').focus();
			}

			if (self.options.content && self.options.content.trigger) {
				self.options.content.trigger('shown.bs.modal', self);
			}

			self.trigger('shown.bs.modal');
		});

		//Adjust the modal and backdrop z-index; for dealing with multiple modals
		var numModals = Modal.count,
		$backdrop = $('.modal-backdrop:eq('+numModals+')'),
		backdropIndex = parseInt($backdrop.css('z-index'),10),
		elIndex = parseInt($backdrop.css('z-index'), 10);

		$backdrop.css('z-index', backdropIndex + numModals);
		this.$el.css('z-index', elIndex + numModals);

		if (this.options.allowCancel) {
			$backdrop.one('click', function() {
				if (self.options.content && self.options.content.trigger) {
					self.options.content.trigger('cancel', self);
				}

				self.trigger('cancel');
			});

			$(document).one('keyup.dismiss.modal', function (e) {
				e.which == 27 && self.trigger('cancel');

				if (self.options.content && self.options.content.trigger) {
					e.which == 27 && self.options.content.trigger('shown.bs.modal', self);
				}
			});
		}

		this.on('cancel', function() {
			self.close();
		});

		Modal.count++;

		//Run callback on OK if provided
		if (cb) {
			self.on('ok', cb);
		}

		return this;
	},

	/**
	 * Closes the modal
	 */
	close: function() {
		var self = this,
		$el = this.$el;

		//Check if the modal should stay open
		if (this._preventClose) {
			this._preventClose = false;
			return;
		}

		$el.one('hidden.bs.modal', function onHidden(e) {
			// Ignore events propagated from interior objects, like bootstrap tooltips
			if(e.target !== e.currentTarget){
				return $el.one('hidden.bs.modal', onHidden);
			}
			self.remove();

			if (self.options.content && self.options.content.trigger) {
				self.options.content.trigger('hidden.bs.modal', self);
			}

			self.trigger('hidden.bs.modal');
		});

		$el.modal('hide');

		Modal.count--;
	},

	/**
	 * Stop the modal from closing.
	 * Can be called from within a 'close' or 'ok' event listener.
	 */
	preventClose: function() {
		this._preventClose = true;
	}
}, {
	//STATICS

	//The number of modals on display
	count: 0
});

module.exports = Modal;
