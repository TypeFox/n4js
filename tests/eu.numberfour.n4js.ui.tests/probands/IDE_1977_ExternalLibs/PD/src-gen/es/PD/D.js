(function(System) {
	'use strict';
	System.register([
		'PC/C'
	], function($n4Export) {
		var C, D;
		D = function D() {
			C.prototype.constructor.call(this);
			console.log('External D<init>');
		};
		$n4Export('D', D);
		return {
			setters: [
				function($_import_PC_C) {
					C = $_import_PC_C.C;
				}
			],
			execute: function() {
				$makeClass(D, C, [], {}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'D',
						origin: 'PD',
						fqn: 'D.D',
						n4superType: C.n4type,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4Method({
								name: 'constructor',
								isStatic: false,
								jsFunction: instanceProto['constructor'],
								annotations: []
							})
						],
						consumedMembers: [],
						annotations: []
					});
					return metaClass;
				});
				Object.defineProperty(D, '$di', {
					value: {
						superType: C,
						fieldsInjectedTypes: []
					}
				});
			}
		};
	});
})(typeof module !== 'undefined' && module.exports ? require('n4js-node/index').System(require, module) : System);
//# sourceMappingURL=D.map
