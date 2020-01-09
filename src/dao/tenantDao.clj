(ns dao.tenantDao
  (:require [connection.connection :refer :all])
  (:require [clojure.java.jdbc :as sql])
  (:gen-class))

(defn get-statusid-by-tenantid [tenant_id]
  (let [statusid (sql/query @db-connection ["select status_id from queuestatus where tenant_id=?" tenant_id])]
  (:status_id (first statusid))))

(defn get-statusmessage-by-statusid [status_id]
  (let [statusmessage (sql/query @db-connection ["select status_message from status where status_id=?" status_id])]
    (:status_message (first statusmessage))))

(defn get-contactlist-by-tenantid  [tenant_id]
  (let [contact-list (sql/query @db-connection ["select * from contacts where tenant_id=?" tenant_id])]
   contact-list))

(defn get-statusid-by-message [message]
  (let [statusid (sql/query @db-connection ["select status_id from status where status_message=?" message])]
    (:status_id (first statusid))))

(defn update-contact-status [contact]
  (sql/update! @db-connection :contacts {:status_id (get-statusid-by-message "QUEUED")} ["contact_id=?" (:contact_id contact)]))

(defn create-queue-status-and-set-to-queued [tenant_id]
  (sql/insert! @db-connection :queuestatus {:tenant_id tenant_id  :queue_name (str tenant_id ".fifo") :status_id (get-statusid-by-message "QUEUED")}))