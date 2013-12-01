(defproject cljlangdetect "0.0.1"
  :description "Language detection for text. Wrapper of http://code.google.com/p/language-detection/"
  :license {:name "Apache License, 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.cybozu.labs/langdetect "1.1-20120112"]]
  :main cljlangdetect.core)
