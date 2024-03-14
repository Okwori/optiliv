clean:
	rm -rf target

backend:
	clj -M:dev

repl:
	clj -M:dev:nrepl

test:
	clj -M:test

uberjar:
	clj -T:build all

frontend:
	npx shadow-cljs watch app
