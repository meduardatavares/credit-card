
(ns credit_card.db
  (:require [java-time :as time]))

(def cliente1  {:nome "Joao"
               :cpf 12345678
               :email "joao@gmail.com"
               :cartao {:numero 5274639975504476
                        :cvv 564
                        :validade (time/local-date 2029 05 18 )
                        :limite 1000
                        :compras-realizadas [{:id :compra1
                                              :data (time/local-date 2021 01 10 )
                                              :valor 50.0
                                              :estabelecimento "ABC"
                                              :categoria "Alimentação"}
                                             {:id :compra2
                                              :data (time/local-date 2021 02 14 )
                                              :valor 200.0
                                              :estabelecimento "Padaria"
                                              :categoria "Alimentação"}
                                              {:id :compra3
                                                       :data (time/local-date 2021 01 22 )
                                                       :valor 100.0
                                                       :estabelecimento "supermercdo"
                                                       :categoria "Alimentação"}
                                              {:id :compra4
                                                       :data (time/local-date 2021 01 06 )
                                                       :valor 200.0
                                                       :estabelecimento "Curso wizard"
                                                       :categoria "Educação"}
                                             {:id :compra5
                                                       :data (time/local-date 2021 02 10 )
                                                       :valor 200.0
                                                       :estabelecimento "Colegio Meta"
                                                       :categoria "Educação"}
                                             {:id :compra6
                                                       :data (time/local-date 2021 02 10 )
                                                       :valor 600.0
                                                       :estabelecimento "Hospital Santa Casa"
                                                       :categoria "Saude"}
                                             {:id :compra7
                                                       :data (time/local-date 2021 01 7 )
                                                       :valor 200.0
                                                       :estabelecimento "Odonto Clear"
                                                       :categoria "Saude"}] }})




(defn retorna-cartao []
  (:cartao cliente1))

