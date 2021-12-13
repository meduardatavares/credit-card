(ns credit_card.model)

(defn uuid [] (java.util.UUID/randomUUID))

(defn nova-compra
  ([data valor estabelecimento categoria cartao]
   (nova-compra (uuid) data valor estabelecimento categoria cartao))
  ([uuid data valor estabelecimento categoria cartao]
   {:compra/id              uuid
    :compra/data            data
    :compra/valor           valor
    :compra/estabelecimento estabelecimento
    :compra/categoria       categoria
    :compra/cartao          cartao}))


(defn novo-cliente
  ([nome cpf email cartao]
   (novo-cliente (uuid) nome cpf email cartao))
  ([uuid nome cpf email cartao]
   {:cliente/id     uuid
    :cliente/nome   nome
    :cliente/cpf    cpf
    :cliente/email  email
    :cliente/cartao cartao}))

(defn novo-cartao
  ([numero cvv validade limite]
   (novo-cartao (uuid) numero cvv validade limite))
  ([uuid numero cvv validade limite]
   {:cartao/id       uuid
    :cartao/numero   numero
    :cartao/cvv      cvv
    :cartao/limite   limite
    :cartao/validade validade}))