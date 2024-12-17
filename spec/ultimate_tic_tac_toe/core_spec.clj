(ns ultimate-tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [ultimate-tic-tac-toe.core :refer :all]))

(describe "Has player won?"
  (it "has no win for nil board"
    (should= false (has-player-won? :x [nil nil nil nil nil nil nil nil nil])))
  (it "has no win for cats game"
    (should= false (has-player-won? :x [:x :o :x :x :o :x :o :x :o])))
  (it "has no win for opponents game"
    (should= false (has-player-won? :x [:o :o :o nil nil nil nil nil nil])))

  (it "finds top row win"
    (should= true (has-player-won? :x [:x :x :x nil nil nil nil nil nil])))
  (it "finds middle row win"
    (should= true (has-player-won? :x [nil nil nil :x :x :x nil nil nil])))
  (it "finds bottom row win"
    (should= true (has-player-won? :x [nil nil nil nil nil nil :x :x :x])))

  (it "finds first column win"
    (should= true (has-player-won? :x [:x nil nil :x nil nil :x nil nil])))
  (it "finds middle column win"
    (should= true (has-player-won? :x [nil :x nil nil :x nil nil :x nil])))
  (it "finds last column win"
    (should= true (has-player-won? :x [nil nil :x nil nil :x nil nil :x])))

  (it "finds forward diagonal win"
    (should= true (has-player-won? :x [nil nil :x nil :x nil :x nil nil])))
  (it "finds backward diagonal win"
    (should= true (has-player-won? :x [:x nil nil nil :x nil nil nil :x]))))
