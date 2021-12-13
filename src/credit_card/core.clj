(ns credit-card.core
  (:use clojure.pprint)
  (:require [credit_card.db :as l.db]
            [credit_card.logic :as l.logic]
            [credit_card.model :as l.model]
            [datomic.api :as d]
            [java-time :as time]))

(println "Bem vindo ao sistema de Cartão de Credito")
(println "")


;(println "Gastos por categoria")
;(l.logic/gastos-por-categoria l.db/cliente1)
;(println "")


;(println "Compras por valor")
;(l.logic/compras-por-valor l.db/cliente1 50.0)
;(println "")

;(println "Compras por estabelecimento")
;(l.logic/compras-por-estabelecimento l.db/cliente1 "ABC")
;(println "")

;(println "Fatura do mes")
;(l.logic/fatura-do-mes (l.logic/compras-realizadas-cliente l.db/cliente1) (month 1)  (year 2021))
;(println "")

;(println "Fatura dos meses por ano atual")
;(l.logic/fatura-dos-meses-por-ano-atual l.db/cliente1)

(def conn (l.db/abra-conexao))

(l.db/cria-schema! conn)

(pprint "Adiciona Cliente")
(def cartao (l.model/novo-cartao 123456789 125N (time/sql-timestamp (time/local-date 2021 10 10)) 20000M))
(def cliente (l.model/novo-cliente "Maria Antonela" "123456789" "maria.antonela@gmail.com" cartao))

(pprint @(l.db/adiciona-cliente conn [cliente]))

(println "Adiciona compra")
(def cartao-id (:cartao/id cartao))
(def curso (l.model/nova-compra  (time/sql-timestamp (time/local-date 2021 10 10)) 100.0M  "Curso wizard" "Educação" [:cartao/id cartao-id]))
(def alimentacao (l.model/nova-compra  (time/sql-timestamp (time/local-date 2021 10 15)) 50.0M  "ABC" "Alimentacao" [:cartao/id cartao-id]))

(pprint @(l.db/adiciona-compras! conn [curso, alimentacao]))

(pprint (l.db/lista-compras (d/db conn)))

;(l.db/apaga-banco)




