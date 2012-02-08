(ns com.github.drsnyder.legion)

(defn command 
  [n f & args] 
  (doall (repeatedly n #(apply f args))))

(defn deploy 
  [t n f & args]
  (doall (pmap (fn [pn] (apply command (flatten [pn f args]))) (repeat t n))))
