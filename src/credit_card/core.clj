(ns credit-card.core
  (:require [credit_card.db :as l.db]
            [credit_card.logic :as l.logic]
            [java-time :as time]))

(println "Bem vindo ao sistema de Cartão de Credito")
(println "")


(println "Gastos por categoria")
(l.logic/gastos-por-categoria l.db/cliente1)
(println "")


(println "Compras por valor")
(l.logic/compras-por-valor l.db/cliente1 50.0)
(println "")

(println "Compras por estabelecimento")
(l.logic/compras-por-estabelecimento l.db/cliente1 "ABC")
(println "")

(println "Fatura do mes")
(l.logic/fatura-do-mes (l.logic/compras-realizadas-cliente l.db/cliente1) (month 1)  (year 2021))
(println "")

(println "Fatura dos meses por ano atual")
(l.logic/fatura-dos-meses-por-ano-atual l.db/cliente1)



