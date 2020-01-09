(ns queue.queue
  (:gen-class))
(def contact-queue (atom []))

(defn enqueue-contact-info [contact]
                 (swap! contact-queue conj contact))
