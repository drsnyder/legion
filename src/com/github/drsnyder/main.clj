(ns com.github.drsnyder.main
  (:use [com.github.drsnyder.legion :as legion])
  (:use clojure.tools.cli)
  (:use [clojure.java.jdbc])
  (:gen-class))

(defn deploy-command [threads calls command & args]
    (when-let [fun (ns-resolve *ns* (symbol command))]
      (deploy threads calls fun args)))

(defn process-results [command results]
    (when-let [fun (ns-resolve *ns* (symbol command))]
      (fun results)))

(defn -main [& args]
  (let [[options args banner] (cli args ["-c" "--command" :default nil]
                                        ["-f" "--command-file" :default "legionfile"]
                                        ["-t" "--threads" :default 1 :parse-fn #(Integer. %)]
                                        ["-n" "--calls"   :default 1 :parse-fn #(Integer. %)]
                                        ["-p" "--post-process" :default nil]
                                        ["-h" "--help" "Show help" :default false :flag true])]
    (when (:help options)
      (println banner)
      (System/exit 0))

    (when (and (:command options) (:command-file options))
      (load-file (:command-file options))
      (println "deploying " (:command options))
      (let [ret (deploy-command (:threads options) (:calls options) (:command options))]
        (if (and ret (:post-process options))
          (process-results (:post-process options) ret)
          (println ret)))
      (System/exit 0))))
