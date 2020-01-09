FROM producerservice:2.0
SHELL ["/bin/bash","-c"]
CMD ["bash","inotify.sh"]