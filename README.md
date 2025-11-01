```
          _   _ _ _   _                 _                
         | | | | | |_(_)_ __ ___   __ _| |_ ___          
         | | | | | __| | '_ ` _ \ / _` | __/ _ \         
         | |_| | | |_| | | | | | | (_| | ||  __/         
          \___/|_|\__|_|_| |_| |_|\__,_|\__\___|         
     _____ _        _____            _____               
    |_   _(_) ___  |_   _|_ _  ___  |_   _|__   __       
      | | | |/ __|   | |/ _` |/ __|   | |/ _ \ / _ \     
      | | | | (__    | | (_| | (__    | | (_) |  __/     
      |_| |_|\___|   |_|\__,_|\___|   |_|\___/ \___|     
```

<a href="https://github.com/NonlinearFruit/ultimate-tic-tac-toe/actions"><img src="https://img.shields.io/github/actions/workflow/status/nonlinearfruit/ultimate-tic-tac-toe/ci.yml"></a>

What is Ultimate Tic Tac Toe? 3x3 grid of tic-tac-toe boards. Goal: Win 3 boards in a line. ([wiki](https://en.wikipedia.org/wiki/Ultimate_tic-tac-toe)) ([game](https://michaelxing.com/UltimateTTT/v3/))

## Clojure

- Leiningen <https://leiningen.org/>
    - Also clojure cli <https://tomekw.com/clojure-deps-edn-a-basic-guide/>
- Speclj <https://github.com/slagyr/speclj>
    - Docs <http://micahmartin.com/speclj/speclj.core.html>
- Docs
    - Examples <https://clojuredocs.org>
    - docstrings <https://cljdoc.org>
- Weird Characters <https://clojure.org/guides/weird_characters>
- GitHub Action <https://github.com/marketplace/actions/setup-clojure>

```clj
(dir speclj.core) ; Prints a sorted directory of public vars in a namespace
(doc dir) ; Prints documentation for a var or special form given its name, or for a spec if given a keyword (very sparse)
```

### Install

<https://clojure.org/guides/install_clojure>

- OpenJDK
- leiningen

```sh
nix-shell -p leiningen
```

### Usage

```sh
lein new speclj PROJECT # Creates folder, not git
lein run
lein spec
```

## Plan

- human v monte carlo bot gif
- cli flags to decide:
     - human v human
     - human v random bot
     - human v monte carlo bot
- renovate

## Demo

![ultimate tic tac toe being played in the terminal](./img/human-vs-random-bot.gif)
