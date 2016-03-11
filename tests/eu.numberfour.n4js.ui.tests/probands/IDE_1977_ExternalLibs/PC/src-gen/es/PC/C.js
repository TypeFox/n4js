(function(System) {
	'use strict';
	System.register([
		'PB/B'
	], function($n4Export) {
		var B, C;
		C = function C() {
			B.prototype.constructor.call(this);
			console.log('External C<init>');
		};
		$n4Export('C', C);
		return {
			setters: [
				function($_import_PB_B) {
					B = $_import_PB_B.B;
				}
			],
			execute: function() {
				$makeClass(C, B, [], {}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'C',
						origin: 'PC',
						fqn: 'C.C',
						n4superType: B.n4type,
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
				Object.defineProperty(C, '$di', {
					value: {
						superType: B,
						fieldsInjectedTypes: []
					}
				});
			}
		};
	});
})(typeof module !== 'undefined' && module.exports ? require('n4js-node/index').System(require, module) : System);
//# sourceMappingURL=C.map
