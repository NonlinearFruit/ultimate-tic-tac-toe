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

What is Ultimate Tic Tac Toe? ([wiki](https://en.wikipedia.org/wiki/Ultimate_tic-tac-toe)) ([game](https://michaelxing.com/UltimateTTT/v3/))

## Clojure

- Leiningen <https://leiningen.org/>
    - Also clojure cli <https://tomekw.com/clojure-deps-edn-a-basic-guide/>
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

## Plan

- Validate inputs
    - Can't steal square already set
    - Input must be in 0-8 range
    - Can't play in a board that is won
    - If opponent plays in square x and board x isn't won, then player must play on board x 
- Bot to play against
    - that makes random moves (player vs bot)
    - that uses a minimax via some heuristic
    - that does a monte-carlo tree search
- Better display
    - Best win display/game conclusion
    - Prompt the user for board, then square
    - Display boards that are won as the symbol that won it
- vhs gif(s) <https://github.com/charmbracelet/vhs>

### Display

Board View

```
         |         |         
   | |   |  1|2|3  |   | |   
  -+-+-  |  -+-+-  |  -+-+-  
   | |   |  4|5|6  |   | |   
  -+-+-  |  -+-+-  |  -+-+-  
   | |   |  7|8|9  |   | |   
         |         |         
---------+---------+---------
         |         |         
  X|X|O  |   | |   |   | |   
  -+-+-  |  -+-+-  |  -+-+-  
  O|X|X  |  O|X|   |   | |   
  -+-+-  |  -+-+-  |  -+-+-  
  O|O|X  |   | |   |   | |   
         |         |         
---------+---------+---------
         |         |         
  X   X  |  OOOOO  |   | |   
   X X   |  O   O  |  -+-+-  
    X    |  O   O  |   | |   
   X X   |  O   O  |  -+-+-  
  X   X  |  OOOOO  |   | |   
         |         |         
```

Board Select
```
         |         |         
    1    |   222   |  3333   
    1    |  2   2  |      3  
    1    |     2   |   333   
    1    |    2    |      3  
    1    |   2222  |  3333   
         |         |         
---------+---------+---------
         |         |         
     4   |   5555  |  66666  
  4  4   |  5      |  6      
  4444   |  55555  |  66666  
     4   |      5  |  6   6  
     4   |  55555  |  66666  
         |         |         
---------+---------+---------
         |         |         
  77777  |   888   |  99999  
      7  |  8   8  |  9   9  
     7   |   888   |  99999  
    7    |  8   8  |      9  
   7     |   888   |  99999  
         |         |         
```
