(ns credit_card.db
  (:require [java-time :as time])
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/credit-card")

(defn abra-conexao []
  (d/create-database db-uri)
  (d/connect db-uri))


(defn apaga-banco []
  (d/delete-database db-uri))

(def schema [
             ;Cliente
             {:db/ident       :cliente/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}

              {:db/ident :cliente/nome
               :db/valueType :db.type/string
               :db/cardinality :db.cardinality/one
               :db/doc "O nome de um cliente"}

             {:db/ident :cliente/cpf
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc "O cpf do cliente"}

             {:db/ident :cliente/email
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc "O email de um cliente"}

             {:db/ident :cliente/cartao
              :db/valueType :db.type/ref
              :db/cardinality :db.cardinality/one}


             ;Cartao
             {:db/ident       :cartao/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}

             {:db/ident :cartao/numero
              :db/valueType :db.type/long
              :db/cardinality :db.cardinality/one
              :db/doc "O numero do cartao"}

             {:db/ident :cartao/cvv
              :db/valueType :db.type/bigint
              :db/cardinality :db.cardinality/one
              :db/doc "O codigo verificador do cartao"}

             {:db/ident :cartao/validade
              :db/valueType :db.type/instant
              :db/cardinality :db.cardinality/one
              :db/doc "A data de validade do cartao"}

             {:db/ident :cartao/limite
              :db/valueType :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc "A data de validade do cartao"}

             ;Compra
             {:db/ident       :compra/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}

             {:db/ident :compra/data
              :db/valueType :db.type/instant
              :db/cardinality :db.cardinality/one
              :db/doc "A data da compra"}

             {:db/ident :compra/valor
              :db/valueType :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc "O valor da compra"}

             {:db/ident :compra/estabelecimento
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc "Estabelecimento da compra"}

             {:db/ident :compra/categoria
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc "Categoria da compra"}

             {:db/ident :compra/cartao
              :db/valueType :db.type/ref
              :db/cardinality :db.cardinality/one}])


(defn cria-schema! [conn]
  (d/transact conn schema))


(defn adiciona-compras! [conn compra]
  (d/transact conn compra))

(defn lista-compras  [db]
  (d/q '[:find (pull ?compra [*])
         :where [?compra :compra/id]] db))


(defn adiciona-cliente
  [conn cliente]
  (d/transact conn cliente))



(def cliente1 {:nome   "Joao"
               :cpf    12345678
               :email  "joao@gmail.com"
               :cartao {:numero             5274639975504476
                        :cvv                564
                        :validade           (time/local-date 2029 05 18)
                        :limite             1000
                        :compras-realizadas [{:id              :compra1
                                              :data            (time/local-date 2021 01 01)
                                              :valor           50.0
                                              :estabelecimento "ABC"
                                              :categoria       "Alimentação"}
                                             {:id              :compra2
                                              :data            (time/local-date 2021 02 14)
                                              :valor           200.0
                                              :estabelecimento "Padaria"
                                              :categoria       "Alimentação"}
                                             {:id              :compra3
                                              :data            (time/local-date 2021 01 22)
                                              :valor           100.0
                                              :estabelecimento "supermercdo"
                                              :categoria       "Alimentação"}
                                             {:id              :compra4
                                              :data            (time/local-date 2021 01 22)
                                              :valor           200.0
                                              :estabelecimento "Curso wizard"
                                              :categoria       "Educação"}
                                             {:id              :compra5
                                              :data            (time/local-date 2021 02 10)
                                              :valor           200.0
                                              :estabelecimento "Colegio Meta"
                                              :categoria       "Educação"}
                                             {:id              :compra6
                                              :data            (time/local-date 2021 02 10)
                                              :valor           600.0
                                              :estabelecimento "Hospital Santa Casa"
                                              :categoria       "Saude"}
                                             {:id              :compra7
                                              :data            (time/local-date 2021 01 7)
                                              :valor           200.0
                                              :estabelecimento "Odonto Clear"
                                              :categoria       "Saude"}
                                             {:id              :compra8
                                              :data            (time/local-date 2021 10 10)
                                              :valor           250.0
                                              :estabelecimento "Curso wizard"
                                              :categoria       "Educação"}]}})


(def compra {:id              :compra9
             :data            (time/local-date 2021 10 10)
             :valor           100.0
             :estabelecimento "Curso wizard"
             :categoria       "Educação"})
(def compra {:id              :compra10
             :data            (time/local-date 2021 10 10)
             :valor           100.0
             :estabelecimento "Curso wizard"
             :categoria       "Educação"})

(defn retorna-cartao []
  (:cartao cliente1))

