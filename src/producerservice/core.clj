(ns producerservice.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [services.tenantServices :refer :all])
  (:gen-class))

(defroutes app-routes
  (GET "/tenant/:tenant_id" [tenant_id] (enqueue-tenant tenant_id)))

(defn -main
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3032"))]
  ; Run the server with Ring.defaults middleware
  (server/run-server (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)) {:port port})
  (println (str "Running webserver at http://localhost:" port "/tenant/"))))

