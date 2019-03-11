fuser -n tcp -k 8086
sleep 4
java -jar target/shiskin-library-starter.jar &
