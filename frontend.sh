kill $(lsof -t -i:3000); cd frontend/;source $HOME/.nvm/nvm.sh; nvm use node; npm run dev;