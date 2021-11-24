(ns credit_card.logic (:require [credit_card.db :as l.db] [java-time :as time] ))

(defn quantia-total-por-categoria
  [[categoria compra]]
  {categoria  (->> compra
                   (map :valor )
                   (reduce +))}
  )

(defn compras-por-categoria  [compras]
  (->>  compras
        (group-by :categoria)
        (map quantia-total-por-categoria )
        println)
  )

(defn gastos-por-categoria [cartao]
  (compras-por-categoria (:compras-realizadas cartao)))

(gastos-por-categoria (l.db/retorna-cartao))

(defn retorna-mes [data]
  (time/month data))
(defn mes  [compras]
  (->> compras
       (map :data )
       (map retorna-mes)
       )
  )
(defn compras-por-mes [compras]
  (->>  compras
        (group-by (mes compras))
        println)
  )



(defn fatura-do-mes [cartao]
  (compras-por-mes (:compras-realizadas cartao))
  )


(fatura-do-mes (l.db/retorna-cartao))
