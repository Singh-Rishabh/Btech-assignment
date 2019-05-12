isOS([200], 'Ubuntu', 16.04, 64, 2000, 1000, ['lxml', 'gcc', 'foo', 'bar']).
isOS([201], 'Fedore', 23, 32, 1500, 700, ['lib_a', 'lib_b', 'lib_image', 'bar']).
isOS([203], 'Ubuntu', 16.04, 64, 20000, 10000, ['lxml', 'gcc', 'foo', 'bar', 'lib_b']).
isMachine(123, 'Physical', [200], 16384, 6144, 16).
isMachine(122, 'Physical', [203], 16384, 6144, 16).
isMachine(121555555, 'Virtual', [201], 4096, 256, 2).
isSoftwareApp(305, 'MySQL Server', 512, 4, 2, [200, 201, 203], ['lxml', 'gcc', 'foo', 'bar']).
isSoftwareApp(30555, 'Apache Web Serverrrrrrrrrrrrrrrr', 512, 1, 2, [200], ['lib_a', 'gcc', 'lib_b', 'bar']).
isSoftwareApp(30500000000000000, 'ImageProcessing Server', 2, 100, 8, [200], ['keras', 'gcc', 'lib_image', 'bar']).

contained_in(L1, L2) :- maplist(contains(L2), L1). 
contains(L, X) :- member(X, L).

query1(Id,M) :-
		isSoftwareApp(Id,_,B,C,D,E,F),
		isMachine(M,_,O,P,Q,R),
		P>=B,
		Q>=C,
		R>=D,
		contained_in(O,E),
		isOS(O,_,_,_,_,_,Y),
		contained_in(F,Y).

query2(Id1,Id2) :- 
		isSoftwareApp(Id1,_,B,C,D,E,F),
		isMachine(Id2,_,O,P,Q,R),
		P>=B,
		Q>=C,
		R>=D,
		contained_in(O,E),
		isOS(O,_,_,_,_,_,Y),
		contained_in(F,Y).