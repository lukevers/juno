(defproject juno "0.0.1"
  :description "Git sharing directly over HTTP"
  :url "https://github.com/lukevers/juno"
  :license {:name "GNU General Public License v3.0"
            :url "http://www.gnu.org/licenses/gpl-3.0.txt"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot juno.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
