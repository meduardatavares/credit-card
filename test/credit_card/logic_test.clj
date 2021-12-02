(ns credit_card.logic-test
  (:require [clojure.test :refer :all]
            [credit_card.logic :refer :all]
            [credit_card.db :refer :all]
            [java-time :as time]))
(def mock1  {:nome "mateus"
             :cpf 12345678
             :email "joao@gmail.com"
             :cartao {:numero 5274639975504476
                      :cvv 564
                      :validade (time/local-date 2029 05 18 )
                      :limite 1000
                      :compras-realizadas [{:id :compra1
                                            :data "01/01/2021"
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
                                            :data "10/09/2021"
                                            :valor 200.0
                                            :estabelecimento "Curso wizard"
                                            :categoria "Alimentação"}
                                           {:id :compra5
                                            :data (time/local-date 2021 02 10 )
                                            :valor 200.0
                                            :estabelecimento "Colegio Meta"
                                            :categoria "Alimentação"}
                                           {:id :compra6
                                            :data (time/local-date 2021 02 10 )
                                            :valor 600.0
                                            :estabelecimento "Hospital Santa Casa"
                                            :categoria "Alimentação"}
                                           {:id :compra7
                                            :data (time/local-date 2021 01 7 )
                                            :valor 200.0
                                            :estabelecimento "Odonto Clear"
                                            :categoria "Alimentação"}
                                           {:id :compra8
                                            :data "10/10/2021"
                                            :valor 250.0
                                            :estabelecimento "Curso wizard"
                                            :categoria "Alimentação"}] }})
(def mock2  {:nome "julia"
             :cpf 12345678
             :email "joao@gmail.com"
             :cartao {:numero 5274639975504476
                      :cvv 564
                      :validade (time/local-date 2029 05 18 )
                      :limite 1000
                      :compras-realizadas [] }})
(def mock3  {:nome "vanessa"
             :cpf 12345678
             :email "joao@gmail.com"
             :cartao {:numero 5274639975504476
                      :cvv 564
                      :validade (time/local-date 2029 05 18 )
                      :limite 1000
                      :compras-realizadas [{:id :compra1,
                                            :data "01/01/2021",
                                            :valor 50.0,
                                            :estabelecimento "ABC",
                                            :categoria "Alimentação"}] }})

(deftest  compras-por-estabelecimento-test
  ( testing "compras por estabelecimento"
    (is (= [] (compras-por-estabelecimento cliente1 "Colegio")) )
    (is (=  [{:id :compra1, :data (time/local-date 2021 01 01 ), :valor 50.0, :estabelecimento "ABC", :categoria "Alimentação"}]  (compras-por-estabelecimento cliente1 "ABC")) )
    (is (=  [ {:id :compra4
               :data (time/local-date 2021 01 22)
               :valor 200.0
               :estabelecimento "Curso wizard"
               :categoria "Educação"}
             {:id :compra8
              :data (time/local-date 2021 10 10 )
              :valor 250.0
              :estabelecimento "Curso wizard"
              :categoria "Educação"}
            ]  (compras-por-estabelecimento cliente1 "Curso wizard")) )
    ))
(deftest gastos-por-categoria-test
  (testing "Tem gastos na categoria"
    (is (= [{"Alimentação" 350.0} {"Educação" 650.0} {"Saude" 800.0}] (gastos-por-categoria cliente1)))
    (is (= [{"Alimentação" 1800.0} ] (gastos-por-categoria mock1)))
    (is (= [] (gastos-por-categoria mock2)))
    ))
(deftest compras-realizadas-cliente-test
  (testing "Compras realizadas pelo cliente"
    (is (= [{:id :compra1,
             :data "01/01/2021",
             :valor 50.0,
             :estabelecimento "ABC",
             :categoria "Alimentação"}] (compras-realizadas-cliente mock3)))
    (is (= [] (compras-realizadas-cliente mock2)))
    (is (= [{:id :compra1
             :data "01/01/2021"
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
             :data "10/09/2021"
             :valor 200.0
             :estabelecimento "Curso wizard"
             :categoria "Alimentação"}
            {:id :compra5
             :data (time/local-date 2021 02 10 )
             :valor 200.0
             :estabelecimento "Colegio Meta"
             :categoria "Alimentação"}
            {:id :compra6
             :data (time/local-date 2021 02 10 )
             :valor 600.0
             :estabelecimento "Hospital Santa Casa"
             :categoria "Alimentação"}
            {:id :compra7
             :data (time/local-date 2021 01 7 )
             :valor 200.0
             :estabelecimento "Odonto Clear"
             :categoria "Alimentação"}
            {:id :compra8
             :data "10/10/2021"
             :valor 250.0
             :estabelecimento "Curso wizard"
             :categoria "Alimentação"}] (compras-realizadas-cliente mock1)))
    ))
(deftest compras-por-valor-test
  (testing "filtrar compras pelo valor"
    (is (= [{:id :compra6
             :data (time/local-date 2021 02 10 )
             :valor 600.0
             :estabelecimento "Hospital Santa Casa"
             :categoria "Alimentação"}
            {:id :compra8
             :data "10/10/2021"
             :valor 250.0
             :estabelecimento "Curso wizard"
             :categoria "Alimentação"}
            ] (compras-por-valor mock1 250 )))
    (is (= [] (compras-por-valor mock1 1000 )))
    (is (= [] (compras-por-valor mock1 -600 )))
    ))



