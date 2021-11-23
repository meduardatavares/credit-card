(ns credit_card.db)
(use 'java-time.util)

(def cliente  {:nome "Joao"
               :cpf 12345678
               :email "joao@gmail.com"
               :cartao {:numero 5274639975504476
                        :cvv 564
                        :validade "23/10/2023"
                        :limite 1000
                        :compras-realizadas { :compra1 {:id :compra1
                                                        :data "23/11/2021"
                                                        :valor 50.0
                                                        :estabelecimento "ABC"
                                                        :categoria "Alimentação"}}}})


(println cliente)

(defn banco []
  [cliente])
