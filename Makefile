APP=$(shell basename $(shell git remote get-url origin))
REGISTRY=pskaletskyy
VERSION=$(shell git describe --tags --abbrev=0)-$(shell git rev-parse --short HEAD)
TARGETOS=linux #linux darwin windows
TARGETARCH=amd64 #arm64

format:
	gofmt -s -w ./

get:
	go get	

lint:
	golint

test:
	go test -v	

image:
	docker build . -t ${REGISTRY}/${APP}:${VERSION}-${TARGETARCH}	

build: format get
	CGO_ENABLED=0 GOOS=${TARGETOS} GOARCH=${TARGETARCH} go build -ldflags "-X="github.com/pskaletskyy/kbot/cmd.app.Version=${VERSION}

push:
	docker push ${REGISTRY}/${APP}:${VERSION}-${TARGETARCH}

clean:
	rm -rf kbot	