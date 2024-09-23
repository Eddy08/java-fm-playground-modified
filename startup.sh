## Killing of all the previously running processes

kill $(lsof -t -i:55500);
kill $(lsof -t -i:3000); 

## Starting the Backend and Frontend
bash ./backend.sh "$AWS_ACCESS_KEY_ID" "$AWS_SECRET_ACCESS_KEY" & bash ./frontend.sh 