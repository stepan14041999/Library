if ! screen -list | grep -q "SL"; then
    screen -X -S "SL" stuff "^C"
fi

sleep 3

screen -Sdm "SL" java -jar target/shiskin-library-starter.jar
screen -ls
