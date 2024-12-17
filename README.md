# Ultimate Tic Tac Toe

What is Ultimate Tic Tac Toe? ([wiki](https://en.wikipedia.org/wiki/Ultimate_tic-tac-toe)) ([game](https://michaelxing.com/UltimateTTT/v3/))

## Clojure

- Leiningen <https://leiningen.org/>
- Speclj <https://github.com/slagyr/speclj>
    - Docs <http://micahmartin.com/speclj/speclj.core.html>
- Docs <https://clojuredocs.org/>
- Weird Characters <https://clojure.org/guides/weird_characters>
- GitHub Action <https://github.com/marketplace/actions/setup-clojure>

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
