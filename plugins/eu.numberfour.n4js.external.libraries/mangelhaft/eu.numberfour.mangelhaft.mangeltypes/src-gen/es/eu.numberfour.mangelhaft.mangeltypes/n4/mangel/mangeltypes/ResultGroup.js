(function(System) {
	'use strict';
	System.register([], function($n4Export) {
		var ResultGroup;
		ResultGroup = function ResultGroup(testResults, description) {
			this.description = "";
			this.testResults = [];
			this.successes = 0;
			this.failures = 0;
			this.errors = 0;
			this.skipped = 0;
			this.testResults = testResults;
			this.description = description;
			for(let testResult of testResults) {
				switch(testResult.testStatus) {
					case 'PASSED':
						this.successes += 1;
						break;
					case 'ERROR':
						this.errors += 1;
						break;
					case 'FAILED':
						this.failures += 1;
						break;
					case 'SKIPPED':
					case 'SKIPPED_FIXME':
					case 'SKIPPED_IGNORE':
					case 'SKIPPED_NOT_IMPLEMENTED':
					case 'SKIPPED_PRECONDITION':
						this.skipped += 1;
						break;
					default:
						throw new Error("Unhandled TestStatus type");
				}
			}
		};
		$n4Export('ResultGroup', ResultGroup);
		return {
			setters: [],
			execute: function() {
				$makeClass(ResultGroup, Object, [], {
					description: {
						value: undefined,
						writable: true
					},
					testResults: {
						value: undefined,
						writable: true
					},
					successes: {
						value: undefined,
						writable: true
					},
					failures: {
						value: undefined,
						writable: true
					},
					errors: {
						value: undefined,
						writable: true
					},
					skipped: {
						value: undefined,
						writable: true
					}
				}, {}, function(instanceProto, staticProto) {
					var metaClass = new N4Class({
						name: 'ResultGroup',
						origin: 'eu.numberfour.mangelhaft.mangeltypes',
						fqn: 'n4.mangel.mangeltypes.ResultGroup.ResultGroup',
						n4superType: N4Object.n4type,
						allImplementedInterfaces: [],
						ownedMembers: [
							new N4DataField({
								name: 'description',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'testResults',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'successes',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'failures',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'errors',
								isStatic: false,
								annotations: []
							}),
							new N4DataField({
								name: 'skipped',
								isStatic: false,
								annotations: []
							}),
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
			}
		};
	});
})(typeof module !== 'undefined' && module.exports ? require('n4js-node/index').System(require, module) : System);
//# sourceMappingURL=ResultGroup.map
