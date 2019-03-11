if screen -list | grep SL; then
    screen -X -S SL quit
    echo 'found old screen'
fi

sleep 1

screen -c multiscreen.conf -Sdm SL java -jar target/shiskin-library-starter.jar --logging.path=/var/lib/jenkins/workspace/shishkin-stage-deploy/target/latest.log
screen -ls
