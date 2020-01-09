(ns services.tenantServices
  (:require  [clojure.string :as str]
             [clojure.data.json :as json]
             [dao.tenantDao :refer :all]
             [queue.queue :refer :all]
             [app.appconstant :refer :all])
  (:gen-class))

(defn notify-availability [tenant_id]
  (def message (hash-map :message (str tenant_id RESPONE_READYFORCONSUMPTION)))
  {:status 200
   :header {"Content-Type","text/json"}
   :body (str (json/write-str message))})


(defn enqueue-contacts [contactslist]
  (doseq [contact contactslist]
    (enqueue-contact-info contact)
    (update-contact-status contact)))

(defn enqueue-tenant [tenant_id]
  (def contact-list-of-tenant (get-contactlist-by-tenantid tenant_id))
  (let [status_id (get-statusid-by-tenantid tenant_id)]
        (if (nil? status_id)
          (do (enqueue-contacts contact-list-of-tenant)
              (create-queue-status-and-set-to-queued tenant_id)
              (def message (notify-availability tenant_id)))
          (do (let [statusmessage (get-statusmessage-by-statusid status_id)]
                   (cond  (= statusmessage QUEUESTATUS_DONE)    ((do (enqueue-contacts contact-list-of-tenant)
                                                           (create-queue-status-and-set-to-queued tenant_id)
                                                           (def message (notify-availability tenant_id))))
                          (= statusmessage QUEUESTATUS_RUNNING) (enqueue-contacts contact-list-of-tenant)
                          (= statusmessage QUEUESTATUS_QUEUED)  (do  (def message (hash-map :message RESPONSE_ALREADYQUEUED))
                                                           {:status 200
                                                            :header {"Content-Type","text/json"}
                                                            :body (str (json/write-str message))})))))))


