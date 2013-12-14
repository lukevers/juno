(defproject juno "0.0.1"
  :description "Git sharing directly over HTTP"
  :url "https://github.com/lukevers/juno"
  :license {:name "GNU General Public License v3.0"
            :url "http://www.gnu.org/licenses/gpl-3.0.txt"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [me.raynes/fs "1.4.4"]
                 [http-kit "2.1.13"]
                 [compojure "1.1.6"]]
  :main ^:skip-aot juno.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
