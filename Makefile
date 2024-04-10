clj_sources  := $(shell find src/clj       -name \*.clj)
cljs_sources := $(shell find src/cljs      -name \*.cljs)
e2e_sources  := $(shell find integration   -name \*.clj)
resources    := $(shell find resources     -name \*.clj)
spec_sources := $(shell find spec          -name \*.clj)

clean:
	rm -rf target

backend:
	clj -M:dev

repl:
	clj -M:dev:nrepl

.test: $(clj_sources) $(spec_sources)
	clojure -M:test
	touch $@

.e2e: $(clj_sources) $(cljs_sources) $(e2e_sources) $(resources)
	clojure -M:e2e
	touch $@

uberjar:
	clojure -T:build all
	cp target/optiliv-standalone.jar .

frontend:
	npx shadow-cljs watch app
