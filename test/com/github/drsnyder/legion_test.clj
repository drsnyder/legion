(ns com.github.drsnyder.legion-test
  (:use [com.github.drsnyder.legion :as legion])
  (:use [clojure.test]))

(deftest command-test
         (let [ret (command 3 (fn [n] (inc n)) 2)]
           (is (= 9 (apply + ret)))))

(deftest deploy-test
         (let [ret (deploy 2 3 (fn [n] (inc n)) 2)]
           (is (= 18 (reduce #(+ %1 %2) (map #(apply + %) ret))))))

(deftest deploy-n-test
         (let [ret (deploy 100 3 (fn [n] n) 3)]
           (is (= 900 (reduce #(+ %1 %2) (map #(apply + %) ret))))))
