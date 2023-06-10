APP=$(shell basename $(shell git remote get-url origin))
REPO_PATH=$(shell git rev-parse --show-toplevel)
REGISTRY=ghcr.io
REPOSITORY=petroskaletskyy
VERSION=v$(shell git describe --tags --abbrev=0)-$(shell git rev-parse --short HEAD)
TARGETOS=linux#linux darwin windows
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
	docker build . -t ${REGISTRY}/${REPOSITORY}/${REPO_PATH}:${VERSION}-${TARGETOS}-${TARGETARCH}	

build: format get
	CGO_ENABLED=0 GOOS=${TARGETOS} GOARCH=${TARGETARCH} go build -ldflags "-X="github.com/pskaletskyy/kbot/cmd.app.Version=${VERSION}

push:
	docker push ${REGISTRY}/${REPOSITORY}/${REPO_PATH}:${VERSION}-${TARGETOS}-${TARGETARCH}

clean:
	rm -rf kbot	
