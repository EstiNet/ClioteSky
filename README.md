# ClioteSky
EstiNet's TCP communication server in the cloud! Java API functions to relay signals on send.
## Terms
Cliote: Connection
ClioteSky: Server


# Protocol
The ClioteSky protocol consists of 5 functions. These are commands that are sent to ClioteSky (encrypted with RSA X509) and arguments are parsed between spaces.

## Client -> Server
### create
The create function initializes a Cliote with the ClioteSky installation. This must be sent before any other functions.
Usage:
create [Cliote Name] [Category] [Password]

### change
The change function changes a Cliote's setting. The IP of the client will be changed as well as the category. The name cannot be changed.
Usage:
change [Cliote Name] [Category] [Password]

### send
The send function sends a string to a category of Cliotes or a single Cliote.
Usage:
send [Cliote Name or Category to be sent to] [String]

### alive
Common "keep-alive" signals sent to the client must be responded with an "alive" function. If the message is not responded within 5 seconds, the Cliote will be marked as offline.
Usage:
alive

### hello
When a client initialized with the server and is online, this function must be sent before anything else to mark the Cliote as "online".
Usage:
hello [Cliote Name] [Password]

## Server -> Client
### alive
This function is sent to the client every 10 seconds to ensure that the client is "online". The client must respond back with an "alive" function within 5 seconds, or the Cliote will be marked as offline.

### message
This function is sent to a client or a group of clients after a client sends a signal to the Category or specific Cliote.
Format:
message [Category Recieved From] [Cliote Name Recieved From] [String]

### error
This function is sent to a client if there is an error with a function sent to the ClioteSky installation. See error codes at the bottom.
Format:
error [Code]

# Error Codes

## 1xx
Errors that are associated with improper syntax.

### 100
Sent back to client when the function is not recognized.

### 101
Sent back to client if there are too many, or not enough arguments.

## 2xx
Errors that are associated with variables sent to ClioteSky.

### 200
Sent back to client if the category is not recognized.

### 201
Sent back to client if the Cliote Name is not recognized.

### 202
Sent back to client if both the category and the Cliote Name isn't recognized.

## 3xx
Errors that are associated with Cliote creation.

### 300
Sent back to client if the category is not recognized.

### 301
Sent back to client if the Cliote name is already used.

## 9xx
Other errors.

### 900
Sent back to client when the "password" is incorrect.

### 901
Sent back to client when the client tries to execute a query before registering.

# Variables
all: Variable to be used as a category which includes all Cliodes, online and offline.
