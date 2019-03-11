if ! screen -list | grep -q "SL"; then
    screen -X -S "SL" stuff "^C"
fi

sleep 15

screen -Sdm "SL" java -jar target/shiskin-library-starter.jar
