(ns app.appconstant
  (:gen-class))

(def QUEUESTATUS_QUEUED "QUEUED")
(def QUEUESTATUS_WAITING "WAITING")
(def QUEUESTATUS_DONE "DONE")
(def QUEUESTATUS_RUNNING "RUNNING")
(def RESPONSE_ALREADYQUEUED "can't queue this again")
(def RESPONE_READYFORCONSUMPTION " queue is ready for consumption")


