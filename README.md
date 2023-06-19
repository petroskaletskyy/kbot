# kbot
devops application from scratch
 
# Telegram bot t.me/petroskaletskyy_bot 
 
Kbot is a simple chatbot that responds to user messages with customized replies.

## Usage
To use the bot, simply send a message to t.me/petroskaletskyy_bot on Telegram.

The following commands are currently supported:

/start - start using the bot

## Installation
To run the bot locally, follow these steps:

1. Clone the repository: 
```
git clone https://github.com/petroskaletskyy/kbot.git
```
2. Start a chat with [@BotFather](t.me/BotFather) and create a new bot 
5. Type down `/newbot`, then write a name of your new bot, ex. kbot
6. Then type down a username for your bot ex. `petroskaletskyy_bot`.
8. Set the TELE_TOKEN environment variable to the API token
```
export TELE_TOKEN=<token>
```
10. Install the necessary dependencies: 
```
go get
go build -ldflags "-X="github.com/petroskaletskyy/kbot/cmd.appVersion=v1.0.0
```
12. Start the bot: 
```
./kbot start
```

## Examples
To start using the bot, simply send the /start command
To receive a customized reply from the bot with your message, send the /start <message> command, replacing <message> with next messages - Hi, Hello, Bye. For example: /start Hi

We hope you enjoy using our Telegram bot!
 

## CICD Workflow
