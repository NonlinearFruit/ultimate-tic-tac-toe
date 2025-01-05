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

## Plan

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
