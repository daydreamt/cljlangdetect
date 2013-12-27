(ns cljlangdetect.core
  (:import [com.cybozu.labs.langdetect.Detector]
           [com.cybozu.labs.langdetect.DetectorFactory]
           [com.cybozu.labs.langdetect LangDetectException]
           [java.io.File]
           [org.apache.commons.io.FileUtils]
           [org.apache.commons.io.IOUtils])
  (:gen-class))

(def sep (java.io.File/separator))

(def profiles (atom false))

(defn- load-profiles []
  "The profiles are in paths given by profiles.txt. Take them and write them to a temporary directory."
  (when (not @profiles)
    (let [res-list (slurp (clojure.java.io/resource "profiles.txt"))
          profiles (clojure.string/split res-list #"\n")
          tmp (str (System/getProperty "java.io.tmpdir") sep)
          folder (loop [it 0]
                   (let [filename (str tmp "cljld" (rand-int 10000))
                         f (java.io.File. filename)]
                     (if (and (< it 1000) (.mkdir f))
                       (str (.getAbsolutePath f) sep)
                       (recur (inc it)))))]

      ;; (println "Extracting to " folder)
      ;; (println "Profiles containing " (count profiles) " entries")
      (doseq [prof profiles]
        ;; (println "writing to " (str folder prof) "from " prof) 
        (spit (str folder prof) (slurp (clojure.java.io/resource prof))))
      ;; (println "Reading from temporary folder " folder)
      (try
        (com.cybozu.labs.langdetect.DetectorFactory/loadProfile folder)
        (catch LangDetectException e
          "Language files already loaded")
        (finally
          (.delete (java.io.File. folder)))))
    (swap! profiles not)))

(defn detect [s]
  "Detects the language for a given string. Might not work well with very short strings."
  (load-profiles)
  (let [detector (com.cybozu.labs.langdetect.DetectorFactory/create)]
    (.append detector s)
    (.detect detector)))

(defn get-probabilities [s]
  (load-profiles)
  (let [detector (com.cybozu.labs.langdetect.DetectorFactory/create)]
    (.append detector s)
    (.getProbabilities detector)))

(defn -main [& args]
  (load-profiles)
  (println (detect (apply str (concat (map str args))))))