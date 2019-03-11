if ! screen -list | grep -q "SL"; then
    screen -X -S "SL" stuff "^C"
fi

sleep 10

screen -Sdm "SL" java -jar target/shiskin-library-starter.jar
screen -S "SL" -X multiuser on
screen -S "SL" -X acladd root
screen -ls
