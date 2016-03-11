(function(System) {
	'use strict';
	System.register([
		'PA/A'
	], function($n4Export) {
		var A, B;
		B = function B() {
			A.prototype.constructor.call(this);
			console.log('External B<init>');
		};
		$n4Export('B', B);
		return {
			setters: [
				function($_import_PA_A) {
					A = $_import_PA_A.A;
				}
			],
			execute: function() {
				$makeClass(B, A, [], {}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'B',
						origin: 'PB',
						fqn: 'B.B',
						n4superType: A.n4type,
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
				Object.defineProperty(B, '$di', {
					value: {
						superType: A,
						fieldsInjectedTypes: []
					}
				});
			}
		};
	});
})(typeof module !== 'undefined' && module.exports ? require('n4js-node/index').System(require, module) : System);
//# sourceMappingURL=B.map
