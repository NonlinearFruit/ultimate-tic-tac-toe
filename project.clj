(defproject ultimate-tic-tac-toe "0.1.0-SNAPSHOT"
  :description "Tic Tac Toe but interesting"
  :url "https://github.com/NonlinearFruit/ultimate-tic-tac-toe"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main ultimate-tic-tac-toe.core
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"]]
  :test-paths ["spec"])
