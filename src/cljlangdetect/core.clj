(ns cljlangdetect.core
  (:import [com.cybozu.labs.langdetect.Detector]
           [com.cybozu.labs.langdetect.DetectorFactory]
           [java.io.File])
  (:gen-class))


(def sep (java.io.File/separator))

;; Use the included data, to train detectors.

;;TODO: Sorry, couldn't bring this to work on time, it's late.
;;; To read them from the jar, however, first extract the directory
;;; to somewhere and then use the wrapped method to read it.
;;; The random temporary folder we will extract the files to
;(def target-dir (str (System/getProperty "java.io.tmpdir")
;                     sep "cljld" (rand-int 1000) sep))
;(def zs (java.util.zip.ZipInputStream. (.openStream (clojure.java.io/resource ";./profiles"))))
;;;Write all profiles to the temporary folder
;(doseq [ze (.getNextEntry zs)]
;  (when ze
;    (let [name (.getName ze)
;          jar-path (clojure.java.io/resource name)
;          path (str target-dir sep)
;          contents (slurp (.openStream jar-path))]
;      (spit path contents))))

(com.cybozu.labs.langdetect.DetectorFactory/loadProfile (str "resources" sep
                                                             "profiles"))

(defn detect [s]
  "Detects the language for a given string. Might not work well with very short strings."
  (let [detector (com.cybozu.labs.langdetect.DetectorFactory/create)]
    (.append detector s)
    (.detect detector)))

(defn get-probabilities [s]
  (let [detector (com.cybozu.labs.langdetect.DetectorFactory/create)]
    (.append detector s)
    (.getProbabilities detector)))

(defn -main [& args]
  (println (detect (apply str (concat (map str args))))))