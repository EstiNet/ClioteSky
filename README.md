# ClioteSky

A reasonably simple, yet secure proxy for protobufs with an easy to implement API (simple RabbitMQ).

## Installation

1. Download the latest stable build for your platform from our [releases page](https://github.com/EstiNet/ClioteSky/releases), or get [bleeding edge](https://gitlab.dolphinbox.net/mirrors/cliotesky/pipelines) builds from our build server.
2. Set the binary as executable if you are on a *nix platform.
```bash
$ chmod +x cliotesky-$PLATFORM-$ARCH
```
3. Run the binary to generate the config file in the same directory. Read the configuration section to learn how to configure the instance.
4. Generate certs for ssl if needed. Here's an example with openssl:
```bash
$ openssl genrsa -out server.key 2048
$ openssl req -new -x509 -sha256 -key server.key -out server.crt -days 3650
```

The install is now complete!
Simply run the binary to start the program.

## Configuration

Initial config.yml file:
```
port = 36000
master_key_location = "./masterkey.key"
cert_file_path = "./server.crt"
key_file_path = "./server.key"
```

| Setting             |   Description                                                   |
|---------------------|-----------------------------------------------------------------|
| port                | The port that the instance should run on.                       |
| master_key_location | Location of the password file that is auto-magically generated. |
| cert_file_path      | Location of the cert file (for ssl).                            |
| key_file_path       | Location of the key file (for ssl).                             |

## How to Build

This project has been built with go1.10+. Make sure that [go](https://golang.org/doc/install) is installed.
<br>
Clone this project into a directory.
```bash
$ git clone https://github.com/EstiNet/ClioteSky.git
```
<br>
Obtain these dependencies:

```bash
$ go get google.golang.org/grpc
$ go get github.com/BurntSushi/toml
```
<br>
Run the buildEverything.sh script to build the binaries and place them in the bin folder.

```bash
$ ./buildEverything.sh
```

## API Specification SECTION WIP

Note: This assumes that the developer has knowledge of GRPC.
<br><br>
Creating a client requires an implementation of [GRPC](https://grpc.io) in the client language. The library abstracts method calls over sockets, allowing for simple API definitions.
<br>
Once you have added GRPC to your project, be sure to generate a [protobuf](https://developers.google.com/protocol-buffers/) spec file and a grpc spec file from the ClioteSky protocol definition (this [file](https://github.com/EstiNet/ClioteSky/blob/master/src/protoc/cliotesky.proto)).
### Specified Types
| Type          | Description |
|---------------|-------------|
| String        | Simple wrapper for a string object.
| Boolean       | Simple wrapper for a boolean object.
| Empty         | Empty variable
| Token         | Contains a string that specifies the user token.
| ClioteMessage | Contains data (bytes), identifier (string), and sender (string).
| ClioteSend    | Contains
| AuthRequest   |
### ClioteSkyService Function Calls
