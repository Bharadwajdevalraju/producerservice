(ns connection.connection
  (:import (com.mchange.v2.c3p0 ComboPooledDataSource))
  (:gen-class))

(def db-spec
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//database-1.crwacodap8je.us-east-2.rds.amazonaws.com:3306/test"
   :user "bharadwaj"
   :password "bharadwaj"
   :initial-pool-size 1
   :max-pool-size 10
   })

(defn pool  [spec]
  (let [comboPooledDataSource (doto (ComboPooledDataSource.)
               (.setDriverClass (:classname spec))
               (.setJdbcUrl (str "jdbc:" (:subprotocol spec) ":" (:subname spec)))
               (.setUser (:user spec))
               (.setPassword (:password spec))
               ;; expire excess connections after 30 minutes of inactivity:
               (.setMaxIdleTimeExcessConnections (* 30 60))
               ;; expire connections after 3 hours of inactivity:
               (.setMaxIdleTime (* 3 60 60))
               (.setInitialPoolSize (:initial-pool-size spec))
               (.setMaxPoolSize (:max-pool-size spec)))]
    {:datasource comboPooledDataSource}))

(def db-connection (delay (pool db-spec)))
