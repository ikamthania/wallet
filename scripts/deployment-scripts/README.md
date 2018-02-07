## Deployment scripts
Script for deployment of services in standalone mode

## SBT Tasks
=====
* `projects`: show a list of projects. Should give output similar to this

```
[info]     client
[info]     emailNotificationsApi
[info]     emailNotificationsImpl
[info]   * i
[info]     keeperApi
[info]     keeperImpl
[info]     lagom-internal-meta-project-cassandra
[info]     lagom-internal-meta-project-kafka
[info]     lagom-internal-meta-project-service-locator
[info]     security
[info]     sharedJS
[info]     sharedJVM
[info]     userContentApi
[info]     userContentImpl
[info]     userProfileAndConnectionsApi
[info]     userProfileAndConnectionsImpl
[info]     walletClient
[info]     webGateway

```
* `project webGateway`: selects a project
* `dist`: Create binary distribution of service
* `exit`: exit sbt console
* `unzip web-gateway/target/universal/webgateway-0.3.zip -d /dist`: unzip the content to a directory
* `mv /dist/* /deployment-script/webGateway` move the content to the respective folder in deployment scripts folder

Go to the deployment script root folder

* `./deployAll.sh start` starts all services in standalone mode
* `./deployAll.sh stop` stops all services
* `./deployAll.sh status` gives the running status of all services
* `./deployAll.sh restart` restarts all service

To deploy individual service go to respective folder and do same tasks with deploy.sh script

