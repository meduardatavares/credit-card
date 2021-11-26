(ns credit_card.logic
  (:require [credit_card.db :as l.db]
            [java-time :as time])
  (:use [clojure pprint]))

(defn valor-total-das-compras
  [compras]
  (->> compras
       (map :valor)
       (reduce +)))

(defn valor-total-das-compras-por-categoria
  [[categoria compras]]
  {categoria
   (valor-total-das-compras compras)})

(defn compras-realizadas-cliente
  [cliente]
  (:compras-realizadas (:cartao cliente)))

(defn gastos-por-categoria
  [cliente]
  (->> (compras-realizadas-cliente cliente)
       (group-by :categoria)
       (map valor-total-das-compras-por-categoria)
       pprint))

(defn fatura-do-mes
  [compras mes ano]
  (->> compras
       (filter #(and (= (time/month (:data %)) mes)  (= (time/year (:data %)) ano)))
       (map :valor)
       (reduce +)
       (println (time/.name mes) (time/.getValue ano) "- valor ")))

(defn fatura-dos-meses-por-ano-atual
  [cliente]
  (for [x (range 1 13)]
    (fatura-do-mes (compras-realizadas-cliente cliente) (time/month x) (time/year (time/local-date)))))

(defn compras-por-valor
  [cliente valor]
  (->> (compras-realizadas-cliente cliente)
       (filter #(> (:valor %) valor))
       pprint))

(defn compras-por-estabelecimento
  [cliente estabelecimento]
  (->> (compras-realizadas-cliente cliente)
       (filter #(= (:estabelecimento %) estabelecimento))
       pprint))


