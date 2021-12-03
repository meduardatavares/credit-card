(ns credit_card.logic
  (:require [credit_card.db :as l.db]
            [java-time :as time]
            [schema.core :as s])
  (:use [clojure pprint])
  (:import java.time.LocalDate
           (java.time Month Year)))

(s/set-fn-validation! true)

(def Compra
  {:id              s/Keyword
   :data            LocalDate
   :valor           s/Num
   :estabelecimento s/Str
   :categoria       s/Str

   })

(def Compras
  [Compra])

(def Cartao
  {:numero             s/Num
   :cvv                s/Num
   :validade           LocalDate
   :limite             s/Num
   :compras-realizadas Compras

   })
(def Cliente
  {:nome   s/Str
   :cpf    s/Num
   :email  s/Str
   :cartao Cartao})



(s/defn valor-total-das-compras
        [compras :- Compras]
        (->> compras
             (map :valor)
             (reduce +)))

(s/defn valor-total-das-compras-por-categoria
  [[categoria  compras]]
  {categoria
   (valor-total-das-compras compras)})

(s/defn compras-realizadas-cliente
        [cliente :- Cliente]
        (:compras-realizadas (:cartao cliente)))

(s/defn gastos-por-categoria
   [cliente :- Cliente]
   (->> (compras-realizadas-cliente cliente)
             (group-by :categoria)
             (map valor-total-das-compras-por-categoria)
        ))

(s/defn fatura-do-mes
  [compras :- Compras mes :- Month ano :- Year]
  (->> compras
       (filter #(and (= (time/month (:data %)) mes) (= (time/year (:data %)) ano)))
       (map :valor)
       (reduce +)
       (println (time/.name mes) (time/.getValue ano) "- valor ")))

(s/defn fatura-dos-meses-por-ano-atual
  [cliente :- Cliente]
  (for [x (range 1 13)]
    (fatura-do-mes (compras-realizadas-cliente cliente) (time/month x) (time/year (time/local-date)))))

(s/defn compras-por-valor
  [cliente :- Cliente valor :- s/Num]
  (->> (compras-realizadas-cliente cliente)
       (filter #(and (>= (:valor %) valor) (> valor 0))
               )))

(s/defn compras-por-estabelecimento
  [cliente :- Cliente estabelecimento :- s/Str]
  (->> (compras-realizadas-cliente cliente)
       (filter #(= (:estabelecimento %) estabelecimento))
       ))

(s/defn adiciona-compra [cliente :- Cliente nova-compra :- Compra]
  (conj (compras-realizadas-cliente cliente) nova-compra))



